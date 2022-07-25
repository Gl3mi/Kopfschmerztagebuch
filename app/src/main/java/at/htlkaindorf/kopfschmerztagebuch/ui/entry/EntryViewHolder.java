package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import at.htlkaindorf.kopfschmerztagebuch.activity.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;

public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
    private final TextView name;
    private final TextView date;
    private final ImageView medic;
    private final EntryAdapter entryAdapter;
    private final GestureDetectorCompat mGestureDetector;
    private Entry entry;
    private final Session session;

    public EntryViewHolder(@NonNull View itemView, TextView name, TextView date, ImageView medic,
                           EntryAdapter entryAdapter, Context context) {
        super(itemView);
        this.name = name;
        this.date = date;
        this.medic = medic;
        this.entryAdapter = entryAdapter;

        MyGestureListener mgl = new MyGestureListener();
        mGestureDetector = new GestureDetectorCompat(context, mgl);

        session = new Session(context);

        itemView.setOnTouchListener(this);
        itemView.setOnClickListener(this);
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDate() {
        return date;
    }

    public ImageView getMedic() {
        return medic;
    }

    @Override
    public void onClick(@NonNull View v) {
        Intent intent = new Intent(v.getContext(), EntryActivity.class);

        intent.putExtra("kindOfPain", entry.getKindOfPain());
        intent.putExtra("painArea", entry.getPainArea());
        intent.putExtra("from", entry.getFrom());
        intent.putExtra("to", entry.getTo());
        intent.putExtra("date", entry.getDate());
        intent.putExtra("medics", entry.getMedics());
        intent.putExtra("intensity", entry.getIntensity() + "");
        intent.putExtra("symptoms", entry.getAttendantSymptoms());
        intent.putExtra("comment", entry.getComment());

        v.getContext().startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaXAbs = Math.abs(deltaX);
            Gson gson = new Gson();

            int MIN_DIST = 1;
            int MAX_DIST = 1000;
            if (deltaX > MIN_DIST && deltaXAbs < MAX_DIST) {
                entryAdapter.getEntries().remove(getAdapterPosition());
                session.getEditor().clear();

                String json = gson.toJson(entryAdapter.getEntries());
                session.getEditor().putString("data", json).apply();

                entryAdapter.notifyDataSetChanged();
            }
            return false;
        }
    }
}
