package com.example.youmaidme;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SignUp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_activity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar action_bar = getSupportActionBar();

        action_bar.setDisplayHomeAsUpEnabled(true);
    }
}
