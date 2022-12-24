package com.example.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);


        Activity listItemView = null;
        ImageView imgView = listItemView.findViewById(R.id.photoInfo);
        imgView.setImageResource(R.drawable.book_default_image);

    }
}