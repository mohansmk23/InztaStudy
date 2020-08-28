package com.istrides.inztastudy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.istrides.inztastudy.connection.ApiConstant;
import com.istrides.inztastudy.connection.ApiGetPost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoSolutions extends Fragment {

    ConstraintLayout vidLay;
    ImageView menu;
    LinearLayout nodatalay;
    RecyclerView chapterList;
    String catName, subCatName, catId, subCatId, subSubCatId, subSubCatName, catImgUrl, from;
    Call<ChapterListModel> list;
    ProgressDialog pDialog;
    ChapterListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_video_solutions, container, false);


        chapterList = v.findViewById(R.id.chapterlist);
        nodatalay = v.findViewById(R.id.nodatalay);


        Map<String, Object> body = new HashMap<>();

        body.put("apk_key", "chapter_list");


        from = getActivity().getIntent().getStringExtra("FROM");
        catImgUrl = getActivity().getIntent().getStringExtra("CATIMG");

        if (from.equalsIgnoreCase("cat")) {

            catName = getActivity().getIntent().getStringExtra("CATNAME");
            catId = getActivity().getIntent().getStringExtra("CATID");


            body.put("category_id", catId);
            body.put("sub_category_id", "");
            body.put("sub_sub_category_id", "");


        } else if (from.equalsIgnoreCase("subcat")) {

            catName = getActivity().getIntent().getStringExtra("CATNAME");
            catId = getActivity().getIntent().getStringExtra("CATID");
            subCatName = getActivity().getIntent().getStringExtra("SUBCATNAME");
            subCatId = getActivity().getIntent().getStringExtra("SUBCATID");


            body.put("category_id", catId);
            body.put("sub_category_id", subCatId);
            body.put("sub_sub_category_id", "");

        } else if (from.equalsIgnoreCase("subsubcat")) {

            catName = getActivity().getIntent().getStringExtra("CATNAME");
            catId = getActivity().getIntent().getStringExtra("CATID");
            subCatName = getActivity().getIntent().getStringExtra("SUBCATNAME");
            subCatId = getActivity().getIntent().getStringExtra("SUBCATID");
            subSubCatId = getActivity().getIntent().getStringExtra("SUBSUBCATID");
            subSubCatName = getActivity().getIntent().getStringExtra("SUBSUBCATNAME");


            body.put("category_id", catId);
            body.put("sub_category_id", subCatId);
            body.put("sub_sub_category_id", subSubCatId);

        }


        loadChapterList(body);


        return v;


    }

    private void loadChapterList(Map<String, Object> body) {


        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.ChapterList(body);

        list.enqueue(new Callback<ChapterListModel>() {
            @Override
            public void onResponse(Call<ChapterListModel> call, Response<ChapterListModel> response) {
                pDialog.dismiss();


                ChapterListModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {


                    if (listResponse.getOutput().get(0).getChapterList().size() == 0) {

                        nodatalay.setVisibility(View.VISIBLE);
                        chapterList.setVisibility(View.GONE);


                    } else {

                        nodatalay.setVisibility(View.GONE);
                        chapterList.setVisibility(View.VISIBLE);

                        setupChapterlist(listResponse);
                    }


                } else {

                    Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ChapterListModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }

    private void setupChapterlist(ChapterListModel listResponse) {

        chapterList.setHasFixedSize(true);
        chapterList.setLayoutManager(new LinearLayoutManager(getActivity()));
        chapterList.scheduleLayoutAnimation();

        adapter = new ChapterListAdapter(getActivity(), listResponse.getOutput().get(0).getChapterList());
        chapterList.setAdapter(adapter);

    }


    public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.MyViewHolder> {

        private Context mContext;
        private List<ChapterListModel.Output.ChapterList> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView chapterName, sno, counttxt;
            ConstraintLayout item;


            public MyViewHolder(View view) {
                super(view);

                chapterName = (TextView) view.findViewById(R.id.chaptername);
                counttxt = view.findViewById(R.id.counttxt);
                sno = (TextView) view.findViewById(R.id.snotxt);
                item = view.findViewById(R.id.chaplay);


            }
        }

        public ChapterListAdapter(Context mContext, List<ChapterListModel.Output.ChapterList> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public ChapterListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chapter_list, parent, false);

            return new ChapterListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ChapterListAdapter.MyViewHolder holder, final int position) {

            final ChapterListModel.Output.ChapterList list = booksList.get(position);

            int sno = position + 1;

            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" + sno);
            holder.chapterName.setText(list.getChapterName());
            holder.counttxt.setText(list.getVideoCount() + " Videos");


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), VideoList.class);

                    i.putExtra("FROM",from);
                    i.putExtra("CHAPNAME",list.getChapterName());
                    i.putExtra("CHAPID",list.getChapterId());
                    i.putExtra("CATIMG",catImgUrl);

                    if (from.equalsIgnoreCase("cat")) {

                        i.putExtra("CATNAME",catName);
                        i.putExtra("CATID",catId);

                    } else if (from.equalsIgnoreCase("subcat")) {

                        i.putExtra("CATNAME",catName);
                        i.putExtra("CATID",catId);
                        i.putExtra("SUBCATNAME",subCatName);
                        i.putExtra("SUBCATID",subCatId);

                    } else if (from.equalsIgnoreCase("subsubcat")) {

                        i.putExtra("CATNAME",catName);
                        i.putExtra("CATID",catId);
                        i.putExtra("SUBCATNAME",subCatName);
                        i.putExtra("SUBCATID",subCatId);
                        i.putExtra("SUBSUBCATID",subSubCatId);
                        i.putExtra("SUBSUBCATNAME",subSubCatName);

                    }

                    startActivity(i);



                }
            });


        }


        @Override
        public int getItemCount() {
            return booksList.size();
        }
    }
}
