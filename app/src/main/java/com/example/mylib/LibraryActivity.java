package com.example.mylib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LibraryActivity extends AppCompatActivity {

    Button infoFragment, bookFragment;

    Boolean isAboutFragmentOn;

    FragmentLibraryBooks flb = new FragmentLibraryBooks();
    FragmentLibraryAbout fla = new FragmentLibraryAbout();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        infoFragment = (Button) findViewById(R.id.infoFragmentItem);
        bookFragment = (Button) findViewById(R.id.bookFragmentItem);

        Library libraryPassed = (Library) getIntent().getSerializableExtra("class");

        TextView libraryFullName = this.findViewById(R.id.libraryName);
        ImageView imgView = this.findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load(libraryPassed.getImagePath()).into(imgView);

        libraryFullName.setText(libraryPassed.getFullName());

        loadInfoFragment(libraryPassed);

        infoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAboutFragmentOn){
                    loadInfoFragment(libraryPassed);
                }
            }
        });

        bookFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,flb).commit();
                isAboutFragmentOn = false;
            }
        });
    }

    private void loadInfoFragment(Library libraryPassed) {
        if(flb != null){
            getSupportFragmentManager().beginTransaction().remove(flb).commit();
        }
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fla = FragmentLibraryAbout.newInstance(libraryPassed.getAbout(), libraryPassed.getWorkingHours(), libraryPassed.getContactNumber(), libraryPassed.getMembershipNeeded(), libraryPassed.getStudyingAreaIncluded());
        fts.add(R.id.fragmentContainerView3,fla);
        isAboutFragmentOn = true;
        fts.commit();
    }

}