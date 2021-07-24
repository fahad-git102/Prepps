package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.APIModels.ResponsePasswordLink;
import com.fahaddev.prepps.APICall.ResponseLoginFile;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.college.CollegeHomeActivity;
import com.fahaddev.prepps.activities.student.StudentSignUpActivity;
import com.fahaddev.prepps.activities.student.StudentsHomeActivity;
import com.fahaddev.prepps.APICall.APIModels.LoginInformation;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    AppCompatButton btnLogin, btnSignUp;
    EditText etEmail, etPassword;
    TextView tvForgotPass;
    Call<ResponseLoginFile> responseLoginCall;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);

        String loginID = prefs.getString("LOGIN_ID", "");
        String loginPWD = prefs.getString("LOGIN_PWD", "");

        if (loginID.length()>0 && loginPWD.length()>0) {
            //YOUR LOGIN CODE
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnGotoSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGotoSignUp:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.btnLogin:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    if (TextUtils.isEmpty(email)){
                        etEmail.setError("Email Required !");
                        return;
                    }
                    if (TextUtils.isEmpty(password)){
                        etPassword.setError("Password Required !");
                        return;
                    }
                }

                login(email, password);

                break;
            case R.id.tvForgotPass:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View v = LayoutInflater.from(this).inflate(R.layout.reset_password_layout, null);
                Button btnCancel = v.findViewById(R.id.btnCancel);
                Button btnDone = v.findViewById(R.id.btnDone);
                EditText etEmail = v.findViewById(R.id.etEmail);
                builder.setView(v);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = etEmail.getText().toString();
                        if (email.isEmpty()){
                            return;
                        }
                        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("Please Wait...");
                        progressDialog.show();
                        Call<ResponsePasswordLink> responsePasswordLinkCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).resetPassword(email);
                        responsePasswordLinkCall.enqueue(new Callback<ResponsePasswordLink>() {
                            @Override
                            public void onResponse(Call<ResponsePasswordLink> call, Response<ResponsePasswordLink> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()){
                                    String message = response.body().getMessage();
                                    Toast.makeText(LoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsePasswordLink> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
                break;
        }
    }

    private void login(String email,String password){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        LoginInformation loginInformation = new LoginInformation();
        loginInformation.setEmail(email);
        loginInformation.setPassword(password);
        responseLoginCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).login(loginInformation);
        responseLoginCall.enqueue(new Callback<ResponseLoginFile>() {
            @Override
            public void onResponse(Call<ResponseLoginFile> call, Response<ResponseLoginFile> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if (response.body().getMessage().equals("success")){

                        if (response.body().getDataObject()!=null){
                            currentUser = response.body().getDataObject().getUser();
                            currentUser.setToken(response.body().getDataObject().getToken());
                            
                            if (currentUser.getType().equals("school")){
                                startActivity(new Intent(LoginActivity.this, StudentsHomeActivity.class));
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                finish();
                            }else {
                                startActivity(new Intent(LoginActivity.this, CollegeHomeActivity.class));
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                finish();
                            }
                        }else {
                            startActivity(new Intent(LoginActivity.this, StudentsHomeActivity.class));
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            finish();
                        }

                    }else {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginFile> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressDialog.dismiss();
            }
        });
    }

}