package com.example.agrionion;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CustomerOrderHistory extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history); // Ensure XML file name matches

        // Initialize views
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnBack = findViewById(R.id.btnBack);

        // Set up back button click listener
        btnBack.setOnClickListener(v -> finish());

        // Set up ViewPager with adapter
        setupViewPager();

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Pending");
                            break;
                        case 1:
                            tab.setText("Completed");
                            break;
                        case 2:
                            tab.setText("Cancelled");
                            break;
                    }
                }).attach();
    }

    private void setupViewPager() {
        // Create and set the adapter for ViewPager2
        CustomerOrderHistoryPagerAdapter adapter = new CustomerOrderHistoryPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }
}