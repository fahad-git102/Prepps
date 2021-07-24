package com.fahaddev.prepps.fragments.colleges;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.MyAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillingSupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillingSupportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    TabLayout tabLayout;
//    ViewPager viewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillingSupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillingSupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillingSupportFragment newInstance(String param1, String param2) {
        BillingSupportFragment fragment = new BillingSupportFragment();
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
        View view = inflater.inflate(R.layout.fragment_billing_support, container, false);
//        tabLayout=view.findViewById(R.id.tabLayout);
//        viewPager=view.findViewById(R.id.viewPager);
//
//        tabLayout.addTab(tabLayout.newTab().setText("Support"));
//        tabLayout.addTab(tabLayout.newTab().setText("Billing"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final MyAdapter adapter = new MyAdapter(requireContext(),requireActivity().getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        SpannableString text = new SpannableString("Support@Prepps.com");
        text.setSpan(new UnderlineSpan(), 0, 6, 0);
        tvEmail.setText(text);
        return view;
    }
}