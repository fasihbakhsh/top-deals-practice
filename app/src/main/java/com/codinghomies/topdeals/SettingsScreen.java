package com.codinghomies.topdeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsScreen extends AppCompatActivity implements View.OnClickListener {

    Button button_privacy, button_feedback, button_app_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Toolbar screen_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(screen_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_privacy = findViewById(R.id.privacy_button);
        button_privacy.setOnClickListener(this);

        button_feedback = findViewById(R.id.feedback_button);
        button_feedback.setOnClickListener(this);

        button_app_rate = findViewById(R.id.app_rate_button);
        button_app_rate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.privacy_button:
                Intent intent_login = new Intent(this, PrivacyPolicy.class);
                startActivity(intent_login);
                break;

            case R.id.feedback_button:
                Intent intent_feedback = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("codinghomies@gmail.com") + "?subject=" + Uri.encode("Feedback");
                Uri mail_uri = Uri.parse(uriText);
                intent_feedback.setData(mail_uri);
                startActivity(Intent.createChooser(intent_feedback, "Send Feedback"));
                break;

            case R.id.app_rate_button:
                rateApp(getPackageName());
                break;
        }
    }

    public void rateApp(final String packageName) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
                }
    }


}