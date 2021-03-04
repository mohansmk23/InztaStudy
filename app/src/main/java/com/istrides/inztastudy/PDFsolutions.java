package com.istrides.inztastudy;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.istrides.inztastudy.connection.ApiConstant;
import com.istrides.inztastudy.connection.ApiGetPost;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.DOWNLOAD_SERVICE;


public class PDFsolutions extends Fragment {

    ConstraintLayout pdfList;
    String catName, subCatName, catId, subCatId, subSubCatId, subSubCatName, catImgUrl, from;
    Call<PDFlistModel> list;
    ProgressDialog pDialog, progressDialog;
    RecyclerView pdfListRecyclerview;
    PdfListAdapter adapter;
    public Dialog downloadingDialog;
    Map<String, Object> body = new HashMap<>();
    String pdfname, pdfprefix = "inztastudy";
    ProgressBar progressBar;
    boolean downloading;
    TextView cancelTxt;
    private long lastDownload = -1L;
    private DownloadManager mgr = null;
    LinearLayout nodatalay;
    Cursor cursor;
    List<String> pdfFileNames = new ArrayList<>();
    File pdfFolder = new File(Environment.getExternalStorageDirectory()
            + "/Download/");
    File[] pdfFiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_pdf_solutions, container, false);
        pdfListRecyclerview = v.findViewById(R.id.pdflist);
        nodatalay = v.findViewById(R.id.nodatalay);
        mgr = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);

        downloadingDialog = new Dialog(getActivity());
        downloadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        downloadingDialog.setCancelable(false);
        downloadingDialog.setContentView(R.layout.dialog_downloading);
        downloadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progressBar = downloadingDialog.findViewById(R.id.progressbar);
        cancelTxt = downloadingDialog.findViewById(R.id.canceltxt);


        body.put("apk_key", "pdf_list");
        body.put("apptype", "main");


        from = getActivity().getIntent().getStringExtra("FROM");

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


        // loadPdfSolutions(body);


        return v;
    }

    private void loadPdfSolutions(Map<String, Object> body) {


        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.Pdflist(body);

        list.enqueue(new Callback<PDFlistModel>() {
            @Override
            public void onResponse(Call<PDFlistModel> call, Response<PDFlistModel> response) {
                pDialog.dismiss();


                PDFlistModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {


                    if (listResponse.getOutput().get(0).getPdfList().size() == 0) {

                        nodatalay.setVisibility(View.VISIBLE);
                        pdfListRecyclerview.setVisibility(View.GONE);


                    } else {

                        nodatalay.setVisibility(View.GONE);
                        pdfListRecyclerview.setVisibility(View.VISIBLE);

                        setupPdflist(listResponse);
                    }


                } else {

                    Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PDFlistModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }

    private void setupPdflist(PDFlistModel listResponse) {

        pdfListRecyclerview.setHasFixedSize(true);
        pdfListRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        pdfListRecyclerview.scheduleLayoutAnimation();

        adapter = new PdfListAdapter(getActivity(), listResponse.getOutput().get(0).getPdfList());
        pdfListRecyclerview.setAdapter(adapter);


    }


    public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.MyViewHolder> {

        private Context mContext;
        private List<PDFlistModel.Output.PdfList> booksList;
        private int size;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView pdfName, sno;
            ConstraintLayout item;
            ImageView downIcon;


            public MyViewHolder(View view) {
                super(view);

                pdfName = (TextView) view.findViewById(R.id.pdfname);
                sno = (TextView) view.findViewById(R.id.snotxt);
                item = view.findViewById(R.id.pdflay);
                downIcon = view.findViewById(R.id.downicon);


            }
        }

        public PdfListAdapter(Context mContext, List<PDFlistModel.Output.PdfList> bookList) {
            this.mContext = mContext;
            this.booksList = bookList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pdf_solution, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final PDFlistModel.Output.PdfList list = booksList.get(position);


            int sno = position + 1;

            holder.sno.setText(sno > 9 ? String.valueOf(sno) : "0" + sno);


            if (list.getPdf_name().length() > 10) {
                holder.pdfName.setText(list.getChapterName());

            } else {

                holder.pdfName.setText(list.getChapterName());

            }


            if (pdfFileNames.contains(list.getPdf_name())) {

                Log.i("thallipogadheytrue", list.getPdf_name());

            } else {

                Log.i("thallipogadheyfalse", list.getPdf_name());

            }

            holder.downIcon.setImageResource(pdfFileNames.contains(list.getPdf_name()) ? R.drawable.dowloading_green : R.drawable.download_red);


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pdfname = list.getPdf_name();

                    if (pdfFileNames.contains(list.getPdf_name())) {


                        openPdfView();


                    } else {
                        if (checkWriteExternalPermission()) {
                            Log.i("grtjewel", list.getUploadFile() + " sds");
                            downloadpdf(list.getUploadFile().trim());

                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }


                }
            });


        }


        @Override
        public int getItemCount() {
            return booksList.size();
        }
    }

    private void downloadpdf(String url) {


        downloadingDialog.show();
        downloading = true;

        String s = url.replaceAll(" ", "%20");
        Uri link = Uri.parse(s);


        File folder1 = new File(Environment.getExternalStorageDirectory()
                + "/Download/" + pdfname);


        if (folder1.exists()) {
            folder1.delete();
        }


        lastDownload =
                mgr.enqueue(new DownloadManager.Request(link)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(pdfname).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDescription("Please wait...")
                        .setDestinationUri(Uri.fromFile(folder1)));



        getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(lastDownload);

                  Cursor cursor = mgr.query(q);
                    cursor.moveToFirst();


                    if( cursor != null && cursor.moveToFirst() ) {

                        int bytes_downloaded = cursor.getInt(cursor
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_FAILED) {
                            downloading = false;
                        }


                        final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);


                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setProgress((int) dl_progress);
                            }
                        });

                        Log.i("johnwick", statusMessage(cursor));



                    }
                    cursor.close();

                }

            }
        }).start();





        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloading = false;
                mgr.remove(lastDownload);
               // getActivity().unregisterReceiver(onComplete);
                downloadingDialog.cancel();


            }
        });


    }


    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                downloadingDialog.cancel();
                openPdfView();
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }

        return (msg);
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

        }
    };


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

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onResume() {
        super.onResume();


        pdfFiles = pdfFolder.listFiles();

        if (pdfFiles != null) {

            for (int i = 0; i < pdfFiles.length; i++) {


                pdfFileNames.add(pdfFiles[i].getName());


            }
        }

        loadPdfSolutions(body);

    }
}
