package com.example.booksapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Book extends RecyclerView.Adapter<Adapter_Book.ViewHolder>  {
    private List<Model_Book> listData;
    private Context mContext;


    public Adapter_Book(Context mContext , List<Model_Book> listData) {
        this.listData = listData;
        this.mContext = mContext;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Model_Book ld=listData.get(position);
        holder.bookname.setText(ld.getName_book());
        holder.booktype.setText(ld.getType_book());
        holder.To_communicate.setText(ld.getCommunicate());
        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),BookDetailsActivity.class);
                intent.putExtra("book",ld);
                v.getContext().startActivity(intent);

            }
        });

        if (ld.getAvailability().equals("Unavailable")){
            holder.aSwitch.setChecked(true);
            holder.bookCard.setCardBackgroundColor(Color.RED);

        }/* else {
            holder.aSwitch.setChecked(false);
            holder.bookCard.setCardBackgroundColor(Color.GRAY);
        }*/

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String uid = ld.id ;

                if (holder.aSwitch.isChecked()){
                    final Task<Void> task = FirebaseDatabase.getInstance().getReference("book")
                            .child(uid).child("availability").setValue("Unavailable");


                    holder.bookCard.setCardBackgroundColor(Color.RED);

                }else {
                    final Task<Void> task = FirebaseDatabase.getInstance().getReference("book")
                            .child(uid).child("availability").setValue("available");
                    holder.bookCard.setCardBackgroundColor(Color.GRAY);

                }
            }
        });
       /* DatabaseReference task = (DatabaseReference) FirebaseDatabase.getInstance().getReference("book")
                .child(ld.getId()).child("availability").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String visb = dataSnapshot.getValue().toString();
                            if (visb.equals("Unavailable")){
                                holder.bookCard.setCardBackgroundColor(Color.RED);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/


      /*  holder.bookCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

               String uid = ld.id;

                Task<Void> voidTask = Utils.removeUser(uid);

                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       Toast.makeText(mContext, "User removed from database...", Toast.LENGTH_SHORT).show();

                    }
                });

                return true;
            }
        });*/



        holder.deletImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = ld.id ;

                Task<Void> voidTask = Utils.removeUser(uid);
                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "User removed from database...", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        holder.updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpDate.class);
                intent.putExtra("book",ld);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView bookname,booktype,To_communicate;
        private CardView bookCard , ALL_bookCard;
        private ImageView deletImage , updata;
        private Switch aSwitch ;
        public ViewHolder(View itemView) {
            super(itemView);
            bookCard=itemView.findViewById(R.id.book_card);
            bookname=itemView.findViewById(R.id.text_nameBook);
            booktype=itemView.findViewById(R.id.TypeBook);
            deletImage = itemView.findViewById(R.id.delet_book);
            To_communicate = itemView.findViewById(R.id.book_card_txt_To_communicate);
            aSwitch = itemView.findViewById(R.id.switch_listbook);
            ALL_bookCard = itemView.findViewById(R.id.ALLbook_card);
            updata = itemView.findViewById(R.id.updata);
        }
    }
}

