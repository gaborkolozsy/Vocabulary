/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.Binary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A szószedet és a kulcs lista kezelése.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Binary
 * @see java.io.IOException
 * @see java.util.ArrayList
 * @see java.util.Collections
 * @see java.util.HashMap
 * @see java.util.List
 * @see java.util.Map
 * @since 2.0.0
 */
public class VocabularyService {
    
    /** A {@code Binary} objektum. */
    private final Binary binary = new Binary();
    
    /** Az aktuális szószedet mappa. */
    private Map<String, Object> vocabulary = new HashMap<>();
    
    /** Az aktuális fordítandó kulcs lista. */
    private final List<String> keyList = new ArrayList<>();

    /**
     * Beállítja a szószedetet a kiválasztott fájlból.
     * @param fileName a kiválasztott fájl.
     * @throws IOException fájlhiba esetén
     * @throws ClassNotFoundException osztályhiba esetén
     */
    public void setVocabulary(String fileName) 
            throws IOException, ClassNotFoundException {
        
        this.vocabulary = this.binary.getData(fileName);
    }
    
    /**
     * Visszaadja az értéket a kiválasztott kulcsnál ha van ilyen a mappában.
     * @param key a keresett erték kulcsa
     * @return a keresett érték
     */
    public String getVocabularyValue(String key) {
        if (vocabulary.get(key) != null) {
            return (String) vocabulary.get(key);
        }
        return "";
    }
    
    /**
     * Visszaadja a paraméterként átadott szószedet fordítottját.
     * Beállítja a kulcs listát.
     * @return a megfordított szószedet
     */
    public Map<String, Object> reverseVocabulary() {
        Map<String, Object> reverse = new HashMap<>();
        
        vocabulary.entrySet().stream().forEach((entry) -> {
            reverse.put((String) entry.getValue(), entry.getKey());
        });
        vocabulary.clear();
        vocabulary = reverse;
        
        return vocabulary;
    }
    
    /**
     * Beállítja a kulcslistát.
     * A szószedet kulcs szettjéből készít rendezett kulcslistát,
     * annak törlése után.
     * @return <b>true</b> ha a lista megváltozott
     */
    public boolean setKeyList() {
        boolean set = false;
        if (!vocabulary.isEmpty()) { 
            keyList.clear();
            set = keyList.addAll(vocabulary.keySet()); 
            Collections.sort(keyList);
        }
        return set;
    }
    
    /**
     * Visszaadja a kulcslista elemét a kiválasztott indexnél.
     * @param index a kiválasztott index
     * @return a keresett elem
     */
    public String getKeyListElem(int index) {
        if (index >= 0 && index < keyList.size()) {
            return keyList.get(index);
        }
        return "";
    }
    
    /**
     * Visszaadja a kulcs lista méretét.
     * @return kulcs lista mérete
     */
    public int getKeyListSize() {
        return keyList.size();
    }
}
