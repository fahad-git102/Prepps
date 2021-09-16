package com.fahaddev.prepps.activities.college;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.APIModels.CollegeSignUpInformation;
import com.fahaddev.prepps.APICall.APIModels.ResponseCollegeSignUpInfo;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.StudentSignUpActivity;
import com.fahaddev.prepps.activities.student.StudentsHomeActivity;
import com.fahaddev.prepps.helpers.StaticClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollegeSignUpActivity extends AppCompatActivity implements View.OnClickListener{

    TextView btnNext, btnSave, tvType, btnSkip;
    LinearLayout required, optional;
    ImageButton goBack;
    FirebaseAuth mAuth;
    String name , email, password, aboutUs, location;
    EditText etName, etEmail, etPassword, etLocation, etAboutUs, etAwards, etAccreditation, etRanking;
    boolean optionalPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_sign_up);
        mAuth = FirebaseAuth.getInstance();
        btnNext = findViewById(R.id.btnNext);
        btnSave = findViewById(R.id.btnSave);
        btnSkip = findViewById(R.id.btnSkip);
        required = findViewById(R.id.required);
        optional = findViewById(R.id.optional);
        goBack = findViewById(R.id.goBack);
        tvType = findViewById(R.id.type);
        btnNext.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        goBack.setOnClickListener(this);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etLocation = findViewById(R.id.etLocation);
        etAboutUs = findViewById(R.id.etAboutUs);
        etAwards = findViewById(R.id.etAwards);
        etAccreditation = findViewById(R.id.etAccreditation);
        etRanking = findViewById(R.id.etRankings);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:

                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                location = etLocation.getText().toString();
                aboutUs = etAboutUs.getText().toString();
                if (name.isEmpty()||email.isEmpty()||password.isEmpty()||location.isEmpty()){
                    if (TextUtils.isEmpty(name)){
                        etName.setError("Field required !");
                    }
                    if (TextUtils.isEmpty(email)){
                        etEmail.setError("Field required !");
                    }
                    if (TextUtils.isEmpty(password)){
                        etPassword.setError("Field required !");
                    }
                    if (TextUtils.isEmpty(location)){
                        etLocation.setError("Field required !");
                    }
                    return;
                }

                required.setVisibility(View.GONE);
                optional.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                tvType.setText("(Optional)");
                btnSkip.setVisibility(View.VISIBLE);
                optionalPage = true;
                break;

            case R.id.goBack:
                if (optionalPage){
                    required.setVisibility(View.VISIBLE);
                    optional.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.GONE);
                    tvType.setText("(Required)");
                    btnSkip.setVisibility(View.GONE);
                    optionalPage = false;
                }else {
                    finish();
                }

                break;

            case R.id.btnSave:
                signUp();
                break;
            case R.id.btnSkip:
                signUp();
                break;
        }
    }

    private void signUp(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        CollegeSignUpInformation collegeSignUpInformation = new CollegeSignUpInformation();
        collegeSignUpInformation.setName(name);
        collegeSignUpInformation.setEmail(email);
        collegeSignUpInformation.setPassword(password);
        collegeSignUpInformation.setCollege_address(location);
        collegeSignUpInformation.setType("college");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Call<ResponseCollegeSignUpInfo> responseCollegeSignUpInfoCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).signUpCollege(collegeSignUpInformation);
                    responseCollegeSignUpInfoCall.enqueue(new Callback<ResponseCollegeSignUpInfo>() {
                        @Override
                        public void onResponse(Call<ResponseCollegeSignUpInfo> call, Response<ResponseCollegeSignUpInfo> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                if (response.body().getMessage().equals("success")){
//                                User_detail user_detail = response.body().getDataObject().getUser_detailObject();
                                    startActivity(new Intent(CollegeSignUpActivity.this, CollegeHomeActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(CollegeSignUpActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(CollegeSignUpActivity.this, "Network Failure.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseCollegeSignUpInfo> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(CollegeSignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(CollegeSignUpActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}