package com.example.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        if(!book.isAvailable()){
            Button reserveBookButton = (Button) findViewById(R.id.button);
            reserveBookButton.setEnabled(false);
            reserveBookButton.setText("Book is not available for reserve");
        }
    }
}