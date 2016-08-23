/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.abstractClasses.Info;
import hu.gaborkolozsy.model.abstractClasses.ext.About;
import hu.gaborkolozsy.model.abstractClasses.ext.AddingItemToRaceComboBox;
import hu.gaborkolozsy.model.abstractClasses.ext.Advice;
import hu.gaborkolozsy.model.abstractClasses.ext.Congratulation;
import hu.gaborkolozsy.model.abstractClasses.ext.CutItemFromRaceComboBox;
import hu.gaborkolozsy.model.abstractClasses.ext.Extra;
import hu.gaborkolozsy.model.abstractClasses.ext.Greeting;

/**
 * Információs ablakok megjelenítése.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Extra
 * @see hu.gaborkolozsy.model.abstractClasses.ext.AddingItemToRaceComboBox
 * @see hu.gaborkolozsy.model.abstractClasses.ext.CutItemFromRaceComboBox
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Congratulation
 * @see hu.gaborkolozsy.model.abstractClasses.ext.About
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Advice
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Greeting
 * @since 2.0.0
 */
public class InfoService {
    
    /** {@code Greeting} objektum. */ 
    private final Info greeting = new Greeting();
    /** {@code About} objektum. */
    private final Info about = new About();
    /** {@code Advice} objektum. */
    private final Info advice = new Advice();
    /** {@code Congratulation}. */
    private Info congratulation;
    /** {@code Extra}. */
    private Info extra;
    /** {@code AddingItemToRaceComboBox}. */
    private Info addingItemToRaceComboBox;
    /** {@code CutItemFromRaceComboBox}. */
    private Info cutItemFromRaceComboBox;

    /**
     * Üres konstruktor.
     */
    public InfoService() {}

    /**
     * Konstruktor egy új {@code Congratulation} objektumnak.
     * 
     * @param combo az aktuális nyelvkombinació
     * @param index a futam tétel indexe
     * @param sumOfCongrat a gratuláció száma
     */
    public InfoService(String combo, int index, int sumOfCongrat) {
        this.congratulation = new Congratulation(combo, index, sumOfCongrat);
    }
    
    /**
     * Konstruktor egy új {@code Extra} objektumnak.
     * 
     * @param sumOfCongrat a gratulációk száma
     */
    public InfoService(int sumOfCongrat) {
        this.extra = new Extra(sumOfCongrat);
    }
    
    /**
     * Konstruktor egy új {@code AddingItemToRaceComboBox} objektumnak.
     * 
     * @param combo az aktuális nyelvkombinació
     * @param item a futamhoz hozzáadott új tétel
     */
    public InfoService(String combo, String item) {
        this.addingItemToRaceComboBox = new AddingItemToRaceComboBox(combo, item);
        this.cutItemFromRaceComboBox = new CutItemFromRaceComboBox(combo, item);
    }
    
    /**
     * Visszaadja a {@code Greeting} objektum tartalmát.
     * @return tartalom
     */
    public String getGreeting() {
        return greeting.getContent();
    }
    
    /**
     * Visszaadja az {@code About} objektum tartalmát.
     * @return tartalom
     */
    public String getAbout() {
        return about.getContent();
    }
    
    /**
     * Visszaadja az {@code Advice} objektum tartalmát.
     * @return tartalom
     */
    public String getAdvice() {
        return advice.getContent();
    }
    
    /**
     * Visszaadja a {@code Congratulation} objektum tartalmát.
     * @return tartalom
     */
    public String getCongratulation() {
        return congratulation.getContent();
    }
    
    /**
     * Visszaadja az {@code Extra} objektum tartalmát.
     * @return tartalom
     */
    public String getExtra() {
        return extra.getContent();
    }
    
    /**
     * Visszaadja az {@code AddingItemToRaceComboBox} objektum tartalmát.
     * @return tartalom
     */
    public String getAddingItenToRaceComboBox() {
        return addingItemToRaceComboBox.getContent();
    }
    
    /**
     * Visszaadja a {@code CutItemFromRaceComboBox} objektum tartalmát.
     * @return tartalom
     */
    public String getCutItemFromRaceComboBox() {
        return cutItemFromRaceComboBox.getContent();
    }
}