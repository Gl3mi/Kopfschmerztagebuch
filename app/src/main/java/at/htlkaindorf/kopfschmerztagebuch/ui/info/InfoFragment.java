package at.htlkaindorf.kopfschmerztagebuch.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        TextView migraineT = binding.migraineTf;
        TextView tensionT = binding.tensionTf;
        TextView clusterT = binding.clusterTf;
        TextView sinusT = binding.sinusTf;
        TextView infoT = binding.moreInfoLinks;
        TextView legendT = binding.legendTf;

        Button legendBtn = binding.legendHeader;
        Button migraineBtn = binding.migraineHeader;
        Button tensionBtn = binding.tensionHeader;
        Button clusterBtn = binding.clusterHeader;
        Button sinusBtn = binding.sinusHeader;
        Button infoBtn = binding.moreInfoHeader;


        class Info implements View.OnClickListener {
            private final TextView text;

            public Info(TextView text) {
                this.text = text;
            }

            @Override
            public void onClick(View v) {
                if (text.getVisibility() != View.VISIBLE) {
                    text.setVisibility(View.VISIBLE);
                } else {
                    text.setVisibility(View.GONE);
                }
            }
        }

        migraineBtn.setOnClickListener(new Info(migraineT));
        tensionBtn.setOnClickListener(new Info(tensionT));
        clusterBtn.setOnClickListener(new Info(clusterT));
        sinusBtn.setOnClickListener(new Info(sinusT));
        infoBtn.setOnClickListener(new Info(infoT));
        legendBtn.setOnClickListener(new Info(legendT));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
