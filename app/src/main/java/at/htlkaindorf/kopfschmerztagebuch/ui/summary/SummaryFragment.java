package at.htlkaindorf.kopfschmerztagebuch.ui.summary;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentSummaryBinding;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    TextView tvR, tvPython, tvCPP, tvJava;
    PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SummaryViewModel summaryViewModel =
                new ViewModelProvider(this).get(SummaryViewModel.class);

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvR = binding.tvR;
        tvPython = binding.tvPython;
        tvCPP = binding.tvCPP;
        tvJava = binding.tvJava;
        pieChart = binding.piechart;

        tvPython.setText(Integer.toString(40));
        tvCPP.setText(Integer.toString(10));
        tvJava.setText(Integer.toString(50));

        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));

        pieChart.startAnimation();
        //final TextView textView = binding.textView;
        //summaryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}