package com.kiu.ekalyan;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView privacyText, disclaimerText;
    private CustomTabsIntent.Builder builder;
    private CustomTabsIntent customTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /**
         * Chrome custom tabs
         */
        builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        builder.setStartAnimations(getApplicationContext(),R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(getApplicationContext(), R.anim.slide_in_left, R.anim.slide_out_right);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp));
        builder.enableUrlBarHiding();
        builder.setShowTitle(false);
        customTabsIntent = builder.build();

        privacyText = findViewById(R.id.privacy_policy_textview);
        disclaimerText = findViewById(R.id.disclaimer_textview);

        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTabsIntent.launchUrl(AboutActivity.this, Uri.parse("https://ekalyan-jharkhandscholorship.blogspot.com/p/privacy-policy-kiusoftech-built-e.html"));
            }
        });
        disclaimerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTabsIntent.launchUrl(AboutActivity.this, Uri.parse("https://ekalyan-jharkhandscholorship.blogspot.com/p/blog-page.html"));
            }
        });

    }
}
