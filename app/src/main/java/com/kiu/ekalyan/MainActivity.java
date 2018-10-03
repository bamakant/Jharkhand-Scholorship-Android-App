package com.kiu.ekalyan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent i;
    FrameLayout frame;
    String link;
    boolean isConnected;
    TextView textView;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //@ Ads section end

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //@Checking internet connection is there on not
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = cm.getActiveNetworkInfo();
        isConnected = active != null && active.isConnectedOrConnecting();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //All Intents

        textView= (TextView) findViewById(R.id.notificationTextView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "http://ekalyan.cgg.gov.in/Login.do";
                    i.putExtra("web_link", link);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }

            }
        });

        frame = (FrameLayout) findViewById(R.id.student_login);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "http://ekalyan.cgg.gov.in/studentLogin.do";
                    i.putExtra("web_link", link);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }
            }
        });

        frame = (FrameLayout) findViewById(R.id.registration_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new AlertDialog.Builder(MainActivity.this).setMessage("Registration Closed For This Session").setNegativeButton("OK", null).show();

                if (isConnected) {
                    i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "http://ekalyan.cgg.gov.in/studentLogin.do";
                    i.putExtra("web_link", link);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }
            }
        });

        frame = (FrameLayout) findViewById(R.id.forgot_password_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected) {
                    i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "http://ekalyan.cgg.gov.in/studentLogin.do";
                    i.putExtra("web_link", link);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }
            }
        });

        frame = (FrameLayout) findViewById(R.id.eligibilty_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, EligibilityActivity.class);
                startActivity(i);
            }
        });

        frame = (FrameLayout) findViewById(R.id.reason_of_rejection_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, ReasonOfRejectionActivity.class);
                startActivity(i);
            }
        });

        frame = (FrameLayout) findViewById(R.id.instruction_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                   i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "https://drive.google.com/file/d/0B6CJIpKRi5gdbm1VanIzMU53OUE/view?usp=sharing";
                    //link = "https://firebasestorage.googleapis.com/v0/b/sbte-jharkhand.appspot.com/o/b.pdf?alt=media&token=4e89d98b-6fa2-4752-bc6b-9e1a14793db9";
                    i.putExtra("web_link", link);
                    i.setData(Uri.parse(link));
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }
            }
        });

        frame = (FrameLayout) findViewById(R.id.complaints_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    i = new Intent(MainActivity.this, WebContainerActivity.class);
                    link = "http://ekalyan.cgg.gov.in/registerComplaint.do";
                    i.putExtra("web_link", link);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Internet Connection Not Available").setNegativeButton("OK", null).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection S@Override
        if (id == R.id.exit) {
            this.finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Intent about = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(about);
        } else if (id == R.id.nav_help) {
            i = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_rate) {
            /*String url="http://ekalyan.cgg.gov.in/Login.do";
            Intent in=new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse(url));
            startActivity(in);*/
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kiu.ekalyan")));

        } else if (id == R.id.nav_more_apps) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Kiusoftech")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Kiusoftech")));
            }
        }else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey guys check out this app of our e-kalyan web portal.\nhttps://play.google.com/store/apps/details?id=com.kiu.ekalyan");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey guys check out this app of e-Kalyan Jharkhand web portal.");
            startActivity(Intent.createChooser(intent, "Share App"));
        } else if (id == R.id.nav_feedback) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "kiusoftech@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding E-Kalyan Android App");
            intent.putExtra(Intent.EXTRA_TEXT, "Write here your suggestion please.\n");
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onDestroy() {
        if (mAd.isLoaded()) {
            mAd.show();
        }
        super.onDestroy();
    }

}