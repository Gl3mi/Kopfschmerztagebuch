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

import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;

    private TextView migraineT;
    private TextView tensionT;
    private TextView clusterT;
    private TextView sinusT;
    private TextView infoT;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        migraineT = binding.migraineTf;
        tensionT = binding.tensionTf;
        clusterT = binding.clusterTf;
        sinusT = binding.sinusTf;
        infoT = binding.moreInfoLinks;

        Button migraineBtn = binding.migraineHeader;
        migraineBtn.setOnClickListener(view -> {
            if (migraineT.getVisibility() != View.VISIBLE){
                migraineT.setVisibility(View.VISIBLE);
            }else{
                migraineT.setVisibility(View.GONE);
            }
        });

        Button tensionBtn = binding.tensionHeader;
        tensionBtn.setOnClickListener(view -> {
            if (tensionT.getVisibility() != View.VISIBLE){
                tensionT.setVisibility(View.VISIBLE);
            }else{
                tensionT.setVisibility(View.GONE);
            }
        });

        Button clusterBtn = binding.clusterHeader;
        clusterBtn.setOnClickListener(view -> {
            if (clusterT.getVisibility() != View.VISIBLE){
                clusterT.setVisibility(View.VISIBLE);
            }else{
                clusterT.setVisibility(View.GONE);
            }
        });

        Button sinusBtn = binding.sinusHeader;
        sinusBtn.setOnClickListener(view -> {
            if (sinusT.getVisibility() != View.VISIBLE){
                sinusT.setVisibility(View.VISIBLE);
            }else{
                sinusT.setVisibility(View.GONE);
            }
        });

        Button infoBtn = binding.moreInfoHeader;
        infoBtn.setOnClickListener(view -> {
            if (infoT.getVisibility() != View.VISIBLE){
                infoT.setVisibility(View.VISIBLE);
            }else{
                infoT.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
