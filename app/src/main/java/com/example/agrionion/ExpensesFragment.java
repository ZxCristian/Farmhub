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

import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.Locale;

public class ExpensesFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);


        // Find TabLayout
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        // TabLayout Listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Find TextViews (Expenses)
        TextView textViewPriceSeeds = view.findViewById(R.id.textViewPriceSeeds);
        TextView textViewFertilizer = view.findViewById(R.id.textViewPriceFertilizer);
        TextView textViewLabor = view.findViewById(R.id.textViewPriceLabor);
        TextView textViewEquipment = view.findViewById(R.id.textViewPriceEquipment);

        // Set click listeners to open dialog (Expenses)
        textViewPriceSeeds.setOnClickListener(v -> showEditDialog(textViewPriceSeeds, true,false,"Expenses: Seeds"));
        textViewFertilizer.setOnClickListener(v -> showEditDialog(textViewFertilizer, true,false,"Expenses: Fertilizer"));
        textViewLabor.setOnClickListener(v -> showEditDialog(textViewLabor, true,false,"Expenses: Labor"));
        textViewEquipment.setOnClickListener(v -> showEditDialog(textViewEquipment, true,false,"Expenses: Equipment"));

        // Find TextViews (Quantities)
        TextView textViewQuantitySeeds = view.findViewById(R.id.textViewQuantitySeeds);
        TextView textViewQuantityFertilizer = view.findViewById(R.id.textViewQuantityFertilizer);
        TextView textViewQuantityInsecticide = view.findViewById(R.id.textViewQuantityInsecticide);

        // Set click listeners to open dialog (Quantities)
        textViewQuantitySeeds.setOnClickListener(v -> showEditDialog(textViewQuantitySeeds, false, true,"Stocks: Seeds"));
        textViewQuantityFertilizer.setOnClickListener(v -> showEditDialog(textViewQuantityFertilizer, false, true,"Stocks: Fertilizer"));
        textViewQuantityInsecticide.setOnClickListener(v -> showEditDialog(textViewQuantityInsecticide, false, true,"Stocks: Insecticide"));

        return view;
    }

    // Method to show the edit dialog


    private void showEditDialog(TextView textView, boolean isCurrency, boolean isWeight, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title); // Set title dynamically

        // Get current text and remove symbols if necessary
        String currentText = textView.getText().toString();
        if (isCurrency) {
            currentText = currentText.replace("₱", "").replace(",", "").trim();
        } else if (isWeight) {
            currentText = currentText.replace("kg", "").replace(",", "").trim();
        }

        // Create an EditText field
        final EditText input = new EditText(requireContext());
        input.setText(currentText);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); // Restrict input to numbers only

        // Filter to allow only valid numbers
        InputFilter numberFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String newValue = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
                try {
                    if (!newValue.isEmpty()) {
                        Double.parseDouble(newValue.replace(",", ""));
                    }
                } catch (NumberFormatException e) {
                    return "";
                }
                return null;
            }
        };
        input.setFilters(new InputFilter[]{numberFilter});

        builder.setView(input);

        // Set dialog buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newText = input.getText().toString().trim();
            if (!newText.isEmpty()) {
                try {
                    // Format number with commas
                    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
                    String formattedText = formatter.format(Double.parseDouble(newText));

                    if (isCurrency) {
                        textView.setText("₱" + formattedText);
                    } else if (isWeight) {
                        textView.setText(formattedText + "kg"); // Ensure "kg" is always added
                    } else {
                        textView.setText(formattedText);
                    }
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


}
