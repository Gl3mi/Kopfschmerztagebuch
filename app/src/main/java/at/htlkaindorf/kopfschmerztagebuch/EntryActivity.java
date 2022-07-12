package at.htlkaindorf.kopfschmerztagebuch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;
import at.htlkaindorf.kopfschmerztagebuch.ui.entry.EntryFragment;

public class EntryActivity extends AppCompatActivity {
    EditText t1;
    EditText t2;
    Button bt;
    TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        t1 = findViewById(R.id.bereich);
        t2 = findViewById(R.id.bereich2);
        bt = findViewById(R.id.btn);
        textView = findViewById(R.id.test);

        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        bt.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            EntryFragment entryFragment = new EntryFragment();

            viewModel.setEntry(new Entry(String.valueOf(t1.getText()), String.valueOf(t2.getText())));
            textView.setText(String.valueOf(t1.getText()) + String.valueOf(t2.getText()));

            bundle.putString("test", String.valueOf(t1.getText()) + String.valueOf(t2.getText()));
            entryFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();

            super.onBackPressed();
        });
    }
}
