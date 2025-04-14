package com.example.agrionion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class CustomerProfileFragment extends Fragment {

    private ShapeableImageView imgProfile;
    private TextView tvName;
    private TextView tvRole;
    private LinearLayout editProfileLayout;
    private LinearLayout orderHistoryLayout;
    private LinearLayout preBuyHistoryLayout;
    private LinearLayout logoutLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_fragment_profile, container, false);

        // Initialize views
        imgProfile = view.findViewById(R.id.imgProfile);
        tvName = view.findViewById(R.id.tvName);
        tvRole = view.findViewById(R.id.tvRole);
        editProfileLayout = view.findViewById(R.id.EditProfile);
        orderHistoryLayout = view.findViewById(R.id.MyProduct);
        preBuyHistoryLayout = view.findViewById(R.id.PreBuyHistory);
        logoutLayout = view.findViewById(R.id.Logout);

        // Set click listeners
        imgProfile.setOnClickListener(v -> {
            // Handle profile image click (e.g., open image picker or view)
            openProfileImageEditor();
        });

        editProfileLayout.setOnClickListener(v -> {
            // Navigate to Edit Profile screen
            Intent intent = new Intent(requireContext(), CustomerEditProfile.class);
            startActivity(intent);
        });

        orderHistoryLayout.setOnClickListener(v -> {
            // Navigate to Order History screen
            Intent intent = new Intent(requireContext(), CustomerOrderHistory.class);
            startActivity(intent);
        });

        preBuyHistoryLayout.setOnClickListener(v -> {
            // Navigate to Pre Buy History screen
            Intent intent = new Intent(requireContext(), CustomerPreOrderHistory.class);
            startActivity(intent);
        });

        logoutLayout.setOnClickListener(v -> {
            // Handle logout
            performLogout();
        });

        // Load user data (example)
        loadUserData();

        return view;
    }

    private void loadUserData() {
        // TODO: Replace with actual user data retrieval (e.g., from Firebase, SharedPreferences, or API)
        tvName.setText("Cristian D. Zarate");
        tvRole.setText("Buyer");
        // Load profile image if available
        // imgProfile.setImageResource(R.drawable.user_profile_image); // Example
    }

    private void openProfileImageEditor() {
        // TODO: Implement image picker or profile image editor
        // Example: Open gallery or camera
    }

    private void performLogout() {
        // TODO: Implement logout logic
        // Example: Clear user session, SharedPreferences, or Firebase auth
        // Navigate to Login screen
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}