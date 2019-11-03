package com.ujjwalsingh.whatsappclone;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class GreatLog extends AppCompatActivity {

    static public int currentViewId=-1;
    static LockEditText password;
    static LinearLayout ll2;
    static LinearLayout origin;
    FirebaseAuth mAuth;
    Button button_logIn;
    EditText email;
    TextView sky,chat;
    TextView forgot_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentViewById(R.layout.activity_great_log);

        sky = (TextView) findViewById(R.id.newsky);
        chat = (TextView) findViewById(R.id.cha);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.monoton);
       // sky.setTypeface(typeface);
        //chat.setTypeface(typeface);
       // sky.setTypeface(sky.getTypeface(), Typeface.BOLD);
        //chat.setTypeface(chat.getTypeface(), Typeface.BOLD);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ll2 = findViewById(R.id.ll2);
        origin = findViewById(R.id.origin);

//        Toolbar toolbar =findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("LogIn");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        email =findViewById(R.id.email);
        password = (LockEditText) findViewById(R.id.password);
        button_logIn = findViewById(R.id.button_logIn);
        forgot_pass = findViewById(R.id.forgot_pass);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ObjectAnimator.ofFloat(origin, "translationY", -200f).setDuration(10).start();
                ObjectAnimator.ofFloat(ll2, "translationY", -200f).setDuration(10).start();
                return false;
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GreatLog.this,ForgotActivity.class));
            }
        });
        button_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u_email = email.getText().toString();
                String u_password = password.getText().toString();
                if (TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_password)) {
                    Toast.makeText(GreatLog.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(u_email, u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(GreatLog.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(GreatLog.this, "Failed to Login with these credentials", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
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

    public void setCurrentViewById(int id){
        setContentView(id);
        currentViewId=id;
    }

    static public int getCurrentViewById(){
        return currentViewId;
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            Log.i("eree","gera");
//
//            finish();
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }



}

