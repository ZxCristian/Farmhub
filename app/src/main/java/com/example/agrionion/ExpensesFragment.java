package com.example.agrionion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExpensesFragment extends Fragment {

    private BarChart expensesBarChart;
    private BarChart stocksBarChart;
    private TextView textViewPriceSeeds, textViewPriceFertilizer, textViewPriceLabor, textViewPriceEquipment;
    private TextView textViewQuantitySeeds, textViewQuantityFertilizer, textViewQuantityInsecticide;
    private String currentTimePeriod = "Day"; // Default time period

    // Data storage for each time period
    private Map<String, float[]> expensesData = new HashMap<>();
    private Map<String, float[]> stocksData = new HashMap<>();

    // Colors for each bar
    private final int[] EXPENSES_COLORS = {
            0xFFFF5722, // Deep Orange (Seeds)
            0xFF3F51B5, // Indigo (Fertilizer)
            0xFF9C27B0, // Purple (Labor)
            0xFF009688  // Teal (Equipment)
    };
    private final int[] STOCKS_COLORS = {
            0xFF4CAF50, // Green (Seeds)
            0xFF2196F3, // Blue (Fertilizer)
            0xFFFFC107  // Amber (Insecticide)
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        // Initialize BarCharts
        expensesBarChart = view.findViewById(R.id.ExpensesbarChart);
        stocksBarChart = view.findViewById(R.id.StocksbarChart);

        // Initialize TextViews (Expenses)
        textViewPriceSeeds = view.findViewById(R.id.textViewPriceSeeds);
        textViewPriceFertilizer = view.findViewById(R.id.textViewPriceFertilizer);
        textViewPriceLabor = view.findViewById(R.id.textViewPriceLabor);
        textViewPriceEquipment = view.findViewById(R.id.textViewPriceEquipment);

        // Initialize TextViews (Stocks)
        textViewQuantitySeeds = view.findViewById(R.id.textViewQuantitySeeds);
        textViewQuantityFertilizer = view.findViewById(R.id.textViewQuantityFertilizer);
        textViewQuantityInsecticide = view.findViewById(R.id.textViewQuantityInsecticide);

        // Initialize sample data for each tab
        initializeData();

        // Set click listeners (Expenses)
        textViewPriceSeeds.setOnClickListener(v -> showEditDialog(textViewPriceSeeds, true, false, "Expenses: Seeds"));
        textViewPriceFertilizer.setOnClickListener(v -> showEditDialog(textViewPriceFertilizer, true, false, "Expenses: Fertilizer"));
        textViewPriceLabor.setOnClickListener(v -> showEditDialog(textViewPriceLabor, true, false, "Expenses: Labor"));
        textViewPriceEquipment.setOnClickListener(v -> showEditDialog(textViewPriceEquipment, true, false, "Expenses: Equipment"));

        // Set click listeners (Stocks)
        textViewQuantitySeeds.setOnClickListener(v -> showEditDialog(textViewQuantitySeeds, false, true, "Stocks: Seeds"));
        textViewQuantityFertilizer.setOnClickListener(v -> showEditDialog(textViewQuantityFertilizer, false, true, "Stocks: Fertilizer"));
        textViewQuantityInsecticide.setOnClickListener(v -> showEditDialog(textViewQuantityInsecticide, false, true, "Stocks: Insecticide"));

        // Initialize TabLayout
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTimePeriod = tab.getText().toString();
                updateCharts();
                updateTextViews();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Load initial chart and TextView data
        updateCharts();
        updateTextViews();

        return view;
    }

    private void initializeData() {
        // Expenses: [Seeds, Fertilizer, Labor, Equipment]
        expensesData.put("Day", new float[]{1000f, 500f, 300f, 200f});
        expensesData.put("Week", new float[]{7000f, 3500f, 2100f, 1400f});
        expensesData.put("Month", new float[]{30000f, 15000f, 9000f, 6000f});
        expensesData.put("Year", new float[]{360000f, 180000f, 108000f, 72000f});

        // Stocks: [Seeds, Fertilizer, Insecticide]
        stocksData.put("Day", new float[]{10f, 5f, 2f});
        stocksData.put("Week", new float[]{70f, 35f, 14f});
        stocksData.put("Month", new float[]{300f, 150f, 60f});
        stocksData.put("Year", new float[]{3600f, 1800f, 720f});
    }

    private void updateCharts() {
        loadExpensesChart();
        loadStocksChart();
    }

    private void updateTextViews() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        float[] expenses = expensesData.get(currentTimePeriod);
        float[] stocks = stocksData.get(currentTimePeriod);

        textViewPriceSeeds.setText("₱" + formatter.format(expenses[0]));
        textViewPriceFertilizer.setText("₱" + formatter.format(expenses[1]));
        textViewPriceLabor.setText("₱" + formatter.format(expenses[2]));
        textViewPriceEquipment.setText("₱" + formatter.format(expenses[3]));

        textViewQuantitySeeds.setText(formatter.format(stocks[0]) + "kg");
        textViewQuantityFertilizer.setText(formatter.format(stocks[1]) + "kg");
        textViewQuantityInsecticide.setText(formatter.format(stocks[2]) + "kg");
    }

    private void loadExpensesChart() {
        float[] data = expensesData.get(currentTimePeriod);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, data[0]));
        entries.add(new BarEntry(1, data[1]));
        entries.add(new BarEntry(2, data[2]));
        entries.add(new BarEntry(3, data[3]));

        BarDataSet dataSet = new BarDataSet(entries, "Expenses (" + currentTimePeriod + ")");
        dataSet.setColors(EXPENSES_COLORS); // Assign different colors to each bar
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        setupBarChart(expensesBarChart, barData, new String[]{"Seeds", "Fertilizer", "Labor", "Equipment"});
    }

    private void loadStocksChart() {
        float[] data = stocksData.get(currentTimePeriod);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, data[0]));
        entries.add(new BarEntry(1, data[1]));
        entries.add(new BarEntry(2, data[2]));

        BarDataSet dataSet = new BarDataSet(entries, "Stocks (" + currentTimePeriod + ")");
        dataSet.setColors(STOCKS_COLORS); // Assign different colors to each bar
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        setupBarChart(stocksBarChart, barData, new String[]{"Seeds", "Fertilizer", "Insecticide"});
    }

    private void setupBarChart(BarChart chart, BarData barData, String[] labels) {
        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setAxisMinimum(0f);

        chart.getAxisRight().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void showEditDialog(TextView textView, boolean isCurrency, boolean isWeight, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        String currentText = textView.getText().toString();
        if (isCurrency) {
            currentText = currentText.replace("₱", "").replace(",", "").trim();
        } else if (isWeight) {
            currentText = currentText.replace("kg", "").replace(",", "").trim();
        }

        final EditText input = new EditText(requireContext());
        input.setText(currentText);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        InputFilter numberFilter = (source, start, end, dest, dstart, dend) -> {
            String newValue = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
            try {
                if (!newValue.isEmpty()) {
                    Double.parseDouble(newValue.replace(",", ""));
                }
            } catch (NumberFormatException e) {
                return "";
            }
            return null;
        };
        input.setFilters(new InputFilter[]{numberFilter});

        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newText = input.getText().toString().trim();
            if (!newText.isEmpty()) {
                try {
                    float newValue = Float.parseFloat(newText);
                    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
                    String formattedText = formatter.format(newValue);

                    // Update the appropriate data array based on the TextView
                    if (isCurrency) {
                        float[] expenses = expensesData.get(currentTimePeriod);
                        if (textView == textViewPriceSeeds) expenses[0] = newValue;
                        else if (textView == textViewPriceFertilizer) expenses[1] = newValue;
                        else if (textView == textViewPriceLabor) expenses[2] = newValue;
                        else if (textView == textViewPriceEquipment) expenses[3] = newValue;
                        textView.setText("₱" + formattedText);
                    } else if (isWeight) {
                        float[] stocks = stocksData.get(currentTimePeriod);
                        if (textView == textViewQuantitySeeds) stocks[0] = newValue;
                        else if (textView == textViewQuantityFertilizer) stocks[1] = newValue;
                        else if (textView == textViewQuantityInsecticide) stocks[2] = newValue;
                        textView.setText(formattedText + "kg");
                    }

                    updateCharts();
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Value cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private float parseValue(String text, String unit) {
        try {
            return Float.parseFloat(text.replace(unit, "").replace(",", "").trim());
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
}