package com.example.agrionion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private LinearLayout editProfile, myProduct, preBuyHistory, plantingHistory, logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        editProfile = view.findViewById(R.id.EditProfile);
        myProduct = view.findViewById(R.id.MyProduct);
        preBuyHistory = view.findViewById(R.id.PreBuyHistory);
        plantingHistory = view.findViewById(R.id.PlantingHistory);
        logout = view.findViewById(R.id.Logout);

        // Set onClick listeners
        editProfile.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EditProfile.class)));

        myProduct.setOnClickListener(v -> startActivity(new Intent(requireActivity(), MyProductHistory.class)));

        preBuyHistory.setOnClickListener(v -> startActivity(new Intent(requireActivity(), PreBuyHistory.class)));

        plantingHistory.setOnClickListener(v -> startActivity(new Intent(requireActivity(), PlantingHistory.class)));

        logout.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void logoutUser() {
        // Implement logout logic here (e.g., clear session, navigate to login screen)
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
