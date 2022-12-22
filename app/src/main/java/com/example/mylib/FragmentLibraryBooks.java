package com.example.mylib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FragmentLibraryBooks extends Fragment {

    ListView listView;
    ArrayList<Book> bookArrayList;
    FirebaseFirestore db;
    AdapterBookList bookListAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentLibraryBooks() {
        // Required empty public constructor
    }

    public static FragmentLibraryBooks newInstance(String param1, String param2) {
        FragmentLibraryBooks fragment = new FragmentLibraryBooks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library_books, container, false);
        db = FirebaseFirestore.getInstance();
        bookArrayList = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.listViewOfBooks);
        bookListAdapter = new AdapterBookList(getActivity(), bookArrayList);
        loadDataInListview();
        return view;
    }

    private void loadDataInListview() {

        db.collection("books").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Book book = d.toObject(Book.class);
                            bookArrayList.add(book);
                            bookListAdapter.notifyDataSetChanged();
                        }
                        listView.setAdapter(bookListAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Fail to load data..", Toast.LENGTH_SHORT).show());
    }
}