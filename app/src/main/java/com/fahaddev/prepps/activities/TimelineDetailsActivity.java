package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.ResponseTimelineDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.APICall.TimelineModel;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.ArticleDetailActivity;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.ArticlesModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public class TimelineDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton goBack;
    CircleImageView profilePic;
    TextView tvUserName, tvCreatedAt, tvDesc, tvLastUpdated;
    ImageView image;
    float id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_details);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        id = getIntent().getFloatExtra("timeline", 0);
        profilePic = findViewById(R.id.profilePic);
        tvUserName = findViewById(R.id.tvUserName);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvDesc = findViewById(R.id.tvDesc);
        image = findViewById(R.id.image);
        getTimeline();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goBack:
                finish();
                break;
        }
    }

    private void getTimeline(){

                    TimelineModel timelineModel = getIntent().getParcelableExtra("timeline");
                    try {
                        if (timelineModel.getTitle()!=null){
                            tvDesc.setText(timelineModel.getTitle());
                        }
                        if (timelineModel.getUser_detail()!=null){
                            tvUserName.setText(timelineModel.getUser_detail().getName());
                        }
                        if (timelineModel.getImage()!=null){
                            Picasso.with(TimelineDetailsActivity.this).load(timelineModel.getImage()).placeholder(R.drawable.default_thumbnail).fit().into(image);
                        }
                        if (timelineModel.getCreated_at()!=null){
                            String credate = StaticClass.getFormattedDate(timelineModel.getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                    "dd MMM, yyyy hh:mm a");
                            if (credate!=null){
                                tvCreatedAt.setText(credate);
                            }
                        }

                        if (timelineModel.getUpdated_at()!=null){
                            String date = StaticClass.getFormattedDate(timelineModel.getUpdated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                    "dd MMM, yyyy hh:mm a");
                            if (date!=null){
                                tvLastUpdated.setText(date);
                            }
                        }


                    }catch (Exception e){
                        Toast.makeText(TimelineDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
    }
}