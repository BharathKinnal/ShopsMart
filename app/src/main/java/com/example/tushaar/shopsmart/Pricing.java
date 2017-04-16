package com.example.tushaar.shopsmart ;

import org.jsoup.Jsoup ;
import org.jsoup.nodes.Document ;
import android.app.ProgressDialog ;
import android.content.Intent ;
import android.net.Uri ;
import android.os.AsyncTask ;
import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle ;
import android.view.View ;
import android.widget.Button ;
import android.widget.RelativeLayout ;
import android.widget.TextView ;

public class Pricing extends AppCompatActivity {
    RelativeLayout object ;
    MainActivity ob1 = new MainActivity() ;
    TextView title ;
    TextView AmazonUS ;         TextView aUSprice ;      Button aUSbrowse ;
    TextView Amazon ;           TextView aprice ;        Button abrowse ;
    TextView Snapdeal ;         TextView sprice ;        Button sbrowse;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_pricing) ;
        object = (RelativeLayout)findViewById(R.id.relative_2) ;
        title = (TextView) findViewById(R.id.textView) ;

        AmazonUS = (TextView) findViewById(R.id.textView3) ;
        Amazon = (TextView) findViewById(R.id.textView4) ;
        Snapdeal = (TextView) findViewById(R.id.textView5) ;

        aUSbrowse = (Button) findViewById(R.id.amUSbutton) ;
        abrowse = (Button) findViewById(R.id.ambutton) ;
        sbrowse = (Button) findViewById(R.id.snapbutton) ;

        // parallel execution
        new AmazonPrice().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;
        new AmazUSAPrice().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;
        new SnapPrice().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;

        aUSbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.amazon.com/s/ref=sr_st_price-asc-rank?keywords=" + ob1.enteredtext + "&fst=as%3Aon&rh=n%3A172282%2Cn%3A541966%2Cn%3A13896617011%2Cn%3A565108%2Cn%3A13896615011%2Ck%3A" + ob1.enteredtext + "&qid=1491992249&sort=price-asc-rank" ;
                goToUrl(url) ;
            }
        });

        abrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.amazon.in/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=" + ob1.enteredtext ;
                goToUrl(url) ;
            }
        });

        sbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.snapdeal.com/search?keyword=" + ob1.enteredtext + "&sort=plth" ;
                goToUrl(url) ;
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url) ;
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl) ;
        startActivity(launchBrowser) ;
    }

    private class AmazUSAPrice extends AsyncTask<Void, Void, Void> {
        String url = "https://www.amazon.com/s/ref=sr_st_price-asc-rank?keywords=" + ob1.enteredtext + "&fst=as%3Aon&rh=n%3A172282%2Cn%3A541966%2Cn%3A13896617011%2Cn%3A565108%2Cn%3A13896615011%2Ck%3A" + ob1.enteredtext + "&qid=1491992249&sort=price-asc-rank" ;
        String price = "" ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute() ;
            progressDialog = new ProgressDialog(Pricing.this) ;
            progressDialog.setTitle("Amazon USA") ;
            progressDialog.setMessage("Loading ...") ;
            progressDialog.setIndeterminate(false) ;
            progressDialog.show() ;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY) ;
                Document amazonUSA = Jsoup.connect(url).userAgent("Mozilla").get() ;
                price = amazonUSA.select("#result_2 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div:nth-child(2) > div.a-column.a-span7 > div:nth-child(1) > div > a > span.a-size-base.a-color-base").first().text();
            } catch (Exception e) {
                e.printStackTrace() ;
            }
            return null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            aUSprice = (TextView) findViewById(R.id.price1) ;
            aUSprice.setText(price) ;progressDialog.dismiss() ;
        }
    }

    private class AmazonPrice extends AsyncTask<Void, Void, Void> {
        String url = "http://www.amazon.in/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=" + ob1.enteredtext ;
        String price = "" ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute() ;
            progressDialog = new ProgressDialog(Pricing.this) ;
            progressDialog.setTitle("Amazon") ;
            progressDialog.setMessage("Loading ...") ;
            progressDialog.setIndeterminate(false) ;
            progressDialog.show() ;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Thread.currentThread().setPriority(Thread.MAX_PRIORITY) ;
                Document amazon = Jsoup.connect(url).userAgent("Mozilla").get() ;
                price = amazon.select("#result_0 > div > div > div > div.a-fixed-left-grid-col.a-col-right > div:nth-child(2) > div.a-column.a-span7 > div.a-row.a-spacing-none > a > span.a-size-base.a-color-price.s-price.a-text-bold").first().text() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
            return null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            aprice = (TextView) findViewById(R.id.price2) ;
            aprice.setText("Rs." + price) ;
            progressDialog.dismiss() ;
        }
    }

    private class SnapPrice extends AsyncTask<Void, Void, Void> {

        String url = "https://www.snapdeal.com/search?keyword=" + ob1.enteredtext + "&sort=plth" ;
        String price = "" ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute() ;
            progressDialog = new ProgressDialog(Pricing.this) ;
            progressDialog.setTitle("Snapdeal") ;
            progressDialog.setMessage("Loading ...") ;
            progressDialog.setIndeterminate(false) ;
            progressDialog.show() ;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Thread.currentThread().setPriority(Thread.MAX_PRIORITY) ;
                Document snapdeal = Jsoup.connect(url).userAgent("Mozilla").get() ;
                price = snapdeal.select("#display-price-646642236948").first().text() ;
            } catch (Exception ioe) {
                ioe.printStackTrace() ;
            }
            return null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sprice = (TextView) findViewById(R.id.price3) ;
            sprice.setText(price) ;
            progressDialog.dismiss() ;
        }
    }
}