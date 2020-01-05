package com.elfstudio.login.ui.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.elfstudio.login.ui.main.MainHandler;

public class LoginViewModel extends ViewModel {

    private Context context;
    private MainHandler handler;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(MainHandler handler) {
        this.handler = handler;
    }
}
