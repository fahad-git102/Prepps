package com.fahaddev.prepps.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fahaddev.prepps.R;

public class ScholarShipActivity extends AppCompatActivity {

    TextView tvLink;
    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_ship);
        tvLink = findViewById(R.id.t2);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}