package com.example.agrionion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PlantingHistoryPagerAdapter extends FragmentStateAdapter {

    public PlantingHistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HarvestedFragment();
            case 1:
                return new WiltedFragment();
            default:
                return new HarvestedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
