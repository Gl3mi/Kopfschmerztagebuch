package at.htlkaindorf.kopfschmerztagebuch.ui.summary;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import at.htlkaindorf.kopfschmerztagebuch.beans.Analysis;
import at.htlkaindorf.kopfschmerztagebuch.bl.Analyse;
import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentSummaryBinding;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    private Analysis analysis;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Analyse analyse = new Analyse(requireContext());
        analysis = analyse.createAnalysis();

        TextView migraeneTv = binding.migraeneTv;
        TextView tensionTv = binding.tensionTv;
        TextView clusterTv = binding.clusterTv;
        TextView sinusTv = binding.sinusTv;
        TextView otherTv = binding.otherTv;

        TableRow sinusTr = binding.sinusTr;
        TableRow clusterTr = binding.clusterTr;
        TableRow tensionTr = binding.tensionTr;
        TableRow migraeneTr = binding.migraeneTr;
        TableRow otherTr = binding.otherTr;

        migraeneTv.setText("Migräne");
        tensionTv.setText("Spannungskopfschmerzen");
        clusterTv.setText("Cluster-Kopfschmerzen");
        sinusTv.setText("Sinusitis-Kopfschmerzen");
        otherTv.setText("Sonstige Kopfschmerzen");

        tv1 = binding.tv1;
        tv2 = binding.tv2;
        tv3 = binding.tv3;
        tv4 = binding.tv4;
        tv5 = binding.tv5;
        PieChart pieChart = binding.piechart;

        analysis.getPercentage().forEach(s -> {
            String[] h = s.split(";");

            switch (h[0]) {
                case "Migräne":
                    if (Double.parseDouble(h[1]) > 0) {
                        migraeneTr.setVisibility(View.VISIBLE);

                        pieChart.addPieSlice(
                                new PieModel(
                                        h[0],
                                        (float) Double.parseDouble(h[1]),
                                        Color.parseColor("#2196F3")));

                        tv1.setText(h[1]);
                    } else {
                        migraeneTr.setVisibility(View.GONE);
                    }
                    break;
                case "Spannungskopfschmerzen":
                    if (Double.parseDouble(h[1]) > 0) {
                        tensionTr.setVisibility(View.VISIBLE);

                        pieChart.addPieSlice(
                                new PieModel(
                                        h[0],
                                        (float) Double.parseDouble(h[1]),
                                        Color.parseColor("#FF0000")));

                        tv2.setText(h[1]);
                    } else {
                        tensionTr.setVisibility(View.GONE);
                    }
                    break;
                case "Cluster-Kopfschmerzen":
                    if (Double.parseDouble(h[1]) > 0) {
                        clusterTr.setVisibility(View.VISIBLE);

                        pieChart.addPieSlice(
                                new PieModel(
                                        h[0],
                                        (float) Double.parseDouble(h[1]),
                                        Color.parseColor("#D3D3D3")));

                        tv3.setText(h[1]);
                    } else {
                        clusterTr.setVisibility(View.GONE);
                    }
                    break;
                case "Sinusitis-Kopfschmerzen":
                    if (Double.parseDouble(h[1]) > 0) {
                        sinusTr.setVisibility(View.VISIBLE);

                        pieChart.addPieSlice(
                                new PieModel(
                                        h[0],
                                        (float) Double.parseDouble(h[1]),
                                        Color.parseColor("#FF000000")));

                        tv4.setText(h[1]);
                    } else {
                        sinusTr.setVisibility(View.GONE);
                    }
                    break;
                case "Sonstige Kopfschmerzen":
                    if (Double.parseDouble(h[1]) > 0) {
                        otherTr.setVisibility(View.VISIBLE);

                        pieChart.addPieSlice(
                                new PieModel(
                                        h[0],
                                        (float) Double.parseDouble(h[1]),
                                        Color.parseColor("#FF9800")));

                        tv5.setText(h[1]);
                    } else {
                        otherTr.setVisibility(View.GONE);
                    }
                    break;
            }
        });

        pieChart.startAnimation();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}