package com.ecosort.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ecosort.app.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private TextView txtLogo;
    private TextView txtTitle;
    private TextView txtSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtLogo = findViewById(R.id.txtLogo);
        txtTitle = findViewById(R.id.txtTitle);
        txtSubtitle = findViewById(R.id.txtSubtitle);

        animateViews();

        new Handler().postDelayed(() -> {

            Intent intent =
                    new Intent(
                            SplashActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);

            overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );

            finish();

        }, 2500);
    }

    private void animateViews() {

        txtLogo.setScaleX(0.5f);
        txtLogo.setScaleY(0.5f);

        txtLogo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1200)
                .start();

        txtTitle.animate()
                .alpha(1f)
                .setDuration(1500)
                .start();

        txtSubtitle.animate()
                .alpha(1f)
                .setDuration(1800)
                .start();
    }
}