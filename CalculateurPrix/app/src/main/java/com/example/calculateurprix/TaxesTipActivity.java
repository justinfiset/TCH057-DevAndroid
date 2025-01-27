package com.example.calculateurprix;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TaxesTipActivity extends AppCompatActivity {
    private TextView priceWithTaxLbl;
    private EditText tipInput;
    private Button okBtn;

    double quantity = -1;
    double individualPrice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes_tip);

        priceWithTaxLbl = findViewById(R.id.lblPrixTaxes);
        okBtn = findViewById(R.id.btnConfirmer);
        tipInput = findViewById(R.id.inputPourboire);

        Intent result = getIntent();
        individualPrice = result.getDoubleExtra("PRICE", 2);
        quantity = result.getDoubleExtra("QUANTITY", 2);

        double price = individualPrice * quantity;
        double priceWithTaxes = price * (1 + Taxes.TPS + Taxes.TVQ);

        priceWithTaxLbl.setText(Double.toString(priceWithTaxes));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                double tip = 0;
                try {
                    tip = Double.parseDouble(tipInput.getText().toString());
                } catch(Exception e) { }

                intent.putExtra("TOTAL_PRICE", priceWithTaxes + tip);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public class Taxes {
        public static final double TPS = 0.05;
        public static final double TVQ = 0.09975;
    }

}