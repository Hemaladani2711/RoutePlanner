package com.example.yagnikbhatt.routeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailView, mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mEmailView = findViewById(R.id.emialid);
        mPasswordView=findViewById(R.id.password);
        Button signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String email = mEmailView.getText().toString();
                String pass = mPasswordView.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    startActivity(new Intent(signup.this, LoginActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signup.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });
    }
}

