package com.istrides.inztastudy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Downloads extends Fragment {

    RecyclerView downloadsList;
    List<String> pdfFileNames = new ArrayList<>();
    File pdfFolder;
    File[] pdfFiles;
    LinearLayout noDataLay;
    DownloadListAdapter adapter;
    TextView pdfCount;
    String pdfname,pdfprefix = "inztamain-";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pdfFolder = new File(Environment.getExternalStorageDirectory()
                + "/Download/");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_downloads, container, false);

        downloadsList = v.findViewById(R.id.downloadList);
        noDataLay = v.findViewById(R.id.nodatalay);
        pdfCount = v.findViewById(R.id.pdfcount);






        return v;
    }

    private void setupDownloadsList() {


        if (pdfFileNames.size() == 0) {

            noDataLay.setVisibility(View.VISIBLE);
            downloadsList.setVisibility(View.GONE);

        } else {
            noDataLay.setVisibility(View.GONE);
            downloadsList.setVisibility(View.VISIBLE);


            downloadsList.setHasFixedSize(true);
            downloadsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            downloadsList.scheduleLayoutAnimation();
            adapter = new DownloadListAdapter(getActivity(), pdfFileNames);
            downloadsList.setAdapter(adapter);
        }

    }

    public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.MyViewHolder> {

        private Context mContext;
        private List<String> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView pdfName, sno;
            ConstraintLayout item;


            public MyViewHolder(View view) {
                super(view);

                pdfName = (TextView) view.findViewById(R.id.pdfname);
                sno = (TextView) view.findViewById(R.id.snotxt);
                item = view.findViewById(R.id.pdflay);


            }
        }

        public DownloadListAdapter(Context mContext, List<String> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;


        }

        @Override
        public DownloadListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_downloads, parent, false);

            return new DownloadListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DownloadListAdapter.MyViewHolder holder, final int position) {

            final String list = booksList.get(position);

            int sno = position + 1;


            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" + sno);
            holder.pdfName.setText(list.substring(10, list.length() - 4));

            Log.i("perak", list);

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pdfname = list;

                    openPdfView();

                }
            });


        }


        @Override
        public int getItemCount() {
            return booksList.size();
        }
    }


    private void openPdfView() {

        File folder1 = new File(Environment.getExternalStorageDirectory()
                + "/Download/" + pdfname);
        Uri pdfURI = null;

        if (Build.VERSION.SDK_INT <= 24) {
            pdfURI = Uri.fromFile(folder1);
        } else {
            pdfURI = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext()
                            .getPackageName() + ".provider", folder1);
        }

        Intent i = new Intent(getActivity(), PDFView.class);


        i.putExtra("PDFURI", pdfURI.toString());
        i.putExtra("PDFNAME", pdfname.substring(10, pdfname.length() - 4));
        startActivity(i);


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("yesappa","ehabba");

       pdfFileNames.clear();

        pdfFiles = pdfFolder.listFiles();


        if (pdfFiles != null) {

            for (int i = 0; i < pdfFiles.length; i++) {



                if (pdfFiles[i].getName().endsWith(".pdf")) {

                    if (pdfFiles[i].getName().startsWith(pdfprefix)) {
                        pdfFileNames.add(pdfFiles[i].getName());
                    }

                }
            }





        }

        setupDownloadsList();
        pdfCount.setText(pdfFileNames.size() + " PDFs");
    }
}
