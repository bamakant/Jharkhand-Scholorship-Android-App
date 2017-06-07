package com.kiu.ekalyan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_container extends AppCompatActivity {

    private WebView mywebview;
    public String web_url;
    Intent i;
    ProgressDialog progressDialog;


    //@it navigate the webview using history of visiting
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //Check wheather there is history page available or note
        //If available then go back on back button press
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mywebview.canGoBack()) {
            mywebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_container);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //@linking xml webview to java code
        mywebview = (WebView) findViewById(R.id.webview);

        //Intent creating
        i = getIntent();
        web_url = i.getStringExtra("web_link");         // Web link is here

        //#loding the url address of the webpage to be displayed on the activity.
        mywebview.loadUrl(web_url);

        //@Enabling java script setting so that webview can hadle website java scripting
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);

        //Progress bar code block
        progressDialog = new ProgressDialog(Web_container.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //this allow the new links or webpages clicked by user to be load on same activity.
        mywebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        });

        //@set the screen size to be large or wide so that whole web page can be displayed on the screen clearly
        mywebview.getSettings().setUseWideViewPort(true);

        //@it allows the users to Zoom the webpage so that user can easily view the webpage.
        mywebview.getSettings().setSupportZoom(true);
        mywebview.getSettings().setBuiltInZoomControls(true);

    }


    // right side upper three dot menu item

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //for back button and option menu item click event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.exit:
                this.finishAffinity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        new AlertDialog.Builder(Web_container.this).setMessage("This is ekalyan.cgg.gov.in web portal of jharkhand goverment. You can easily Zoom it by your finger touch to get a better access.").setNegativeButton("OK",null).show();
        super.onStart();
    }
}

