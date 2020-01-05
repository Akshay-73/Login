package com.elfstudio.login.ui.register;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.elfstudio.login.ui.main.MainHandler;

public class RegisterViewModel extends ViewModel {

    private Context context;
    MainHandler handler;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(MainHandler handler) {
        this.handler = handler;
    }


}
