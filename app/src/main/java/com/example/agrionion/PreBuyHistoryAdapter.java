package com.example.agrionion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PreBuyHistoryAdapter extends FragmentStateAdapter {

    public PreBuyHistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PreBuyProcessingFragment();
            case 1:
                return new PreBuyCompletedFragment();
            case 2:
                return new PreBuyCancelledFragment();
            default:
                return new PreBuyProcessingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}