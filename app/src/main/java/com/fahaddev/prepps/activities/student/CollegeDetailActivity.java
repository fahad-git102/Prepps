package com.fahaddev.prepps.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.ResponseArticleDetail;
import com.fahaddev.prepps.APICall.ResponseCollegeDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.CollegeNavigatorModel;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollegeDetailActivity extends AppCompatActivity implements View.OnClickListener{

    CollegeNavigatorModel collegeNavigatorModel;
    ImageButton goBack;
    RelativeLayout progressBar;
    TextView collegeName, location, grad_ratio, size, campusHousing, population, underGradStudent,
            tuitionInState, tuitionOutState, tuitionFees, programsOffered;
    RelativeLayout btnProgramsOffered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        collegeName = findViewById(R.id.collegeName);
        location = findViewById(R.id.location);
        grad_ratio = findViewById(R.id.grad_ratio);
        size = findViewById(R.id.size);
        campusHousing = findViewById(R.id.campusHousing);
        tuitionFees = findViewById(R.id.tuitionFees);
        population = findViewById(R.id.population);
        underGradStudent = findViewById(R.id.underGradStudent);
        tuitionInState = findViewById(R.id.tuitionInState);
        tuitionOutState = findViewById(R.id.tuitionOutState);
        programsOffered = findViewById(R.id.programsOffered);
        btnProgramsOffered = findViewById(R.id.btnPrograms);
        btnProgramsOffered.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        int collegeId = getIntent().getIntExtra("college", 0);
        if (collegeId>0){
            progressBar.setVisibility(View.VISIBLE);
            Call<ResponseCollegeDetail> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getCollegeDetail(collegeId);
            call.enqueue(new Callback<ResponseCollegeDetail>() {
                @Override
                public void onResponse(Call<ResponseCollegeDetail> call, Response<ResponseCollegeDetail> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()){
                        collegeNavigatorModel = response.body().getData();
                        collegeName.setText(collegeNavigatorModel.getName());
                        location.setText(collegeNavigatorModel.getState());
                        grad_ratio.setText(collegeNavigatorModel.getGrad_ratio());
                        size.setText(collegeNavigatorModel.getSize());
                        campusHousing.setText(collegeNavigatorModel.getCampus_housing());
                        population.setText(collegeNavigatorModel.getPopulation());
                        underGradStudent.setText(collegeNavigatorModel.getUndergrad_student());
                        tuitionInState.setText(collegeNavigatorModel.getTuition_in_state());
                        tuitionOutState.setText(collegeNavigatorModel.getTuition_out_state());
                        if (collegeNavigatorModel.getTuition_fees().equals("")){
                            tuitionFees.setText("Not Provided");
                        }else {
                            tuitionFees.setText(collegeNavigatorModel.getTuition_fees());
                        }
                        programsOffered.setText(collegeNavigatorModel.getPrograms().size()+ " Programs Offered");
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CollegeDetailActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCollegeDetail> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CollegeDetailActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.goBack){
            finish();
        }else if (view.getId()==R.id.btnPrograms){
            Intent intent = new Intent(this, ProgramsCollegeActivity.class);
            intent.putParcelableArrayListExtra("programs", (ArrayList<? extends Parcelable>) collegeNavigatorModel.getPrograms());
            startActivity(intent);
        }
    }
}