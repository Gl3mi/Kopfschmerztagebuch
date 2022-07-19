package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.activity.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;

public class EntryFragment extends Fragment {
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private Session session;
    private RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entry, container, false);

        session = new Session(requireContext());

        adapter = new EntryAdapter(container.getContext());
        adapter.setEntries(session.getEntries("data"));

        recyclerView = v.findViewById(R.id.entryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        ImageButton bt = v.findViewById(R.id.add);

        bt.setOnClickListener(view -> {
            //relativeLayout = v.findViewById(R.id.entry_item);

            //relativeLayout.setBackgroundResource(R.drawable.intensity4);

            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onResume() {
        super.onResume();

        adapter.setEntries(session.getEntries("data"));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}