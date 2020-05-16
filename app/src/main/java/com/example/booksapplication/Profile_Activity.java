package com.example.booksapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.math.BigInteger;
import java.security.SecureRandom;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth.AuthStateListener mListener ;
    private FirebaseAuth mAuth ;
    private int CAMERA_REQUEST_CODE = 0 ;
    private ProgressDialog progressDialog_profile ;
    private StorageReference storageReference ;
    private CircleImageView imageView ;
    private TextView textView_Name ,textView_emil , nav_header_profile ;
    Uri uri ;
    String imagUrl;

    private DatabaseReference databaseReference ;
    TextView tvEmail,tvName;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvEmail = headerView.findViewById(R.id.textView_nav_header_profile);
        tvName = headerView.findViewById(R.id.Name_nav_header_profile);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        def();
        //Picasso.with(Profile_Activity.this).load(imagUrl).fit().centerCrop().into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img_intent = new Intent();
                img_intent.setType("image/*");
                img_intent.setAction(Intent.ACTION_GET_CONTENT);
                if (img_intent.resolveActivity(getPackageManager()) != null){

                    startActivityForResult(Intent.createChooser(img_intent,"Select a picture for your profile"),CAMERA_REQUEST_CODE);
                }

            }
        });


        progressDialog_profile = new ProgressDialog(this);
        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    storageReference = FirebaseStorage.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            textView_Name.setText(dataSnapshot.child("name").getValue().toString());
                            textView_emil.setText(dataSnapshot.child("emil").getValue().toString());
                            imagUrl = dataSnapshot.child("image").getValue().toString();

                            if (dataSnapshot.child("emil").getValue().toString() != null ){
                                tvEmail.setText(dataSnapshot.child("emil").getValue().toString());
                            }

                            if (dataSnapshot.child("name").getValue().toString() != null){
                                 tvName.setText(dataSnapshot.child("name").getValue().toString());

                            }

                            if (!imagUrl.equals("default")|| TextUtils.isEmpty(imagUrl));
                           //Picasso.with(Profile_Activity.this).load(Uri.parse(dataSnapshot.child("image").getValue().toString())).into(imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    startActivity(new Intent(Profile_Activity.this , MainActivity.class));
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Edite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_books) {
            Intent List_Booke = new Intent(Profile_Activity.this,List_Book.class);
            startActivity(List_Booke);        }
        else if (id == R.id.nav_gallery) {
            Intent Show_all_books = new Intent(Profile_Activity.this,ShowAllBooks.class);
            startActivity(Show_all_books);
        } else if (id == R.id.nav_Add_Book) {
            Intent addbook = new Intent(Profile_Activity.this,Add_Book.class);
            startActivity(addbook);
        } else if (id == R.id.nav_logout) {
            if (mAuth.getCurrentUser() != null){
                mAuth.signOut();
            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public String getRandomString(){

        SecureRandom random = new SecureRandom();
        return new BigInteger(130,random).toString(32);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){


            if (mAuth.getCurrentUser() == null)
                return;

            progressDialog_profile.setMessage("Uploading image... ");
            progressDialog_profile.show();
            uri = data.getData();
            if (uri == null){
                progressDialog_profile.dismiss();
                return;
            }
            if (mAuth.getCurrentUser() == null)
                return;

            if (storageReference == null){
                storageReference = FirebaseStorage.getInstance().getReference();
            }
            if (databaseReference == null)
                databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
            final StorageReference filepath = storageReference.child("Photos").child(getRandomString());
            final DatabaseReference currentUserDB = databaseReference.child(mAuth.getCurrentUser().getUid());
            currentUserDB.child("image").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.getValue().toString();
                    if (!image.equals("default")|| !image.isEmpty()){


                       Task<Void> task = FirebaseStorage.getInstance().getReferenceFromUrl(image).delete();
                        task.addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Profile_Activity.this,"Deleted image succesfully ",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(Profile_Activity.this,"Deleted image failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });//currentUserDB
                        currentUserDB.child("image").removeEventListener(this);
                        filepath.putFile(uri).addOnSuccessListener(Profile_Activity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog_profile.dismiss();
                                Uri dow = taskSnapshot.getUploadSessionUri();
                                Toast.makeText(Profile_Activity.this,"Finished",Toast.LENGTH_LONG).show();
                                Picasso.with(Profile_Activity.this).load(uri).fit().centerCrop().into(imageView);
                                DatabaseReference currentUserDB = databaseReference.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.child("image").setValue(dow.toString());


                            }
                        }).addOnFailureListener(Profile_Activity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog_profile.dismiss();
                                Toast.makeText(Profile_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public  void def(){
        imageView = findViewById(R.id.img_profile);
        textView_Name = findViewById(R.id.txt_userName);
        mAuth = FirebaseAuth.getInstance();
        nav_header_profile = findViewById(R.id.textView_nav_header_profile);
        textView_emil = findViewById(R.id.txt_emil);



    }
}
