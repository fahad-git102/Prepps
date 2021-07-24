package com.fahaddev.prepps.fragments.students;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.ResponseCollegeNavigator;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.college.CollegeHomeActivity;
import com.fahaddev.prepps.activities.student.CollegeDetailActivity;
import com.fahaddev.prepps.adapters.CollegeAdapter;
import com.fahaddev.prepps.adapters.ProgramsAdapter;
import com.fahaddev.prepps.adapters.SingleStringSelectAdapter;
import com.fahaddev.prepps.adapters.StudentsSearchAdapter;
import com.fahaddev.prepps.helpers.Prepps;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.ApiModels.CollegeNavigatorResponse;
import com.fahaddev.prepps.models.ApiModels.SearchCollegeInfo;
import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.fahaddev.prepps.models.ProgramsModel;
import com.fahaddev.prepps.models.StudentModel;
import com.rizlee.rangeseekbar.RangeSeekBar;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCollegeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCollegeFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerColleges;
    EditText etSearchCollege;
    List<String> allProgramsList;
    List<CollegeNavigatorModel> collegeNavigatorModels;
    ProgressBar progress;
    int minFees= 10;
    int maxFees = 100000;
    int minPopulation=0;
    int maxPopulation = 10000;
    String name, state, program;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCollegeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCollegeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCollegeFragment newInstance(String param1, String param2) {
        MyCollegeFragment fragment = new MyCollegeFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_college, container, false);
        recyclerColleges = view.findViewById(R.id.recycler_college);
        progress = view.findViewById(R.id.progressBar);
        etSearchCollege = view.findViewById(R.id.searchView);
        etSearchCollege.setFocusableInTouchMode(false);
        etSearchCollege.setFocusable(false);
        etSearchCollege.setOnClickListener(this);
        collegeNavigatorModels= new ArrayList<>();
        recyclerColleges.setLayoutManager(new LinearLayoutManager(getActivity()));
        allProgramsList = new ArrayList<>();
        getCollegeNavigators();
        return view;
    }
    private void getCollegeNavigators(){
        progress.setVisibility(View.VISIBLE);
        Call<ResponseCollegeNavigator> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getCollegeNavigatorsList();
        call.enqueue(new Callback<ResponseCollegeNavigator>() {
            @Override
            public void onResponse(Call<ResponseCollegeNavigator> call, Response<ResponseCollegeNavigator> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    CollegeNavigatorResponse collegeNavigatorResponse = response.body().getData();
                    collegeNavigatorModels = collegeNavigatorResponse.getData();
                    for (int i = 0; i<collegeNavigatorModels.size(); i++){
                        if (collegeNavigatorModels.get(i).getPrograms()!=null){
                            for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
                                if (!allProgramsList.contains(collegeNavigatorModels.get(i).getPrograms().get(a).getName())){
                                    allProgramsList.add(collegeNavigatorModels.get(i).getPrograms().get(a).getName());
                                }
                            }

                        }
                    }
                    CollegeAdapter adapter = new CollegeAdapter(collegeNavigatorModels, getActivity());
                    recyclerColleges.setAdapter(adapter);
                    RecycleClick.addTo(recyclerColleges).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            Intent intent = new Intent(getActivity(), CollegeDetailActivity.class);
                            intent.putExtra("college", collegeNavigatorModels.get(i).getId());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseCollegeNavigator> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.searchView){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.college_filter_dialog, null);
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
            EditText etTuitionFees = view1.findViewById(R.id.etTuitionFees);
            EditText etPopulation = view1.findViewById(R.id.etPopulation);
            ImageButton btnCancel = view1.findViewById(R.id.btnCancel);
            etTuitionFees.setFocusable(false);
            etTuitionFees.setFocusableInTouchMode(false);
            etPopulation.setFocusable(false);
            etPopulation.setFocusableInTouchMode(false);
            EditText etPrograms = view1.findViewById(R.id.etProgramsOffered);
            etPrograms.setFocusable(false);
            etPrograms.setFocusableInTouchMode(false);
            Button btnDone = view1.findViewById(R.id.btnDone);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            etTuitionFees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    View view2 = getLayoutInflater().inflate(R.layout.sinple_selection_dialog, null);
                    SearchView searchView = view2.findViewById(R.id.searchView);
                    searchView.setVisibility(View.GONE);
                    RecyclerView recyclerView = view2.findViewById(R.id.recycler_programs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    List<String>strings = new ArrayList<>();
                    strings.add("Under $10k");
                    strings.add("$10k - $20k");
                    strings.add("$20k - $30k");
                    strings.add("Over $30k");
                    SingleStringSelectAdapter adapter = new SingleStringSelectAdapter(strings, getActivity());
                    recyclerView.setAdapter(adapter);
                    builder1.setView(view2);
                    AlertDialog alertDialog1 = builder1.create();
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            etTuitionFees.setText(strings.get(i));
                            alertDialog1.dismiss();
                            if (i==0){
                                maxFees = 10000;
                                minFees = 10;
                            }else if (i==1){
                                maxFees = 20000;
                                minFees = 10000;
                            }else if (i==2){
                                maxFees = 20000;
                                minFees = 30000;
                            }else if (i==3){
                                maxFees = 100000;
                                minFees = 30000;
                            }
                        }
                    });
                    alertDialog1.show();
                }
            });

            etPopulation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    View view2 = getLayoutInflater().inflate(R.layout.sinple_selection_dialog, null);
                    SearchView searchView = view2.findViewById(R.id.searchView);
                    searchView.setVisibility(View.GONE);
                    RecyclerView recyclerView = view2.findViewById(R.id.recycler_programs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    List<String>strings = new ArrayList<>();
                    strings.add("Under 3,000");
                    strings.add("3,000 - 15,000");
                    strings.add("Over 15,000");
                    SingleStringSelectAdapter adapter = new SingleStringSelectAdapter(strings, getActivity());
                    recyclerView.setAdapter(adapter);
                    builder1.setView(view2);
                    AlertDialog alertDialog1 = builder1.create();
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            etPopulation.setText(strings.get(i));
                            alertDialog1.dismiss();
                            if (i==0){
                                maxPopulation = 3000;
                                minPopulation = 10;
                            }else if (i==2){
                                maxPopulation = 15000;
                                minPopulation = 3000;
                            }else if (i==2){
                                maxPopulation = 50000;
                                minPopulation = 15000;
                            }
                        }
                    });
                    alertDialog1.show();
                }
            });

            etPrograms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    View view2 = getLayoutInflater().inflate(R.layout.sinple_selection_dialog, null);
                    SearchView searchView = view2.findViewById(R.id.searchView);
                    RecyclerView recyclerView = view2.findViewById(R.id.recycler_programs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    SingleStringSelectAdapter adapter = new SingleStringSelectAdapter(allProgramsList, getActivity());
                    recyclerView.setAdapter(adapter);
                    searchView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchView.setIconified(false);
                        }
                    });
                    searchView.clearFocus();
                    builder1.setView(view2);
                    AlertDialog alertDialog1 = builder1.create();
                    if (searchView != null){
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                search(newText, recyclerView, etPrograms, alertDialog1);
                                return true;
                            }
                        });
                    }
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            etPrograms.setText(allProgramsList.get(i));
                            alertDialog1.dismiss();
                        }
                    });
                    alertDialog1.show();
                }
            });
