package com.example.application_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    EditText regEmail;
    EditText regPassword;
    Button registerBtn;
    TextView signIn;
    FirebaseAuth fireAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fireAuth = FirebaseAuth.getInstance();

        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        signIn = findViewById(R.id.signinHere);

        registerBtn.setOnClickListener(view -> {
            createUser();

        });
        signIn.setOnClickListener(View -> {
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });
    }

    private void createUser(){
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            regEmail.setError("El correo electrónico no puede estar vacío");
            regEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            regPassword.setError("La palabra clave no puede estar vacía");
            regPassword.requestFocus();
        }else{
            fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Inscripción error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}