/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Az elérhető nyelvek.
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @since 2.3.0
 */
public class Languages {
    
    /** A magyar nyelv rövidítése. */
    protected final String HUNGARIAN;
    
    /** Az angol nyelv rövidítése. */
    protected final String ENGLISH;

    /** A német nyelv rövidítése. */
    protected final String GERMAN;
    
    /** Az elérhetö nyelvkek listája. */
    protected final List<String> languageList = new ArrayList<>();
    
    /**
     * Konstuktor.
     */
    public Languages() {
        this.GERMAN = "GER";
        this.ENGLISH = "ENG";
        this.HUNGARIAN = "HUN";
        this.languageList.add(HUNGARIAN);
        this.languageList.add(ENGLISH);
        this.languageList.add(GERMAN);
    }
    
    /**
     * Visszaadja a magyar nyelv rövidítését.
     * @return "HUN"
     */
    public  String getHUNGARIAN() {
        return HUNGARIAN;
    }
    
    /**
     * Visszaadja az angol nyelv rövidítését.
     * @return "ENG"
     */
    public String getENGLISH() {
        return ENGLISH;
    }
    
    /**
     * Visszaadja a német nyelv rövidítését.
     * @return "GER"
     */
    public String getGERMAN() {
        return GERMAN;
    }
}
