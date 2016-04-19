package com.csc.lesson8;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            addNewFragment();
        }
    }

    public void add(View view) {
        addNewFragment();
    }

    private void addNewFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_container, MasterFragment.newInstance("#" + counter++ + ": " + new Date().toString()))
                .addToBackStack(null)
                .commit();
    }
}
