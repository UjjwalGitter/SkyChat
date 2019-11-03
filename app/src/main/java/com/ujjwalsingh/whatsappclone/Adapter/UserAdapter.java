package com.ujjwalsingh.whatsappclone.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ujjwalsingh.whatsappclone.MessageActivity;
import com.ujjwalsingh.whatsappclone.Model.Chats;
import com.ujjwalsingh.whatsappclone.Model.Users;
import com.ujjwalsingh.whatsappclone.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mcontext;
    private List<Users> musers;
    private String screen;
    String lastMessage;
    private boolean isChat;

    public UserAdapter(Context mcontext, List<Users> musers, String screen, boolean isChat) {
        this.mcontext = mcontext;
        this.musers = musers;
        this.screen = screen;
        this.isChat = isChat;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (screen == "user") {
            view = LayoutInflater.from(mcontext).inflate(R.layout.user_item, viewGroup, false);
        } else
            view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Users user = musers.get(i);
        viewHolder.username.setText(user.getUsername());

        if (user.getImgUrl().equals("default")) {
            viewHolder.profile_picture.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mcontext).load(user.getImgUrl()).into(viewHolder.profile_picture);
        }

        if (isChat){
            last(user.getId(),viewHolder.last_message);
        }else{
            viewHolder.last_message.setVisibility(View.GONE);
        }

        if (isChat) {
            if (user.getStatus().equals("online")) {
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            } else {
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.img_off.setVisibility(View.GONE);
            viewHolder.img_on.setVisibility(View.GONE);

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return musers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_picture;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_message;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_picture = itemView.findViewById(R.id.profile_picture);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_message = itemView.findViewById(R.id.last_message);
//            username = itemViewfindViewWithId(R.id.username);
//            profile_picture = itemView.findViewWithTag(R.id.profile_picture);
        }
    }

    private void last(final String userid, final TextView last_message) {

        lastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        if (firebaseUser != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chats chat = snapshot.getValue(Chats.class);
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getSender().equals(firebaseUser.getUid()) && chat.getReceiver().equals(userid)) {
                            lastMessage = chat.getMessage();

                        }
                    }
                    switch (lastMessage) {
                        case "default":
                            last_message.setText("No message");
                            break;
                        default:
                            last_message.setText(lastMessage);
                            break;
                    }
                    lastMessage = "default";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}




