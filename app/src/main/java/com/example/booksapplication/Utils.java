package com.example.booksapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Utils {



    public static Task<Void> removeUser(String userId){

        Task<Void> task = FirebaseDatabase.getInstance().getReference("book")
                .child(userId)
                .removeValue();

        return task;
    }

}
