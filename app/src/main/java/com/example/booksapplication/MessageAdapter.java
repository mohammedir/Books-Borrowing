package com.example.booksapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0 ;
    public static final int MSG_TYPE_RIGTY = 1 ;

    private Context mcontext;
    private List<Model_Chat> mchats;
    private String imageurl ;

    FirebaseUser firebaseUser ;

    public MessageAdapter(Context mcontext , List<Model_Chat> mchats ,String imageurl){

        this.mcontext = mcontext ;
        this.mchats = mchats ;
        this.imageurl = imageurl ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGTY){
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right,viewGroup,false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left,viewGroup,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder Holder, int position) {
        Model_Chat chat = mchats.get(position);
        Holder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")){
            Holder.imageView_profile.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mcontext).load(imageurl).into(Holder.imageView_profile);
        }

    }

    @Override
    public int getItemCount() {
        return mchats.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

     public TextView show_message ;
     public ImageView imageView_profile ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            imageView_profile = itemView.findViewById(R.id.profile_image);
        }


    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchats.get(position).getSender().equals(firebaseUser.getUid())){

            return MSG_TYPE_RIGTY ;
        }else {
            return MSG_TYPE_LEFT ;
        }

    }
}
