package com.fiset.labo5_pourlabnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fiset.labo5_pourlabnote.adaptateur.TachesAdapter;
import com.fiset.labo5_pourlabnote.dao.TachesDao;
import com.fiset.labo5_pourlabnote.entite.Tache;

import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TachesDao tachesDao;
    private TachesAdapter tachesAdapter;

    private Button btnAjouterTache;
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

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK) {
                            rafraichirListeTaches();
                        }
                    }
                }
        );

        btnAjouterTache = findViewById(R.id.btnAjouterTache);
        btnAjouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AjouterTacheActivity.class);
                activityResultLauncher.launch(intent);
            }
        });


        listView = findViewById(R.id.listeTaches);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tache tache = (Tache) adapterView.getAdapter().getItem(i);

                Intent intent = new Intent(MainActivity.this, ModifierTacheActivity.class);
                intent.putExtra("NOM_TACHE", tache.getNom());
                activityResultLauncher.launch(intent);
            }
        });

        rafraichirListeTaches();
    }

    private void rafraichirListeTaches() {
        tachesDao = TachesDao.getInstance();
        tachesAdapter = new TachesAdapter(this, R.layout.tache_layout, tachesDao.getTaches());
        listView.setAdapter(tachesAdapter);
    }
}