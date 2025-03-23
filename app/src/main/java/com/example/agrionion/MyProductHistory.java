package com.example.agrionion;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyProductHistory extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_history);

        btnBack = findViewById(R.id.btnBack);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        btnBack.setOnClickListener(v -> finish());

        viewPager.setAdapter(new MyProductHistoryAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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
        }).attach();
    }

    private static class MyProductHistoryAdapter extends FragmentStateAdapter {
        public MyProductHistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MpProcessingFragment();
                case 1:
                    return new MpCompletedFragment();
                case 2:
                    return new MpCancelledFragment();
                default:
                    return new MpProcessingFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
