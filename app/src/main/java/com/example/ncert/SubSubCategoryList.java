package com.example.ncert;

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
import com.example.ncert.connection.ApiConstant;
import com.example.ncert.connection.ApiGetPost;
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

public class SubSubCategoryList extends AppCompatActivity {

    ConstraintLayout catlay;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView back;
    Call<SubSubCategoryModel> list;
    String catName, subCatName, catId, subCatId,catImgUrl;
    TextView catTxt, subCatTxt;
    ProgressDialog pDialog;
    ImageView catImg;
    SubSubCategoryAdapter adapter;
    RecyclerView subSubCatlist;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);



        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);
        catTxt = findViewById(R.id.cattxt);
        subCatTxt = findViewById(R.id.subcattxt);
        catImg = findViewById(R.id.catimg);
        subSubCatlist = findViewById(R.id.subsubcatlist);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        catName = getIntent().getStringExtra("CATNAME");
        catId = getIntent().getStringExtra("CATID");
        subCatName = getIntent().getStringExtra("SUBCATNAME");
        subCatId = getIntent().getStringExtra("SUBCATID");
        catImgUrl = getIntent().getStringExtra("CATIMG");


        Glide.with(this).load(catImgUrl).into(catImg);

        catTxt.setText(catName);
        subCatTxt.setText(subCatName);



        Map<String, Object> body = new HashMap<>();

        body.put("apk_key", "sub_sub_category_list");
        body.put("sub_category_id", subCatId);

        loadSubSubCategoryList(body);





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
                    getSupportActionBar().setTitle(subCatName);
                    isShow = true;
                } else if(isShow) {
                    getSupportActionBar().hide();
                    getSupportActionBar().setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void loadSubSubCategoryList(Map<String, Object> body) {

        pDialog = new ProgressDialog(SubSubCategoryList.this);
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.SubSubCategory(body);

        list.enqueue(new Callback<SubSubCategoryModel>() {
            @Override
            public void onResponse(Call<SubSubCategoryModel> call, Response<SubSubCategoryModel> response) {
                pDialog.dismiss();


                SubSubCategoryModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {


                    setupSubSubCategories(listResponse);

                } else {

                    Toast.makeText(getApplicationContext(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SubSubCategoryModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });





    }

    private void setupSubSubCategories(SubSubCategoryModel listResponse) {
        subSubCatlist.setHasFixedSize(true);
        subSubCatlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        subSubCatlist.scheduleLayoutAnimation();

        adapter = new SubSubCategoryAdapter(getApplicationContext(),listResponse.getOutput().get(0).getSubSubCategoryList());
        subSubCatlist.setAdapter(adapter);



    }


    public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<SubSubCategoryModel.Output.SubSubCategoryList> booksList;
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

        public SubSubCategoryAdapter(Context mContext, List<SubSubCategoryModel.Output.SubSubCategoryList> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public SubSubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_subjectlist, parent, false);

            return new SubSubCategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final SubSubCategoryAdapter.MyViewHolder holder, final int position) {

            final SubSubCategoryModel.Output.SubSubCategoryList list = booksList.get(position);

            int sno = position  + 1;

            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" +sno);
            holder.subCatName.setText(list.getSubSubCategoryName());


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(new Intent(getApplicationContext(), Solutions.class));


                    i.putExtra("CATID", catId);
                    i.putExtra("CATNAME", list.getCategoryName());
                    i.putExtra("SUBCATID", subCatId);
                    i.putExtra("SUBCATNAME", subCatName);
                    i.putExtra("SUBSUBCATID", list.getSubSubCategoryId());
                    i.putExtra("SUBSUBCATNAME", list.getSubSubCategoryName());
                    i.putExtra("CATIMG", catImgUrl);
                    i.putExtra("FROM","subsubcat");

                    startActivity(i);





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