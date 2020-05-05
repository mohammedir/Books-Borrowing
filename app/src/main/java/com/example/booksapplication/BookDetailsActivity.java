package com.example.booksapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity {

    TextView dec_book ;
    Model_Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dec_book = findViewById(R.id.txt_details);

        Intent intent = getIntent();
        if(intent!=null) {
             book = intent.getParcelableExtra("book");
            if (book.getDescribe() !=null) {
                dec_book.setText(book.getDescribe());
            }
        }

    }

}
