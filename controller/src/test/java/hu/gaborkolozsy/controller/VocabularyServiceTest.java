/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.Binary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A {@code VocabularyService} objektum tesztje.
 * @author Kolozsy Gábor
 * @version 1.0
 */
public class VocabularyServiceTest {
    
    /** {@code Binary} objektum. */
    private static Binary binary;
    /** {@code VocabularyService} objektum. */
    private static VocabularyService instance;
    /** A "test" fájlba kerülő mappa. */
    private static Map<String, Object> map;
    /** A fájl neve. */
    private static String fileName;
    /** A mappa megfordíthatósága. */
    private static boolean reverse;
    /** A megfordított mappa. */
    private Map<String, Object> reverseMap;
    /** A mappa kulcslistája. */
    private static List<String> keyList;
    
    
    /**
     * Minden teszt metodus elött lefut.
     * Konstruktor.
     */
    public VocabularyServiceTest() {
    }
    
    /**
     * Lefut a tesztek futása elött 1x.
     * Beállítja az teszt osztály adattagjait.
     * @throws IOException fájlhiba esetén
     * @throws ClassNotFoundException osztályhiba esetén
     */
    @BeforeClass
    public static void setUpClass() throws IOException, ClassNotFoundException {
        binary = new Binary();
        instance = new VocabularyService();
        fileName = "test";
        map = new HashMap<>();
        map.put("1", "first");
        map.put("2", "second");
        map.put("3", "third");
        binary.saveData(map, fileName);
        instance.setVocabulary(fileName);
        
        reverse = false;
        
        keyList = new ArrayList<>();
        keyList.addAll(map.keySet());
    }
    
    /**
     * Lefut a tesztek futása végén 1x.
     * Törli a létrehozott teszt fájlt.
     */
    @AfterClass
    public static void tearDownClass() {
        binary.delete(fileName);
        keyList.clear();
    }
    
    /**
     * Lefut minden teszt metodus elött a konstruktor után.
     */
    @Before
    public void setUp() {
        if (reverse) {
            reverseMap = new HashMap<>();
            reverseMap.put("first", "1");
            reverseMap.put("second", "2");
            reverseMap.put("third", "3");
            reverse = false;
        }
    }
    
    /**
     * Lefut minden teszt metodus után.
     */
    @After
    public void tearDown() {
        if (reverseMap != null) {
            reverseMap.clear();
        }
    }

    /**
     * Test of setVocabulary method, of class VocabularyService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSetVocabulary() throws Exception {
        assertEquals("first", instance.getVocabularyValue("1"));
        assertEquals("second", instance.getVocabularyValue("2"));
        assertTrue("third".equals(instance.getVocabularyValue("3")));
        assertFalse(map.get("1").equals("second"));
    }

    /**
     * Test of getVocabularyValue method, of class VocabularyService.
     */
    @Test
    public void testGetVocabularyValue() {
        String key = "1";
        String expResult = "first";
        String result = instance.getVocabularyValue(key);
        assertEquals(expResult, result);
        assertFalse(expResult.equals(instance.getVocabularyValue("2")));
        reverse = true;
    }

    /**
     * Test of reverseVocabulary method, of class VocabularyService.
     */
    @Test
    public void testReverseVocabulary() {
        Map<String, Object> result = instance.reverseVocabulary();
        assertEquals(reverseMap, result);
        assertFalse(map.equals(reverseMap));
    }

    /**
     * Test of setKeyList method, of class VocabularyService.
     */
    @Test
    public void testSetKeyList() {
        boolean expResult = true;
        boolean result = instance.setKeyList();
        assertEquals(expResult, result);
        
        VocabularyService uj = new VocabularyService();
        assertFalse(expResult == uj.setKeyList());
    }

    /**
     * Test of getKeyListElem method, of class VocabularyService.
     * A {@code testSetKeyList} metodusban a megfordított mappa kulcs setjével
     * tőlti fel a {@code keyList} adattagot.
     */
    @Test
    public void testGetKeyListElem() {
        int index = 0;
        String expResult = "first";
        String result = instance.getKeyListElem(index);
        assertEquals(expResult, result);
        assertFalse(expResult.equals(instance.getKeyListElem(1)));
    }

    /**
     * Test of getKeyListSize method, of class VocabularyService.
     */
    @Test
    public void testGetKeyListSize() {
        int expResult = keyList.size();
        int result = instance.getKeyListSize();
        assertEquals(expResult, result);
        assertFalse(expResult == instance.getKeyListSize() - 1);
    }
    
}