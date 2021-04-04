package com.tex.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tex.utils.Action;

public class BaseActivity extends AppCompatActivity {

    /**
     * Method to hide action bar
     */
    public void hideActionBar() {
//        getSupportActionBar().hide();
    }

    /**
     * Method to check if particular permission has been granted to application.
     *
     * @param iPermission Permission to be checked.
     * @param iSuccess    Callback to notify when permission has been granted.
     * @param iFailure    Callback to notify when permission was declined.
     */
    public void checkPermissionGranted(String iPermission, Action iSuccess, Action iFailure) {
        if (ContextCompat.checkSelfPermission(
                this, iPermission) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            iSuccess.execute();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // We can explain to user why it is necessary for now we fail the case only.
            iFailure.execute();
        } else {
            // You can directly ask for the permission.
            ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                        if (isGranted) {
                            iSuccess.execute();
                        } else {
                            iFailure.execute();
                        }
                    });
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
