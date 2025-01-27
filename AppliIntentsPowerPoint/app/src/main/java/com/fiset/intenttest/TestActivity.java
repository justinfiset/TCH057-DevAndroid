package com.fiset.intenttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TestActivity extends AppCompatActivity {
    int equatinRes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        if(username != null) {
            TextView textViewNom = findViewById(R.id.nomTxt);
            textViewNom.setText(username);
        }

        TextView operation = findViewById(R.id.operationTxt);
        Button btnOk = findViewById(R.id.confirm);
        EditText res = findViewById(R.id.resInput);

        int op = (int) (Math.random() * 3) + 1;
        int n1 = (int) (Math.random() * 100);
        int n2 = (int) (Math.random() * 100);

        switch (op) {
            case 1:
                equatinRes = n1 + n2;
                operation.setText(n1 + " + " + n2);
                break;
            case 2:
                equatinRes = n1 - n2;
                operation.setText(n1 + " - " + n2);
                break;
            case 3:
                equatinRes = n1 * n2;
                operation.setText(n1 + " * " + n2);
                break;
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean resValide = false;

                try {
                    int resUser = Integer.parseInt(res.getText().toString());
                    if(resUser == 1) {
                        resValide = true;
                    }
                } catch(Exception e) { }

                Intent intent = new Intent();
                intent.putExtra("TEST_REUSSI", resValide);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
