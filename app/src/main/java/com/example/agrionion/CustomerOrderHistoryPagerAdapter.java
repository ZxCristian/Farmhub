package com.example.agrionion;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomerOrderHistoryPagerAdapter extends FragmentStateAdapter {

    public CustomerOrderHistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PendingOrdersFragment(); // Create this fragment
            case 1:
                return new CompletedOrdersFragment(); // Create this fragment
            case 2:
                return new CancelledOrdersFragment(); // Create this fragment
            default:
                return new PendingOrdersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}