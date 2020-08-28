package com.istrides.inztastudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Solutions extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView back;
    ImageView catImg;
    String title;
    String catName, subCatName, catId, subCatId, subSubCatId, subSubCatName, catImgUrl,from;
    TextView name1,name2,name3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        catImg = findViewById(R.id.catimg);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        back = findViewById(R.id.back);




        from = getIntent().getStringExtra("FROM");

        if(from.equalsIgnoreCase("cat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            name3.setText(catName);
            title =  catName;

        }else if (from.equalsIgnoreCase("subcat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            subCatName = getIntent().getStringExtra("SUBCATNAME");
            subCatId = getIntent().getStringExtra("SUBCATID");

            title = subCatName;

            name2.setText(catName);
            name3.setText(subCatName);

        }else if(from.equalsIgnoreCase("subsubcat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            subCatName = getIntent().getStringExtra("SUBCATNAME");
            subCatId = getIntent().getStringExtra("SUBCATID");
            subSubCatId = getIntent().getStringExtra("SUBSUBCATID");
            subSubCatName = getIntent().getStringExtra("SUBSUBCATNAME");

            title = subSubCatName;

            name1.setText(catName);
            name2.setText(subCatName);
            name3.setText(subSubCatName);

        }


        catImgUrl = getIntent().getStringExtra("CATIMG");


        Glide.with(this).load(catImgUrl).into(catImg);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().show();
                    getSupportActionBar().setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    getSupportActionBar().setTitle(" ");
                    getSupportActionBar().hide();//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PDFsolutions(), "PDFs");
        adapter.addFrag(new VideoSolutions(), "Videos");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
