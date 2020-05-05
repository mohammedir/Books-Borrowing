package com.example.booksapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText input_Name , input_Email , input_Password , retype_input_password;
    Button btn_sign_up ;
    ImageButton goto_login ;
    private FirebaseAuth mAuth ;
    private ProgressDialog progressDialog ;
    private FirebaseAuth.AuthStateListener mListener ;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        def();

        progressDialog = new ProgressDialog(this);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    Intent intent_to_login = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent_to_login);
                    finish();
                }
            }
        };
        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_login = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(goto_login);
                finish();
            }
        });
    }

    private void startRegister() {

        final String name = input_Name.getText().toString().trim();
        final String email = input_Email.getText().toString().trim();
        final String password = input_Password.getText().toString().trim();
        final String Retype_password = retype_input_password.getText().toString().trim();

        if (TextUtils.isEmpty(name)||name.length()<3){
            input_Name.setError("at least 3 characters");
        }

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.this,"some filed is empty",Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(Retype_password)){
            Toast.makeText(SignUpActivity.this,"password and Retype password is not equals",Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            progressDialog.setMessage("Registering, please wait...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        mAuth.signInWithEmailAndPassword(email,password);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
                        DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                        currentUserDB.child("name").setValue(name);
                        currentUserDB.child("image").setValue("default");
                        currentUserDB.child("emil").setValue(email);
                        currentUserDB.child("password").setValue(password);
                        //currentUserDB.child("Books").setValue("default");


                    }else{

                        Toast.makeText(SignUpActivity.this,"error",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void def(){

        input_Name = findViewById(R.id.sign_up_input_name);
        input_Email = findViewById(R.id.sign_up_input_email);
        input_Password = findViewById(R.id.sign_up_input_password);
        retype_input_password =findViewById(R.id.Retype_sign_up_input_password);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        goto_login = findViewById(R.id.goto_login);
        mAuth = FirebaseAuth.getInstance();


    }

}
