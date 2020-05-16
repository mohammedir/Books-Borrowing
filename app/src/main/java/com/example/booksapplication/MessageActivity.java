package com.example.booksapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image_messageActivity;
    TextView username ;
    ImageView send_masseage ;
    EditText text_send ;

    MessageAdapter messageAdapter ;
    List<Model_Chat> mchats ;
    RecyclerView recyclerView ;

    Intent intent ;
    Uri urinotification;
    FirebaseUser user ;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        def();
       Toolbar toolbar = findViewById(R.id.toolbar_message);

        setSupportActionBar(toolbar);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        if (getSupportActionBar() != null){
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);
            this.getSupportActionBar().setTitle("");
        }

        recyclerView = findViewById(R.id.recyview_message);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference  = FirebaseDatabase.getInstance().getReference().child("user").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.child("name").getValue().toString());
                if (dataSnapshot.child("image").equals("default")){
                    profile_image_messageActivity.setImageResource(R.mipmap.ic_launcher);

                }else {
                    Glide.with(MessageActivity.this).load(dataSnapshot.child("image")).into(profile_image_messageActivity);

                }
                readMessages(user.getUid() , userid ,"default");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send_masseage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    send_message(user.getUid(),userid,msg);
                }else {
                    Toast.makeText(MessageActivity.this,"you can't send empty message", Toast.LENGTH_LONG).show();
                }
                text_send.setText("");
            }
        });
    }

    private void send_message(String sender , String receiver , String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);


    }
    private void readMessages(final String myid , final String userid , final String imageURL){
        mchats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchats.clear();
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    Model_Chat chat =Snapshot.getValue(Model_Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchats.add(chat);


                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this ,mchats , imageURL);
                    recyclerView.setAdapter(messageAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void def(){
        profile_image_messageActivity = findViewById(R.id.profile_image);
        username = findViewById(R.id.userName);
        send_masseage = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.edt_sentText);
        recyclerView = findViewById(R.id.recyview_message);
    }

}
