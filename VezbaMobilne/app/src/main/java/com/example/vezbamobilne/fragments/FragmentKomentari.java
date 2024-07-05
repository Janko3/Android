package com.example.vezbamobilne.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.widget.Toast;

import com.example.vezbamobilne.R;
import com.example.vezbamobilne.models.Komentar;
import com.example.vezbamobilne.repo.DatabaseHelper;
import com.example.vezbamobilne.utils.FragmentTransition;

import java.util.List;

public class FragmentKomentari extends Fragment {
    private ListView listView;
    private DatabaseHelper databaseHelper;
    private Button dodajButton;
    private Button mojiKomentari;
    private Button camAllow;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_CAMERA = 201;

    public FragmentKomentari() {
    }

    public static FragmentKomentari newInstance() {
        return new FragmentKomentari();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_komentari, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        dodajButton = view.findViewById(R.id.dodaj);
        camAllow = view.findViewById(R.id.camAllow);

        dodajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(FragmentNoviKomentari.newInstance(), getActivity(), true,
                        R.id.fragmentContainerView);
            }
        });

        mojiKomentari = view.findViewById(R.id.myComments);
        mojiKomentari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mojiKomentari.setBackgroundColor(Color.RED);
                FragmentTransition.to(FragmentMojiKomentari.newInstance(), getActivity(), true,
                        R.id.fragmentContainerView);
            }
        });

        camAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    if (isCameraAvailable()) {
                        Toast.makeText(getContext(), "Already has permission", Toast.LENGTH_SHORT).show();

                        openCamera();
                    } else {
                        Toast.makeText(getContext(), "No camera app available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ucitajKomentare();
        return view;
    }

    private void ucitajKomentare() {
        if (getContext() != null) {
            List<Komentar> komentari = databaseHelper.getAllKomentari();
            ArrayAdapter<Komentar> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, komentari);
            listView.setAdapter(adapter);
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    private boolean isCameraAvailable() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent.resolveActivity(getActivity().getPackageManager()) != null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            // Handle the camera image here
        }
    }
}

