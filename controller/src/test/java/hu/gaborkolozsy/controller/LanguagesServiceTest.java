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
 * A {@code LanguagesService} objektum tesztje.
 * @author Kolozsy Gábor
 * @version 1.0
 */
public class LanguagesServiceTest {
    
    /** {@code LanguagesService} objektum. */
    private static LanguagesService instance;
    
    /** A nyelvkombinációk listája. */
    private static final List<String> languageCombinations = new ArrayList<>();
    
    /** Egy teszt lista. */
    private static final List<String> testCombinations = new ArrayList<>();
    
    /**
     * Minden teszt metódus előtt lefut.
     * Konstruktor.
     */
    public LanguagesServiceTest() {}
    
    /**
     * Lefut a tesztek futása előtt 1x.
     * Az osztály adattagjainak példányosítása a teszt futása előtt.
     */
    @BeforeClass
    public static void setUpClass() {
        languageCombinations.add("HUN-ENG");
        languageCombinations.add("HUN-GER");
        languageCombinations.add("GER-HUN");
        languageCombinations.add("GER-ENG");
        languageCombinations.add("ENG-GER");
        languageCombinations.add("ENG-HUN");
        
        testCombinations.add("1-2");
        testCombinations.add("1-3");
        testCombinations.add("3-1");
        testCombinations.add("3-2");
        testCombinations.add("2-3");
        testCombinations.add("2-1");
    }
    
    /**
     * Lefut a tesztek futása végén 1x.
     */
    @AfterClass
    public static void tearDownClass() {
        languageCombinations.clear();
    }
    
    /**
     * Lefut minden teszt metódus előtt a konstruktor után.
     * A nyelvkombinációk lista példányosítása és feltőltése.
     */
    @Before
    public void setUp() {
        instance = new LanguagesService();
    }
    
    /**
     * Lefut minden teszt metódus után.
     */
    @After
    public void tearDown() {}

    /**
     * Test of getHUNGARIAN method, of class LanguagesService.
     */
    @Test
    public void testGetHUNGARIAN() {
        String result = instance.getHUNGARIAN();
        assertEquals("HUN", result);
        assertFalse("hun".equals(result));
    }

    /**
     * Test of getENGLISH method, of class LanguagesService.
     */
    @Test
    public void testGetENGLISH() {
        String result = instance.getENGLISH();
        assertEquals("ENG", result);
        assertFalse("eng".equals(result));
    }

    /**
     * Test of getGERMAN method, of class LanguagesService.
     */
    @Test
    public void testGetGERMAN() {
        String result = instance.getGERMAN();
        assertEquals("GER", result);
        assertFalse("ger".equals(result));
    }

    /**
     * Test of getDefaultCombo method, of class LanguagesService.
     */
    @Test
    public void testGetDefaultCombo() {
        String result = instance.getDefaultCombo();
        assertEquals("ENG-HUN", result);
        assertFalse("ENGHUN".equals(result));
    }

    /**
     * Test of getCurrentCombo method, of class LanguagesService.
     */
    @Test
    public void testGetCurrentCombo() {
        String result = instance.getCurrentCombo();
        assertEquals("ENG-HUN", result);
        assertFalse("ENGHUN".equals(result));
    }
    
    /**
     * Test of setCurrentCombo method, of class LanguagesService.
     */
    @Test
    public void testSetCurrentCombo() {
        instance.setCurrentCombo("first", "second");
        String result = instance.getCurrentCombo();
        assertEquals("first-second", result);
        assertFalse("firstsecond".equals(result));
    }
    
    /**
     * Test of reverseCombo method, of class LanguagesService.
     */
    @Test
    public void testGetReverseCombo() {
        instance.setCurrentCombo("first", "second");
        String result = instance.reverseCombo();
        assertEquals("second-first", result);
        assertFalse("secondfirst".equals(result));
    }
    
    /**
     * Test of getList method, of class LanguagesService.
     */
    @Test
    public void testGetComboList() {
        List<String> result = instance.getList();
        assertEquals(languageCombinations, result);
        
        result.remove(0);
        assertFalse(languageCombinations.equals(result));
    }

    /**
     * Test of makeList method, of class LanguagesService.
     */
    @Test
    public void testMakeComboList() {
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        List<String> result = instance.makeList(test);
        assertEquals(testCombinations, result);
        
        result.remove(0);
        assertFalse(testCombinations.equals(result));
    }
}
