package com.example.cuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private TextView tvBanner, tvRegister;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        TextView txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        etEmail = findViewById(R.id.etPassword);
        etPassword = findViewById(R.id.etConfirmPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,StartActivity.class));
//                Login();
            }
        });
        tvRegister = findViewById(R.id.txtRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }
    private void Login(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Khong the them vi thong tin thieu",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this,StartActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"Khong the them vi loi",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}