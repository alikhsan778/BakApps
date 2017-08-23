package com.udacityscholar.alikhsan778.bakkingapps;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class VideosActivity extends AppCompatActivity {

    FrVideos frVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        if (savedInstanceState ==null){
            frVideos = new FrVideos();
            frVideos.setArguments(getIntent().getExtras());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.video_description_container,frVideos).commit();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
            }else{
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
