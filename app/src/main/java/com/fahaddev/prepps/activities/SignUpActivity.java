package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.college.CollegeSignUpActivity;
import com.fahaddev.prepps.activities.student.StudentSignUpActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    AppCompatButton btnStudent, btnCollege;
    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnCollege = findViewById(R.id.btnCollege);
        btnStudent = findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(this);
        btnCollege.setOnClickListener(this);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStudent:
                startActivity(new Intent(SignUpActivity.this, StudentSignUpActivity.class));
                break;
            case R.id.btnCollege:
                startActivity(new Intent(SignUpActivity.this, CollegeSignUpActivity.class));
                break;
            case R.id.goBack:
                finish();
                break;
        }
    }
}