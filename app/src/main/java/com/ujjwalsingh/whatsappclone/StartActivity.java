package com.ujjwalsingh.whatsappclone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity {
    Drawable background;
    GifImageView let;
    LinearLayout ll1;
    TextView sky, chate;
    LinearLayout ll2;
    //Button login,button_register;
    LinearLayout main;
    LinearLayout ll3;
    ExpandableRelativeLayout relativeLayout;
    Button button_logIn, button_register;
    FirebaseUser firebaseUser;



    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button_logIn = findViewById(R.id.login);
        button_register = findViewById(R.id.button_register);

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        let = findViewById(R.id.gif);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        sky = findViewById(R.id.sky);
        chate = findViewById(R.id.chate);
        ll3 = findViewById(R.id.ll3);
        main = findViewById(R.id.main);
        background= main.getBackground();

        let.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout = findViewById(R.id.out);
                if (!relativeLayout.isExpanded()) {
                    relativeLayout.setBackgroundResource(R.drawable.cutom);
                    relativeLayout.expand();
                    //relativeLayout.setBackgroundResource().d;
                    main.setBackgroundResource(R.drawable.skywhite);
                   // gradientDrawable.get
                    button_register.setBackgroundColor(Color.parseColor("#000000"));
                    button_logIn.setBackgroundColor(Color.parseColor("#3591C3"));
                    sky.setTextColor(Color.parseColor("#FFFFFF"));
                    chate.setTextColor(Color.parseColor("#41A9D7"));
                }
                else if ( relativeLayout.isExpanded()){
                    //ll1.setAlpha(1);
                    relativeLayout.setBackgroundResource(R.drawable.invert_custom);
                    ll2.setAlpha(1);
                    ll3.setAlpha(1);

                    button_logIn.setBackgroundColor(Color.parseColor("#000000"));
                    button_register.setBackgroundColor(Color.parseColor("#3591C3"));
                    chate.setTextColor(Color.parseColor("#FFFFFF"));
                    sky.setTextColor(Color.parseColor("#41A9D7"));

                    relativeLayout.collapse();
                    main.setBackground(background);
                }
            }
        });

        button_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,GreatLog.class);
                startActivity(intent);

            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent);            }
        });

    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
