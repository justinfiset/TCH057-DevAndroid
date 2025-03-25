package com.fiset.labo5_pourlabnote;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fiset.labo5_pourlabnote.dao.TachesDao;
import com.fiset.labo5_pourlabnote.entite.Tache;

public class AjouterTacheActivity extends AppCompatActivity {
    private Button btnAjouter, btnAnnuler;
    private EditText editNom, editDesc;
    private Spinner spinnerEtat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajouter_tache);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNom = findViewById(R.id.editNomTache);
        editDesc = findViewById(R.id.editDescTache);
        spinnerEtat = findViewById(R.id.spinnerEtatTache);

        btnAjouter = findViewById(R.id.btnAjouterTache);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = editNom.getText().toString().trim();
                if(!nom.isEmpty()) {
                    Tache tache = new Tache();
                    tache.setNom(nom);
                    tache.setDescription(editDesc.getText().toString());
                    tache.setEtat(spinnerEtat.getSelectedItem().toString());

                    if(TachesDao.getInstance().ajouter(tache)) {
                        setResult(RESULT_OK);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                } else {
                    Toast.makeText(AjouterTacheActivity.this, "Le nom de la tâche ne peut pas être vide.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}