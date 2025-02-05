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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Button backBtn, callBtn;
    private EditText phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                    }
                }
        );

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        callBtn = findViewById(R.id.callBtn);
        callBtn.setOnClickListener(this);
        phoneInput = findViewById(R.id.phoneInput);
    }

    @Override
    public void onClick(View v) {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };

        if(v == backBtn) {
            finish();
        } else if(v == callBtn) {
                Uri uri = Uri.parse("tel:" + phoneInput.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                activityResultLauncher.launch(intent);
        }
    }
}