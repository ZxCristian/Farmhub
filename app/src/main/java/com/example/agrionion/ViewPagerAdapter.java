package com.example.agrionion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CurrentListingFragment(); // Replace with actual fragment
            case 1:
                return new MyProductFragment(); // Replace with actual fragment
            case 2:
                return new PreOrderFragment(); // Replace with actual fragment
            default:
                return new MyProductFragment(); // Default case
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
