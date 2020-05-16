package com.example.booksapplication;

import android.content.Intent;
import android.icu.lang.UProperty;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class UpDate extends AppCompatActivity {


    EditText editText_namebook , editText_typebook , editText_describe , editText_communicate;
    Button ADD_Book ;
    private FirebaseAuth mAuth ;
    Model_Book book;
    private DatabaseReference databaseReference ;
    private String vis ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data);
        def();


       Intent intent = getIntent();
        if(intent!=null) {
            book = intent.getParcelableExtra("book");
            if (book.getName_book() !=null) {
                editText_namebook.setText(book.getName_book());
            }if (book.getType_book() != null){
                editText_typebook.setText(book.getType_book());
            }if (book.getDescribe() != null){
                editText_describe.setText(book.getDescribe());
            }

        }
        String uid = book.id ;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("book").child(uid);

        ADD_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        databaseReference.child("availability").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vis = dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* Intent vislab = new Intent(UpDate.this, Adapter_Book.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)vis);
        intent.putExtra("BUNDLE",args);
        startActivity(intent);
*/

    }

    public void update(){
        if (isNameChanged() || isTypeChange()){
            Toast.makeText(UpDate.this,"Done",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(UpDate.this,"not Done",Toast.LENGTH_LONG).show();

        }
    }

    private boolean isTypeChange() {
        if (!book.getType_book().equals(editText_typebook.getText().toString())){
            databaseReference.child("type_book").setValue(editText_typebook.getText().toString());
            return true ;
        }else {
            return false ;
        }

    }

    private boolean isNameChanged() {
        if (!book.getName_book().equals(editText_namebook.getText().toString())){
            databaseReference.child("name_book").setValue(editText_namebook.getText().toString());
            return true ;
        }else {
            return false ;
        }
    }


    private void def(){
        editText_namebook = findViewById(R.id.edt_Updata_book_name);
        editText_typebook = findViewById(R.id.edt_Updata_book_type);
        ADD_Book = findViewById(R.id.btn_UpdataBook);
        editText_describe = findViewById(R.id.edt_Updata_book_describe);
        editText_communicate = findViewById(R.id.edt_UpdataTo_communicate);
        mAuth = FirebaseAuth.getInstance();

    }
}


