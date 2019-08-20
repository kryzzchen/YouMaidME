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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView login_activity_create_new_account_tv, login_activity_forgot_password_tv;
    private Button login_activity_login_button_bt;
    private EditText login_activity_password_et, login_activity_username_et;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_activity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar action_bar = getSupportActionBar();

        action_bar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //dashboard activity here
            progressDialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
        }

        login_activity_username_et = (EditText) findViewById(R.id.login_activity_username_et);
        login_activity_password_et = (EditText) findViewById(R.id.login_activity_password_et);
        login_activity_login_button_bt = (Button) findViewById(R.id.login_activity_login_button_bt);
        login_activity_create_new_account_tv = (TextView) findViewById(R.id.login_activity_create_new_account_tv);
        login_activity_forgot_password_tv = (TextView) findViewById(R.id.login_activity_forgot_password_tv);

        login_activity_login_button_bt.setOnClickListener(this);
        login_activity_create_new_account_tv.setOnClickListener(this);
        login_activity_forgot_password_tv.setOnClickListener(this);
    }

    private void userLogin() {
        String email = login_activity_username_et.getText().toString().trim();
        String password = login_activity_password_et.getText().toString().trim();

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
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == login_activity_create_new_account_tv) {
            finish();
            startActivity(new Intent(LogIn_Activity.this, SignUp_Activity.class));
        }
        if (view == login_activity_forgot_password_tv) {
            //forgot password
            Toast.makeText(this, "Forgot Password", Toast.LENGTH_SHORT).show();
        }
        if (view == login_activity_login_button_bt){
            //Send to dashboard activity
            userLogin();
        }
    }
}
