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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText Name_obj,Email_obj,Mobile_obj,Date_obj,Password_obj;
    Button button;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name_obj=findViewById(R.id.Name_id);
        Email_obj=findViewById(R.id.Email_id);
        Mobile_obj = findViewById(R.id.Mobile_id);
        Date_obj=findViewById(R.id.Dob_id);
        Password_obj=findViewById(R.id.Password1_id);
        button=findViewById(R.id.button_id);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME=Name_obj.getText().toString();
                String EMAIL=Email_obj.getText().toString();
                String MOBILE=Mobile_obj.getText().toString();
                String DATE=Date_obj.getText().toString();
                String PASS=Password_obj.getText().toString();

                if (NAME.isEmpty()){
                    Name_obj.setError("Name Can't be Empty");
                    Name_obj.requestFocus();
                    return;
                }

                if (EMAIL.isEmpty()){
                    Email_obj.setError("Email can't be Empty");
                    Email_obj.requestFocus();
                    return;
                }


                if (MOBILE.isEmpty()){
                    Mobile_obj.setError("Number Can't be Empty");
                    Mobile_obj.requestFocus();
                    return;
                }
                if (DATE.isEmpty()){
                    Date_obj.setError("Password can't be Empty");
                    Date_obj.requestFocus();
                    return;
                }
                if (PASS.isEmpty()){
                    Password_obj.setError("Enter Valid Password");
                    Password_obj.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Account crated Successfully", Toast.LENGTH_SHORT).show();

                        }
                        String ID=databaseReference.push().getKey();
                        Model model=new Model(ID,NAME,EMAIL,MOBILE,DATE,PASS);
                        databaseReference.child(NAME).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}