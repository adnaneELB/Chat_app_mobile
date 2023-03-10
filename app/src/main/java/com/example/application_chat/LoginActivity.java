package com.example.application_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class LoginActivity extends AppCompatActivity {
    EditText regEmail;
    EditText regPassword;
    Button loginBtn;
    TextView signUp;
    FirebaseAuth fireAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fireAuth = FirebaseAuth.getInstance();

        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signupHere);

        loginBtn.setOnClickListener(view -> {
            loginUser();

        });
        signUp.setOnClickListener(View -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });
    }

    private void loginUser(){
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            regEmail.setError("El correo electrónico no puede estar vacío");
            regEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            regPassword.setError("La palabra clave no puede estar vacía");
            regPassword.requestFocus();
        }else{
            fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Usuario conectado correctamente", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("email",email));

                    }else{
                        Toast.makeText(LoginActivity.this, "Iniciar sesión error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}