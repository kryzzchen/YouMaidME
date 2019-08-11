package com.example.youmaidme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView activity_main_employer_click = (TextView) findViewById(R.id.activity_main_employer_tv);
        TextView activity_main_maid_click = (TextView) findViewById(R.id.activity_main_maid_tv);

        activity_main_employer_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Activity.this, LogIn_Activity.class));
            }
        });

        activity_main_maid_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Activity.this, LogIn_Activity.class));
            }
        });
    }
}
