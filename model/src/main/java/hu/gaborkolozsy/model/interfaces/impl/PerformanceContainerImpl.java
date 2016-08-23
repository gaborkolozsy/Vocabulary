/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces.impl;

import hu.gaborkolozsy.model.Performance;
import hu.gaborkolozsy.model.interfaces.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Performance} objektumok tárolója.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Performance
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see java.util.Map
 * @see java.util.HashMap
 * @since 2.0.0
 */
public class PerformanceContainerImpl implements Container<Performance> {
    
    /** A {@code Performance} objektumokat tároló mappa. */
    private Map<String, Performance> performanceMap = new HashMap<>();

    /**
     * Visszaadja a tároló mappát.
     * @return {@code Performance} objektumokat tartalmazó mappa
     */
    @Override
    public Map<String, Performance> getMap() {
        return performanceMap;
    }

    /**
     * Beállítja a tároló mappát.
     * @param map a {@code Performance} objektumokat tartalmazó mappa
     */
    @Override
    public void setMap(Map<String, Performance> map) {
        this.performanceMap = map;
    }
    
    /**
     * Ellenőrzi, hogy a tároló tartalmazza e a kiválasztott kulcsot.
     * @param key kulcs
     * @return <b>true</b> ha a mappa tartalmazza a kiválasztott kulcsot
     */
    @Override
    public boolean containsKey(String key) {
        return performanceMap.containsKey(key);
    }
    
    /**
     * Visszaadja a kiválasztott kulcshoz tartozó {@code Performance} objektumot.
     * @param key kulcs
     * @return {@code Performance} objektum
     */
    @Override
    public Performance get(String key) {
        if (performanceMap.get(key) != null) {
            return performanceMap.get(key);
        } else {
            return null;
        }
    }
    
    /**
     * A tárolóhoz adja a {@code Performance} objektumot a kiválasztott kulccsal.
     * @param key kulcs
     * @param performance {@code Performance} objektum
     */
    @Override
    public void add(String key, Performance performance) {
        performanceMap.put(key, performance);
    }

    /**
     * Törli a kiválasztott kulcsot a mappából.
     * @param key kulcs
     * @return {@code Performance} objektum ha sikerült a törlés<br>
     * {@code null} ha nem tartalmazta a mappa a kulcsot
     */
    @Override
    public Performance remove(String key) {
        return performanceMap.remove(key);
    }
}
