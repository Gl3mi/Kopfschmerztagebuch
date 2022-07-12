package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import at.htlkaindorf.kopfschmerztagebuch.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentEntryBinding;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryFragment extends Fragment {
     TextView textView;
    SharedViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        textView = v.findViewById(R.id.text_entry);
        Button bt = v.findViewById(R.id.add);

        if (getArguments() != null) {
            System.out.println(getArguments().getString("test"));
        }else{
            System.out.println("null");
        }

        bt.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        viewModel.getEntry().observe(getViewLifecycleOwner(), entries -> {
            textView.setText(entries.toString());
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume\n---------------------------------\n");
        if (getArguments() != null) {
            System.out.println(getArguments().getString("test"));
        }else{
            System.out.println("null");
        }

        viewModel.getEntry().observe(getViewLifecycleOwner(), entries -> textView.setText(entries.toString()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}