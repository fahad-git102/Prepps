package com.fahaddev.prepps.activities.college;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.LoginActivity;
import com.fahaddev.prepps.fragments.colleges.InboxFragment;
import com.fahaddev.prepps.fragments.colleges.BillingSupportFragment;
import com.fahaddev.prepps.fragments.colleges.CollegeHomeFragment;
import com.fahaddev.prepps.fragments.colleges.StudentsSearchFragment;
import com.fahaddev.prepps.fragments.students.ProfileFragment;
import com.fahaddev.prepps.helpers.MyFirebaseMessagingService;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.UserTokenModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollegeHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ImageButton openDrawer;
    FirebaseAuth mAuth;
    ImageButton openFavourites;

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
        setContentView(R.layout.activity_college_home);
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,new CollegeHomeFragment()).commit();
        }
        dl = findViewById(R.id.activity_main);
        openDrawer = findViewById(R.id.openDrawer);
        openFavourites = findViewById(R.id.openFavourites);
        openFavourites.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        openDrawer.setOnClickListener(this);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        addDeviceToken();
        nv = findViewById(R.id.nv);
        View headerView = nv.getHeaderView(0);
        ImageView image= headerView.findViewById(R.id.navlogo);
        Glide.with(this).load(R.drawable.logo_png).centerCrop()
                .override(150, 150).error(R.drawable.logo_png_min).into(image);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new CollegeHomeFragment()).commit();
                        dl.closeDrawer(Gravity.LEFT, true);
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                        dl.closeDrawer(Gravity.LEFT, true);
                        break;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentsSearchFragment()).commit();
                        dl.closeDrawer(Gravity.LEFT, true);
                        break;
                    case R.id.messages:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new InboxFragment()).commit();
                        dl.closeDrawer(Gravity.LEFT, true);
                        break;

                    case R.id.billing:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new BillingSupportFragment()).commit();
                        dl.closeDrawer(Gravity.LEFT, true);
                        break;
                    case R.id.logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(CollegeHomeActivity.this);
                        builder.setTitle("Log out");
                        builder.setMessage("Are you sure you want to log out ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteDeviceToken();
                                mAuth.signOut();
                                Intent intent = new Intent(CollegeHomeActivity.this, LoginActivity.class);
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
                }
                return true;
            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.openDrawer:
                dl.openDrawer(Gravity.LEFT, true);
                break;
            case R.id.openFavourites:
                startActivity(new Intent(CollegeHomeActivity.this, FavouriteStudentsActivity.class));
                break;
        }
    }
}