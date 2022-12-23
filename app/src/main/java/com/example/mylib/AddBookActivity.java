package com.example.mylib;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {
    private Uri selectedImageUri;
    private boolean mDateEditPopulated = false;
    private EditText dateEdt;
    private Spinner spinner;
    private FirebaseFirestore firestore;
    private Map<String, String> fullNameToIdMap;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Button selectPhotoBtn = (Button)findViewById(R.id.selectPhotoButton);
        Button addBookBtn = (Button)findViewById(R.id.btnAddBook);
        CheckBox isBookAvailable = (CheckBox)findViewById(R.id.isBookAlreadyBorrowed);
        EditText borrowedUntil = (EditText)findViewById(R.id.idEdtDate);
        dateEdt = findViewById(R.id.idEdtDate);
        borrowedUntil.setVisibility(View.GONE);

        spinner = findViewById(R.id.spinner);
        firestore = FirebaseFirestore.getInstance();

        fullNameToIdMap = new HashMap<>();

        // Retrieve the data from Firestore
        firestore.collection("libraries")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Convert the data to a list of full names and store the full names and ids in the map
                            List<String> fullNames = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String fullName = document.getString("fullName");
                                String id = document.getId();
                                fullNames.add(fullName);
                                fullNameToIdMap.put(fullName, id);
                            }
                            // Set up the spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBookActivity.this, android.R.layout.simple_spinner_item, fullNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(AddBookActivity.this, "Error while getting Library information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddBookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        selectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImageUri != null){
                    selectedImageUri = null;
                    Toast.makeText(AddBookActivity.this, "Photo deleted!", Toast.LENGTH_SHORT).show();
                    selectPhotoBtn.setText("Select Photo Of The Book");
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1001);
                }
            }
        });

        isBookAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDateEditPopulated){
                    borrowedUntil.setVisibility(View.GONE);
                    mDateEditPopulated = false;
                } else {
                    borrowedUntil.setVisibility(View.VISIBLE);
                    mDateEditPopulated = true;
                }
            }
        });

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book addedBook;
                String bookTitle = ((EditText) findViewById(R.id.bookTitle)).getText().toString();
                String bookAuthor = ((EditText) findViewById(R.id.author)).getText().toString();
                String bookGenre = ((EditText) findViewById(R.id.genre)).getText().toString();
                String bookAbout = ((EditText) findViewById(R.id.about)).getText().toString();
                String libraryChoosen = getSelectedLibraryId();
                boolean bookBorrowed = ((CheckBox) findViewById(R.id.isBookAlreadyBorrowed)).isChecked();

                if(selectedImageUri != null){
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference photoRef = storageRef.child("imgBook/" + selectedImageUri.getLastPathSegment());
                    // Upload the photo to Firebase Storage
                    photoRef.putFile(selectedImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get the download URL for the photo
                                    photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //photoPath = "imgBook/" + selectedImageUri.getLastPathSegment();
                                            //Toast.makeText(AddBookActivity.this, photoPath, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                }

                if(selectedImageUri != null){
                    Book addedBookWithImage = new Book(bookTitle,bookAuthor,bookGenre,bookAbout,libraryChoosen,!bookBorrowed,"imgBook/" + selectedImageUri.getLastPathSegment());
                    firestore.collection("books").add(addedBookWithImage);
                    Toast.makeText(AddBookActivity.this, "The book with photo added to" + getSelectedLibraryId(), Toast.LENGTH_SHORT).show();
                } else {
                    //If book is borrowed, that means it is not available...
                    addedBook = new Book(bookTitle,bookAuthor,bookGenre,bookAbout,libraryChoosen,!bookBorrowed,"123");
                    firestore.collection("books").add(addedBook);
                    Toast.makeText(AddBookActivity.this, "The book added to" + getSelectedLibraryId(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to retrieve the selected library's id
    private String getSelectedLibraryId() {
        String fullName = spinner.getSelectedItem().toString();
        return fullNameToIdMap.get(fullName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            Button selectPhotoBtn = (Button)findViewById(R.id.selectPhotoButton);
            selectedImageUri = data.getData();
            Toast.makeText(this, "Photo selected!", Toast.LENGTH_SHORT).show();
            selectPhotoBtn.setText("Delete Photo");
        }
    }
}

