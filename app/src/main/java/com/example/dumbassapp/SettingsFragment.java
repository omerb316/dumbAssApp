package com.example.dumbassapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    private ImageView imageView;
    private final ActivityResultLauncher<Void> cameraLauncher =registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),bitmap -> {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    });
    private final ActivityResultLauncher<String> galleryLauncher =registerForActivityResult(new ActivityResultContracts.GetContent(),uri -> {
        if (uri != null) {
            imageView.setImageURI(uri);
        }
    });

    private Button cameraBt,galleryBt;

        public SettingsFragment() {

        }

        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState
        ) {
            return inflater.inflate(R.layout.fragment_settings, container, false);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);

        cameraBt = view.findViewById(R.id.camera_button);
        cameraBt.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (requireContext().checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
                    return;
                }
            }
            cameraLauncher.launch(null);
        });

        galleryBt = view.findViewById(R.id.gallery_button);
        galleryBt.setOnClickListener(view1 -> galleryLauncher.launch("image/*"));
    }
}