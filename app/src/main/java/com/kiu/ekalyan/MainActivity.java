package com.kiu.ekalyan;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent i;
    FrameLayout frame;
    String link;
    boolean isConnected;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
                    i = new Intent(MainActivity.this, Web_container.class);
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
                    i = new Intent(MainActivity.this, Web_container.class);
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
                new AlertDialog.Builder(MainActivity.this).setMessage("Registration Closed For This Session").setNegativeButton("OK", null).show();

            }
        });

        frame = (FrameLayout) findViewById(R.id.forgot_password_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected) {
                    i = new Intent(MainActivity.this, Web_container.class);
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
                i = new Intent(MainActivity.this, Eligibility.class);
                startActivity(i);
            }
        });

        frame = (FrameLayout) findViewById(R.id.reason_of_rejection_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, ReasonOfRejection.class);
                startActivity(i);
            }
        });

        frame = (FrameLayout) findViewById(R.id.instruction_frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    i = new Intent(MainActivity.this, Web_container.class);
                    link = "https://drive.google.com/file/d/0B6CJIpKRi5gdbm1VanIzMU53OUE/view?usp=sharing";
                    i.putExtra("web_link", link);
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
                    i = new Intent(MainActivity.this, Web_container.class);
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

        //noinspection SimplifiableIfStatement
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

            Intent about = new Intent(MainActivity.this, AboutUs.class);
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

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey guys check out this app of our e-kalyan web portal.\nhttps://play.google.com/store/apps/details?id=com.kiu.ekalyan");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey guys check out this app of e-Kalyan Jharkhand web portal.");
            startActivity(Intent.createChooser(intent, "Share"));
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


}