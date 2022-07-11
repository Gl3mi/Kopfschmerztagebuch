package at.htlkaindorf.kopfschmerztagebuch.ui.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import at.htlkaindorf.kopfschmerztagebuch.EntryActivity;
import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentEntryBinding;

public class EntryFragment extends Fragment {

    private FragmentEntryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EntryViewModel entryViewModel =
                new ViewModelProvider(this).get(EntryViewModel.class);

        binding = FragmentEntryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEntry;
        entryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button bt = binding.add;

        bt.setOnClickListener(view -> {
            Intent intent = new Intent(container.getContext(), EntryActivity.class);
            startActivity(intent);
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}