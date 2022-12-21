package com.example.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LibraryActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Library libraryPassed = (Library) getIntent().getSerializableExtra("class");

        TextView libraryFullName = this.findViewById(R.id.libraryName);
        TextView libraryAbout = this.findViewById(R.id.libraryAbout);
        TextView libraryHours = this.findViewById(R.id.libraryWorkingHours);
        TextView libraryContactNumber = this.findViewById(R.id.libraryContact);
        TextView libraryNeedsMembership = this.findViewById(R.id.libraryNeedsMembership);
        TextView libraryStudyingArea = this.findViewById(R.id.libraryStudyingArea);
        ImageView imgView = this.findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load(libraryPassed.getImagePath()).into(imgView);


        libraryFullName.setText(libraryPassed.getFullName());
        libraryAbout.setText(libraryPassed.getAbout());
        libraryAbout.setMovementMethod(new ScrollingMovementMethod());
        libraryContactNumber.setText(libraryPassed.getContactNumber());
        libraryHours.setText(libraryPassed.getWorkingHours());
        if (libraryPassed.isMembershipNeeded()){
            libraryNeedsMembership.setText("Yes");
        } else {
            libraryNeedsMembership.setText("No");
        }
        if (libraryPassed.isStudyingAreaIncluded()){
            libraryStudyingArea.setText("Yes");
        } else {
            libraryStudyingArea.setText("No");
        }
    }
}