package com.ujjwalsingh.whatsappclone;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import static com.ujjwalsingh.whatsappclone.GreatLog.currentViewId;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button button_regsiter;
    boolean val;
    EditText username, email;
    static LockEditText rpassword;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentViewById(R.layout.activity_register);

        Log.i("regis","line 1");
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        Log.i("regis","line 2");
        username =findViewById(R.id.username);
        email =findViewById(R.id.email);
        rpassword =findViewById(R.id.password);
        button_regsiter = findViewById(R.id.button_register);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            String str = s.toString();
            String[] arrOfStr = str.split("@");
            if (arrOfStr[1].equals("iiitdmj.ac.in")){
                val=true;
                button_regsiter.setEnabled(true);
                }else {
                validit();
                val=false;
            }
            }
        });
        Log.i("regis","line 3");
//        Toolbar toolbar =findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Registration");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_regsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (val) {
                    String u_username = username.getText().toString();
                    String u_email = email.getText().toString();
                    String u_password = rpassword.getText().toString();
                    if (TextUtils.isEmpty(u_username) || TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_password)) {
                        Toast.makeText(RegisterActivity.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                    } else if (u_password.length() < 6) {
                        Toast.makeText(RegisterActivity.this, "Password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        register(u_username, u_email, u_password);
                    }
                }else
                    button_regsiter.setEnabled(false);
            }
        });
    }

    public void validit(){
        email.setError("Please use your college email");

    }

    public void register(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userUId = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userUId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userUId);
                    hashMap.put("username",username);
                    hashMap.put("imgUrl","default");
                    hashMap.put("status","offline");
                    hashMap.put("search",username.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                }else{
                    Toast.makeText(RegisterActivity.this, "Unable to imgUrl with this username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setCurrentViewById(int id){
        setContentView(id);
        currentViewId=id;
    }

    static public int getCurrentViewById(){
        return currentViewId;
    }

}
