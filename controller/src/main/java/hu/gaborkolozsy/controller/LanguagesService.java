/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.interfaces.impl.LanguageCombinationCombinationImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Az elérhető nyelveket és az alapértelmezett ill az aktuálisan használt 
 * nyelvkombinációkat(mint neveket) kezeli.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.LanguageCombinationCombinationImpl
 * @see java.util.ArrayList
 * @see java.util.List
 * @since 2.0.0
 */
public class LanguagesService extends LanguageCombinationCombinationImpl {
    
    /**
     * Konstruktor.<br>
     * A <b>comboList</b> adattagot feltőlti a lehetséges nyelvkombinációkkal.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LanguagesService() {
        super.comboList = makeList(languageList);
    }
    
    /**
     * Nyelvkombináció listát készít az elérhetö, paraméterként átadott 
     * nyelvekböl.
     * @param languageList nyelv lista
     * @return a lehetséges nyelvkombinációk teljes listája
     */
    @Override
    public List<String> makeList(List<String> languageList) {
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
