package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.htlkaindorf.kopfschmerztagebuch.activity.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryFragment extends Fragment {
    private TextView textView;
    private SharedViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private static final String MYPREFS = "myprefs";
    private final Gson gson = new Gson();
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entry, container, false);



        adapter = new EntryAdapter(container.getContext());

        recyclerView = v.findViewById(R.id.entryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedPreferences = requireContext().getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();


        //textView = v.findViewById(R.id.text_entry);
        ImageButton bt = v.findViewById(R.id.add);

        bt.setOnClickListener(view -> {
            relativeLayout = (RelativeLayout) v.findViewById(R.id.entry_item);
            relativeLayout.setBackgroundResource(R.drawable.ic_launcher_background);

            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume\n---------------------------------\n");

        System.out.println(sharedPreferences.getString("testi", " "));

        System.out.println(viewModel.getLiveData());

        if (!sharedPreferences.getString("testi", " ").equals(" ")) {
            Entry json = gson.fromJson(sharedPreferences.getString("testi", ""), Entry.class);
            //textView.setText(json.toString());
            List<Entry> test = new ArrayList<>();

            test.add(new Entry("Sinus", "21.07", true));
            test.add(new Entry("Cluster", "22.07", false));
            test.add(new Entry("Spannungs", "23.09", false));
            test.add(new Entry("dsdsdd", "20.05", true));

            adapter.setEntries(test);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}