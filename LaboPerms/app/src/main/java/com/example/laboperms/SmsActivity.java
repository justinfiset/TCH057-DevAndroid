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

public class SmsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Button backBtn, smsBtn;
    private EditText phoneInput, msgInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

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

        smsBtn = findViewById(R.id.smsBtn);
        smsBtn.setOnClickListener(this);

        phoneInput = findViewById(R.id.phoneInput);
        msgInput = findViewById(R.id.msgInput);
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn) {
            finish();
        } else if(v == smsBtn) {
            Uri uri = Uri.parse("smsto:" + phoneInput.getText().toString());
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", msgInput.getText().toString());
            activityResultLauncher.launch(intent);
        }
    }
}