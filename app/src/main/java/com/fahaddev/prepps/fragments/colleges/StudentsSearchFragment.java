package com.fahaddev.prepps.fragments.colleges;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.APIModels.ResponseCheckSuccess;
import com.fahaddev.prepps.APICall.APIModels.ResponseStudentSearch;
import com.fahaddev.prepps.APICall.ResponseArticleDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.JobDetailsActivity;
import com.fahaddev.prepps.activities.student.StudentsHomeActivity;
import com.fahaddev.prepps.adapters.JobsAdapter;
import com.fahaddev.prepps.adapters.SingleStringSelectAdapter;
import com.fahaddev.prepps.adapters.StudentsSearchAdapter;
import com.fahaddev.prepps.helpers.Prepps;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.ApiModels.StudentUsers;
import com.fahaddev.prepps.models.StudentFilterModel;
import com.fahaddev.prepps.models.StudentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public class StudentsSearchFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    List<StudentModel> studentModelList;
    StudentsSearchAdapter adapter;
    public static List<String> statesList;
    ImageButton btnFilter;
    RecyclerView recyclerStudents;
    RelativeLayout progress;
    String state = "", name = "", careersInterested = "", gpa = "", zipCode = "";
    TextView tvNoData;
    SearchView searchView;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentsSearchFragment() {
        // Required empty public constructor
    }

    public static StudentsSearchFragment newInstance(String param1, String param2) {
        StudentsSearchFragment fragment = new StudentsSearchFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students_search, container, false);
        recyclerStudents = view.findViewById(R.id.recyceler_students);
        studentModelList = new ArrayList<>();
        statesList = new ArrayList<>();
        btnFilter = view.findViewById(R.id.btnFilter);
        progress = view.findViewById(R.id.progress);
        btnFilter.setOnClickListener(this);
        tvNoData = view.findViewById(R.id.noData);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.clearFocus();
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false; }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText, recyclerStudents);
                    return true;
                }
            });
        }
        recyclerStudents.setLayoutManager(new LinearLayoutManager(getActivity()));
        getStudents(recyclerStudents);

        return view;
    }

    private void search(String text, RecyclerView recyclerView) {
        final List<StudentModel> studentsModels = new ArrayList<>();
        for (StudentModel studentModel : studentModelList) {
            if (studentModel.getName() != null) {
                boolean nameMatch = false;

                if (studentModel.getName() != null) {
                    if (studentModel.getName().toLowerCase().contains(text.toLowerCase())) {
                        nameMatch = true;
                    }
                }
                if (nameMatch) {
                    studentsModels.add(studentModel);
                    nameMatch = false;
                }

                adapter = new StudentsSearchAdapter(studentsModels, getActivity());
                recyclerView.setAdapter(adapter);
                RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.student_detail_dialog, null);
                        CircleImageView profilePic = v.findViewById(R.id.profilePic);
                        TextView tvUserName = v.findViewById(R.id.tvUserName);
                        TextView tvTown = v.findViewById(R.id.tvTown);
                        TextView tvSchool = v.findViewById(R.id.tvSchool);
                        TextView tvGrade = v.findViewById(R.id.tvGrade);
                        TextView tvAwards = v.findViewById(R.id.tvAwards);
                        TextView tvExtraActivities = v.findViewById(R.id.tvExtraActivities);
                        TextView tvInterested = v.findViewById(R.id.tvInterested);
                        tvUserName.setText(studentsModels.get(i).getName());
                        tvTown.setText(studentsModels.get(i).getCity()+", "+studentsModels.get(i).getState());
                        tvSchool.setText(studentsModels.get(i).getHighSchool());
                        if (studentsModels.get(i).getGpa()!=null){
                            tvGrade.setText(studentsModels.get(i).getGpa());
                        }
                        if (studentsModels.get(i).getAward()!=null){
                            tvAwards.setText(studentsModels.get(i).getAward());
                        }
                        if (studentsModels.get(i).getActivity()!=null){
                            tvExtraActivities.setText(studentsModels.get(i).getActivity());
                        }
                        if (studentsModels.get(i).getCareerInterested()!=null){
                            tvInterested.setText(studentsModels.get(i).getCareerInterested());
                        }
                        ImageButton btnCancel = v.findViewById(R.id.btnCancel);
                        Button btnAddToFav = v.findViewById(R.id.btnAddToFav);
                        builder.setView(v);
                        AlertDialog alertDialog = builder.create();
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        btnAddToFav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addtoFavourite(i, alertDialog, studentsModels);
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }
                });

                if (studentsModels.size() < 1) {
                    recyclerView.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void getStudents(RecyclerView recyclerView){
        progress.setVisibility(View.VISIBLE);
        Call<ResponseStudentSearch> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                .searchStudents("Bearer "+currentUser.getToken());
        call.enqueue(new Callback<ResponseStudentSearch>() {
            @Override
            public void onResponse(Call<ResponseStudentSearch> call, Response<ResponseStudentSearch> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    StudentUsers studentUsers = response.body().getData().getUsers();
                    studentModelList = studentUsers.getData();
                    adapter = new StudentsSearchAdapter(studentModelList, getActivity());
                    recyclerView.setAdapter(adapter);
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.student_detail_dialog, null);
                            CircleImageView profilePic = v.findViewById(R.id.profilePic);
                            TextView tvUserName = v.findViewById(R.id.tvUserName);
                            TextView tvTown = v.findViewById(R.id.tvTown);
                            TextView tvSchool = v.findViewById(R.id.tvSchool);
                            TextView tvGrade = v.findViewById(R.id.tvGrade);
                            TextView tvAwards = v.findViewById(R.id.tvAwards);
                            TextView tvExtraActivities = v.findViewById(R.id.tvExtraActivities);
                            TextView tvInterested = v.findViewById(R.id.tvInterested);
                            tvUserName.setText(studentModelList.get(i).getName());
                            tvTown.setText(studentModelList.get(i).getCity()+", "+studentModelList.get(i).getState());
                            tvSchool.setText(studentModelList.get(i).getHighSchool());
                            if (studentModelList.get(i).getGpa()!=null){
                                tvGrade.setText(studentModelList.get(i).getGpa());
                            }
                            if (studentModelList.get(i).getAward()!=null){
                                tvAwards.setText(studentModelList.get(i).getAward());
                            }
                            if (studentModelList.get(i).getActivity()!=null){
                                tvExtraActivities.setText(studentModelList.get(i).getActivity());
                            }
                            if (studentModelList.get(i).getCareerInterested()!=null){
                                tvInterested.setText(studentModelList.get(i).getCareerInterested());
                            }
                            ImageButton btnCancel = v.findViewById(R.id.btnCancel);
                            Button btnAddToFav = v.findViewById(R.id.btnAddToFav);
                            builder.setView(v);
                            AlertDialog alertDialog = builder.create();
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });
                            btnAddToFav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addtoFavourite(i, alertDialog, studentModelList);
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseStudentSearch> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addtoFavourite(int position, AlertDialog alertDialog, List<StudentModel> studentModels){
        Call<ResponseCheckSuccess> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).addToFavourite("Bearer "+ currentUser.getToken(), studentModels.get(position).getId());
        call.enqueue(new Callback<ResponseCheckSuccess>() {
            @Override
            public void onResponse(Call<ResponseCheckSuccess> call, Response<ResponseCheckSuccess> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("success")){
                        studentModels.remove(position);
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckSuccess> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnFilter){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.student_filter_dialog, null);
            EditText etState = view1.findViewById(R.id.etState);
            etState.setFocusable(false);
            etState.setFocusableInTouchMode(false);
            etState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    View view2 = getLayoutInflater().inflate(R.layout.sinple_selection_dialog, null);
                    androidx.appcompat.widget.SearchView searchView = view2.findViewById(R.id.searchView);
                    searchView.setVisibility(View.GONE);
                    TextView tvHeading = view2.findViewById(R.id.tvHeading);
                    tvHeading.setText("Select State");
                    RecyclerView recyclerView = view2.findViewById(R.id.recycler_programs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    SingleStringSelectAdapter adapter = new SingleStringSelectAdapter(Prepps.statesList, getActivity());
                    recyclerView.setAdapter(adapter);
                    builder1.setView(view2);
                    AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            etState.setText(Prepps.statesList.get(i));
                            alertDialog1.dismiss();
                        }
                    });
                }
            });
            EditText etName = view1.findViewById(R.id.etName);
            EditText etCareerInterested = view1.findViewById(R.id.etCareersInterested);
            EditText etGpa = view1.findViewById(R.id.etGPA);
            ImageButton btnCancel = view1.findViewById(R.id.btnCancel);
            EditText etZipCode = view1.findViewById(R.id.etZipCode);
            Button btnDone = view1.findViewById(R.id.btnDone);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    state = etState.getText().toString();
                    name = etName.getText().toString();
                    careersInterested = etCareerInterested.getText().toString();
                    gpa = etGpa.getText().toString();
                    zipCode = etZipCode.getText().toString();
                    filterStudents();
                    alertDialog.dismiss();
                }
            });
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }
    private void filterStudents(){
        progress.setVisibility(View.VISIBLE);
        Call<ResponseStudentSearch> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                .filterStudents("Bearer "+currentUser.getToken(), name, state, careersInterested, gpa, zipCode);
        call.enqueue(new Callback<ResponseStudentSearch>() {
            @Override
            public void onResponse(Call<ResponseStudentSearch> call, Response<ResponseStudentSearch> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    studentModelList.clear();
                    StudentUsers studentUsers = response.body().getData().getUsers();
                    studentModelList = studentUsers.getData();
                    adapter = new StudentsSearchAdapter(studentModelList, getActivity());
                    recyclerStudents.setAdapter(adapter);
                    RecycleClick.addTo(recyclerStudents).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.student_detail_dialog, null);
                            CircleImageView profilePic = v.findViewById(R.id.profilePic);
                            TextView tvUserName = v.findViewById(R.id.tvUserName);
                            TextView tvTown = v.findViewById(R.id.tvTown);
                            TextView tvSchool = v.findViewById(R.id.tvSchool);
                            TextView tvGrade = v.findViewById(R.id.tvGrade);
                            TextView tvAwards = v.findViewById(R.id.tvAwards);
                            TextView tvExtraActivities = v.findViewById(R.id.tvExtraActivities);
                            TextView tvInterested = v.findViewById(R.id.tvInterested);
                            tvUserName.setText(studentModelList.get(i).getName());
                            tvTown.setText(studentModelList.get(i).getCity()+", "+studentModelList.get(i).getState());
                            tvSchool.setText(studentModelList.get(i).getHighSchool());
                            if (studentModelList.get(i).getGpa()!=null){
                                tvGrade.setText(studentModelList.get(i).getGpa());
                            }
                            if (studentModelList.get(i).getAward()!=null){
                                tvAwards.setText(studentModelList.get(i).getAward());
                            }
                            if (studentModelList.get(i).getActivity()!=null){
                                tvExtraActivities.setText(studentModelList.get(i).getActivity());
                            }
                            if (studentModelList.get(i).getCareerInterested()!=null){
                                tvInterested.setText(studentModelList.get(i).getCareerInterested());
                            }
                            ImageButton btnCancel = v.findViewById(R.id.btnCancel);
                            Button btnAddToFav = v.findViewById(R.id.btnAddToFav);
                            builder.setView(v);
                            AlertDialog alertDialog = builder.create();
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });
                            btnAddToFav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addtoFavourite(i, alertDialog, studentModelList);
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStudentSearch> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}