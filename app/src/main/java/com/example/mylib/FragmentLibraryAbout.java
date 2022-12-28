package com.example.mylib;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//Aylin Duran

public class FragmentLibraryAbout extends Fragment {

    String about;
    String hours;
    String contact;
    String membership;
    String studyingarea;

    public static FragmentLibraryAbout newInstance(String param1, String param2, String param3, String param4, String param5) {
        FragmentLibraryAbout fragment = new FragmentLibraryAbout();
        Bundle args = new Bundle();
        args.putString("1", param1);
        args.putString("2", param2);
        args.putString("3", param3);
        args.putString("4", param4);
        args.putString("5", param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            about = getArguments().getString("1");
            hours = getArguments().getString("2");
            contact = getArguments().getString("3");
            membership = getArguments().getString("4");
            studyingarea = getArguments().getString("5");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_library_about, container, false);

        TextView libraryAbout = (TextView) view.findViewById(R.id.libraryAbout);
        TextView libraryHours = (TextView) view.findViewById(R.id.libraryWorkingHours);
        TextView libraryContactNumber = (TextView) view.findViewById(R.id.libraryContact);
        TextView libraryNeedsMembership = (TextView) view.findViewById(R.id.libraryNeedsMembership);
        TextView libraryStudyingArea = (TextView) view.findViewById(R.id.libraryStudyingArea);


        //String textLibraryAbout = bundle.getString("1");
        //String textLibraryHours = bundle.getString("2");
        //String textLibraryContactNumber = bundle.getString("3");
        //String textLibraryNeedsMembership = bundle.getString("4");
        //String textLibraryStudyingArea = bundle.getString("5");
        //boolean textLibraryNeedsMembership = bundle.getBoolean("4");
        //boolean textLibraryStudyingArea = bundle.getBoolean("5");

        libraryAbout.setText(about);
        libraryAbout.setMovementMethod(new ScrollingMovementMethod());
        libraryContactNumber.setText(contact);
        libraryHours.setText(hours);
        libraryNeedsMembership.setText(membership);
        libraryStudyingArea.setText(studyingarea);

        return view;
    }
}