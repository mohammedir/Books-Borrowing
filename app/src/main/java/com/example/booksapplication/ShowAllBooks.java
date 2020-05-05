package com.example.booksapplication;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAllBooks extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private DatabaseReference databaseReference ;
    private FirebaseAuth mAuth ;
    private Adapter_all_Book adapter;
    ProgressDialog progressDialog;
    List<Model_Book> list = new ArrayList<>();
    private ImageView delet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_books);

        def();

        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();



        databaseReference = FirebaseDatabase.getInstance().getReference().child("book");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Model_Book bookDetails;
                bookDetails = new Model_Book();
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    bookDetails = dataSnapshot.getValue(Model_Book.class);

                    list.add(bookDetails);
                }





                adapter = new Adapter_all_Book( ShowAllBooks.this,list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });



    }

    private void def(){

        recyclerView = findViewById(R.id.recyView_show_all_books);
        mAuth = FirebaseAuth.getInstance();
        delet = findViewById(R.id.delet_book);

    }
}
