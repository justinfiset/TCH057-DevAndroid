package com.fiset.labo5_pourlabnote.dao;

import com.fiset.labo5_pourlabnote.entite.Tache;

import java.util.ArrayList;
import java.util.List;

public class TachesDao {
    private static TachesDao instance = null;
    private List<Tache> taches = new ArrayList<>();

    public TachesDao() {
        String[]
            noms = {"Faire le labo noté", "Vérifier le labo", "Corriger le labo"},
            descriptions = {"Terminer le code", "Vérifier que le code fonctionne", "Vérifier que les critères sont respectées"},
            etats = {"Terminée", "En cours", "Initial"}
        ;

        for(int i = 0; i < noms.length; i++) {
            Tache tache = new Tache();
            tache.setNom(noms[i]);
            tache.setDescription(descriptions[i]);
            tache.setEtat(etats[i]);

            taches.add(tache);
        }
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public Tache getTacheParNom(String nom) {
        for (Tache tache : taches) {
            if(nom.equals(tache.getNom())) {
                return tache;
            }
        }
        return null;
    }

    public boolean ajouter(Tache tache) {
        if(getTacheParNom(tache.getNom()) != null) {
            return false;
        } else {
            return taches.add(tache);
        }
    }

    public boolean modifier(Tache nouvelleTache) {
        Tache tache = getTacheParNom(nouvelleTache.getNom());

        if(tache != null) {
            tache.setNom(nouvelleTache.getNom());
            tache.setEtat(nouvelleTache.getEtat());
            tache.setDescription(nouvelleTache.getDescription());
            return true;
        } else return false;
    }

    // Gestion du singleton
    public static TachesDao getInstance() {
        if(instance == null) {
            instance = new TachesDao();
        }
        return instance;
    }
}
