package com.elfstudio.login.api.retrofit;

import androidx.databinding.DataBindingUtil;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.elfstudio.login.R;
import com.elfstudio.login.api.Api;
import com.elfstudio.login.api.pojo.SignUpResponse;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityRetrofitSignupBinding;
import com.elfstudio.login.ui.main.MainHandler;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitSignUpActivity extends BaseActivity implements MainHandler {

    ActivityRetrofitSignupBinding binding;
    SignUpResponse signUpResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit_signup);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate(binding.fullName)&& ValidateEmail() && Validate(binding.password)){
                    SignUp();
                    binding.fullName.getText().clear();
                    binding.email.getText().clear();
                    binding.password.getText().clear();
                }

            }
        });

        binding.openListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisteredUsersActivity.class));
            }
        });
    }

    private boolean ValidateEmail(){
        String email = binding.email.getText().toString().trim();

        if (email.isEmpty()){
            binding.email.setError("Email is not valid");
            binding.email.requestFocus();
            return false;
        }

        return true;
    }

//    private static boolean isValidEmail(String email){
//        return !TextUtils.isEmpty(email) /* && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()*/;
//    }

    private boolean Validate(EditText editText){

        if (editText.getText().toString().trim().length() > 0){
            return true;
        }

        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void SignUp(){

        final ProgressDialog progressDialog = new ProgressDialog(RetrofitSignUpActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message here
        progressDialog.show(); // this will show the progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        (Api.getClient().registration(binding.fullName.getText().toString().trim(),
                                      binding.email.getText().toString().trim(),
                                      binding.password.getText().toString().trim(),
                                      "email")).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignUpResponse> call, @NotNull Response<SignUpResponse> response) {
                signUpResponseData = response.body();
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(Objects.requireNonNull(response).body()).getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<SignUpResponse> call, @NotNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                progressDialog.dismiss();

            }
        });

    }

}
