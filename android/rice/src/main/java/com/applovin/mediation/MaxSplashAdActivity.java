package com.applovin.mediation;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Date：2025/6/12
 * Describe:
 */
public class MaxSplashAdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        ((ViewGroup) (this.getWindow().getDecorView())).removeAllViews();
        super.onDestroy();
    }
}
