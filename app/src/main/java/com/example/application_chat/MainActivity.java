package com.example.application_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragments.Calls_Fragment;
import com.example.fragments.Contactos_Fragment;
import com.example.fragments.MapsFragment;
import com.example.fragments.Profile_Fragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button logoutBtn;
    FirebaseAuth fireAuth;
    FirebaseUser firebaseUser;
    String userType;
    Fragment selectedFragment;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_premium);
        fireAuth = FirebaseAuth.getInstance();
        firebaseUser = fireAuth.getCurrentUser();

     /* selectedFragment = new Profile_Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();*/

        // check if user is not signed in


        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

            // retrieve user type from Firebase database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("userType").exists() && dataSnapshot.child("userType").getValue(String.class).equals("premium")) {
                        userType = "premium";
                    } else {
                        userType = null;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });


            // inflate layout based on user type
                     /*   if (userType.equals("standard")) {
                            setContentView(R.layout.activity_main_free);
                        } else {
                            setContentView(R.layout.activity_main_premium);
                        }

                        // find views and set up bottom navigation view
                        logoutBtn = findViewById(R.id.logoutBtn);*/
                        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

                        bottomNav.setOnItemSelectedListener(menuItem -> {


                            Fragment selectedFragment = null;
                            switch (menuItem.getItemId()) {
                                case R.id.contactos_Fragment:

                                    selectedFragment = new Contactos_Fragment();
                                    break;
                                case R.id.calls_Fragment:

                                    selectedFragment = new Calls_Fragment();
                                    break;
                                case R.id.profile_Fragment:

                                    selectedFragment = new Profile_Fragment();
                                    break;
                               case R.id.nav_map:
                                    if (userType.equals("premium")) {
                                        selectedFragment = new MapsFragment();
                                    }
                                    break;
                            }

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();

                            return true;
                        });

                        // set default fragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new Contactos_Fragment())
                                .commit();

                     /*   logoutBtn.setOnClickListener(view -> {
                            fireAuth.signOut();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        });*/
                    });
        }
    }
}
