package com.example.booksapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Add_Book extends AppCompatActivity {

    EditText editText_namebook , editText_typebook , editText_describe , editText_communicate;
    Button ADD_Book ;
    private FirebaseAuth mAuth ;
    Model_Book book;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__book);

        def();

        ADD_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseReference mDatabaseBook = FirebaseDatabase.getInstance().getReference().child("book");

                if (!TextUtils.isEmpty(editText_namebook.getText().toString())||!TextUtils.isEmpty(editText_typebook.getText().toString())){

                    String key =  mDatabaseBook.push().getKey();

                    Model_Book model_book2 = new Model_Book(editText_namebook.getText().toString() , editText_typebook.getText().toString(),editText_describe.getText().toString(),editText_communicate.getText().toString() ,
                            mAuth.getCurrentUser().getUid(),key,"Available");
                   mDatabaseBook.child(key).setValue(model_book2);
                           Toast.makeText(Add_Book.this,"ADD Succesful",Toast.LENGTH_LONG).show();


                        }else {
                            Toast.makeText(Add_Book.this,"ADD error",Toast.LENGTH_LONG).show();

                        }

                    }



            });

    }


    private void def(){
        editText_namebook = findViewById(R.id.edt_add_book_name);
        editText_typebook = findViewById(R.id.edt_add_book_type);
        ADD_Book = findViewById(R.id.btn_addBook);
        editText_describe = findViewById(R.id.edt_add_book_describe);
        editText_communicate = findViewById(R.id.edt_To_communicate);
        mAuth = FirebaseAuth.getInstance();

    }
}
