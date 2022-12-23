package com.example.mylib;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFs = FirebaseFirestore.getInstance();

        Button btnCreate = (Button)findViewById(R.id.btnCreateAcc);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editEmail = (EditText)findViewById(R.id.editTextEmailReg);
                EditText editPw = (EditText)findViewById(R.id.editTextPasswordReg);
                EditText editPwConfirm = (EditText)findViewById(R.id.editTextConfirmPassword);
                EditText editFullName = (EditText)findViewById(R.id.editTextFullName);
                EditText editPhoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
                CheckBox editIsLibraryOwner = (CheckBox)findViewById(R.id.checkBoxOwner);
                String userEmail = editEmail.getText().toString();
                String userFullName = editFullName.getText().toString();
                String userPassword = editPw.getText().toString();
                String userPasswordConfirm = editPwConfirm.getText().toString();
                String userPhoneNumber = editPhoneNumber.getText().toString();
                Boolean userLibraryOwner = editIsLibraryOwner.isChecked();
                if(!userPassword.equals(userPasswordConfirm)){
                    Toast.makeText(RegisterActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userEmail.isEmpty() || userFullName.isEmpty() || userPassword.isEmpty() || userPhoneNumber.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "There should be no empty field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUp(userEmail, userPassword, userFullName, userPhoneNumber, userLibraryOwner);
            }
        });
    }

    public void signUp(String email, String password, String fullName, String phoneNumber, boolean isOwner){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            CollectionReference dbUsers = mFs.collection("users");
                            DocumentReference userDocumentRef = dbUsers.document(userId);
                            User registeredUser = new User(email, password, fullName, phoneNumber, isOwner);
                            userDocumentRef.set(registeredUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "You have been registered.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Fail to register user \n" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            if (isOwner){
                                Toast.makeText(RegisterActivity.this, "IS OWNER", Toast.LENGTH_SHORT).show();
                            } {
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed..",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}