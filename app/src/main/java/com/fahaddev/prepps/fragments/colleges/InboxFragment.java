package com.fahaddev.prepps.fragments.colleges;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.ResponseInboxCall;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.ChatActivity;
import com.fahaddev.prepps.adapters.AdapterInbox;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.InboxModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InboxFragment extends Fragment {

    RecyclerView recyclerInbox;
    AdapterInbox adapter;
    List<InboxModel> inboxModelList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InboxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InboxFragment newInstance(String param1, String param2) {
        InboxFragment fragment = new InboxFragment();
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
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        recyclerInbox = view.findViewById(R.id.recyceler_inbox);
        inboxModelList = new ArrayList<>();
        recyclerInbox.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerInbox.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        getInboxList();
        return view;
    }

    private void getInboxList(){
        if (StaticClass.currentUser!=null){
            Call<ResponseInboxCall> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                    .getInboxList("Bearer "+StaticClass.currentUser.getToken());
            call.enqueue(new Callback<ResponseInboxCall>() {
                @Override
                public void onResponse(Call<ResponseInboxCall> call, Response<ResponseInboxCall> response) {
                    if (response.isSuccessful()){
                        inboxModelList = response.body().getData();
                        adapter = new AdapterInbox(inboxModelList, getActivity());
                        recyclerInbox.setAdapter(adapter);
                        RecycleClick.addTo(recyclerInbox).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra("inbox", inboxModelList.get(i));
                                intent.putExtra("message", inboxModelList.get(i).getMessage());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ResponseInboxCall> call, Throwable t) {

                }
            });
        }
    }

}