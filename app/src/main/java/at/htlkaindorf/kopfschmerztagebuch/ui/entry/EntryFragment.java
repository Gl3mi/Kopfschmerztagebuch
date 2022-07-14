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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.kopfschmerztagebuch.activity.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryFragment extends Fragment {
    private SharedViewModel viewModel;
    private final Gson gson = new Gson();
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private RelativeLayout relativeLayout;
    private List<Entry> entries = new ArrayList<>();
    private Session session;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entry, container, false);

        adapter = new EntryAdapter(container.getContext());

        recyclerView = v.findViewById(R.id.entryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        session = new Session(requireContext());

        ImageButton bt = v.findViewById(R.id.add);

        bt.setOnClickListener(view -> {
            //relativeLayout = (RelativeLayout) v.findViewById(R.id.entry_item);
            //relativeLayout.setBackgroundResource(R.drawable.ic_launcher_background);

            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onResume() {
        super.onResume();

        System.out.println(viewModel.getLiveData());

        adapter.setEntries(session.getEntries("data"));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}