package com.example.ncert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class VideoList extends AppCompatActivity {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView back;
    RecyclerView videolist;
    String catName, subCatName, catId, subCatId, subSubCatId, subSubCatName, catImgUrl,from, chapName,chapId;
    TextView name1,name2,name3,name4;
    ImageView catImg;
    Call<VideoListModel> list;
    LinearLayout nodatalay;
    ProgressDialog pDialog;
    VideoListAdapter adapter;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        back = findViewById(R.id.back);
        videolist = findViewById(R.id.videolist);
        nodatalay = findViewById(R.id.nodatalay);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        catImg = findViewById(R.id.catimg);





        from = getIntent().getStringExtra("FROM");
        chapName = getIntent().getStringExtra("CHAPNAME");
        chapId = getIntent().getStringExtra("CHAPID");

        Map<String, Object> body = new HashMap<>();

        body.put("apk_key", "video_list");
        body.put("chapter_id", chapId);

        if(from.equalsIgnoreCase("cat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            name3.setText(catName);


            body.put("category_id", catId);
            body.put("sub_category_id", "");
            body.put("sub_sub_category_id", "");

        }else if (from.equalsIgnoreCase("subcat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            subCatName = getIntent().getStringExtra("SUBCATNAME");
            subCatId = getIntent().getStringExtra("SUBCATID");

            name2.setText(catName);
            name3.setText(subCatName);


            body.put("category_id", catId);
            body.put("sub_category_id", subCatId);
            body.put("sub_sub_category_id", "");

        }else if(from.equalsIgnoreCase("subsubcat")){

            catName = getIntent().getStringExtra("CATNAME");
            catId = getIntent().getStringExtra("CATID");
            subCatName = getIntent().getStringExtra("SUBCATNAME");
            subCatId = getIntent().getStringExtra("SUBCATID");
            subSubCatId = getIntent().getStringExtra("SUBSUBCATID");
            subSubCatName = getIntent().getStringExtra("SUBSUBCATNAME");

            name1.setText(catName);
            name2.setText(subCatName);
            name3.setText(subSubCatName);


            body.put("category_id", catId);
            body.put("sub_category_id", subCatId);
            body.put("sub_sub_category_id", subSubCatId);

        }
        name4.setText(chapName);


        catImgUrl = getIntent().getStringExtra("CATIMG");


        Glide.with(this).load(catImgUrl).into(catImg);



        loadVideoList(body);








        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F7C765")));
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().hide();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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
                    getSupportActionBar().setTitle(chapName);
                    isShow = true;
                } else if(isShow) {
                    getSupportActionBar().setTitle(" ");
                    getSupportActionBar().hide();//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void loadVideoList(Map<String, Object> body) {

        pDialog = new ProgressDialog(VideoList.this);
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.VideoList(body);

        list.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, Response<VideoListModel> response) {
                pDialog.dismiss();


                VideoListModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {


                    if (listResponse.getOutput().get(0).getVideoList().size()==0){

                        nodatalay.setVisibility(View.VISIBLE);
                        videolist.setVisibility(View.GONE);



                    }else{
                        nodatalay.setVisibility(View.GONE);
                        videolist.setVisibility(View.VISIBLE);

                        setupVideolist(listResponse);
                    }






                } else {

                    Toast.makeText(getApplicationContext(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });




    }

    private void setupVideolist(VideoListModel listResponse) {


        videolist.setHasFixedSize(true);
        videolist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        videolist.scheduleLayoutAnimation();

        adapter = new VideoListAdapter(getApplicationContext(), listResponse.getOutput().get(0).getVideoList());
        videolist.setAdapter(adapter);




    }



    public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {

        private Context mContext;
        private List<VideoListModel.Output.VideoList> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView vidname, sno,duration;
            ImageView playBtn;
            LinearLayout item;


            public MyViewHolder(View view) {
                super(view);

                vidname = (TextView) view.findViewById(R.id.vidname);
                duration = view.findViewById(R.id.durationtxt);
                sno = (TextView) view.findViewById(R.id.snotxt);
                playBtn = view.findViewById(R.id.playbtn);
                item = view.findViewById(R.id.vidlay);


            }
        }

        public VideoListAdapter(Context mContext, List<VideoListModel.Output.VideoList> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public VideoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_video_solutions, parent, false);

            return new VideoListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final VideoListAdapter.MyViewHolder holder, final int position) {

            final VideoListModel.Output.VideoList list = booksList.get(position);

            int sno = position  + 1;

            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" +sno);
            holder.vidname.setText(list.getVideoName());
            holder.duration.setText(list.getVideoDuration());


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                     Intent i = new Intent(getApplicationContext(),VideoView.class);

                     i.putExtra("VIDID",list.getUploadVideo());

                    startActivity(i);



                }
            });

            holder.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getApplicationContext(),VideoView.class);

                    i.putExtra("VIDID",list.getUploadVideo());

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
