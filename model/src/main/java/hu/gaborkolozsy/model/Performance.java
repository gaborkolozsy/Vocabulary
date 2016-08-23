/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.Serializable;

/**
 * A különböző nyelvkombinációk futamszámain élért legalább 90%-os
 * teljesítményt számolja.<br>
 * 3 után hozzáad a program egy további választható tételt a futam combo 
 * box-hoz.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.PerformanceContainerImpl
 * @see hu.gaborkolozsy.model.RaceComboBox
 * @see java.io.Serializable
 * @since 2.0.0
 */
public class Performance implements Serializable {
    
    /** Serial version. */
    private static final long serialVersionUID = 1L;
    /** "Azonos" szintű teljesítmények száma. */
    private int counter;

    /**
     * Üres konstruktor.
     */
    public Performance() {}
    
    /**
     * Konstruktor.
     * 
     * @param counter "azonos" teljesítmények száma
     */
    public Performance(int counter) {
        this.counter = counter;
    }

    /**
     * Visszaadja a számlálót.
     * @return counter
     */
    public int getCounter() {
        return counter;
    }
}
