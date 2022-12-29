package com.example.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

// Emre 180702004

public class ViewBookActivity extends AppCompatActivity {
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        book = (Book) getIntent().getSerializableExtra("class");

        TextView bookGenre = findViewById(R.id.bookGenreInfo);
        bookGenre.setText(book.getGenre());

        ImageView imgView = findViewById(R.id.bookPhotoInfo);
        if (imgView != null) {
            imgView.setImageResource(R.drawable.book_default_image);
        }

        TextView bookTitle = findViewById(R.id.bookTitleInfo);
        bookTitle.setText(book.getBookTitle());
        TextView bookAbout = findViewById(R.id.bookAboutInfo);
        bookAbout.setText(book.getAbout());
        bookAbout.setMovementMethod(new ScrollingMovementMethod());
        TextView bookAuthor = findViewById(R.id.bookAuthorInfo);
        bookAuthor.setText(book.getAuthor());
        if (imgView != null) {
            new DownloadImageTask(imgView, book.getImagePath()).execute();
        }

        Button reserveBookButton = (Button) findViewById(R.id.button);
        if(!book.isAvailable()){
            reserveBookButton.setEnabled(false);
            reserveBookButton.setText("Book is not available for reserve");
        }

        reserveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book.isAvailable()){
                    reserveBookButton.setEnabled(false);
                    reserveBookButton.setText("You've reserved the book for a day");
                }
            }
        });
    }
}