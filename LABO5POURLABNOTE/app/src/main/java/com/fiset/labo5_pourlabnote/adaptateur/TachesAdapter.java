package com.fiset.labo5_pourlabnote.adaptateur;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fiset.labo5_pourlabnote.R;
import com.fiset.labo5_pourlabnote.entite.Tache;

import java.util.List;

public class TachesAdapter extends ArrayAdapter<Tache> {
    private List<Tache> taches;
    private Context context;
    private int viewResourceID;

    public TachesAdapter(@NonNull Context context, int viewResourceID, @NonNull List<Tache> taches) {
        super(context, viewResourceID, taches);

        this.context = context;
        this.viewResourceID = viewResourceID;
        this.taches = taches;
    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @NonNull
    @Override
    public View getView(int positoin, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Récupération du view
        View view = convertView;
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewResourceID, parent, false);
        }

        // Récupératin de la tâche courrante à infalte
        Tache tache = taches.get(positoin);

        if(tache != null) {
            TextView nomTache = view.findViewById(R.id.labelTacheNom);
            TextView etatTache = view.findViewById(R.id.labelTacheEtat);
            TextView descriptionTache = view.findViewById(R.id.labelTacheDescription);

            nomTache.setText(tache.getNom());
            etatTache.setText(tache.getEtat());
            descriptionTache.setText(tache.getDescription());

            switch (tache.getEtat()) {
                case "Initial" :
                    etatTache.setTextColor(Color.BLACK);
                    break;
                case "En cours":
                    etatTache.setTextColor(Color.CYAN);
                    break;
                case "Terminée":
                    etatTache.setTextColor(Color.GREEN);
                    break;
            }
        }
        return view;
    }
}
