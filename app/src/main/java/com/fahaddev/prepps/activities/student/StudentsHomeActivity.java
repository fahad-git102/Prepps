package com.fahaddev.prepps.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.InboxActivity;
import com.fahaddev.prepps.activities.LoginActivity;
import com.fahaddev.prepps.fragments.students.FinancialAidFragment;
import com.fahaddev.prepps.fragments.students.HomeFragment;
import com.fahaddev.prepps.fragments.students.MyCareerFragment;
import com.fahaddev.prepps.fragments.students.MyCollegeFragment;
import com.fahaddev.prepps.fragments.students.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentsHomeActivity extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottomNavigationView;
    ImageButton logout, btnInbox;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to exit app ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
        btnInbox = findViewById(R.id.btnInbox);
        btnInbox.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
                case R.id.logout:
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentsHomeActivity.this);
                    builder.setTitle("Log out");
                    builder.setMessage("Are you sure you want to log out ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(StudentsHomeActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;

            case R.id.btnInbox:
                Intent intent = new Intent(StudentsHomeActivity.this, InboxActivity.class);
                startActivity(intent);
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch(id)
            {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                    break;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                    break;
                case R.id.collegeSearch:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyCollegeFragment()).commit();
                    break;
                case R.id.career:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyCareerFragment()).commit();
                    break;
                case R.id.financial:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FinancialAidFragment()).commit();
                    break;
                default:
                    return true;
            }
            return true;
        }
    };
}