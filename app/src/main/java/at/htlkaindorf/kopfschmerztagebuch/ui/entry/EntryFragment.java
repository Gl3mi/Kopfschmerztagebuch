package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import at.htlkaindorf.kopfschmerztagebuch.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryFragment extends Fragment {
    private TextView textView;
    private SharedViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private static final String MYPREFS = "myprefs";
    private final Gson gson = new Gson();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        sharedPreferences = requireContext().getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear().apply();

        textView = v.findViewById(R.id.text_entry);
        Button bt = v.findViewById(R.id.add);

        bt.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume\n---------------------------------\n");

        System.out.println(sharedPreferences.getString("testi", " "));

        System.out.println(viewModel.getLiveData());

        if (!sharedPreferences.getString("testi", " ").equals(" ")) {
            Entry json = gson.fromJson(sharedPreferences.getString("testi", ""), Entry.class);
            textView.setText(json.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}