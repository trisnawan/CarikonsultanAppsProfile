package com.carikonsultan.apps.consultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.carikonsultan.apps.consultant.core.AppInterface;
import com.carikonsultan.apps.consultant.core.AuthInterface;
import com.carikonsultan.apps.consultant.core.Credential;
import com.carikonsultan.apps.consultant.ui.auth.ConnectFragment;
import com.carikonsultan.apps.consultant.ui.profile.ProfileFragment;

public class CarikonsultanProfileActivity extends AppCompatActivity implements AppInterface {
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Profile");
        }
        onBack();
    }

    @Override
    public void onUnauthorized() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ConnectFragment connectFragment = new ConnectFragment();
        transaction.replace(R.id.frame_layout, connectFragment);
        transaction.commit();
    }

    @Override
    public void onBack() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        transaction.replace(R.id.frame_layout, profileFragment);
        transaction.commit();
    }
}