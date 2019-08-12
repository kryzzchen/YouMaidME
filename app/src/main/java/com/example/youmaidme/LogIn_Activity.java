package com.example.youmaidme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LogIn_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_activity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar action_bar = getSupportActionBar();

        action_bar.setDisplayHomeAsUpEnabled(true);

        TextView login_activity_create_new_account_tv = (TextView) findViewById(R.id.login_activity_create_new_account_tv);

        login_activity_create_new_account_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn_Activity.this, SignUp_Activity.class));
            }
        });
    }
}
