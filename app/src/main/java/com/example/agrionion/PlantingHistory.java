package com.example.agrionion;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PlantingHistory extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planting_history);

        btnBack = findViewById(R.id.btnBack);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        btnBack.setOnClickListener(v -> finish());

        // Set up ViewPager2 Adapter
        PlantingHistoryPagerAdapter adapter = new PlantingHistoryPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Harvested");
                    break;
                case 1:
                    tab.setText("Wilted");
                    break;
            }
        }).attach();
    }
}
