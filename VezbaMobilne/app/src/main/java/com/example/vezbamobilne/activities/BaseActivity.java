package com.example.vezbamobilne.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.vezbamobilne.R;
import com.example.vezbamobilne.fragments.FragmentKomentari;
import com.example.vezbamobilne.utils.FragmentTransition;

import java.util.Objects;


public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch ((Objects.requireNonNull(item.getTitle())).toString()) {
            case "Komentari":
                Toast.makeText(this, "Komentari clicked", Toast.LENGTH_SHORT).show();
                FragmentTransition.to(FragmentKomentari.newInstance(), BaseActivity.this, true, R.id.fragmentContainerView);
                return true;
            case "Knjige":
                Toast.makeText(this, "Knjige clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}