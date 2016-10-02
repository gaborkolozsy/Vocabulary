/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Az aktuális futam adatait kezeli.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.util.ArrayList
 * @see java.util.Collections
 * @see java.util.List
 * @since 2.0.0
 */
public class RaceService {
    
    /** Az ideiglenes index lista. */
    private final List<Integer> temporary = new ArrayList<>();
    
    /** A futam fordításra váró indexeinek listája. */
    private final List<Integer> raceIdxsList = new ArrayList<>();
    
    /** Jó fordítás. */
    private double goodTranslation;
    
    /** Rossz fordítás. */
    private double badTranslation;
    
    /** Jó fordítás %-ban. */
    private int goodTranslationPercent;
    
    /** Rossz fordítás %-ban. */
    private int badTranslationPercent;
    
    /**
     * Konstruktor.<br>
     * Feltölti a <b>raceIdxsList</b> adattagot a már megtanult és paraméterül 
     * átadott <b>learnedIdxs</b> lista figyelembevételével.
     * 
     * @param keyListSize a szószedet kulcslistájának mérete
     * @param learnedIdxs a már megtanult szavak azonosító indexeinek listája
     * @param newWordInThisRace a futamban fordítandó új szavak száma
     */
    public RaceService(int keyListSize, List<Integer> learnedIdxs, 
            int newWordInThisRace) {
        
        for (int i = 0; i < keyListSize; i++) {
            this.temporary.add(i);
        }
        
        this.temporary.removeAll(learnedIdxs);
        
        for (int i = 0; i < newWordInThisRace; i++) {
            int idx = (int) (Math.random() * this.temporary.size());
            if (this.raceIdxsList.contains(this.temporary.get(idx))) {
                i--;
            } else {
                this.raceIdxsList.add(this.temporary.get(idx));
            }
        }
        
        Collections.sort(this.raceIdxsList);
    }
    
    /**
     * Visszaadja a <b>raceIdxsList</b> lista elemét a kiválasztott indexnél.
     * @param index a kiválasztott index
     * @return a keresett elem
     */
    public int getElem(int index) {
        if (index >= 0 && index < raceIdxsList.size()) {
            return raceIdxsList.get(index);
        }
        return 0;
    }
    
    /**
     * Hozzáadja a kiválasztott elemet a <b>raceIdxsList</b> lista végéhez.
     * @param elem a kiválasztott elem
     */
    public void addElem(int elem) {
        raceIdxsList.add(elem);
    }
    
    /**
     * Törli a kiválasztott elemet a <b>raceIdxsList</b> listából.
     * @param elem a kiválasztott elem
     */
    public void removeElem(int elem) {
        raceIdxsList.remove(elem);
    }
    
    /**
     * <b>True</b> ha a <b>raceIdxsList</b> lista nem tartalmaz elemet.
     * @return <b>true</b> ha a <b>raceIdxsList</b> lista adattag üres<br>
     * <b>false</b> máskülönben
     */
    public boolean isEmpty() {
        return raceIdxsList.isEmpty();
    }

    /**
     * Visszaadja a <b>goodTranslation</b> adattagot.
     * @return jó fordítások száma
     */
    public double getGoodTranslation() {
        return goodTranslation;
    }

    /**
     * Növeli a <b>goodTranslation</b> adattag értékét.
     */
    public void addingGoodTranslation() {
        this.goodTranslation++;
    }
    
    /**
     * Visszaadja a <b>badTranslation</b> adattagot.
     * @return rossz fordítások száma
     */
    public double getBadTranslation() {
        return badTranslation;
    }

    /**
     * Növeli a <b>badTranslation</b> adattag értékét.
     */
    public void addingBadTranslation() {
        this.badTranslation++;
    }

    /**
     * Visszaadja a <b>goodTranslationPercent</b> adattagot.
     * @return jó fordítások százalékosan
     */
    public int getGoodTranslationPercent() {
        return goodTranslationPercent;
    }
    
    /**
     * Visszaadja a <b>badTranslationPercent</b> adattagot.
     * @return rossz fordítások százalékosan
     */
    public int getBadTranslationPercent() {
        return badTranslationPercent;
    }
    
    /**
     * Beállítja a <b>goodTranslationPercent</b> és 
     * <b>badTranslationPercent</b> adattagokat.
     */
    public void calculateTranslationsInPercent() {
        this.goodTranslationPercent = (int) (goodTranslation /
                (goodTranslation + badTranslation) * 100);
        
        this.badTranslationPercent = (int) (badTranslation /
                (goodTranslation + badTranslation) * 100);
    }
}
