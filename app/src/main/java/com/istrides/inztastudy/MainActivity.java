package com.istrides.inztastudy;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

public class MainActivity extends AppCompatActivity {

    BubbleTabBar bottomNavBar;
    FrameLayout layout;
    Fragment course  = new Course();
    Fragment downloads = new Downloads();
    Fragment profile = new Profile();
    Fragment otherApps = new OtherApps();
    Fragment active = course;
    private AdView mAdView;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    final FragmentManager fm = getSupportFragmentManager();
    PermissionListener permissionlistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        layout = findViewById(R.id.container);

        bottomNavBar = findViewById(R.id.bubbleTabBar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setTitle("InztaStudy - Home");
        getSupportActionBar().setElevation(0);


        fm.beginTransaction().add(R.id.container, profile, "4").hide(profile).commit();
        fm.beginTransaction().add(R.id.container, otherApps, "3").hide(otherApps).commit();
        fm.beginTransaction().add(R.id.container, downloads, "2").hide(downloads).commit();
        fm.beginTransaction().add(R.id.container, course, "1").commit();



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
             //   Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
              //  Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();




        bottomNavBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {

                if(i==R.id.courses){
                    getSupportActionBar().setTitle("InztaStudy - Home");
                    fm.beginTransaction().hide(active).show(course).commit();
                    active = course;
                }else if(i==R.id.download){


                    getSupportActionBar().setTitle("InztaStudy - Downloads");
                    fm.beginTransaction().hide(active).show(downloads).commit();
                    active = downloads;
                }else if(i==R.id.profile){
                    getSupportActionBar().setTitle("InztaStudy - Profile");
                    fm.beginTransaction().hide(active).show(profile).commit();
                    active = profile;

                }else if(i == R.id.apps){
                    getSupportActionBar().setTitle("InztaStudy - Other Apps");
                    fm.beginTransaction().hide(active).show(otherApps).commit();
                    active = otherApps;
                }



            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //File write logic here

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }



        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.share:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "Checkout this NCERT app!";
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "<URL>");
                startActivity(Intent.createChooser(intent, "Share using"));

                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}