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

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.LoginActivity;
import com.fahaddev.prepps.fragments.colleges.InboxFragment;
import com.fahaddev.prepps.fragments.colleges.BillingSupportFragment;
import com.fahaddev.prepps.fragments.colleges.CollegeHomeFragment;
import com.fahaddev.prepps.fragments.colleges.StudentsSearchFragment;
import com.fahaddev.prepps.fragments.students.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class CollegeHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ImageButton openDrawer;
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
        openDrawer.setOnClickListener(this);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();

        nv = findViewById(R.id.nv);
        View headerView = nv.getHeaderView(0);
        ImageView image= headerView.findViewById(R.id.navlogo);
        Picasso.with(this).load(R.drawable.logo_png).centerCrop()
                .resize(150, 150).error(R.drawable.logo_png_min).into(image);
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