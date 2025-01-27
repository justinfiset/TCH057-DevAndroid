package com.fiset.intenttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnFaireTest, btnContinuer;
    private EditText nomInput;

    private ActivityResultLauncher<Intent> activityResultLauncher;

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

        btnFaireTest = findViewById(R.id.testButtton);
        btnContinuer = findViewById(R.id.continueBtn);
        nomInput = findViewById(R.id.nameInput);

        btnContinuer.setEnabled(false);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                            boolean testReussi = result.getData().getBooleanExtra("TEST_REUSSI", false);
                            if(testReussi) {
                                btnContinuer.setEnabled(testReussi);
                            }
                        }
                    }
                }
        );

        btnFaireTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nomInput.getText().toString().trim();
                if(!username.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("USERNAME", username);
                    activityResultLauncher.launch(intent);
                } else {
                    nomInput.setError("Veuillez entrer un nom pls");
                }
            }
        });
    }
}