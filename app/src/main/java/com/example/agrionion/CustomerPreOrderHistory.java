package com.example.agrionion;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CustomerPreOrderHistory extends AppCompatActivity {

        private TabLayout tabLayout;
        private ViewPager2 viewPager;
        private ImageView btnBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pre_buy_history); // Assumes XML is named activity_pre_buy_history.xml

            // Initialize views
            tabLayout = findViewById(R.id.tabLayout);
            viewPager = findViewById(R.id.viewPager);
            btnBack = findViewById(R.id.btnBack);

            // Setup ViewPager2 with adapter
            setupViewPager();

            // Setup TabLayout with ViewPager2
            setupTabLayout();

            // Setup back button click listener
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        private void setupViewPager() {
            // Create and set adapter for ViewPager2
            CustomerPreOrderViewPager adapter = new CustomerPreOrderViewPager(this);
            viewPager.setAdapter(adapter);
        }

        private void setupTabLayout() {
            // Configure tabs
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Processing"); // Replace with actual tab name
                        break;
                    case 1:
                        tab.setText("Completed"); // Replace with actual tab name
                        break;
                    // Add more cases as needed
                }
            }).attach();

            // Optional: Customize tab appearance
            tabLayout.setTabTextColors(
                    getResources().getColor(android.R.color.black),
                    getResources().getColor(R.color.orange)
            );
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange));
        }
    }