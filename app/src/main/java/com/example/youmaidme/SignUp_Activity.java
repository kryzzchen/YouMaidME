package com.example.youmaidme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button signup_activity_signup_btn;
    private EditText signup_activity_username_et;
    private EditText signup_activity_email_et;
    private EditText signup_activity_password_et;
    private EditText signup_activity_location_et;
    private TextView signup_activity_has_account_tv;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        //Support toolbar back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_activity_toolbar);
        setSupportActionBar(toolbar);
        //ActionBar action_bar = getSupportActionBar();
        //action_bar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //dashboard activity here
            progressDialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
        }

        progressDialog = new ProgressDialog(this);

        signup_activity_email_et = (EditText) findViewById(R.id.signup_activity_email_et);
        signup_activity_username_et = (EditText) findViewById(R.id.signup_activity_username_et);
        signup_activity_password_et = (EditText) findViewById(R.id.signup_activity_password_et);
        signup_activity_location_et = (EditText) findViewById(R.id.signup_activity_location_et);
        signup_activity_signup_btn = (Button) findViewById(R.id.signup_activity_signup_button_bt);
        signup_activity_has_account_tv = (TextView) findViewById(R.id.signup_activty_old_account);

        signup_activity_signup_btn.setOnClickListener(this);
        signup_activity_has_account_tv.setOnClickListener(this);
    }

    private void registerUser(){
        String username = signup_activity_username_et.getText().toString().trim();
        String email = signup_activity_email_et.getText().toString().trim();
        String password = signup_activity_password_et.getText().toString().trim();
        String location = signup_activity_location_et.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            //if username is empty
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            //if email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //if password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(location)){
            //if location is empty
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Completing Registration");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                            Toast.makeText(SignUp_Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUp_Activity.this, "Could Not Register User", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == signup_activity_signup_btn){
            registerUser();
        }
        if(view == signup_activity_has_account_tv){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
        }
    }

}
