package com.zhirova.task_3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";
    private final int PERMISSION_REQUEST_INTERNET_ACCESS = 0;
    private View mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainLayout = findViewById(R.id.start);
    }


    @Override
    protected void onStart() {
        super.onStart();
        showPermissionPreview();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_INTERNET_ACCESS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startApplication();
            } else {
                Snackbar.make(mainLayout, R.string.internet_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    private void showPermissionPreview() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_GRANTED) {
            startApplication();
        } else {
            requestInternetPermission();
        }
    }


    private void requestInternetPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Snackbar.make(mainLayout, R.string.internet_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok,
                    view -> ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.INTERNET},
                            PERMISSION_REQUEST_INTERNET_ACCESS)).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_INTERNET_ACCESS);
        }
    }


    private void startApplication() {
        setContentView(R.layout.activity_main);
    }


}
