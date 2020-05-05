package com.example.booksapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView nameBook , typeBook ;
    View mview ;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view , getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });

    }
    public void setDetails(Context ctx , String name , String type){
        nameBook = itemView.findViewById(R.id.text_nameBook);
        typeBook = itemView.findViewById(R.id.TypeBook);

        nameBook.setText(name);
        typeBook.setText(type);
    }
    private FirebaseViewHolder.ClickListener mClickListener ;

    public interface ClickListener {

        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }
    public void setOnClickListener(FirebaseViewHolder.ClickListener clickListener){
        mClickListener = clickListener ;
    }
}
