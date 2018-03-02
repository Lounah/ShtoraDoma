package com.lounah.silkapp.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lounah.silkapp.R;
import com.lounah.silkapp.ui.login.LoginActivity;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.DaggerActivity;


public class SplashActivity extends DaggerActivity {

    @Inject
    SharedPreferences sharedPreferences;

    @Nullable
    @Inject
    @Named("uid")
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfFirstLaunch();
    }

    private void checkIfFirstLaunch() {
        Log.i("uid", userId + " ");
        if (userId == null) {
            onStartLoginActivity();
        } else onStartMainActivity();
    }

//    private void generateAndSaveUserId() {
//        final Random random = new Random(999999999);
//        final int id = random.nextInt();
//        userId = String.valueOf(id);
//        sharedPreferences.edit().putString("uid", userId).apply();
//    }

    private void onStartLoginActivity() {
        Intent toLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(toLoginActivity);
    }

    private void onStartMainActivity() {
        Intent toMainActivity = new Intent(this, MainActivity.class);
        startActivity(toMainActivity);
    }

}
