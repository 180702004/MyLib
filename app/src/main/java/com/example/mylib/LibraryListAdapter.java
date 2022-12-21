package com.example.mylib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LibraryListAdapter extends ArrayAdapter<Library> {

    public LibraryListAdapter(@NonNull Context context, ArrayList<Library> LibraryArrayList) {
        super(context, 0, LibraryArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.librarylistviewitem, parent, false);
        }
        Library library = getItem(position);
        TextView libraryFullName = listItemView.findViewById(R.id.fullNameOfLibrary);
        ImageView imgView = listItemView.findViewById(R.id.photoOfLibrary);
        libraryFullName.setText(library.getFullName());
        Glide.with(getContext()).load(library.getImagePath()).into(imgView);
        listItemView.setOnClickListener(v -> {
            Intent intentLibActivity = new Intent(getContext(),LibraryActivity.class);
            intentLibActivity.putExtra("class", library);
            getContext().startActivity(intentLibActivity);
            //Toast.makeText(getContext(), library.getFullName() + "Clicked", Toast.LENGTH_SHORT).show();
        });
        return listItemView;
    }
}