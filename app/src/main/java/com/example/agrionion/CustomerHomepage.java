package com.example.agrionion;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerHomepage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CustomerMarketFragment())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_market) {
                selectedFragment = new CustomerMarketFragment();
            } else if (itemId == R.id.nav_preorder) {
                selectedFragment = new CustomerPreOrderFragment();
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CustomerCartFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new CustomerProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Handle logout logic, e.g., clearing user session and finishing activity
                    finishAffinity(); // Closes all activities and exits app
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}