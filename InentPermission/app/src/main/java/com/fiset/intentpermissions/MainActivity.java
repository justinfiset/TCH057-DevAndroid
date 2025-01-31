package com.fiset.intentpermissions;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher<String[]> multiplePermissionLauncher;

    private Button btnAppeler, btnComposer, btnInternet;
    private EditText phoneInput, webInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        multiplePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> o) {
                        boolean allGranted = true;
                        for (boolean value : o.values()) {
                            if(!value) allGranted = false;
                            break;
                        }

                        if(allGranted) {
                            Toast.makeText(MainActivity.this, "Toutes les permissions sont accordées!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                    }
                }
        );

        phoneInput = findViewById(R.id.phoneInput);
        webInput = findViewById(R.id.webInput);

        btnAppeler = findViewById(R.id.callBtn);
        btnAppeler.setOnClickListener(this);

        btnComposer = findViewById(R.id.dialBtn);
        btnComposer.setOnClickListener(this);

        btnInternet = findViewById(R.id.internetBtn);
        btnInternet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String[] permissionAAccorder = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
        };

        String strTel = "tel:" + phoneInput.getText().toString();
        Uri uri = Uri.parse(strTel);

        if(v == btnAppeler) {
            boolean callPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
            boolean locatinPermissionGrantedd = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if(callPermissionGranted && locatinPermissionGrantedd) {
                Intent iAppeler = new Intent(Intent.ACTION_CALL, uri);
                activityResultLauncher.launch((iAppeler));
            } else {
                multiplePermissionLauncher.launch(permissionAAccorder);
            }
        } else if(v == btnComposer) {
            Intent iComposer = new Intent(Intent.ACTION_DIAL, uri);
            activityResultLauncher.launch((iComposer));
        } else if(v == btnInternet) {
            uri = Uri.parse(webInput.getText().toString());
            Intent iNaviguer = new Intent(Intent.ACTION_VIEW, uri);
            activityResultLauncher.launch((iNaviguer));
        }
    }
}