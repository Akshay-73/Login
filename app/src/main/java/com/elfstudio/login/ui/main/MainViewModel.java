package com.elfstudio.login.ui.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Context context;
    private MainHandler handler;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(MainHandler handler) {
        this.handler = handler;
    }
}
