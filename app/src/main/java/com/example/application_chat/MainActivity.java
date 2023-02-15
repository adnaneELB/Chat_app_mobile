package com.example.application_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragments.Calls_Fragment;
import com.example.fragments.Contactos_Fragment;
import com.example.fragments.MapsFragment;
import com.example.fragments.Profile_Fragment;
import com.example.fragments.QrFragment;
import com.example.fragments.WebViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button logoutBtn;

    FirebaseAuth fireAuth;
    FirebaseUser firebaseUser;
    String userType="";
    Fragment selectedFragment;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        fireAuth = FirebaseAuth.getInstance();
        firebaseUser = fireAuth.getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("userType").exists() && dataSnapshot.child("userType").getValue(String.class).equals("premium")) {
                    userType = "premium";
                    setContentView(R.layout.activity_main_premium);
                    Toolbar materialToolbar=findViewById(R.id.P_toolbar);
                    setSupportActionBar(materialToolbar);
                    setLayout();
                } else {
                    userType = "basic";
                    setContentView(R.layout.activity_main);
                    Toolbar materialToolbar=findViewById(R.id.toolbar);
                    setSupportActionBar(materialToolbar);
                    setLayout();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
       switch (item.getItemId()){
           case R.id.qr_tab:
               selectedFragment = new QrFragment();break;

           case R.id.page_tab:
               selectedFragment = new WebViewFragment();break;

           case R.id.ajustes_tab:
               Toast.makeText(this, "ajustes", Toast.LENGTH_SHORT).show();
               return true;

       }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
        return super.onOptionsItemSelected(item);


    }

    void setLayout(){
        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

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
                    case R.id.mapa:

                        selectedFragment = new MapsFragment();

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




        }
    }
}
