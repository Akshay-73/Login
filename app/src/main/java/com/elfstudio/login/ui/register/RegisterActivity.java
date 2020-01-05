package com.elfstudio.login.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.elfstudio.login.R;
import com.elfstudio.login.RxJava_Retrofit.retrofit.SecondActivity;
import com.elfstudio.login.api.retrofit.CommentResultActivity;
import com.elfstudio.login.api.retrofit.PostResultActivity;
import com.elfstudio.login.api.retrofit.RetrofitSignUpActivity;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityRegisterBinding;
import com.elfstudio.login.google_maps.MapsActivity;
import com.elfstudio.login.ui.firebase_data.TeacherActivity;
import com.elfstudio.login.ui.login.LoginActivity;
import com.elfstudio.login.ui.main.MainActivity;
import com.elfstudio.login.ui.main.MainHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends BaseActivity implements MainHandler {

    // variables
    private FirebaseAuth firebaseAuth;
    ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_register );
        RegisterViewModel viewModel = ViewModelProviders.of( this ).get( RegisterViewModel.class );
        viewModel.setContext( getApplicationContext() );
        viewModel.setHandler( this );
        binding.setRegisterViewModel( viewModel );
        init();
        firebaseAuth = FirebaseAuth.getInstance();
        registerUser();

    }

    private void init() {

        binding.tvAlreadyAccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), LoginActivity.class ) );
            }
        } );

        binding.openData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), TeacherActivity.class ) );
            }
        } );

        binding.openMap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( getApplicationContext(), MapsActivity.class ) );
            }
        } );

        binding.openRetrofit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( getApplicationContext(), SecondActivity.class ) );
            }
        } );

        binding.openComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( getApplicationContext(), CommentResultActivity.class ) );
            }
        } );

        binding.openPost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( getApplicationContext(), PostResultActivity.class ) );
            }
        } );

        binding.openRetrofitSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), RetrofitSignUpActivity.class ) );
            }
        } );
    }

    private void registerUser() {

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
        }

        binding.registerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();


                if (TextUtils.isEmpty( email )) {
                    binding.email.setError( "Email is Required" );
                    return;
                }

                if (TextUtils.isEmpty( password )) {
                    binding.password.setError( "Password is Required" );
                    return;
                }

                if (password.length() < 6) {
                    binding.password.setError( "Password must be greater than 6 character" );
                }

                binding.progressBar.setVisibility( View.VISIBLE );

                firebaseAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            showMsg( "Account Created" );
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                            binding.progressBar.setVisibility( View.GONE );
                        } else {
                            showMsg( "Can not create Account" );
                            binding.progressBar.setVisibility( View.GONE );
                        }
                    }
                } );
            }
        } );


    }
}
