package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class EntryAdapter extends RecyclerView.Adapter<EntryViewHolder> implements View.OnTouchListener {
    private List<Entry> entries = new ArrayList<>();
    private final Context context;
    private GestureDetectorCompat gestureDetectorCompat;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy", Locale.GERMAN);

    public EntryAdapter(Context context) {
        this.context = context;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        TextView name = view.findViewById(R.id.name);
        TextView date = view.findViewById(R.id.date);
        ImageView medic = view.findViewById(R.id.medic);
        EntryViewHolder entryViewHolder = new EntryViewHolder(view, name, date, medic, this, context);

        MyGestureListener mgl = new MyGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(context, mgl);
        view.setOnTouchListener(this);
        return entryViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entries.get(position);
        LocalDate date = LocalDate.parse(entry.getDate(), formatter);
        holder.getName().setText(entry.getKindOfPain());
        holder.getDate().setText(date.getDayOfMonth() + "." + date.getMonthValue());

        if (entry.getCheckMedic()) {
            Picasso.with(context)
                    .load(R.mipmap.ic_full_pill)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.getMedic());
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_empty_pill)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.getMedic());
        }
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int MIN_DIST = 50;
        private final int MAX_DIST = 1000;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaXAbs = Math.abs(deltaX);
            System.out.println("fsfsfsf");
            if (deltaXAbs > MIN_DIST && deltaXAbs < MAX_DIST) {
                if (deltaX > 0) {
                    System.out.println("fsfsfsf");
                }
            }
            System.out.println("fsfsfsf");
            return false;
        }
    }
}
