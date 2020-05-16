package com.example.booksapplication;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    private EditText editText_email , editText_password ;
    private Button btn_Login ;
    private Button btn_register ;
    private ProgressDialog progressDialog_main ;
    private FirebaseAuth.AuthStateListener mListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        def();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_UP = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(sign_UP);
            }
        });
        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){

                    Toast.makeText(MainActivity.this,"Now you are logged In "+firebaseAuth.getCurrentUser().getUid(),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,Profile_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    private void doLogin() {

        String Email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)|| TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"some filed is empty",Toast.LENGTH_LONG).show();
            return;
        }


        if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(password)){
            progressDialog_main.setMessage("Login , please wait");
            progressDialog_main.show();
            mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog_main.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Login Succesful",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivity.this,"Error on Login ",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    private void def(){
        editText_email = findViewById(R.id.etUsername);
        editText_password = findViewById(R.id.etPassword);
        btn_Login = findViewById(R.id.btLogin);
        btn_register = findViewById(R.id.btn_sign_up);
        progressDialog_main = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
    }

}
