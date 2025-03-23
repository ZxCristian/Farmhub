package com.example.agrionion;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PreBuyHistory extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PreBuyHistoryAdapter adapter;

    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_buy_history);

        btnBack = findViewById(R.id.btnBack);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        btnBack.setOnClickListener(v -> finish());

        adapter = new PreBuyHistoryAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Processing");
                        break;
                    case 1:
                        tab.setText("Completed");
                        break;
                    case 2:
                        tab.setText("Cancelled");
                        break;
                }
            }
        }).attach();
    }
}
