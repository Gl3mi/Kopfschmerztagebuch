package at.htlkaindorf.kopfschmerztagebuch.ui.summary;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Analyse analyse = new Analyse(requireContext());
        analysis = analyse.createAnalysis();

        TextView tvR = binding.tv1;
        TextView tvPython = binding.tv2;
        TextView tvCPP = binding.tv3;
        TextView tvJava = binding.tv4;
        PieChart pieChart = binding.piechart;

        analysis.getPercentage().forEach(s -> {
            String[] h = s.split(";");

            switch (h[0]) {
                case "Migräne":
                    pieChart.addPieSlice(
                            new PieModel(
                                    h[0],
                                    (float) Double.parseDouble(h[1]),
                                    Color.parseColor("#FFA726")));
                    break;
                case "Spannungskopfschmerzen":
                    pieChart.addPieSlice(
                            new PieModel(
                                    h[0],
                                    (float) Double.parseDouble(h[1]),
                                    Color.parseColor("#66BB6A")));
                    break;
                case "Cluster-Kopfschmerzen":
                    pieChart.addPieSlice(
                            new PieModel(
                                    h[0],
                                    (float) Double.parseDouble(h[1]),
                                    Color.parseColor("#EF5350")));
                    break;
                case "Sinusitis-Kopfschmerzen":
                    pieChart.addPieSlice(
                            new PieModel(
                                    h[0],
                                    (float) Double.parseDouble(h[1]),
                                    Color.parseColor("#29B6F6")));
                    break;
                case "Sonstige Kopfschmerzen":
                    pieChart.addPieSlice(
                            new PieModel(
                                    h[0],
                                    (float) Double.parseDouble(h[1]),
                                    Color.parseColor("#1BC122")));
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