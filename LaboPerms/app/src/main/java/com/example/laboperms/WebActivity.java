package com.example.laboperms;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Button backBtn, webBtn;
    private EditText webInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

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

        webBtn = findViewById(R.id.webBtn);
        webBtn.setOnClickListener(this);

        webInput = findViewById(R.id.webInput);
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            finish();
        } else if(v == webBtn) {
            Uri uri = Uri.parse(webInput.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activityResultLauncher.launch(intent);
        }
    }
}