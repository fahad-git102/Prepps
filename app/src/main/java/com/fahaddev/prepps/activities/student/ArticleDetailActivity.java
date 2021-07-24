package com.fahaddev.prepps.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.ResponseArticleDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.ArticlesModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public class ArticleDetailActivity extends AppCompatActivity {

    float id;
    TextView tvUserName, tvCreatedAt, tvLastUpdated, tvTitle, tvDescription;
    ImageView imageMain;
    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        id = getIntent().getFloatExtra("article", 0);
        goBack = findViewById(R.id.goBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvUserName = findViewById(R.id.tvUserName);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);
        tvDescription = findViewById(R.id.tvDesc);
        imageMain = findViewById(R.id.image);
        getArticleDetails();
    }

    private void getArticleDetails(){
        Call<ResponseArticleDetail> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                .getArticlesDetail("Bearer "+currentUser.getToken(), id);
        call.enqueue(new Callback<ResponseArticleDetail>() {
            @Override
            public void onResponse(Call<ResponseArticleDetail> call, Response<ResponseArticleDetail> response) {
                if (response.isSuccessful()){
                    ArticlesModel articlesModel = response.body().getData();
                    try {
                        tvTitle.setText(articlesModel.getTitle());
                        tvDescription.setText(Html.fromHtml(articlesModel.getDescription()));
                        Picasso.with(ArticleDetailActivity.this).load(articlesModel.getImage()).placeholder(R.drawable.default_thumbnail).fit().into(imageMain);
                        String credate = StaticClass.getFormattedDate(articlesModel.getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                "dd MMM, yyyy hh:mm a");
                        if (credate!=null){
                            tvCreatedAt.setText(credate);
                        }

                        String date = StaticClass.getFormattedDate(articlesModel.getUpdated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                "dd MMM, yyyy hh:mm a");
                        if (date!=null){
                            tvLastUpdated.setText("Last Update at: "+date);
                        }

                    }catch (Exception e){}
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseArticleDetail> call, Throwable t) {
                Toast.makeText(ArticleDetailActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}