/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces.impl;

import hu.gaborkolozsy.model.IndexValue;
import hu.gaborkolozsy.model.interfaces.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code IndexValue} objektumok tárolója.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.IndexValue
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see java.util.HashMap
 * @see java.util.Map
 * @since 2.0.0
 */
public class IndexValueContainerImpl implements Container<IndexValue> {
    
    /** A {@code IndexValue} objektumokat tároló mappa. */
    private Map<String, IndexValue> indexValueMap = new HashMap<>();

    /**
     * Visszaadja a tároló mappát.
     * @return {@code IndexValue} objektumokat tartalmazó mappa
     */
    @Override
    public Map<String, IndexValue> getMap() {
        return indexValueMap;
    }

    /**
     * Beállítja a tároló mappát.
     * @param map a {@code IndexValue} objektumokat tartalmazó mappa
     */
    @Override
    public void setMap(Map<String, IndexValue> map) {
        this.indexValueMap = map;
    }

    /**
     * Ellenőrzi, hogy a tároló tartalmazza e a kiválasztott kulcsot.
     * @param key kulcs
     * @return <b>true</b> ha a mappa tartalmazza a kiválasztott kulcsot
     */
    @Override
    public boolean containsKey(String key) {
        return indexValueMap.containsKey(key);
    }

    /**
     * Visszaadja a kiválasztott kulcshoz tartozó {@code IndexValue} objektumot.
     * @param key kulcs
     * @return {@code IndexValue} objektum
     */
    @Override
    public IndexValue get(String key) {
        if (indexValueMap.get(key) != null) {
            return indexValueMap.get(key);
        } else {
            return null;
        }
    }

    /**
     * A tárolóhoz adja a {@code IndexValue} objektumot a kiválasztott kulccsal.
     * @param key kulcs
     * @param indexValue {@code IndexValue} objektum
     */
    @Override
    public void add(String key, IndexValue indexValue) {
        indexValueMap.put(key, indexValue);
    }
    
    /**
     * Törli a kiválasztott kulcsot a mappából.
     * @param key kulcs
     * @return a törölt {@code IndexValue} objektum ha sikerült a törlés<br>
     * {@code null} ha nem tartalmazta a mappa a kulcsot
     */
    @Override
    public IndexValue remove(String key) {
        return indexValueMap.remove(key);
    }
}
