/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A {@code LanguageCombinationsService} objektum tesztje.
 * @author Kolozsy Gábor
 * @version 1.0
 */
public class LanguageCombinationsServiceTest {
    
    /** {@code LanguageCombinationsService} objektum. */
    private static LanguageCombinationsService instance;
    /** A nyelvkombinációk listája. */
    private List<String> languageCombinations;
    /** A nyelvkombináció lista létrehozható. */
    private static boolean addLanguageCombinations;
    
    /**
     * Minden teszt metódus elött lefut.
     * Konstruktor.
     */
    public LanguageCombinationsServiceTest() {
    }
    
    /**
     * Lefut a tesztek futása elött 1x.
     * Az osztály adattagjainak példányosítása a teszt futása elött.
     */
    @BeforeClass
    public static void setUpClass() {
        instance = new LanguageCombinationsService() {};
        addLanguageCombinations = false;
    }
    
    /**
     * Lefut a tesztek futása végén 1x.
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Lefut minden teszt metódus elött a konstruktor után.
     * A nyelvkombinációk lista példányosítása és feltőltése.
     */
    @Before
    public void setUp() {
        if (addLanguageCombinations) {
            languageCombinations = new ArrayList<>();
            languageCombinations.add("HUN-ENG");
            languageCombinations.add("HUN-GER");
            languageCombinations.add("GER-HUN");
            languageCombinations.add("GER-ENG");
            languageCombinations.add("ENG-GER");
            languageCombinations.add("ENG-HUN");
            addLanguageCombinations = false;
        }
    }
    
    /**
     * Lefut minden teszt metódus után.
     */
    @After
    public void tearDown() {
        if (languageCombinations != null) {
            languageCombinations.clear();
        }
    }

    /**
     * Test of getHUNGARIAN method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetHUNGARIAN() {
        String expResult = "HUN";
        String result = instance.getHUNGARIAN();
        assertEquals(expResult, result);
        assertFalse(result.equals(instance.getENGLISH()));
    }

    /**
     * Test of getENGLISH method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetENGLISH() {
        String expResult = "ENG";
        String result = instance.getENGLISH();
        assertEquals(expResult, result);
        assertFalse(result.equals(instance.getHUNGARIAN()));
    }

    /**
     * Test of getGERMAN method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetGERMAN() {
        String expResult = "GER";
        String result = instance.getGERMAN();
        assertEquals(expResult, result);
        assertFalse(result.equals(instance.getENGLISH()));
    }

    /**
     * Test of getDefaultCombo method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetDefaultCombo() {
        String expResult = "ENG-HUN";
        String result = instance.getDefaultCombo();
        assertEquals(expResult, result);
        assertFalse(result.equals("ENGHUN"));
    }

    /**
     * Test of getCurrentCombo method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetCurrentCombo() {
        String expResult = "ENG-HUN";
        String result = instance.getCurrentCombo();
        assertEquals(expResult, result);
        assertFalse(result.equals("ENGHUN"));
    }
    
    /**
     * Test of setCurrentCombo method, of class LanguageCombinationsService.
     */
    @Test
    public void testSetCurrentCombo() {
        instance.setCurrentCombo("first", "second");
        String expResult = "first-second";
        String result = instance.getCurrentCombo();
        assertEquals(expResult, result);
        assertFalse(result.equals("second-first"));
        addLanguageCombinations = true;
    }
    
    /**
     * Test of getComboList method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetComboList() {
        List<String> expResult = languageCombinations;
        List<String> result = instance.getComboList();
        assertEquals(expResult, result);
        
        expResult.remove(0);
        assertFalse(result.equals(expResult));
    }

    /**
     * Test of getReverseCombo method, of class LanguageCombinationsService.
     */
    @Test
    public void testGetReverseCombo() {
        instance.setCurrentCombo("first", "second");
        String expResult = "second-first";
        String result = instance.getReverseCombo();
        assertEquals(expResult, result);
        String combo = "first-second";
        assertFalse(result.equals(combo));
    }
}
