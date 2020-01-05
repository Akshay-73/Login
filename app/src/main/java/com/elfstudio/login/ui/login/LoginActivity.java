package com.elfstudio.login.ui.login;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.elfstudio.login.R;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityLoginBinding;
import com.elfstudio.login.ui.main.MainActivity;
import com.elfstudio.login.ui.main.MainHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends BaseActivity implements MainHandler {

    ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_login );
        viewModel = ViewModelProviders.of( this ).get( LoginViewModel.class );
        viewModel.setContext( getApplicationContext() );
        viewModel.setHandler( this );
        binding.setLoginViewModel( viewModel );

        firebaseAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.mail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();


                if (TextUtils.isEmpty( email )){
                    binding.mail.setError( "Email is Required" );
                    return;
                }

                if (TextUtils.isEmpty( password )){
                    binding.loginPassword.setError( "Password is Required" );
                    return;
                }

                if (password.length()<6){
                    binding.loginPassword.setError( "Password must be greater than 6 character" );
                }

                binding.progressBar2.setVisibility( View.VISIBLE );


                firebaseAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showMsg( "Login Successful" );
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                            binding.progressBar2.setVisibility( View.GONE );
                        }else {
                            Toast.makeText( LoginActivity.this,"Error !" + Objects.requireNonNull( task.getException() ).getMessage(),Toast.LENGTH_SHORT ).show();
                            binding.progressBar2.setVisibility( View.GONE );
                        }
                    }
                } );
            }
        } );
    }
}
