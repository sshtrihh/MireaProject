package com.example.MireaProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseActivity extends AppCompatActivity {
    private final String TAG = "Firebase";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText EtEmail, ETPassword;
    Button buttonSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        EtEmail = (EditText) findViewById(R.id.editViewEmail);
        ETPassword = (EditText) findViewById(R.id.editViewPassword);
        mAuth = FirebaseAuth.getInstance();
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                } else{

                }

            }
        };

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signing(EtEmail.getText().toString(), ETPassword.getText().toString());
            }
        });
    }

    public void signing(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent appIntent = new Intent(FirebaseActivity.this, MainActivity.class);
                    FirebaseActivity.this.startActivity(appIntent);
                } else {
                    Toast.makeText(FirebaseActivity.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}