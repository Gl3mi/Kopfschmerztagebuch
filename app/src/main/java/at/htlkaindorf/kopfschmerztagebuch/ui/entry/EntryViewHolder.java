package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener{
    private TextView name;
    private TextView date;
    private ImageView medic;
    private EntryAdapter entryAdapter;
    private GestureDetectorCompat gestureDetectorCompat;

    public EntryViewHolder(@NonNull View itemView, TextView name, TextView date, ImageView medic,
                           EntryAdapter entryAdapter, Context context) {
        super(itemView);
        this.name = name;
        this.date = date;
        this.medic = medic;
        this.entryAdapter = entryAdapter;

        MyGestureListener mgl = new MyGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(context, mgl);
        itemView.setOnTouchListener(this);
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
    public void onClick(View v) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int MIN_DIST = 50;
        private final int MAX_DIST = 1000;

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaXAbs = Math.abs(deltaX);

            if (deltaXAbs > MIN_DIST && deltaXAbs < MAX_DIST) {
                if (deltaX > 0) {
                    entryAdapter.getEntries().remove(getAdapterPosition());
                    entryAdapter.notifyDataSetChanged();
                }
            }
            return false;
        }
    }
}
