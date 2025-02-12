package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int computerSymbol = -1;
    private int playerSymbol = -1;

    private Button resetButton;
    private TextView statusText;
    private GridLayout grid;

    int playNumber = 0;
    Random rand = new Random(10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.gridTicTacToe);

        statusText = findViewById(R.id.status);
        resetButton = findViewById(R.id.restartButton);
        resetButton.setOnClickListener(this);

        nouvellePartie();
    }

    private void nouvellePartie() {
        // Det random des symbols
        if(rand.nextInt(2) == 0) {
            computerSymbol = R.drawable.o;
            playerSymbol = R.drawable.x;
        } else {
            computerSymbol = R.drawable.x;
            playerSymbol = R.drawable.o;
        }

        for(int i = 0; i < 9; i++) {
            View view = grid.getChildAt(i);

            // raz des tuiles
            view.setBackground(null);
            view.setBackgroundColor(Color.DKGRAY);
            view.setEnabled(true);

            Drawable symbol = (Drawable) this.getResources().getDrawable(playerSymbol);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackground(symbol);
                    view.setEnabled(false);
                    playNumber++;
                    tourOrdinateur();
                }
            });
        }

        // Det random du joueur qui commence;
        if(rand.nextInt(2) == 0) {
            attendreTourJouer();
        } else {
            if(playNumber < 9) {
                tourOrdinateur();
            }
        }
    }

    private void attendreTourJouer() {
        String symbol = (playerSymbol == R.drawable.x) ? "X" : "O";
        statusText.setText("Votre tour (" + symbol + ") - À vous de jouer!");
    }

    private void tourOrdinateur() {
        statusText.setText("L'ordinateur joue...");

        boolean caseValide = false;
        View view = null;
        while(!caseValide) {
            int index = rand.nextInt(10);
            View caseGrille = grid.getChildAt(index);
                view = caseGrille;
                caseValide = true;
        }

        if(view != null) {
            Drawable drawable = (Drawable) this.getResources().getDrawable(computerSymbol);
            view.setBackground(drawable);
            view.setEnabled(false);
        }

        playNumber++;
        if(playNumber < 9) {
            attendreTourJouer();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == resetButton) {
            nouvellePartie();
        }
    }
}