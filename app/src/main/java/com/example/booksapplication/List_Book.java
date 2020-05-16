package com.example.booksapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_Book extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private DatabaseReference databaseReference ;
    private FirebaseAuth mAuth ;
    FloatingActionButton fab ;
    private Adapter_Book adapter;
    ProgressDialog progressDialog;
    List<Model_Book> list = new ArrayList<>();
    private ImageView deletbutton ;
    private ChildEventListener mChildListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__book);

        def();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addbook = new Intent(List_Book.this,Add_Book.class);
                startActivity(addbook);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        toolbar.setTitle("my book");

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();


        //databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("Books");
        databaseReference =  FirebaseDatabase.getInstance().getReference().child("book");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Model_Book bookDetails;
                bookDetails = new Model_Book();
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    bookDetails = dataSnapshot.getValue(Model_Book.class);
                    if ( bookDetails.getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        list.add(bookDetails);
                    }
                }


                adapter = new Adapter_Book( List_Book.this,list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Model_Book book = dataSnapshot.getValue(Model_Book.class);
                book.setId(dataSnapshot.getKey());
                list.remove(book);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Model_Book book=dataSnapshot.getValue(Model_Book.class);
                book.setId(dataSnapshot.getKey());
                list.remove(book);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mChildListener);
    }



    private void delet(){

        databaseReference.removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Model_Book bookDetails;
                bookDetails = new Model_Book();
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    bookDetails = dataSnapshot1.getValue(Model_Book.class);
                    if ( bookDetails.getUser_id().equals(mAuth.getCurrentUser().getUid())){

                        dataSnapshot1.getRef().removeValue();

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void def(){

        recyclerView = findViewById(R.id.recyView_list);
        fab =  findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();
        deletbutton = findViewById(R.id.delet_book);

    }



}
