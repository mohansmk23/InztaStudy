package com.istrides.inztastudy;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFView extends AppCompatActivity implements OnPageChangeListener  {

    com.github.barteksc.pdfviewer.PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);


        getSupportActionBar().setTitle(getIntent().getStringExtra("PDFNAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pdfView = findViewById(R.id.pdfView);


        pdfView.fromUri(Uri.parse(getIntent().getStringExtra("PDFURI")))  .scrollHandle(new DefaultScrollHandle(this)).load();




    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle(String.format("%s %s / %s", "Soutions", page + 1, pageCount));
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
