package com.example.youmaidme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button signup_activity_signup_btn;
    private TextInputLayout signup_activity_username_et,signup_activity_email_et;
    private TextInputLayout signup_activity_location_et,signup_activity_password_et;
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

        signup_activity_email_et = findViewById(R.id.signup_activity_email_et);
        signup_activity_username_et = findViewById(R.id.signup_activity_username_et);
        signup_activity_password_et = findViewById(R.id.signup_activity_password_et);
        signup_activity_location_et = findViewById(R.id.signup_activity_location_et);
        signup_activity_signup_btn = (Button) findViewById(R.id.signup_activity_signup_button_bt);
        signup_activity_has_account_tv = (TextView) findViewById(R.id.signup_activty_old_account);

        signup_activity_signup_btn.setOnClickListener(this);
        signup_activity_has_account_tv.setOnClickListener(this);
    }

    private boolean validateUsername(){
        String username = signup_activity_username_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            //if username is empty
            signup_activity_username_et.setError("Enter Username");
            return false;
        } else {
            signup_activity_username_et.setError(null);
            return true;
        }
    }
    private boolean validateEmail(){
        String email = signup_activity_email_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            signup_activity_email_et.setError("Enter Email");
            return false;
        } else{
            signup_activity_email_et.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String password = signup_activity_password_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            signup_activity_password_et.setError("Enter Password");
            return false;
        } else {
            signup_activity_password_et.setError(null);
            return true;
        }
    }
    private boolean validateLocation(){
        String location = signup_activity_location_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(location)){
            signup_activity_location_et.setError("Enter Location");
            return false;
        } else {
            signup_activity_location_et.setError(null);
            return true;
        }
    }
    private void registerUser(){
        String username = signup_activity_username_et.getEditText().getText().toString().trim();
        String email = signup_activity_email_et.getEditText().getText().toString().trim();
        String password = signup_activity_password_et.getEditText().getText().toString().trim();
        String location = signup_activity_location_et.getEditText().getText().toString().trim();

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
            if(!validateUsername() | !validateEmail() | !validatePassword() | !validateLocation()){
                return;
            }
            registerUser();
        }
        if(view == signup_activity_has_account_tv){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
        }
    }

}
