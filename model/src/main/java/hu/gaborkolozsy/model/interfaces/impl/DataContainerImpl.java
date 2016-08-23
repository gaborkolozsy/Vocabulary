/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces.impl;

import hu.gaborkolozsy.model.Data;
import hu.gaborkolozsy.model.interfaces.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Data} objektumok tárolója.
 * Fájlba írás elött és után.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Data
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see java.util.HashMap
 * @see java.util.Map
 * @since 2.0.0
 */
public class DataContainerImpl implements Container<Data> {
    
    /** A {@code Data} objektumokat tároló mappa. */
    private Map<String, Data> dataMap = new HashMap<>();

    /**
     * Visszaadja a tároló mappát.
     * @return {@code Data} objektumokat tartalmazó mappa
     */
    @Override
    public Map<String, Data> getMap() {
        return dataMap;
    }

    /**
     * Beállítja a tároló mappát.
     * @param map az {@code Data} objektumokat tartalmazó mappa
     */
    @Override
    public void setMap(Map<String, Data> map) {
        this.dataMap = map;
    }
    
    /**
     * Ellenőrzi, hogy a tároló tartalmazza e a kiválasztott kulcsot.
     * @param key kulcs
     * @return <b>true</b> ha a mappa tartalmazza a kiválasztott kulcsot
     */
    @Override
    public boolean containsKey(String key) {
        return dataMap.containsKey(key);
    }
    
    /**
     * Visszaadja a kiválasztott kulcshoz tartozó {@code Data} objektumot.
     * @param key kulcs
     * @return {@code Data} objektum
     */
    @Override
    public Data get(String key) {
        if (dataMap.get(key) != null) {
            return dataMap.get(key);
        } else {
            return null;
        }
    }
    
    /**
     * A tárolóhoz adja a {@code Data} objektumot a kiválasztott kulccsal.
     * @param key kulcs
     * @param data {@code Data} objektum
     */
    @Override
    public void add(String key, Data data) {
        dataMap.put(key, data);
    }
    
    /**
     * Törli a kiválasztott kulcsot a mappából.
     * @param key kulcs
     * @return {@code Data} objektum ha sikerült a törlés<br> 
     * {@code null} ha nem tartalmazta a mappa a kulcsot
     */
    @Override
    public Data remove(String key) {
        return dataMap.remove(key);
    }
}
