package at.htlkaindorf.kopfschmerztagebuch.ui.info;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import at.htlkaindorf.kopfschmerztagebuch.R;
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

        legendT.setText(Html.fromHtml(getResources().getString(R.string.legend_html),
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));
        migraineT.setText(Html.fromHtml(getResources().getString(R.string.migraine_html),
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));
        tensionT.setText(Html.fromHtml(getResources().getString(R.string.tension_html),
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));
        clusterT.setText(Html.fromHtml(getResources().getString(R.string.cluster_html),
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));
        sinusT.setText(Html.fromHtml(getResources().getString(R.string.sinus_html),
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));

        Button legendBtn = binding.legendHeader;
        Button migraineBtn = binding.migraineHeader;
        Button tensionBtn = binding.tensionHeader;
        Button clusterBtn = binding.clusterHeader;
        Button sinusBtn = binding.sinusHeader;
        Button infoBtn = binding.moreInfoHeader;

        ImageView sinusImg = binding.imgSinus;
        ImageView tensionImg = binding.imgTension;
        ImageView clusterImg = binding.imgCluster;
        ImageView migraineImg = binding.imgMigraine;


        class Info implements View.OnClickListener {
            private final TextView text;
            private final ImageView img;

            public Info(TextView text, ImageView img) {
                this.text = text;
                this.img = img;
            }

            @Override
            public void onClick(View v) {
                try {
                    if (text.getVisibility() != View.VISIBLE) {
                        text.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    } else {
                        text.setVisibility(View.GONE);
                        img.setVisibility(View.GONE);
                    }
                } catch (NullPointerException n) {
                    n.printStackTrace();
                }
            }
        }

        migraineBtn.setOnClickListener(new Info(migraineT, migraineImg));
        tensionBtn.setOnClickListener(new Info(tensionT, tensionImg));
        clusterBtn.setOnClickListener(new Info(clusterT, clusterImg));
        sinusBtn.setOnClickListener(new Info(sinusT, sinusImg));
        infoBtn.setOnClickListener(new Info(infoT, null));
        legendBtn.setOnClickListener(new Info(legendT, null));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
