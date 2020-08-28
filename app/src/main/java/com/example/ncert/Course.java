package com.example.ncert;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ncert.connection.ApiConstant;
import com.example.ncert.connection.ApiGetPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Course extends Fragment {
    BooksAdapter booksAdapter;
    CategoryAdapter categoriesAdapter;
    ProgressDialog pDialog, bDialog;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerView categories;
    Call<BooksModel> Call;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Map<String, Object> books = new HashMap<>();

        books.put("apk_key", "home_tag_list");
        books.put("search_key", "");

        loadCategoryList(books);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_course, container, false);

        categories = v.findViewById(R.id.catlist);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);


        return v;
    }



    private void loadCategoryList(Map<String, Object> books) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        Call = service.getBooks(books);

        Call.enqueue(new Callback<BooksModel>() {
            @Override
            public void onResponse(Call<BooksModel> call, Response<BooksModel> response) {
                pDialog.dismiss();


                BooksModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {

                    Log.i("yes","success");
                    setupCategories(listResponse);

                } else {

                    Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<BooksModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    private void setupCategories(BooksModel bookslist) {


        Log.i("yes","setup");



        categories.setHasFixedSize(true);
        categories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        categories.setNestedScrollingEnabled(false);
        categoriesAdapter = new CategoryAdapter(getActivity(), bookslist.getOutput().get(0).getHometaglist());
        categories.setAdapter(categoriesAdapter);

    }



    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<BooksModel.Output.Hometaglist> categoriesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView categoryName;
            RecyclerView booksRecyclerView;


            public MyViewHolder(View view) {
                super(view);

                categoryName = (TextView) view.findViewById(R.id.catName);
                booksRecyclerView = (RecyclerView) view.findViewById(R.id.bookslist);


            }
        }

        public CategoryAdapter(Context mContext, List<BooksModel.Output.Hometaglist> categoriesList) {
            this.mContext = mContext;
            this.categoriesList = categoriesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_homecategories, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, int position) {


            Log.i("yes",categoriesList.size() + " ade");
            final BooksModel.Output.Hometaglist list = categoriesList.get(position);




            holder.categoryName.setText(list.getGroupName());

            List<Books> booksList = new ArrayList<>();



            for (BooksModel.Output.Hometaglist.GroupDetail books : list.getGroupDetails()) {

                Log.i("yes",books.getCategoryId());
                booksList.add(new Books(books.getCategoryId(), books.getCategoryName(), books.getSubTitle(), books.getDate(), books.getCatImage(), books.getSubCategory()));
            }


            if (list.getViewType().equalsIgnoreCase("slider")){

                Log.i("yes","sliderstart");
                holder.booksRecyclerView.setHasFixedSize(true);
                holder.booksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                holder.booksRecyclerView.setNestedScrollingEnabled(false);
                holder.booksRecyclerView.setAdapter(new BooksAdapter(getActivity(), booksList));
                Log.i("yes","sliderend");

            }else if (list.getViewType().equalsIgnoreCase("grid")){
                Log.i("yes","gridstart");
                holder.booksRecyclerView.setHasFixedSize(true);
                holder.booksRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                holder.booksRecyclerView.setNestedScrollingEnabled(false);
                holder.booksRecyclerView.setAdapter(new BooksAdapter(getActivity(), booksList));
                Log.i("yes","gridend");
            }









        }


        @Override
        public int getItemCount() {
            return categoriesList.size();
        }
    }


    public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

        private Context mContext;
        private List<Books> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView bookName, authorName;
            ImageView bookLogo;
            ConstraintLayout item;


            public MyViewHolder(View view) {
                super(view);

                bookName = (TextView) view.findViewById(R.id.bookname);
                authorName = (TextView) view.findViewById(R.id.authname);
                bookLogo = (ImageView) view.findViewById(R.id.booklogo);
                item = view.findViewById(R.id.item);


            }
        }

        public BooksAdapter(Context mContext, List<Books> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_books, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final BooksAdapter.MyViewHolder holder, int position) {



            final Books list = booksList.get(position);


            // holder.item.setBackground(list.getBg());
            // holder.item.setClipToOutline(true);
            Glide.with(getActivity()).load(list.getCatImg()).into(holder.bookLogo);
            holder.bookName.setText(list.getName());
            holder.authorName.setText(list.getSubTitle());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (list.getSubCat().equalsIgnoreCase("yes")) {


                        Intent i = new Intent(new Intent(getActivity(), SubCategoryList.class));

                        i.putExtra("CATID",list.getId());
                        i.putExtra("CATNAME",list.getName());
                        i.putExtra("CATIMG",list.getCatImg());

                        startActivity(i);



                    } else {

                        Intent i = new Intent(new Intent(getActivity(), Solutions.class));

                        i.putExtra("CATID",list.getId());
                        i.putExtra("CATNAME",list.getName());
                        i.putExtra("CATIMG",list.getCatImg());
                        i.putExtra("FROM","cat");

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






}
