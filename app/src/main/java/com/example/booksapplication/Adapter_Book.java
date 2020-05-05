package com.example.booksapplication;

import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView bookname,booktype,To_communicate;
        private CardView bookCard;
        private ImageView deletImage ;
        public ViewHolder(View itemView) {
            super(itemView);
            bookCard=itemView.findViewById(R.id.book_card);
            bookname=itemView.findViewById(R.id.text_nameBook);
            booktype=itemView.findViewById(R.id.TypeBook);
            deletImage = itemView.findViewById(R.id.delet_book);
            To_communicate = itemView.findViewById(R.id.book_card_txt_To_communicate);
        }
    }
}

