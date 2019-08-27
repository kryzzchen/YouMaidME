package com.example.youmaidme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Client_Profile_Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView client_profile;
    TextView txtview_name, txtview_email, txtview_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__profile_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Employee");

        client_profile = findViewById(R.id.client_profile);
        txtview_name = findViewById(R.id.txtview_name);
        txtview_email = findViewById(R.id.txtview_email);
        txtview_phone = findViewById(R.id.txtview_phone);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String name = ""+ ds.child("USERNAME").getValue();
                    String email = ""+ ds.child("EMAIL").getValue();
                    String phone = ""+ ds.child("PHONE NUMBER").getValue();
                    String image = ""+ ds.child("IMAGE").getValue();

                    txtview_name.setText(name);
                    txtview_email.setText(email);
                    txtview_phone.setText(phone);
                    try{
                        Picasso.get().load(image).into(client_profile);
                    }
                    catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_add_image).into(client_profile);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
