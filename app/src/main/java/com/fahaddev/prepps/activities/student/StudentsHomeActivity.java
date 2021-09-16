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
import android.widget.Toast;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.InboxActivity;
import com.fahaddev.prepps.activities.LoginActivity;
import com.fahaddev.prepps.fragments.students.FinancialAidFragment;
import com.fahaddev.prepps.fragments.students.HomeFragment;
import com.fahaddev.prepps.fragments.students.MyCareerFragment;
import com.fahaddev.prepps.fragments.students.MyCollegeFragment;
import com.fahaddev.prepps.fragments.students.ProfileFragment;
import com.fahaddev.prepps.helpers.MyFirebaseMessagingService;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.UserTokenModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsHomeActivity extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottomNavigationView;
    ImageButton logout, btnInbox;
    FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        btnInbox.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }
        addDeviceToken();
    }

    private void addDeviceToken(){
        if (StaticClass.currentUser!=null){
            String deviceToken = MyFirebaseMessagingService.getToken(this);
            List<String> tokens = new ArrayList<>();
            if (StaticClass.currentUserToken!=null){
                if (StaticClass.currentUserToken.getDeviceTokens()!=null){
                    tokens = StaticClass.currentUserToken.getDeviceTokens();
                }
            }
            tokens.add(deviceToken);
            DatabaseReference tokensRef = FirebaseDatabase.getInstance().getReference("tokens");
            if (StaticClass.currentUserToken!=null){
                Map<String, Object> map = new HashMap<>();
                map.put("uid", String.valueOf(StaticClass.currentUser.getId()));
                map.put("deviceTokens", tokens);
                tokensRef.child(StaticClass.currentUserToken.getKey()).updateChildren(map);
            }else {
                UserTokenModel userTokenModel = new UserTokenModel();
                userTokenModel.setDeviceTokens(tokens);
                userTokenModel.setUid(String.valueOf(StaticClass.currentUser.getId()));
                String key = tokensRef.push().getKey();
                tokensRef.child(key).setValue(userTokenModel);
            }
        }
    }

    private void deleteDeviceToken(){
        if (StaticClass.currentUser!=null) {
            String deviceToken = MyFirebaseMessagingService.getToken(this);
            List<String> tokens = new ArrayList<>();
            if (StaticClass.currentUserToken != null) {
                if (StaticClass.currentUserToken.getDeviceTokens() != null) {
                    tokens = StaticClass.currentUserToken.getDeviceTokens();
                }
            }
            boolean deviceTokenFound = false;
            if (tokens.size()>0){
                if (tokens.contains(deviceToken)){
                    tokens.remove(deviceToken);
                    deviceTokenFound = true;
                }
            }
            if (deviceTokenFound){
                if (StaticClass.currentUserToken!=null){
                    DatabaseReference tokensRef = FirebaseDatabase.getInstance().getReference("tokens");
                    Map<String, Object> map = new HashMap<>();
                    map.put("uid", String.valueOf(StaticClass.currentUser.getId()));
                    map.put("deviceTokens", tokens);
                    tokensRef.child(StaticClass.currentUserToken.getKey()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            StaticClass.currentUserToken = null;
                        }
                    });
                }
            }
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
                            deleteDeviceToken();
                            mAuth.signOut();
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