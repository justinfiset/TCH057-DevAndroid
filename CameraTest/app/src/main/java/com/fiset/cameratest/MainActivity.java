package com.fiset.cameratest;

import android.Manifest;
import android.app.Instrumentation;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnPrendrePhoto;
    private ImageView imageView;

    private ActivityResultLauncher<Void> launcherPrendrePhoto;
    private ActivityResultLauncher<String> permissionLauncher;

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

        btnPrendrePhoto = findViewById(R.id.photoBtn);
        imageView = findViewById(R.id.imageView);

        launcherPrendrePhoto = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if(result != null) {
                            imageView.setImageBitmap(result);
                            Toast.makeText(MainActivity.this, "Photo prise.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Photo annulée.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean o) {
                        if(o) {
                            prendrePhoto();
                        } else {
                            Toast.makeText(MainActivity.this, "Vous devez donner l'accès à la caméra.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnPrendrePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    prendrePhoto();
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }
        });
    }

    private void prendrePhoto() {
        launcherPrendrePhoto.launch(null);
    }
}