package com.fahaddev.prepps.fragments.students;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.ResponseArticlesCall;
import com.fahaddev.prepps.APICall.ResponseTimelineFile;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.APICall.TimelineModel;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.ArticleDetailActivity;
import com.fahaddev.prepps.activities.TimelineDetailsActivity;
import com.fahaddev.prepps.activities.student.ReferFriendActivity;
import com.fahaddev.prepps.activities.student.ScholarShipActivity;
import com.fahaddev.prepps.adapters.ArticlesAdapter;
import com.fahaddev.prepps.adapters.original.TimelineOAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.ApiModels.TimelineResponseData;
import com.fahaddev.prepps.models.ArticlesModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerArticles, recyclerTimeline ;
    List<ArticlesModel> list;
    ProgressBar progressBar;
    TextView tvRefer, tvPreppsScholarShip;
    Call<ResponseArticlesCall> responseArticlesCall;
    Call<ResponseTimelineFile> responseTimelineFileCall;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerArticles = view.findViewById(R.id.recyceler_articles);
        recyclerTimeline = view.findViewById(R.id.recyclerTimeline);
        tvRefer = view.findViewById(R.id.tvRefer);
        tvPreppsScholarShip = view.findViewById(R.id.tvPreppsScholarShip);
        tvPreppsScholarShip.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progressBar);
        tvRefer.setOnClickListener(this);
        recyclerTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        recyclerArticles.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        getArticles();
        getTimeline();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRefer:
                startActivity(new Intent(getActivity(), ReferFriendActivity.class));
                break;
            case R.id.tvPreppsScholarShip:
                startActivity(new Intent(getActivity(), ScholarShipActivity.class));
                break;
        }
    }

    private void getArticles(){
        progressBar.setVisibility(View.VISIBLE);
        responseArticlesCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getArtices();
        responseArticlesCall.enqueue(new Callback<ResponseArticlesCall>() {
            @Override
            public void onResponse(Call<ResponseArticlesCall> call, Response<ResponseArticlesCall> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()){
                    list = response.body().getDataObject().getData();
                    ArticlesAdapter adapter = new ArticlesAdapter(list, getActivity(), recyclerArticles);
                    recyclerArticles.setAdapter(adapter);
                    RecycleClick.addTo(recyclerArticles).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                            intent.putExtra("article", list.get(i).getId());
                            startActivity(intent);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseArticlesCall> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getTimeline(){
        if (currentUser!=null) {
            progressBar.setVisibility(View.VISIBLE);
            responseTimelineFileCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getTimeline("Bearer "+currentUser.getToken());
            responseTimelineFileCall.enqueue(new Callback<ResponseTimelineFile>() {
                @Override
                public void onResponse(Call<ResponseTimelineFile> call, Response<ResponseTimelineFile> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()){
                        TimelineResponseData data = response.body().getData();
                        List<TimelineModel> timelineModels = data.getData();
                        TimelineOAdapter adapter = new TimelineOAdapter(timelineModels, getActivity());
                        recyclerTimeline.setAdapter(adapter);
                        RecycleClick.addTo(recyclerTimeline).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                                Intent intent = new Intent(getActivity(), TimelineDetailsActivity.class);
                                intent.putExtra("timeline", timelineModels.get(i));
                                startActivity(intent);
                            }
                        });
                    }else {
                        String errorMsg = response.message();
                        Toast.makeText(getActivity(), ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseTimelineFile> call, Throwable t) {
                    String ee = t.getMessage();
                    Toast.makeText(getActivity(), ""+ee, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}