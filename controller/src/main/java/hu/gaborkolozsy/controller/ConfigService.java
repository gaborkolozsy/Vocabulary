/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.Binary;
import hu.gaborkolozsy.model.Config;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A kézzel megszerkesztett {@code .ini} kiterjesztésű szószedet fájl beolvasása 
 * és mentése<br> {@code .bin} fájlba.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Config
 * @see hu.gaborkolozsy.model.Binary
 * @see java.io.IOException
 * @see java.util.HashMap
 * @see java.util.HashSet
 * @see java.util.Map
 * @see java.util.Set
 * @since 2.0.0
 */
public class ConfigService {
    
    /** A {@code Config} objektum. */
    private final Config config = new Config();
    
    /** A {@code Binary} objektum. */
    private final Binary binary = new Binary();
    
    /** A kulcs set az .ini fájlból. */
    private Set<Object> keySet = new HashSet<>();
    
    /** Az aktuális szószedet mappa. */
    private final Map<String, Object> vocabulary = new HashMap<>();

    /**
     * Kétparaméteres konstruktor.
     * Bináris fájlba menti az elkészített szószedetet.
     * 
     * @param file az .ini fajl neve
     * @param fileName a mentendő fájl neve
     * @throws IOException fájl hiba esetén
     */
    public ConfigService(String file, String fileName) throws IOException {
        fillVocabulary(file);
        
        // az alulvonások(_) cseréje szóközre
        Map<String, Object> temporary = new HashMap<>();
        temporary.putAll(vocabulary);
        vocabulary.clear();
        
        temporary.entrySet().stream().forEach((entry) -> {
            vocabulary.put(entry.getKey().replace("_", " "), entry.getValue());
        });
        
        saveVocabularyFromIniToBin(fileName);
    }
    
    /**
     * Háromparaméteres konstruktor. 
     * Bináris fájlba menti a kevert szószedetet.
     * 
     * @param file1 az elsö .ini fajl neve
     * @param file2 a második .ini fájl neve
     * @param fileName a mentendő fájl neve
     * @throws IOException fájl hiba esetén
     */
    public ConfigService(String file1, String file2, String fileName) 
            throws IOException {
        
        // kulcsok az ELSŐ fájlból
        fillVocabulary(file1);
        Map<String, String> language1 = underscoreToSpace();
        
        // kulcsok a MÁSODIK fájlból
        this.keySet.clear();
        vocabulary.clear();
        fillVocabulary(file2);
        Map<String, String> language2 = underscoreToSpace();
        
        // a kevert szószedet
        vocabulary.clear();
        language1.entrySet().stream().forEach((entry) -> {
            vocabulary.put(entry.getValue(), language2.get(entry.getKey()));
        });
        
        saveVocabularyFromIniToBin(fileName);
    }

    /**
     * A szószedet mappa feltöltése egy .ini fájból.
     * @param file az .ini fajl neve
     * @throws IOException fájl hiba esetén
     */
    private void fillVocabulary(String file) throws IOException {
        this.keySet = config.getSet(file);
        for (Object key: keySet) {
            vocabulary.put((String) key, config.getValue((String) key, file));
        }
    }
    
    /**
     * Az alulvonások(_) cseréje szóközre.
     */
    private Map<String, String> underscoreToSpace() {
        Map<String, String> language = new HashMap<>();
        vocabulary.entrySet().stream().forEach((entry) -> {
            language.put((String) entry.getValue(), entry.getKey().replace("_", " "));
        });
        return language;
    }

    /**
     * Menti a szószedetet egy binaris fájlba.
     * 
     * @param fileName amivel elmenti
     * @throws IOException fájl hiba esetén
     */
    private void saveVocabularyFromIniToBin(String fileName) throws IOException {
        this.binary.saveData(vocabulary, fileName);
    }
}
