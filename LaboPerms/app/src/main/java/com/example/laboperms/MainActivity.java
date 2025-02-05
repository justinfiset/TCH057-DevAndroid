package com.example.laboperms;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.Permission;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<String[]> multiplePersmissionLauncher;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Button smsBtn, phoneBtn, webBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                    }
                }
        );

        multiplePersmissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> permissions) {
                        boolean allGranted = true;
                        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                            if (!entry.getValue()) {
                                allGranted = false;
                                Toast.makeText(MainActivity.this, "Veuillez accorder toutes les permissions dans les paramèetres", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        smsBtn = findViewById(R.id.smsBtn);
        phoneBtn = findViewById(R.id.phoneBtn);
        webBtn = findViewById(R.id.webBtn);

        smsBtn.setOnClickListener(this);
        phoneBtn.setOnClickListener(this);
        webBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };

        if(view == phoneBtn) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                multiplePersmissionLauncher.launch(permissions);
            } else {
                Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                activityResultLauncher.launch(intent);
            }
        } else if(view == smsBtn) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                multiplePersmissionLauncher.launch(permissions);
            } else {
                Intent intent = new Intent(MainActivity.this, SmsActivity.class);
                activityResultLauncher.launch(intent);
            }
        } else if(view == webBtn) {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            activityResultLauncher.launch(intent);
        }
    }
}