package com.example.agrionion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

    public class CustomerPreOrderViewPager extends FragmentStateAdapter {

        public CustomerPreOrderViewPager(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Return appropriate fragment based on position
            switch (position) {
                case 0:
                    return new CustomerPreOrderProcessingHistory(); // Replace with your first fragment
                case 1:
                    return new CustomerPreOrderCompletedHistory(); // Replace with your second fragment
                default:
                    return new CustomerPreOrderProcessingHistory(); // Default fragment
            }
        }

        @Override
        public int getItemCount() {
            return 2; // Adjust based on number of tabs
        }
    }