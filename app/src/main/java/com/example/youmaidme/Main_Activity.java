package com.example.youmaidme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Activity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private TextView activity_main_employer_click;
    private TextView activity_main_maid_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            startActivity(new Intent(this, Dashboard_Activity.class));
        }

        activity_main_employer_click = (TextView) findViewById(R.id.activity_main_employer_tv);
        activity_main_maid_click = (TextView) findViewById(R.id.activity_main_maid_tv);

        activity_main_employer_click.setOnClickListener(this);
        activity_main_maid_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == activity_main_employer_click){
            finish();
            startActivity(new Intent(Main_Activity.this, LogIn_Activity.class));
        }
        if(view == activity_main_maid_click){
            finish();
            startActivity(new Intent(Main_Activity.this, LogIn_Activity.class));
        }
    }
}
