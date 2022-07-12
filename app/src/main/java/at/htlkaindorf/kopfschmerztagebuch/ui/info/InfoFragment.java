package at.htlkaindorf.kopfschmerztagebuch.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;
    private Button migraineBtn;
    private Button tensionBtn;
    private Button clusterBtn;
    private Button sinusBtn;
    private Button infoBtn;

    private TextView migraineT;
    private TextView tensionT;
    private TextView clusterT;
    private TextView sinusT;
    private TextView infoT;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfoViewModel infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);
        binding = FragmentInfoBinding.inflate(inflater, container, false);

        migraineBtn = binding.migraineHeader;
        migraineBtn.setOnClickListener(view -> {
            if (migraineT.getVisibility() != View.VISIBLE){
                migraineT.setVisibility(View.VISIBLE);
            }else{
                migraineT.setVisibility(View.GONE);
            }
        });

        tensionBtn = binding.tensionHeader;
        tensionBtn.setOnClickListener(view -> {
            if (tensionT.getVisibility() != View.VISIBLE){
                tensionT.setVisibility(View.VISIBLE);
            }else{
                tensionT.setVisibility(View.GONE);
            }
        });

        clusterBtn = binding.clusterHeader;
        clusterBtn.setOnClickListener(view -> {
            if (clusterT.getVisibility() != View.VISIBLE){
                clusterT.setVisibility(View.VISIBLE);
            }else{
                clusterT.setVisibility(View.GONE);
            }
        });

        sinusBtn = binding.sinusHeader;
        sinusBtn.setOnClickListener(view -> {
            if (sinusT.getVisibility() != View.VISIBLE){
                sinusT.setVisibility(View.VISIBLE);
            }else{
                sinusT.setVisibility(View.GONE);
            }
        });

        infoBtn = binding.moreInfoHeader;
        infoBtn.setOnClickListener(view -> {
            if (infoT.getVisibility() != View.VISIBLE){
                infoT.setVisibility(View.VISIBLE);
            }else{
                infoT.setVisibility(View.GONE);
            }
        });

        migraineT = binding.migraineTf;
        tensionT = binding.tensionTf;
        clusterT = binding.clusterTf;
        sinusT = binding.sinusTf;
        infoT = binding.moreInfoLinks;

        View root = binding.getRoot();

        //final TextView textView = binding.textView2;
        //infoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
