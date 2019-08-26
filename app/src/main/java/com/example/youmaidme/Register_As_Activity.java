package com.example.youmaidme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register_As_Activity extends AppCompatActivity implements View.OnClickListener{

    private ImageView employeriv;
    private ImageView employeeiv;

    private TextView employertv;
    private TextView employeetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_as_activity);

        employeriv = findViewById(R.id.register_as_activity_employer_iv);
        employertv = findViewById(R.id.register_as_activity_employer_tv);

        employeeiv = findViewById(R.id.register_as_activity_employee_iv);
        employeetv = findViewById(R.id.register_as_activity_employee_tv);


        employeriv.setOnClickListener(this);
        employertv.setOnClickListener(this);

        employeeiv.setOnClickListener(this);
        employeetv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == (employeriv)){
            finish();
            startActivity(new Intent(getApplicationContext(), Employer_Registration_Activity.class));
        } else if (view == employertv){
            finish();
            startActivity(new Intent(getApplicationContext(), Employer_Registration_Activity.class));
        } else if (view == employeeiv){
            finish();
            startActivity(new Intent(getApplicationContext(), Employee_Registration_Activity.class));
        } else if (view == employeetv){
            finish();
            startActivity(new Intent(getApplicationContext(), Employee_Registration_Activity.class));
        }
    }
}
