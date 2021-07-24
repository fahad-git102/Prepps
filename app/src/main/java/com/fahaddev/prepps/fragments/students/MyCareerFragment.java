package com.fahaddev.prepps.fragments.students;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.JobDetailsActivity;
import com.fahaddev.prepps.adapters.JobsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCareerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCareerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCareerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCareerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCareerFragment newInstance(String param1, String param2) {
        MyCareerFragment fragment = new MyCareerFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_career, container, false);

        RecyclerView recyclerJobs = view.findViewById(R.id.recyceler_jobs);
        recyclerJobs.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button btnTakeCareerTest = view.findViewById(R.id.btnCareerTest);
        btnTakeCareerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String url = "https://www.123test.com/career-test/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        JobsAdapter adapter = new JobsAdapter();
        recyclerJobs.setAdapter(adapter);
        RecycleClick.addTo(recyclerJobs).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}