package com.example.vezbamobilne.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.vezbamobilne.R;
import com.example.vezbamobilne.models.Komentar;
import com.example.vezbamobilne.repo.DatabaseHelper;
import com.example.vezbamobilne.utils.FragmentTransition;

import java.util.List;

public class FragmentMojiKomentari extends Fragment {
    private ListView listView;
    private DatabaseHelper databaseHelper;
    private Button dodajButton;
    private Button myComments;

    public FragmentMojiKomentari() {
    }
    public static FragmentMojiKomentari newInstance() {
        return new FragmentMojiKomentari();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Entered onCreate");
        // Initialize the database helper here for use later in onCreateView or onViewCreated
        databaseHelper = new DatabaseHelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_komentari,container,false);
        listView = (ListView) view.findViewById(android.R.id.list);
        dodajButton = view.findViewById(R.id.dodaj);

        dodajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(FragmentNoviKomentari.newInstance(),getActivity(),true,
                        R.id.fragmentContainerView);
            }
        });
        myComments = view.findViewById(R.id.myComments);
        myComments.setBackgroundColor(Color.RED);
        ucitajKomentare();
        return view;
    }

    private void ucitajKomentare(){
        SharedPreferences sharedPreferences=
                requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username_shared","default_username");
        if(getContext() != null){
            List<Komentar> komentari = databaseHelper.getKomentar(username);
            ArrayAdapter<Komentar> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1,komentari);
            listView.setAdapter(adapter);
        }
    }

}
