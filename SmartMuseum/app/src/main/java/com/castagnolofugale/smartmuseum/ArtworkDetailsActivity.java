package com.castagnolofugale.smartmuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ArtworkDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ObjectID = extras.getString("ObjectId");
            System.out.println(ObjectID.toString());

        }
    }
}