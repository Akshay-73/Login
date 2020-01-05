package com.elfstudio.login.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseHandler {

    @Override
    public void showMsg(String msg) {
        Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
