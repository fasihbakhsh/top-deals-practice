package com.codinghomies.topdeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class PrivacyPolicy extends AppCompatActivity {

    TextView textView, textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Toolbar screen_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(screen_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.text);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

        textView.setText(R.string.privacy_policy_details_one);

        textView1.setText(R.string.privacy_policy_google);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse("https://policies.google.com/privacy"));
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy"));
                    startActivity(i);
                }
            }
        });

        textView2.setText(R.string.privacy_policy_details_two);

        textView3.setText(R.string.privacy_policy_feedback);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_feedback = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("codinghomies@gmail.com") + "?subject=" + Uri.encode("Feedback");
                Uri mail_uri = Uri.parse(uriText);
                intent_feedback.setData(mail_uri);
                startActivity(Intent.createChooser(intent_feedback, "Send Feedback"));
            }
        });

    }
}