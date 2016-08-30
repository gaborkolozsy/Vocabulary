/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.abstractClasses.LanguageCombination;
import java.util.ArrayList;
import java.util.List;

/**
 * Az alapértelmezett és az aktuálisan használt nyelvkombinációkat kezeli.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.LanguageCombination
 * @see java.util.ArrayList
 * @see java.util.List
 * @since 2.0.0
 */
public class LanguageCombinationsService extends LanguageCombination {
    
    /** Az elérhetö nyelvkombinaciok listája. */
    private final List<String> languageList = new ArrayList<>();
    /** Az elérhetö nyelvkombinaciok listája. */
    private List<String> comboList = new ArrayList<>();

    /**
     * Konstruktor.<br>
     * Feltőlti a <b>comboList</b> adattagot és egy listát készít a 
     * lehetséges nyelvkombinációkról ugyanoda.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LanguageCombinationsService() {
        this.languageList.add(HUNGARIAN);
        this.languageList.add(ENGLISH);
        this.languageList.add(GERMAN);
        this.comboList = makeComboList(languageList);
    }
    
    /**
     * Visszaadja a magyar nyelv rövidítését.
     * @return "HUN"
     */
    @Override
    public String getHUNGARIAN() {
        return HUNGARIAN;
    }
    
    /**
     * Visszaadja az angol nyelv rövidítését.
     * @return "ENG"
     */
    @Override
    public String getENGLISH() {
        return ENGLISH;
    }
    
    /**
     * Visszaadja a német nyelv rövidítését.
     * @return "GER"
     */
    @Override
    public String getGERMAN() {
        return GERMAN;
    }
    
    /**
     * Visszaadja az alapértelmezett nyelvkombinációt.
     * @return alapértelmezett nyelvkombináció
     */
    @Override
    public String getDefaultCombo() {
        return DEFAULT;
    }
    
    /**
     * Visszaadja az aktuális nyelvkombinációt.
     * @return az aktuális nyelvkombináció
     */
    @Override
    public String getCurrentCombo() {
        return currentCombo;
    }
    
    /**
     * Beállítja a nyelvkombinációt.
     * @param from amiről fordítunk
     * @param to amire fordítunk  
     */
    @Override
    public void setCurrentCombo(String from, String to) {
        currentCombo = from + "-" + to;
    }
    
    /**
     * Visszaadja az aktuális nyelvkombináció fordítottját.
     * @return az aktuális nyelvkombonáció fordítottja
     */
    public String getReverseCombo() {
        String[] str = currentCombo.split("-");
        return str[1] + "-" + str[0];
    }
    
    /**
     * Visszaadja az elérhető nyelvkombinációk listáját.
     * @return nyelvkombinációk listája
     */
    public List<String> getComboList() {
        return comboList;
    }
    
    /**
     * Nyelvkombináció listát készít az elérhetö és paraméterként átadott 
     * nyelvekböl.
     * @param languageList nyelv lista
     * @return a lehetséges nyelvkombinációk teljes listája
     */
    public List<String> makeComboList(List<String> languageList) {
        List<String> cl = new ArrayList<>();
        for (int i = 0; i < languageList.size(); i++) {
            for (int j = 1; j <= languageList.size() - 1; j++) {
                setCurrentCombo(languageList.get(0), languageList.get(j));
                cl.add(getCurrentCombo());
                
                // a lista manipulációja
                if (j == languageList.size() - 1) {
                    languageList.add(0, languageList.get(j));
                    languageList.remove(languageList.lastIndexOf(languageList.get(0)));
                }
            }
        }
        return cl;
    }
}
