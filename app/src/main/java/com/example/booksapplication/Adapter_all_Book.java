package com.example.booksapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class Adapter_all_Book extends RecyclerView.Adapter<Adapter_all_Book.ALLViewHolder>  {

    private List<Model_Book> listData;
    private Context mContext;


    public Adapter_all_Book(Context mContext , List<Model_Book> listData) {
        this.listData = listData;
        this.mContext = mContext;

    }

        @NonNull
        @Override
        public ALLViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup,int i){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_all_list,viewGroup,false);
            return new ALLViewHolder(view);
        }

        @Override
        public void onBindViewHolder (@NonNull ALLViewHolder holder,int position){
            final Model_Book ld=listData.get(position);
            holder.bookname.setText(ld.getName_book());
            holder.booktype.setText(ld.getType_book());
            holder.commencat.setText(ld.getCommunicate());
            holder.bookCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),BookDetailsActivity.class);
                    intent.putExtra("book",ld);
                    v.getContext().startActivity(intent);

                }
            });
            holder.go_to_messageActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,MessageActivity.class);
                    intent.putExtra("userid",ld.getUser_id());
                    mContext.startActivity(intent);
                }
            });


            if ( ld.getAvailability().equals("Unavailable")){
                holder.bookCard.setCardBackgroundColor(Color.RED);
            }
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



        }

        @Override
        public int getItemCount () {
            return listData.size();

        }


        public class ALLViewHolder extends RecyclerView.ViewHolder {
            private TextView bookname, booktype,commencat;
            private CardView bookCard;
            private ImageView   go_to_messageActivity;

            public ALLViewHolder(View itemView) {
                super(itemView);
                bookCard = itemView.findViewById(R.id.ALLbook_card);
                bookname = itemView.findViewById(R.id.text_nameBook);
                booktype = itemView.findViewById(R.id.TypeBook);
                commencat = itemView.findViewById(R.id.txt_allbookcard_To_communicate);
                go_to_messageActivity = itemView.findViewById(R.id.go_to_chate);
            }
        }
    }

