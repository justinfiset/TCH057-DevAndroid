package com.example.calculateurprix;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Button taxTipBtn;
    private EditText inputPrice, inputQuantity;
    private TextView totalPriceLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taxTipBtn = findViewById(R.id.taxTipBtn);
        inputPrice = findViewById(R.id.inputPrix);
        inputQuantity = findViewById(R.id.inputQuantity);
        totalPriceLbl = findViewById(R.id.totalPriceLbl);

        totalPriceLbl.setText("");

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK && result != null) {
                            double price = result.getData().getDoubleExtra("TOTAL_PRICE", -1);
                            if(price >= 0) {
                                totalPriceLbl.setText(Double.toString(price));
                            }
                        }
                    }
                }
        );

        taxTipBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaxesTipActivity.class);

                double price = 0, qts = 0;
                try {
                    price = Double.parseDouble(inputPrice.getText().toString());
                    qts = Double.parseDouble(inputQuantity.getText().toString());
                } catch(Exception e) {
                    taxTipBtn.setError("Veuillez entrez des informations valides.");
                }

                intent.putExtra("PRICE", price);
                intent.putExtra("QUANTITY", qts);
                activityResultLauncher.launch(intent);
            }
        });
    }
}