package com.example.mylib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// Emre - 180702004

public class AdapterBookList extends ArrayAdapter<Book> {

    public AdapterBookList(@NonNull Context context, ArrayList<Book> BookArrayList) {
        super(context, 0, BookArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        //To not display different book cover, we disabled this null check.
        //if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.bookslistviewitem, parent, false);
        //}
        Book book = getItem(position);
        TextView titleOfBook = listItemView.findViewById(R.id.titleOfBook);
        TextView authorOfBook = listItemView.findViewById(R.id.authorOfBook);
        Button buttonToShowBookAvailability = listItemView.findViewById(R.id.isAvailable);
        ImageView imgView = listItemView.findViewById(R.id.photoOfBook);
        imgView.setImageResource(R.drawable.book_default_image);
        new DownloadImageTask(imgView, book.getImagePath()).execute();
        titleOfBook.setText(book.getBookTitle());
        authorOfBook.setText(book.getAuthor());
        if(book.isAvailable()){
            buttonToShowBookAvailability.setBackgroundColor(Color.GREEN);
        } else {
            buttonToShowBookAvailability.setBackgroundColor(Color.RED);
        }
        //bindView(listItemView, getContext(), position);
        listItemView.setOnClickListener(v -> {
            Intent intentLibActivity = new Intent(getContext(),ViewBookActivity.class);
            intentLibActivity.putExtra("class", book);
            getContext().startActivity(intentLibActivity);
        });
        return listItemView;
    }

    // Another try out to displaying different photo of book.
    public void bindView(View view, Context context, int position) {
        ImageView imageView = view.findViewById(R.id.photoOfBook);
        imageView.setImageResource(R.drawable.book_default_image);

        Book book = getItem(position);

        TextView titleView = view.findViewById(R.id.titleOfBook);
        titleView.setText(book.getBookTitle());

        TextView authorView = view.findViewById(R.id.authorOfBook);
        authorView.setText(book.getAuthor());

        new DownloadImageTask(imageView, book.getImagePath()).execute();
    }
}