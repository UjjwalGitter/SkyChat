package com.ujjwalsingh.whatsappclone;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import static com.ujjwalsingh.whatsappclone.GreatLog.getCurrentViewById;
import static com.ujjwalsingh.whatsappclone.GreatLog.ll2;
import static com.ujjwalsingh.whatsappclone.GreatLog.origin;
import static com.ujjwalsingh.whatsappclone.GreatLog.password;

public class LockEditText extends AppCompatEditText {
    /* Must use this constructor in order for the layout files to instantiate the class properly */

    public LockEditText(Context context) {
        super(context);
    }

    public LockEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public LockEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (getCurrentViewById()==R.layout.activity_great_log) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                Log.i("speae", "Login");
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
                ObjectAnimator.ofFloat(origin, "translationY", 0f).setDuration(10).start();
                ObjectAnimator.ofFloat(ll2, "translationY", 0f).setDuration(10).start();
            password.clearFocus();
           // ll2.setFocusable(true);
            }
            return true;
        }
        else if (getCurrentViewById()==R.layout.activity_register){
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                Log.i("speae","REgister");
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
                ObjectAnimator.ofFloat(origin, "translationY", 0f).setDuration(10).start();
                ObjectAnimator.ofFloat(ll2, "translationY", 0f).setDuration(10).start();
                RegisterActivity.rpassword.clearFocus();
            }
        return true;  // So it is not propagated.
        }
        return super.dispatchKeyEvent(event);
    }

    public interface OnKeyboardDownListener{
        void onKeyDown();
    }

    public void setListener(OnKeyboardDownListener listener){

    }




}