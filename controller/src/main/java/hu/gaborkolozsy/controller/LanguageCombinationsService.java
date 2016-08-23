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
    private List<String> comboList = new ArrayList<>();

    /**
     * Konstruktor.<br>
     * Feltőlti a <b>comboList</b> adattagot és egy listát készít a 
     * lehetséges nyelvkombinációkról ugyanoda.
     */
    public LanguageCombinationsService() {
        this.comboList.add(HUNGARIAN);
        this.comboList.add(ENGLISH);
        this.comboList.add(GERMAN);
        this.comboList = makeComboList();
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
     * Visszaadja az elérhető nyelvkombinációk listáját.
     * @return nyelvkombinációk listája
     */
    public List<String> getComboList() {
        return comboList;
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
     * Nyelvkombináció listát készít az elérhetö és paraméterként átadott 
     * nyelvekböl.
     * @return a lehetséges nyelvkombinációk teljes listája
     */
    private List<String> makeComboList() {
        List<String> cl = new ArrayList<>();
        for (int i = 0; i < comboList.size(); i++) {
            for (int j = 1; j <= comboList.size() - 1; j++) {
                setCurrentCombo(comboList.get(0), comboList.get(j));
                cl.add(getCurrentCombo());
                
                // a lista manipulációja
                if (j == comboList.size() - 1) {
                    comboList.add(0, comboList.get(j));
                    comboList.remove(
                        comboList.lastIndexOf(comboList.get(0)));
                }
            }
        }
        return cl;
    }
}
