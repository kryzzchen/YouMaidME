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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.youmaidme.maid_ui_activities.Dashboard_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Employee_Registration_Activity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private TextInputLayout employee_activity_username_et, employee_activity_email_et, employee_activity_password_et;
    private TextInputLayout employee_activity_location_et, employee_activity_phone_number_et;
    private Button employee_activity_signup_btn;
    private TextView employee_activity_old_account_tv;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_registration_activity);

        toolbar = (Toolbar) findViewById(R.id.employee_activity_toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //dashboard activity here
            progressDialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
        }

        progressDialog = new ProgressDialog(this);

        employee_activity_username_et = findViewById(R.id.employee_activity_username_et);
        employee_activity_email_et = findViewById(R.id.employee_activity_email_et);
        employee_activity_password_et = findViewById(R.id.employee_activity_password_et);
        employee_activity_location_et = findViewById(R.id.employee_activity_location_et);
        employee_activity_phone_number_et = findViewById(R.id.employee_activity_phone_number_et);

        employee_activity_signup_btn = findViewById(R.id.employee_activity_signup_button_bt);
        employee_activity_old_account_tv = findViewById(R.id.employee_activity_old_account);

        employee_activity_signup_btn.setOnClickListener(this);
        employee_activity_old_account_tv.setOnClickListener(this);
    }
    private boolean validateUserName(){
        String username = employee_activity_username_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            employee_activity_username_et.setError("Enter Username");
            return false;
        } else {
            employee_activity_username_et.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String email = employee_activity_email_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            employee_activity_email_et.setError("Enter email");
            return false;
        } else {
            employee_activity_email_et.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = employee_activity_password_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            employee_activity_password_et.setError("Enter Password");
            return false;
        } else if (password.length() < 6){
            employee_activity_password_et.setError("Password should be 6 characters and above");
            return false;
        } else {
            employee_activity_password_et.setError(null);
            return true;
        }

    }

    private boolean validateLocation(){
        String location = employee_activity_location_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(location)){
            employee_activity_location_et.setError("Enter location");
            return false;
        } else {
            employee_activity_location_et.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String phonenumber = employee_activity_phone_number_et.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(phonenumber)){
            employee_activity_phone_number_et.setError("Enter Phone Number");
            return false;
        } else {
            employee_activity_phone_number_et.setError(null);
            return true;
        }
    }

    private void registerUser() {
        String username = employee_activity_username_et.getEditText().getText().toString().trim();
        String email = employee_activity_email_et.getEditText().getText().toString().trim();
        String password = employee_activity_password_et.getEditText().getText().toString().trim();
        String location = employee_activity_location_et.getEditText().getText().toString().trim();
        String phonenumber = employee_activity_phone_number_et.getEditText().getText().toString();
        Map<String, String> userMap = new HashMap<>();

        userMap.put("USERNAME", username);
        userMap.put("EMAIL", email);
        userMap.put("PASSWORD", password);
        userMap.put("LOCATION", location);
        userMap.put("PHONE NUMBER", phonenumber);

        firebaseFirestore.collection("Employee").add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Employee_Registration_Activity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(Employee_Registration_Activity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });

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
                            Toast.makeText(Employee_Registration_Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Employee_Registration_Activity.this, "Could Not Register User", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == employee_activity_signup_btn){
            if(!validateUserName() | !validateEmail() | !validatePassword() | !validateLocation() | !validatePhoneNumber()){
                return;
            }
            registerUser();
        } else if (view == employee_activity_old_account_tv){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
        }
    }
}
