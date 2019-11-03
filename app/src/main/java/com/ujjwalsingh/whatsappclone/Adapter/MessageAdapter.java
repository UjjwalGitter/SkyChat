package com.ujjwalsingh.whatsappclone.Adapter;

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
import com.ujjwalsingh.whatsappclone.Model.Chats;
import com.ujjwalsingh.whatsappclone.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int msgTypeLeft = 0;
    public static final int msgTypeRight = 1;
    private Context mcontext;
    private List<Chats> mchat;
    private String imgUrl;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context mcontext, List<Chats> mchat, String imgUrl) {
        this.mcontext = mcontext;
        this.mchat = mchat;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == msgTypeRight) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, final int i) {
        Chats chats = mchat.get(i);
        viewHolder.show_msg.setText(chats.getMessage());

        if(imgUrl.equals("default")){
            viewHolder.profile_picture.setImageResource(R.mipmap.ic_launcher);
        }else
            Glide.with(mcontext).load(imgUrl).into(viewHolder.profile_picture);

        if (i==mchat.size()-1){
            if(chats.isIsseen()){
                viewHolder.text_seen.setText("Seen");
            }else {
                viewHolder.text_seen.setText("Delivered");
            }
        }else
            viewHolder.text_seen.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_msg;
        public ImageView profile_picture;
        public TextView text_seen;

        public ViewHolder(View itemView) {
            super(itemView);
            show_msg = itemView.findViewById(R.id.show_msg);
            profile_picture = itemView.findViewById(R.id.profile_picture);
            text_seen = itemView.findViewById(R.id.text_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(firebaseUser.getUid()))
            return msgTypeRight;
        else {
            return msgTypeLeft;
        }
    }
}