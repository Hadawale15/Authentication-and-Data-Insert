package com.example.authenticationanddatainsert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText Email,Pass;
    Button login_button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email=findViewById(R.id.Email1_id);
        Pass=findViewById(R.id.Pass1_id);
        login_button=findViewById(R.id.Login_id);



    }
    public void RegisterMethod(View view) {
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }


    public void LoginMethod(View view) {
        String EMAIL=Email.getText().toString();
        String PASS=Pass.getText().toString();

        if (EMAIL.isEmpty()){
            Email.setError("Email can't be Empty");
            Email.requestFocus();
            return;
        }
        if (PASS.isEmpty()){
            Pass.setError("Enter Valid Password");
            Pass.requestFocus();
            return;
        }


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this,HomePage.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}