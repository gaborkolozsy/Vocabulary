/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces;

import java.util.Map;

/**
 * Tároló interface.
 * 
 * @author Kolozsy Gábor
 * 
 * @param <V> valamilyen objektum
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.DataContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.IndexValueContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.PerformanceContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.RaceComboBoxContainerImpl
 * @see java.util.Map
 * @since 2.0.0
 */
public interface Container<V> {
    
    /**
     * Visszaad egy mappát.
     * @return {@code Map}
     */
    Map<String, V> getMap();
    
    /**
     * Beállít egy mappát.
     * @param map {@code Map}
     */
    void setMap(Map<String, V> map);
    
    /**
     * Ellenőrzi a kiválasztott kulcs előfordulását a tárolóban.
     * @param key kulcs
     * @return <b>true</b> ha tartalmazza a tároló a kiválasztott kulcsot
     */
    boolean containsKey(String key);
    
    /**
     * Visszaad egy {@code V} objektumot a kiválasztott kulcsnál.
     * @param key kulcs
     * @return {@code V} objektum
     */
    V get(String key);
    
    /**
     * Hozzáad a tárolóhoz egy {@code V} objektumot a kiválasztott kulccsal.
     * @param key kulcs
     * @param v objektum
     */
    void add(String key, V v);
    
    /**
     * Törli a kiválasztott kulcsot.
     * @param key kulcs
     * @return a törölt {@code V} objektum vagy {@code null} ha nem tartalmazta
     * a tároló a kiválasztott kulcsot
     */
    V remove(String key);
}
