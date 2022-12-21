package com.example.mylib;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Library> libraryArrayList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView)findViewById(R.id.listViewOfLibraries);
        libraryArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        loadDataInListview();
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