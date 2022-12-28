package com.example.mylib;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Library> libraryArrayList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(R.string.app_name);
        //Try out to show the logo on the action bar.
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        listView = (ListView)findViewById(R.id.listViewOfLibraries);
        libraryArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        loadDataInListview();

        Button btn = (Button)findViewById(R.id.loginButton);
        Button btnAddBook = (Button)findViewById(R.id.addBookButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentUser = mAuth.getCurrentUser();
                if(mCurrentUser != null){
                    mAuth.getInstance().signOut();
                    Toast.makeText(HomeActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                    btnAddBook.setVisibility(View.INVISIBLE);
                    btn.setText("LOGIN");
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        });

        //Aylin Duran

        Button addButton = (Button)findViewById(R.id.addBookButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddBookActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrentUser = mAuth.getCurrentUser();
        Button userBtn = (Button)findViewById(R.id.addBookButton);
        if(mCurrentUser != null){
            Button btn = (Button)findViewById(R.id.loginButton);
            btn.setText("LOGOUT");
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String userId = mAuth.getUid();
            firestore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = document.toObject(User.class);
                            Boolean isUserOwnerOfLibrary = user.getLibraryOwner();
                            if(isUserOwnerOfLibrary){
                                userBtn.setVisibility(View.VISIBLE);
                                //Toast.makeText(HomeActivity.this, "Owner of a library, you can add book.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(HomeActivity.this, "Non owner, you can reserve a book.", Toast.LENGTH_SHORT).show();
                                userBtn.setVisibility(View.INVISIBLE);
                            }
                        } else {
                        }
                    } else {
                        Log.d(TAG, "Task is not successfull");
                    }
                }
            });
        } else {
            userBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void loadDataInListview() {
        db.collection("libraries").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Library library = d.toObject(Library.class);
                            libraryArrayList.add(library);
                        }
                        LibraryListAdapter adapter = new LibraryListAdapter(HomeActivity.this, libraryArrayList);
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(HomeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show());
    }
}