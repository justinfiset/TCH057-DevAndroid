package com.fiset.emptyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2, button3;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textViewMessage = findViewById(R.id.textViewMessage);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1) {
            textViewMessage.setText("Vous avez cliqué sur le boutton 1");
        } else if(v.getId() == R.id.button2) {
            textViewMessage.setText("Vous avez cliqué sur le boutton 2");
        } else if(v.getId() == R.id.button3) {
            textViewMessage.setText("Vous avez cliqué sur le boutton 3");
        }
    }
}
