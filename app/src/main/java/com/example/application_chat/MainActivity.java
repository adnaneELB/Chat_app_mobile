package com.example.application_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button logoutBtn;
    FirebaseAuth fireAuth;
    FirebaseUser firebaseUser;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireAuth = FirebaseAuth.getInstance();
        firebaseUser = fireAuth.getCurrentUser();

        // check if user is not signed in
        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            // retrieve user type from Firebase database
            FirebaseFirestore.getInstance().collection("users")
                    .document(firebaseUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        userType = documentSnapshot.getString("type");

                        // inflate layout based on user type
                        if (userType.equals("standard")) {
                            setContentView(R.layout.activity_main_free);
                        } else {
                            setContentView(R.layout.activity_main_premium);
                        }

                        // find views and set up bottom navigation view
                        logoutBtn = findViewById(R.id.logoutBtn);
                        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

                        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
                            Fragment selectedFragment = null;

                            switch (menuItem.getItemId()) {
                                case R.id.nav_contacts:
                                    selectedFragment = new ContactsFragment();
                                    break;
                                case R.id.nav_calls:
                                    selectedFragment = new CallsFragment();
                                    break;
                                case R.id.nav_profile:
                                    selectedFragment = new ProfileFragment();
                                    break;
                                case R.id.nav_map:
                                    if (userType.equals("premium")) {
                                        selectedFragment = new MapFragment();
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
                                .replace(R.id.fragment_container, new ContactsFragment())
                                .commit();

                        logoutBtn.setOnClickListener(view -> {
                            fireAuth.signOut();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        });
                    });
        }
    }
}
