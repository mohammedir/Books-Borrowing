package com.example.booksapplication;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Utils {



    public static Task<Void> removeUser(String userId){

        Task<Void> task = FirebaseDatabase.getInstance().getReference("book")
                .child(userId)
                .removeValue();

        return task;
    }



}
