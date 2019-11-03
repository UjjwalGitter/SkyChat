package com.ujjwalsingh.whatsappclone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ujjwalsingh.whatsappclone.Fragments.ChatsFragment;
import com.ujjwalsingh.whatsappclone.Fragments.ProfileFragment;
import com.ujjwalsingh.whatsappclone.Fragments.UsersFragment;
import com.ujjwalsingh.whatsappclone.Model.Chats;
import com.ujjwalsingh.whatsappclone.Model.Users;
import com.ujjwalsingh.whatsappclone.Notification.Data;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    CircleImageView circleImageView;
    TextView username;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        circleImageView = findViewById(R.id.profile_picture);
        username = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                    username.setText(users.getUsername());
                    if (users.getImgUrl().equals("default")) {
                        circleImageView.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(getApplicationContext()).load(users.getImgUrl()).into(circleImageView);
                    }
                //} catch (Exception e) {
                  //  e.printStackTrace();
                    Log.i("hybrid", users.toString());
               // }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager viewPager= findViewById(R.id.viewPager);

       reference = FirebaseDatabase.getInstance().getReference("Chats");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

               int unreadMsg = 0;
               for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                   Chats chat = snapshot.getValue(Chats.class);
                   if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()){
                       unreadMsg++;
                   }
               }
               if (unreadMsg==0){
                   viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
               }else{
               viewPagerAdapter.addFragment(new ChatsFragment(), "("+unreadMsg+") Chats");
           }
               viewPagerAdapter.addFragment(new UsersFragment(), "Users");
               viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");

               viewPager.setAdapter(viewPagerAdapter);
               tabLayout.setupWithViewPager(viewPager);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{


        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);
    }


    protected void onResume(){
        super.onResume();
        //if(firebaseUser != null) {
            status("online");
        //}
        //status("online");
    }

    protected void onPause(){
        super.onPause();
        //if(firebaseUser != null) {
            status("offline");
        }

}