//            rangeTuitionFees.setListenerRealTime(new RangeSeekBar.OnRangeSeekBarRealTimeListener() {
//                @Override
//                public void onValuesChanging(float v, float v1) {}
//                @Override
//                public void onValuesChanging(int i, int i1) {
//                    minFees = i;
//                    maxFees = i1;
//                }
//            });
//            rangePopulation.setListenerRealTime(new RangeSeekBar.OnRangeSeekBarRealTimeListener() {
//                @Override
//                public void onValuesChanging(float v, float v1) { }
//                @Override
//                public void onValuesChanging(int i, int i1) {
//                    minPopulation = i;
//                    maxPopulation = i1;
//                }
//            });
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    state = etState.getText().toString();
                    name = etName.getText().toString();
                    program = etPrograms.getText().toString();
                    callToSearchCollege();
                    alertDialog.dismiss();
                }
            });
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

        private void search(String text, RecyclerView recyclerView, EditText etPrograms, AlertDialog alertDialog1) {
            final List<String> strings = new ArrayList<>();
            for (String program : allProgramsList) {
                    boolean nameMatch = false;

                    if (program != null) {
                        if (program.toLowerCase().contains(text.toLowerCase())) {
                            nameMatch = true;
                        }
                    }
                    if (nameMatch) {
                        strings.add(program);
                        nameMatch = false;
                    }

                    SingleStringSelectAdapter adapter = new SingleStringSelectAdapter(strings, getActivity());
                    recyclerView.setAdapter(adapter);
                    RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                            etPrograms.setText(strings.get(i));
                            alertDialog1.dismiss();
                        }
                    });
                }
            }

    private void callToSearchCollege(){
        try{
            progress.setVisibility(View.VISIBLE);
            SearchCollegeInfo searchCollegeInfo = new SearchCollegeInfo(state, name, minFees, maxFees, maxPopulation, program);
            Call<ResponseCollegeNavigator> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                    .getFilteredCollegeList(searchCollegeInfo);
            call.enqueue(new Callback<ResponseCollegeNavigator>() {
                @Override
                public void onResponse(Call<ResponseCollegeNavigator> call, Response<ResponseCollegeNavigator> response) {
                    progress.setVisibility(View.GONE);
                    if (response.isSuccessful()){
                        CollegeNavigatorResponse collegeNavigatorResponse = response.body().getData();
                        collegeNavigatorModels = collegeNavigatorResponse.getData();
                        if (collegeNavigatorModels.size()>0){
                            recyclerColleges.setVisibility(View.VISIBLE);
                            CollegeAdapter adapter = new CollegeAdapter(collegeNavigatorModels, getActivity());
                            recyclerColleges.setAdapter(adapter);
                            RecycleClick.addTo(recyclerColleges).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                                    Intent intent = new Intent(getActivity(), CollegeDetailActivity.class);
                                    intent.putExtra("college", collegeNavigatorModels.get(i).getId());
                                    startActivity(intent);
                                }
                            });
                        }else {
                            recyclerColleges.setVisibility(View.GONE);
                        }
                    }else {
                        Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCollegeNavigator> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}

    }

//    private void getSuggestedColleges(){
//        if (name!=null&&state!=null&&program!=null&&minFees>0&&maxFees>0){
//
//        }else if (name!=null&&state!=null&&program!=null&&minFees>0){
//            if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                    collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())&&
//                    Integer.parseInt(collegeNavigatorModels.get(i).getTuition_fees())>minFees){
//
//                for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
//                    if (collegeNavigatorModels.get(i).getPrograms().get(a).getName().toLowerCase().contains(program.toLowerCase())){
//                        collegeMatched = true;
//                    }
//                }
//            }
//        }else if (name!=null&&state!=null&&program!=null){
//            if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                    collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())){
//
//                for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
//                    if (collegeNavigatorModels.get(i).getPrograms().get(a).getName().toLowerCase().contains(program.toLowerCase())){
//                        collegeMatched = true;
//                    }
//                }
//            }
//        }else if (name!=null&&state!=null){
//            if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                    collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())){
//                collegeMatched =true;
//            }
//        }else if (name!=null){
//            if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())){
//                collegeMatched =true;
//            }
//        }else {
//            collegeMatched = true;
//        }
//    }
//
//    private void getFilteredCollege(){
//        List<CollegeNavigatorModel> newList = new ArrayList<>();
//        for (int i = 0; i<collegeNavigatorModels.size(); i++){
//            boolean collegeMatched = false;
//            if (name!=null&&state!=null&&program!=null&&minFees>0&&maxFees>0){
//                if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                        collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())&&
//                        (Integer.parseInt(collegeNavigatorModels.get(i).getTuition_fees())>minFees&&Integer.parseInt(collegeNavigatorModels.get(i).getTuition_fees())<maxFees)){
//
//                    for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
//                        if (collegeNavigatorModels.get(i).getPrograms().get(a).getName().toLowerCase().contains(program.toLowerCase())){
//                            collegeMatched = true;
//                        }
//                    }
//                }
//            }else if (name!=null&&state!=null&&program!=null&&minFees>0){
//                if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                        collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())&&
//                        Integer.parseInt(collegeNavigatorModels.get(i).getTuition_fees())>minFees){
//
//                    for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
//                        if (collegeNavigatorModels.get(i).getPrograms().get(a).getName().toLowerCase().contains(program.toLowerCase())){
//                            collegeMatched = true;
//                        }
//                    }
//                }
//            }else if (name!=null&&state!=null&&program!=null){
//                if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                        collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())){
//
//                    for (int a = 0; a<collegeNavigatorModels.get(i).getPrograms().size(); a++){
//                        if (collegeNavigatorModels.get(i).getPrograms().get(a).getName().toLowerCase().contains(program.toLowerCase())){
//                            collegeMatched = true;
//                        }
//                    }
//                }
//            }else if (name!=null&&state!=null){
//                if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())&&
//                        collegeNavigatorModels.get(i).getState().toLowerCase().contains(state.toLowerCase())){
//                    collegeMatched =true;
//                }
//            }else if (name!=null){
//                if (collegeNavigatorModels.get(i).getName().toLowerCase().contains(name.toLowerCase())){
//                    collegeMatched =true;
//                }
//            }else {
//                collegeMatched = true;
//            }
//            if (collegeMatched){
//                newList.add(collegeNavigatorModels.get(i));
//                CollegeAdapter adapter = new CollegeAdapter(collegeNavigatorModels, getActivity());
//                recyclerColleges.setAdapter(adapter);
//                RecycleClick.addTo(recyclerColleges).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int i, View view) {
//                        Intent intent = new Intent(getActivity(), CollegeDetailActivity.class);
//                        intent.putExtra("college", collegeNavigatorModels.get(i).getId());
//                        startActivity(intent);
//                    }
//                });
//            }
//        }
//    }
}