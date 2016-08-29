/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A bináris fájlokat írja és olvassa.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.io.File
 * @see java.io.FileInputStream
 * @see java.io.FileOutputStream
 * @see java.io.IOException
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 * @see java.util.HashMap
 * @see java.util.Map
 * @since 0.1.0
 */
public class Binary {
    
    /**
     * Visszaad a kiválasztott fájlnévvel egy mappát.
     * @param fileName a fájl neve
     * @return {@code Map} {@code String} kulccsal és {@code Object} értékkel
     * @throws IOException fájl olvasási hiba esetén
     * @throws java.lang.ClassNotFoundException osztály hiba esetén
     */
    public Map<String, Object> getData(String fileName) 
            throws IOException, ClassNotFoundException {
        
        ObjectInputStream in = 
                new ObjectInputStream(new FileInputStream(fileName));
        return new HashMap<>((Map<String, Object>) in.readObject());
    }
    
    /**
     * Fájlba írja a mappát a kiválasztott névvel.
     * @param map mappa
     * @param fileName a fájl neve
     * @throws IOException fájl írási hiba esetén
     */
    public void saveData(Map<String, Object> map, String fileName) 
            throws IOException {
        
        ObjectOutputStream out = 
                new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(map);
    }
    
    /**
     * Törli a kiválasztott fájlt.
     * @param fileName a fájl neve
     */
    public void delete(String fileName) {
        if (new File(fileName).exists()) {
            new File(fileName).delete();
        }
    }
    
    /**
     * Ellenőrzi, hogy létezik-e a fájl a kiválasztott névvel.
     * @param fileName a fájl neve
     * @return <b>true</b> ha létezik a fájl<br>
     * <b>false</b> máskülönben
     */
    public boolean isFileExists(String fileName) {
        return new File(fileName).exists();
    }
}
