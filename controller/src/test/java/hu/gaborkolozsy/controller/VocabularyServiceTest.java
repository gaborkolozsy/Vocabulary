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
    private static final Binary binary = new Binary();
    /** {@code VocabularyService} objektum. */
    private static VocabularyService instance;
    /** A fájl neve. */
    private static final String FILENAME  = "test.bin";
    /** A "test" fájlba kerülő mappa. */
    private static final Map<String, Object> map = new HashMap<>();
    /** A megfordított mappa. */
    private static final Map<String, Object> reverseMap = new HashMap<>();
    /** A mappa kulcslistája. */
    private static final List<String> keyList = new ArrayList<>();
    
    /**
     * Minden teszt metódus előtt lefut.
     * Konstruktor.
     */
    public VocabularyServiceTest() {}
    
    /**
     * Lefut a tesztek futása előtt 1x.
     * Beállítja az teszt osztály adattagjait.
     * @throws IOException fájlhiba esetén
     * @throws ClassNotFoundException osztályhiba esetén
     */
    @BeforeClass
    public static void setUpClass() throws IOException, ClassNotFoundException {
        map.put("1", "first");
        map.put("2", "second");
        map.put("3", "third");
        binary.saveData(map, FILENAME);
        
        reverseMap.put("first", "1");
        reverseMap.put("second", "2");
        reverseMap.put("third", "3");
        
        keyList.addAll(map.keySet());
    }
    
    /**
     * Lefut a tesztek futása végén 1x.
     * Törli a létrehozott teszt fájlt.
     */
    @AfterClass
    public static void tearDownClass() {
        binary.delete(FILENAME);
        keyList.clear();
        reverseMap.clear();
    }
    
    /**
     * Lefut minden teszt metódus előtt a konstruktor után.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        instance = new VocabularyService();
        instance.setVocabulary(FILENAME);
    }
    
    /**
     * Lefut minden teszt metódus után.
     */
    @After
    public void tearDown() {}

    /**
     * Test of setVocabulary method, of class VocabularyService.
     */
    @Test
    public void testSetVocabulary() {
        assertEquals("first", instance.getVocabularyValue("1"));
        assertEquals("second", instance.getVocabularyValue("2"));
        assertTrue("third".equals(instance.getVocabularyValue("3")));
        assertFalse("first".equals(instance.getVocabularyValue("3")));
    }

    /**
     * Test of getVocabularyValue method, of class VocabularyService.
     */
    @Test
    public void testGetVocabularyValue() {
        String result = instance.getVocabularyValue("1");
        assertEquals("first", result);
        assertFalse("second".equals(result));
    }

    /**
     * Test of reverseVocabulary method, of class VocabularyService.
     */
    @Test
    public void testReverseVocabulary() {
        Map<String, Object> result = instance.reverseVocabulary();
        assertEquals(reverseMap, result);
        assertFalse(map.equals(result));
    }

    /**
     * Test of setKeyList method, of class VocabularyService.
     */
    @Test
    public void testSetKeyList() {
        boolean result = instance.setKeyList();
        assertEquals(true, result);
        assertFalse(false == result);
    }

    /**
     * Test of getKeyListElem method, of class VocabularyService.
     * A {@code testSetKeyList} metodusban a megfordított mappa kulcs setjével
     * tőlti fel a {@code keyList} adattagot.
     */
    @Test
    public void testGetKeyListElem() {
        instance.setKeyList();
        String result = instance.getKeyListElem(0);
        assertEquals("1", result);
        assertFalse("2".equals(result));
    }

    /**
     * Test of getKeyListSize method, of class VocabularyService.
     */
    @Test
    public void testGetKeyListSize() {
        instance.setKeyList();
        int result = instance.getKeyListSize();
        assertEquals(keyList.size(), result);
        assertFalse(keyList.size() == result - 1);
    }
    
}