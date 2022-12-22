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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterBookList extends ArrayAdapter<Book> {

    public AdapterBookList(@NonNull Context context, ArrayList<Book> BookArrayList) {
        super(context, 0, BookArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.bookslistviewitem, parent, false);
        }
        Book book = getItem(position);
        TextView titleOfBook = listItemView.findViewById(R.id.titleOfBook);
        TextView authorOfBook = listItemView.findViewById(R.id.authorOfBook);
        Button buttonToShowBookAvailability = listItemView.findViewById(R.id.isAvailable);

        //ImageView imgView = listItemView.findViewById(R.id.photoOfLibrary);
        titleOfBook.setText(book.getTitle());
        authorOfBook.setText(book.getAuthor());
        if(book.isAvailable()){
            buttonToShowBookAvailability.setBackgroundColor(Color.GREEN);
        } else {
            buttonToShowBookAvailability.setBackgroundColor(Color.RED);
        }
        //Glide.with(getContext()).load(book.getImagePath()).into(imgView);
        listItemView.setOnClickListener(v -> {
            //Intent intentLibActivity = new Intent(getContext(),FragmentLibraryBooks.class);
            //intentLibActivity.putExtra("class", book);
            //getContext().startActivity(intentLibActivity);
            Toast.makeText(getContext(), book.getTitle() + "Clicked", Toast.LENGTH_SHORT).show();
        });
        return listItemView;
    }
}