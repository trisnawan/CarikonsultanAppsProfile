package com.carikonsultan.consultant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.carikonsultan.apps.consultant.core.Credential;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Credential credential = new Credential(getApplicationContext());
        credential.setCredential(
                "",
                "",
                "",
                ""
        );
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}