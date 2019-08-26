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

import com.example.youmaidme.maid_ui_activities.Dashboard_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView login_activity_create_new_account_tv, login_activity_forgot_password_tv;
    private Button login_activity_login_button_bt;
    private TextInputLayout login_activity_password_et, login_activity_username_et;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        toolbar = findViewById(R.id.login_activity_toolbar);
        setSupportActionBar(toolbar);

        //ActionBar action_bar = getSupportActionBar();

        //action_bar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //dashboard activity here
            progressDialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
        }

        login_activity_username_et = findViewById(R.id.login_activity_username_et);
        login_activity_password_et = findViewById(R.id.login_activity_password_et);
        login_activity_login_button_bt = (Button) findViewById(R.id.login_activity_login_button_bt);
        login_activity_create_new_account_tv = (TextView) findViewById(R.id.login_activity_create_new_account_tv);
        login_activity_forgot_password_tv = (TextView) findViewById(R.id.login_activity_forgot_password_tv);

        login_activity_login_button_bt.setOnClickListener(this);
        login_activity_create_new_account_tv.setOnClickListener(this);
        login_activity_forgot_password_tv.setOnClickListener(this);
    }


    private boolean validateEmail(){
        String email = login_activity_username_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            login_activity_username_et.setError("Email can't be empty");
            return false;
        }else{
            login_activity_username_et.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = login_activity_password_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            login_activity_password_et.setError("Password can't be empty");
            return false;
        }else {
            login_activity_password_et.setError(null);
            return true;
        }
    }

    private void userLogin() {
        String email = login_activity_username_et.getEditText().getText().toString().trim();
        String password = login_activity_password_et.getEditText().getText().toString().trim();


        progressDialog.setMessage("Signing In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the dashboard activity '
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                        } else {
                            login_activity_username_et.setError("Invalid Email");
                            login_activity_password_et.setError("Invalid Password");
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == login_activity_login_button_bt){
            if(!validateEmail() | !validatePassword()){
                return;
            }
            userLogin();
        } else if (view == login_activity_create_new_account_tv) {
            finish();
            startActivity(new Intent(LogIn_Activity.this, Register_As_Activity.class));
        } else if (view == login_activity_forgot_password_tv) {
            //forgot password
            Toast.makeText(this, "Forgot Password", Toast.LENGTH_SHORT).show();
        }
    }
}
