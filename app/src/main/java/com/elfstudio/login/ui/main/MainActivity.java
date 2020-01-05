package com.elfstudio.login.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.elfstudio.login.R;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityMainBinding;
import com.elfstudio.login.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements MainHandler {

    ActivityMainBinding binding;
    private MainViewModel viewModel;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_main );
        viewModel = ViewModelProviders.of( this ).get( MainViewModel.class );
        binding.setViewModel( viewModel );

    }

    public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity( new Intent( getApplicationContext(), LoginActivity.class ) );
        finish();
    }
}
