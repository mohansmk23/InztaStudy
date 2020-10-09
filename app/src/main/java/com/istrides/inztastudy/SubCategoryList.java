package com.istrides.inztastudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.istrides.inztastudy.connection.ApiConstant;
import com.istrides.inztastudy.connection.ApiGetPost;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryList extends AppCompatActivity {

    ConstraintLayout catlay;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView back;
    RecyclerView subCatList;
    String catName, catId, catImgUrl;
    TextView catNametxt;
    ImageView catImg;
    Call<SubCategoryModel> list;
    ProgressDialog pDialog;
    SubCategoryAdapter adapter;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        catlay = findViewById(R.id.catlay);
        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);
        catNametxt = findViewById(R.id.catname);
        catImg = findViewById(R.id.catimg);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        subCatList = findViewById(R.id.subcatlist);

//normal build
        catName = getIntent().getStringExtra("CATNAME");
        catId = getIntent().getStringExtra("CATID");
        catImgUrl = getIntent().getStringExtra("CATIMG");


        //build 1
//        catName = "NCERT SOLUTIONS";
//        catId = "2";
//        catImgUrl = "https://cdn.elearningindustry.com/wp-content/uploads/2016/05/top-10-books-every-college-student-read-1024x640.jpeg";



        Glide.with(this).load(catImgUrl).into(catImg);


        catNametxt.setText(catName);


        Map<String, Object> body = new HashMap<>();

        body.put("apk_key", "sub_category_list");
        body.put("category_id", catId);

        loadSubCategoryList(body);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
                    getSupportActionBar().setTitle(catName);
                    isShow = true;
                } else if (isShow) {
                    getSupportActionBar().hide();
                    getSupportActionBar().setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void loadSubCategoryList(Map<String, Object> body) {

        pDialog = new ProgressDialog(SubCategoryList.this);
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.SubCategory(body);

        list.enqueue(new Callback<SubCategoryModel>() {
            @Override
            public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                pDialog.dismiss();


                SubCategoryModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {


                    setupSubCategories(listResponse);

                } else {

                    Toast.makeText(getApplicationContext(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SubCategoryModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }

    private void setupSubCategories(SubCategoryModel listResponse) {
        subCatList.setHasFixedSize(true);
        subCatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        subCatList.scheduleLayoutAnimation();

        adapter = new SubCategoryAdapter(getApplicationContext(), listResponse.getOutput().get(0).getSubCategoryList());
        subCatList.setAdapter(adapter);


    }


    public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<SubCategoryModel.Output.SubCategoryList> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView subCatName, sno;
            ConstraintLayout item;


            public MyViewHolder(View view) {
                super(view);

                subCatName = (TextView) view.findViewById(R.id.catname);
                sno = (TextView) view.findViewById(R.id.snotxt);
                item = view.findViewById(R.id.catlay);


            }
        }

        public SubCategoryAdapter(Context mContext, List<SubCategoryModel.Output.SubCategoryList> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_subjectlist, parent, false);

            return new SubCategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final SubCategoryAdapter.MyViewHolder holder, int position) {

            final SubCategoryModel.Output.SubCategoryList list = booksList.get(position);

            int sno = position  + 1;

            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" +sno);
            holder.subCatName.setText(list.getSubCategoryName());

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (list.getSubSubCategory().equalsIgnoreCase("yes")) {


                        Intent i = new Intent(new Intent(getApplicationContext(), SubSubCategoryList.class));


                        i.putExtra("CATID", catId);
                        i.putExtra("CATNAME", list.getCategoryName());
                        i.putExtra("SUBCATID", list.getSubCategoryId());
                        i.putExtra("SUBCATNAME", list.getSubCategoryName());
                        i.putExtra("CATIMG", catImgUrl);

                        startActivity(i);


                    } else {

                        Intent i = new Intent(new Intent(getApplicationContext(), Solutions.class));

                        i.putExtra("CATID", catId);
                        i.putExtra("CATNAME", list.getCategoryName());
                        i.putExtra("SUBCATID", list.getSubCategoryId());
                        i.putExtra("SUBCATNAME", list.getSubCategoryName());
                        i.putExtra("CATIMG", catImgUrl);
                        i.putExtra("FROM","subcat");

                        startActivity(i);

                    }


                }
            });


        }


        @Override
        public int getItemCount() {
            return booksList.size();
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
