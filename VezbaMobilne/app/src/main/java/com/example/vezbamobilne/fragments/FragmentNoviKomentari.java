package com.example.vezbamobilne.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.vezbamobilne.R;
import com.example.vezbamobilne.models.Komentar;
import com.example.vezbamobilne.repo.DatabaseHelper;

public class FragmentNoviKomentari extends Fragment {
    private String username;;

    public FragmentNoviKomentari() {
    }
    private DatabaseHelper databaseHelper;
    public static FragmentNoviKomentari newInstance() {
        return new FragmentNoviKomentari();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.novi_komentar, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username_shared", "default_username");
        System.out.println("Username je " + username);

        databaseHelper = new DatabaseHelper(getContext());

        Button btnKomentar = view.findViewById(R.id.btnKomentar);
        EditText textKomentar = view.findViewById(R.id.txtkomentar);

        btnKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String komentarText = textKomentar.getText().toString();
                System.out.println("Komentar je: " + komentarText);

                Komentar komentar = new Komentar(komentarText, username);
                databaseHelper.insertKomentar(komentar);
            }
        });

        return view;
    }
}
