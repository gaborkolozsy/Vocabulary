/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces.impl;

import hu.gaborkolozsy.model.RaceComboBox;
import hu.gaborkolozsy.model.interfaces.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code RaceComboBox} objektumok tárolója.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.RaceComboBox
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see java.util.HashMap
 * @see java.util.Map
 * @since 2.0.0
 */
public class RaceComboBoxContainerImpl implements Container<RaceComboBox> {
    
    /** A {@code RaceComboBox} objektumokat tároló mappa. */
    private Map<String, RaceComboBox> raceComboBoxMap = new HashMap<>();

    /**
     * Visszaadja a tároló mappát.
     * @return {@code RaceComboBox} objektumokat tartalmazó mappa
     */
    @Override
    public Map<String, RaceComboBox> getMap() {
        return raceComboBoxMap;
    }

    /**
     * Beállítja a tároló mappát.
     * @param map a {@code RaceComboBox} objektumokat tartalmazó mappa
     */
    @Override
    public void setMap(Map<String, RaceComboBox> map) {
        this.raceComboBoxMap = map;
    }

    /**
     * Ellenőrzi, hogy a tároló tartalmazza e a kiválasztott kulcsot.
     * @param key kulcs
     * @return <b>true</b> ha a mappa tartalmazza a kiválasztott kulcsot
     */
    @Override
    public boolean containsKey(String key) {
        return raceComboBoxMap.containsKey(key);
    }

    /**
     * Visszaadja a kiválasztott kulcshoz tartozó {@code RaceComboBox} objektumot.
     * @param key kulcs
     * @return {@code RaceComboBox} objektum
     */
    @Override
    public RaceComboBox get(String key) {
        if (raceComboBoxMap.get(key) != null) {
            return raceComboBoxMap.get(key);
        } else {
            return null;
        }
    }

    /**
     * A tárolóhoz adja a {@code RaceComboBox} objektumot a kiválasztott kulccsal.
     * @param key kulcs
     * @param raceComboBox {@code RaceComboBox} objektum
     */
    @Override
    public void add(String key, RaceComboBox raceComboBox) {
        raceComboBoxMap.put(key, raceComboBox);
    }
    
    /**
     * Törli a kiválasztott kulcsot a mappából.
     * @param key kulcs
     * @return a törölt {@code RaceComboBox} objektum ha sikerült a törlés<br>
     * {@code null} ha nem tartalmazta a mappa a kulcsot
     */
    @Override
    public RaceComboBox remove(String key) {
        return raceComboBoxMap.remove(key);
    }
}
