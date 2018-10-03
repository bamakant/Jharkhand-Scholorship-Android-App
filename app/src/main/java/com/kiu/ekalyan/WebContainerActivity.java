package com.kiu.ekalyan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class WebContainerActivity extends AppCompatActivity {
        //web_url = i.getStringExtra("web_link");         // Web link is here
    private static final String TAG = "webview";
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    ProgressBar progressBar;
    WebView webView;
    private RewardedVideoAd mAd;
    private InterstitialAd mInterstitialAd;
    private String webview_url;
    private AdRequest adRequest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_container);
        progressBar = findViewById(R.id.progressbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        checkStoragePermition();
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        this.adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(this.adRequest);
        webview_url = getIntent().getStringExtra("web_link");
        // Use an activity context to get the rewarded video instance.
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.loadAd(getResources().getString(R.string.video_ad), new AdRequest.Builder().build());

        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                exitActivity();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
            }

            @Override
            public void onRewardedVideoCompleted() {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interntitial_ad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                printTheWebview();
            }
        });
        setWebView();
    }

    private void exitActivity() {
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    Log.d(TAG, "The Ad is not loaded.");
                    exitActivity();
                }
                return true;
            case R.id.action_print:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    printTheWebview();
                }
                return true;
            case R.id.reload:
                this.webView.reload();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printTheWebview() {
		/*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;*/
        try {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
            String jobName = this.webView.getTitle() + " Document";
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

            if (printJob.isCompleted()) {
                Toast.makeText(getApplicationContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
            } else if (printJob.isFailed()) {
                Toast.makeText(getApplicationContext(), R.string.print_failed, Toast.LENGTH_LONG).show();
            }
            // Save the job object for later status checking
        } catch (Exception e) {
            Toast.makeText(this, "Error : Try Again Later", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            Log.d(TAG, "The Ad is not loaded.");
            exitActivity();
        }
    }

    public void setWebView() {
        this.webView = (WebView) findViewById(R.id.webview);
        //this.webView.loadUrl("http://jharbhoomi.nic.in/jhrlrmsmis/");
        this.webView.loadUrl(webview_url);
        this.webView.setWebChromeClient(new C04733());
        this.webView.setWebViewClient(new MyWebViewClient());
        this.webView.setDownloadListener(new MyWebViewClient());
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.clearCache(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().getDomStorageEnabled();
        this.webView.getSettings().setSupportZoom(true);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.setOnKeyListener(new C04744());
    }

    class C04733 extends WebChromeClient {

        class C04721 implements View.OnKeyListener {
            C04721() {
            }

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == 0) {
                    WebView newView = (WebView) v;
                    switch (keyCode) {
                        case 4:
                            if (newView.canGoBack()) {
                                newView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        }

        C04733() {
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView newView = new WebView(WebContainerActivity.this);
            newView.setWebViewClient(new MyWebViewClient());
            newView.setDownloadListener(new MyWebViewClient());
            newView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            newView.getSettings().getDomStorageEnabled();
            newView.getSettings().setSupportZoom(true);
            newView.getSettings().setBuiltInZoomControls(true);
            newView.getSettings().setAllowFileAccess(true);
            newView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            newView.clearCache(true);
            newView.setOnKeyListener(new C04721());
            WebContainerActivity.this.webView.addView(newView);
//            resultMsg.obj.setWebView(newView);
            resultMsg.sendToTarget();
            return true;
        }
    }

    class C04744 implements View.OnKeyListener {
        C04744() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == 0) {
                WebView webView = (WebView) v;
                switch (keyCode) {
                    case 4:
                        if (webView.canGoBack()) {
                            webView.goBack();
                            return true;
                        }
                        break;
                }
            }
            return false;
        }
    }

    class MyWebViewClient extends WebViewClient implements DownloadListener {
        MyWebViewClient() {
        }
        @Override
        public void onLoadResource(WebView view, String url) {
            if(!isConnected()){
                webView.setVisibility(View.INVISIBLE);
                new AlertDialog.Builder(WebContainerActivity.this).setMessage("Internet Connection Not Available")
                        .setTitle("Network Error")
                        .setCancelable(false)
                        .setIcon(R.drawable.fail)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                WebContainerActivity.this.finish();
                            }
                        }).show();
            }
            super.onLoadResource(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(WebContainerActivity.this);
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case 0:
                    message = "The certificate is not yet valid.";
                    break;
                case 1:
                    message = "The certificate has expired.";
                    break;
                case 2:
                    message = "The certificate Hostname mismatch.";
                    break;
                case 3:
                    message = "The certificate authority is not trusted.";
                    break;
            }
            message = message + " Do you want to continue anyway?";
            builder.setTitle("SSL Certificate Error");
            builder.setIcon(R.drawable.fail);
            builder.setMessage(message);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            builder.create().show();
        }

        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(WebContainerActivity.this);
            alert.setTitle("Download");
            alert.setIcon(R.drawable.icondownload);
            alert.setMessage("Do you want to download this file?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(DialogInterface dialog, int which) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(1);
                    request.setDestinationInExternalPublicDir("Ekalyan", url.substring(url.lastIndexOf("/")+1));
                    ((DownloadManager) WebContainerActivity.this.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
                    Toast.makeText(WebContainerActivity.this.getApplicationContext(), "Downloading File", 1).show();
                }
            });
            alert.create();
            alert.show();
        }
    }
    private void checkStoragePermition() {
        if (ContextCompat.checkSelfPermission(this,  Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Write Storage Permission Needed")
                        .setMessage("Needs the  Permition, please accept the request.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(WebContainerActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_STORAGE);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
        }
    }
    /**
     * Check if there is any connectivity
     *
     * @return is Device Connected
     */
    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        }

        return false;

    }

}

