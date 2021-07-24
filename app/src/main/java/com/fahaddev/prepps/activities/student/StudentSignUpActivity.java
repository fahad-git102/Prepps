package com.fahaddev.prepps.activities.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.APIModels.SignUpInformation;
import com.fahaddev.prepps.APICall.ResponseSignUpFile;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.LoginActivity;
import com.fahaddev.prepps.adapters.SportsSelectAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;
import com.fahaddev.prepps.models.User_detail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentSignUpActivity extends AppCompatActivity implements View.OnClickListener{

    TextView btnNext, btnSave, tvType, btnSkip;
    LinearLayout required, optional;
    ImageButton goBack;
    boolean optionalPage = false;
    TextView tvSportsName;
    RelativeLayout progress;
    Call<ResponseSignUpFile> responseSignUpFileCall;
    public static List<String> selectedSports;
    CheckBox checkBoxSports;
    String name, email, password, city, highSchool, gpa, awards, activity, clubs, careers, weight, height;
    EditText etName, etEmail, etPassword, etCity, etHighSchool, etGpa, etAwards, etActivities, etClubs, etCareers, etWeight, etHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        btnNext = findViewById(R.id.btnNext);
        btnSave = findViewById(R.id.btnSave);
        btnSkip = findViewById(R.id.btnSkip);
        progress = findViewById(R.id.progress);
        selectedSports = new ArrayList<>();
        tvSportsName = findViewById(R.id.tvSportsName);
        required = findViewById(R.id.required);
        optional = findViewById(R.id.optional);
        goBack = findViewById(R.id.goBack);
        checkBoxSports = findViewById(R.id.checkbox_sports);
        tvType = findViewById(R.id.type);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etCity = findViewById(R.id.etState);
        etHighSchool = findViewById(R.id.etHighSchool);
        etGpa = findViewById(R.id.etGPA);
        etAwards = findViewById(R.id.etAwards);
        etActivities = findViewById(R.id.etExtraActivity);
        etClubs = findViewById(R.id.etClubs);
        etCareers = findViewById(R.id.etPrograms);

        btnNext.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        goBack.setOnClickListener(this);

        List<String> stringList = new ArrayList<>();
        stringList.add("Baseball");
        stringList.add("Basketball");
        stringList.add("Football");
        stringList.add("Wrestling");
        stringList.add("Soccer");
        stringList.add("Track and Field");
        stringList.add("Hockey");
        stringList.add("Gymnastics");
        stringList.add("Tennis");
        stringList.add("Lacrosse");
        stringList.add("Softball");
        stringList.add("Volleyball");
        checkBoxSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentSignUpActivity.this);
                    View view = LayoutInflater.from(StudentSignUpActivity.this).inflate(R.layout.select_sports_dialog, null, false);
                    RecyclerView recyclerView = view.findViewById(R.id.recyceler_sports);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StudentSignUpActivity.this));
                    SportsSelectAdapter adapter = new SportsSelectAdapter(stringList, StudentSignUpActivity.this, StudentSignUpActivity.this);
                    recyclerView.setAdapter(adapter);
                    Button btnDone = view.findViewById(R.id.btnDone);
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    btnDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            String idList = selectedSports.toString();
                            String csv = idList.substring(1, idList.length() - 1).replace(", ", ", ");
                            tvSportsName.setText(csv);
                        }
                    });
                    alertDialog.show();
                }else {
                    checkBoxSports.setChecked(false);
                    tvSportsName.setText("");
                    selectedSports.clear();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:

                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                city = etCity.getText().toString();
                highSchool = etHighSchool.getText().toString();

                if (TextUtils.isEmpty(name)){
                    etName.setError("Name required !");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email required !");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    etPassword.setError("Password required !");
                    return;
                }
                if (TextUtils.isEmpty(city)){
                    etCity.setError("City required !");
                    return;
                }
                if (TextUtils.isEmpty(highSchool)){
                    etHighSchool.setError("High School required !");
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

                progress.setVisibility(View.VISIBLE);
                gpa = etGpa.getText().toString();
                awards = etAwards.getText().toString();
                activity = etActivities.getText().toString();
                clubs = etClubs.getText().toString();
                careers = etCareers.getText().toString();
                weight = etWeight.getText().toString();
                height = etHeight.getText().toString();

                SignUpInformation signUpInformation = new SignUpInformation(name, email, password, "school", city, city,
                        highSchool, height, weight, "", gpa, clubs, activity, careers);

                responseSignUpFileCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).signUp(signUpInformation);
                responseSignUpFileCall.enqueue(new Callback<ResponseSignUpFile>() {
                    @Override
                    public void onResponse(Call<ResponseSignUpFile> call, Response<ResponseSignUpFile> response) {
                        progress.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().getMessage().equals("success")){
                                Toast.makeText(StudentSignUpActivity.this, "Registered Successfully !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentSignUpActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                Toast.makeText(StudentSignUpActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(StudentSignUpActivity.this, "Network Failure.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSignUpFile> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(StudentSignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.btnSkip:
                gpa = "";
                awards = "";
                activity = "";
                clubs = "";
                careers = "";
                weight = "";
                height = "";
                progress.setVisibility(View.VISIBLE);

                SignUpInformation signUpInformation1 = new SignUpInformation(name, email, password, "school", city, city,
                        highSchool, height, weight, "", gpa, clubs, activity, careers);

                responseSignUpFileCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).signUp(signUpInformation1);
                responseSignUpFileCall.enqueue(new Callback<ResponseSignUpFile>() {
                    @Override
                    public void onResponse(Call<ResponseSignUpFile> call, Response<ResponseSignUpFile> response) {
                        progress.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            if (response.body().getMessage().equals("success")){
                                Toast.makeText(StudentSignUpActivity.this, "Registered Successfully !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentSignUpActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                Toast.makeText(StudentSignUpActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(StudentSignUpActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSignUpFile> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(StudentSignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}