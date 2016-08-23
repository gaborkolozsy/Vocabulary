/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.Binary;
import hu.gaborkolozsy.model.Data;
import hu.gaborkolozsy.model.IndexValue;
import hu.gaborkolozsy.model.Performance;
import hu.gaborkolozsy.model.RaceComboBox;
import hu.gaborkolozsy.model.interfaces.Container;
import hu.gaborkolozsy.model.interfaces.impl.DataContainerImpl;
import hu.gaborkolozsy.model.interfaces.impl.IndexValueContainerImpl;
import hu.gaborkolozsy.model.interfaces.impl.PerformanceContainerImpl;
import hu.gaborkolozsy.model.interfaces.impl.RaceComboBoxContainerImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A program futásához szükséges adatok tárolóit kezeli.<br>
 * A <b>data.bin</b> fájlból az adatok a {@code DataContainerImpl} objektumba
 * kerülnek.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Binary
 * @see hu.gaborkolozsy.model.Data
 * @see hu.gaborkolozsy.model.IndexValue
 * @see hu.gaborkolozsy.model.Performance
 * @see hu.gaborkolozsy.model.RaceComboBox
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see hu.gaborkolozsy.model.interfaces.impl.DataContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.IndexValueContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.PerformanceContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.RaceComboBoxContainerImpl
 * @see java.io.IOException
 * @see java.util.ArrayList
 * @see java.util.List
 * @since 2.0.0
 */
public class DataService {
    
    /** {@code Binary} objektum. */
    private final Binary binary = new Binary();
    /** {@code Data} objektum. */
    private Data data = new Data();
    /** {@code DataContainerImpl} objektum. */
    private final Container dataContainer = new DataContainerImpl();
    /** {@code RaceComboBoxContainerImpl} objektum. */
    private final Container raceComboBoxContainer = new RaceComboBoxContainerImpl();
    /** {@code IndexValueContainerImpl} objektum. */
    private final Container indexValueContainer = new IndexValueContainerImpl();
    /** {@code PerformanceContainerImpl} objektum. */
    private final Container performanceContainer = new PerformanceContainerImpl();
    /** A megtanult szavak indexei az aktuális nyelvkombináción. */
    private List<Integer> learnedIdxs = new ArrayList<>();
    /** A megtanult szavak indexlisájának mérete a nyelvkombináció fordítottján. */
    private int learnedIdxsSizeByReverseCombo;
    /** Az aktuális nyelvkombináción teljesített teljes kör. */
    private int round;
    /** A körszám változás esetén az összehasonlításához. */
    private int previousRound;
    /** Kombo és futamszámonként az első 100%-os teljesítményért járó gratuláció. */
    private int congratulation;
    
    /** A .bin fájlba írandó mappák azonosítói. */
    private final String INDEXVALUE = "indexValue";
    private final String PERFORMANCE = "performance";
    private final String RACEMAXINDEX = "raceMaxIndex";
    private final String CONGRAT = "congrat";

    /**
     * Konstruktor a kezdö értékek beállításához.
     * Csak egyszer az első indításkor hívja meg.
     * @param combos a lehetséges nyelvkombinációk
     */
    public DataService(List<String> combos) {
        combos.stream().filter((k) -> (dataContainer.get(k) == null)).map((k) -> {
            List<Integer> list = new ArrayList<>();
            dataContainer.add(k, new Data(list));
            return k;
        }).forEach((k) -> {
            dataContainer.add(k + "-K", new Data(0));
        });
    }
    
    /**
     * Ha már létezik a "<b>data.bin</b>" fájl, akkor beolvassa az adatokat 
     * példányosításkor.<br>
     * A {@code learnedIdxs} lista a <b>setLearnedIdxs(..)</b> a {@code round} 
     * a <b>setRound(..)</b> metódusban lesz beállítva.
     * Ezek hívása a Main konstruktorából a <b>setStars()</b> metóduson 
     * keresztül közvetve történik.
     * 
     * @param file a fájl neve
     * @param combo az alapértelmezett nyelvkombináció
     * @param reverseCombo az alapértelmezett nyelvkombináció fordítottja
     * 
     * @throws java.io.IOException fájlhiba esetén
     * @throws java.lang.ClassNotFoundException osztályhiba esetén
     * 
     * @see #setLearnedIdxs(java.lang.String)
     * @see #setRound(java.lang.String)
     */
    public DataService(String file, String combo, String reverseCombo) 
            throws IOException, ClassNotFoundException {
        
        this.dataContainer.setMap(binary.getData(file));
        
        this.data = (Data) dataContainer.get(RACEMAXINDEX);
        this.raceComboBoxContainer.setMap(data.getMap());
        
        this.data = (Data) dataContainer.get(INDEXVALUE);
        this.indexValueContainer.setMap(data.getMap());
        
        this.data = (Data) dataContainer.get(PERFORMANCE);
        this.performanceContainer.setMap(data.getMap());
        
        this.data = (Data) dataContainer.get(reverseCombo);
        this.learnedIdxsSizeByReverseCombo = data.getList().size();
        
        this.data = (Data) dataContainer.get(CONGRAT);
        this.congratulation = data.getValue();
    }
    
    /**
     * Beállítja az adatokat nyelv cserekor.
     * @param combo az aktuális nyelvkombináció
     * @param reverseCombo az aktuális nyelkombináció fordítottja
     */
    public void setIdxAndRoundByLanguageSwap(String combo, String reverseCombo) {
        if (dataContainer.get(combo) != null) {
            data = (Data) dataContainer.get(combo);
            learnedIdxs = data.getList();
        }
        
        if (dataContainer.get(reverseCombo) != null) {
            data = (Data) dataContainer.get(reverseCombo);
            learnedIdxsSizeByReverseCombo = data.getList().size();
        }
        
        if (dataContainer.get(combo + "-K") != null) {
            data = (Data) dataContainer.get(combo + "-K");
            previousRound = round = data.getValue();
        }
    }
    
    /**
     * Az adattárolóhoz adja a tanult szavak indexeit a kiválasztott 
     * nyelvkombinációnál.
     * @param combo az aktuális nyelvkombináció
     */
    public void addLarnedIdxsToDataContainer(String combo) {
        dataContainer.add(combo, new Data(learnedIdxs));
    }
    
    /**
     * Az adattárolóhoz adja a körök számát a kiválasztott nyelvkombinációnál.
     * @param combo az aktuális nyelvkombináció
     */
    public void addRoundToDataContainer(String combo) {
        dataContainer.add(combo + "-K", new Data(round));
    }
    
    /**
     * Menti az értékeket és a kiválasztott fájlba írja őket.
     * @param file a mentendő fájl neve
     * @throws java.io.IOException fájlhiba esetén
     */
    public void saveAllData(String file) throws IOException {
        dataContainer.add(RACEMAXINDEX, new Data(raceComboBoxContainer.getMap()));
        dataContainer.add(INDEXVALUE, new Data(indexValueContainer.getMap()));
        dataContainer.add(PERFORMANCE, new Data(performanceContainer.getMap()));
        dataContainer.add(CONGRAT, new Data(congratulation));
        binary.saveData(dataContainer.getMap(), file);
    }
    
    /**
     * Beállítja az index kezdő értékét.
     * @param indexID az index azonosítója
     */
    public void initialIndexValue(String indexID) {
        if (!indexValueContainer.containsKey(indexID)) {
            indexValueContainer.add(indexID, new IndexValue(1));
        }
    }
    
    /**
     * Növeli az index értékét.
     * @param indexID az index azonosítója
     */
    public void addingIndexValue(String indexID) {
        indexValueContainer.add(indexID, new IndexValue(getIndexValue(indexID) + 1));
    }
    
    /**
     * Visszaadja az index értékét a kiválasztott azonosítónál.
     * @param indexID az index azonosítója
     * @return az index értéke
     */
    public int getIndexValue(String indexID) {
        IndexValue ie = (IndexValue) indexValueContainer.get(indexID);
        return ie.getValue();
    }
    
    /**
     * Reseteli az index értékét.
     * @param indexID az index azonosítója
     */
    public void resetIndexValue(String indexID) {
        indexValueContainer.add(indexID, new IndexValue(0));
    }
    
    /**
     * Törli az index értékét a kiválasztott azonosítónál.
     * @param indexID az index azonosítója
     */
    public void removIndexValue(String indexID) {
        indexValueContainer.remove(indexID);
    }
    
    /**
     * <b>True</b> ha a kiválasztott azonosítót tartalmazza a tároló, 
     * <b>false</b> máskülönben.
     * @param key a futam azonosítója
     * @return <b>true</b> ha a tároló tartalmazza a kiválasztott azonosítót
     */
    public boolean containsPerformance(String key) {
        return performanceContainer.containsKey(key);
    }
    
    /**
     * Növeli a futamon elért teljesítmény(T%) értékét a kiválasztott 
     * azonosítónál.<br>
     * <b>90≤T≤100</b>
     * Kezdő érték 0.
     * @param key a futam azonosítója
     */
    public void addingPerformanceValue(String key) {
        performanceContainer.add(key, new Performance(getPerformanceValue(key) + 1));
    }
    
    /**
     * Visszaadja a futamon elért teljesítmény(T%) értékét a kiválasztott 
     * azonosítónál.
     * <b>90≤T≤100</b>
     * @param key a futam azonosítója
     * @return a teljesítmény értéke
     */
    public int getPerformanceValue(String key) {
        Performance  t = (Performance) performanceContainer.get(key);
        if (t != null) {
            return t.getCounter();
        } else {
            return 0;
        }
    }
    
    /**
     * Visszaadja a futam combo box legnagyobb indexét a kiválasztott 
     * nyelvkombinációnál.
     * @param combo az aktuális nyelvkombináció
     * @return a futam combo box legnagyobb tételének indexe
     */
    public int getRaceComboBoxMaxIndex(String combo) {
        RaceComboBox fsz = (RaceComboBox) raceComboBoxContainer.get(combo);
        if (fsz != null) {
            return fsz.getMaxIndex();
        }
        return 0;
    }
    
    /**
     * Beállítja a futam combo box maximális indexét.
     * @param combo az aktuális nyelvkombináció
     * @param maxIndex az új max index
     */
    public void setRaceComboBoxMaxIndex(String combo, int maxIndex) {
        raceComboBoxContainer.add(combo, new RaceComboBox(maxIndex));
    }
    
    /**
     * @deprecated
     * Levágja a futamComboBox legnagyobb indexét a kiválasztott 
     * nyelvkombinációnál ha a kikérdezetlen szavak száma kevesebb mint a 
     * legnagyobb indexhez rendelt tétel(választható futamszám).
     * <p>
     * <b>Igy viszont nem lesznek elérhetőek a második körtől!</b>
     * @param keyListSize a kulcs lista mérete
     * @param combo az aktuális nyelvkombináció
     * @return <b>true</b> ha eltávolította a legnagyobb szükségtelen indexet<br>
     * <b>false</b> máskülönben
     */
    @Deprecated
    public boolean cutRaceComboBoxMaxIndex(int keyListSize, String combo) {
        int highLevel = 100;
        int lowLevel = 50;
        int unlearned = getUnlearned(keyListSize);
        int maxIndex = getRaceComboBoxMaxIndex(combo);
        
        for (int i = 5; i >= 0; i--) {
            if (unlearned < highLevel && unlearned >= lowLevel) {
                if (maxIndex > i) {
                    raceComboBoxContainer.add(combo, new RaceComboBox(i));
                    return true;
                } else {
                    break;
                }
            }
            
            if (i < 5) {
                highLevel -= 10;
            } else {
                highLevel -= 50;
            }
            
            lowLevel -= 10;
        }
        return false;
    }
    
    /**
     * <b>True</b> ha a tároló tartalmazza a kiválasztott nyelvkombinációt
     * (a futam elérhetö legnagyobb indexével), <b>false</b> maskülönben.
     * @param combo az aktuális nyelvkombináció
     * @return <b>true</b> ha a tároló tartalmazza a kiválasztott 
     * nyelvkombinációt
     */
    public boolean containsRaceComboBoxBox(String combo) {
        return raceComboBoxContainer.containsKey(combo);
    }
    
    /**
     * Visszaadja a futam kombobox kiválasztott indexénél megjelenö értékét.
     * @param index a kiválasztott index
     * @return <b>item</b> a komboboxban az indexnek megfelelően megjelenő érték
     */
    public String getRaceComboBoxItem(int index) {
        return new RaceComboBox().getItems(index);
    }
    
    /**
     * Visszaadja a <b>learnedIdxs</b> adattagot.
     * @return a tanult indexek listáját
     */
    public List<Integer> getLearnedIdxs() {
        return learnedIdxs;
    }
    
    /**
     * Beállítja a <b>learnedIdxs</b> lista adattagot a kiválasztott 
     * nyelvkombináción.
     * @param combo a nyelvkombináció
     */
    public void setLearnedIdxs(String combo) {
        this.data = (Data) dataContainer.get(combo);
        this.learnedIdxs = data.getList();
    }
    
    /**
     * Hozzáadja a kiválasztott indexet a <b>learnedIdxs</b> lista adattag 
     * végéhez.
     * @param index a kiválasztott index
     */
    public void addLearnedIdxs(int index) {
        this.learnedIdxs.add(index);
    }
    
    /**
     * Visszaadja a <b>learnedIdxs</b> adattag méretét.
     * @return a tanult indexek lista mérete
     */
    public int getLearnedIdxsSize() {
        return learnedIdxs.size();
    }
    
    /**
     * Törli a <b>learnedIdxs</b> lista adattagot.
     */
    public void clearLearnedIdxs() {
        learnedIdxs.clear();
    }
    
    /**
     * Visszaadja az aktuális nyelvkombináción teljesített körök számát.
     * 1 kör = 1x lefordítva a teljes szószedet az aktuális nyelvkombináción.
     * @return kör szám
     */
    public int getRound() {
        return round;
    }
    
    /**
     * Beállítja a teljesített körök számát.
     * @param combo a kiválasztott nyelvkombináció
     */
    public void setRound(String combo) {
        this.data = (Data) dataContainer.get(combo + "-K");
        this.previousRound = this.round = data.getValue();
    }
    
    /**
     * Növeli a <b>round</b> adattag értékét.
     */
    public void addingRound() {
        round++;
    }
    
    /**
     * Megszámolja hány nyelvkombináción teljesült legalább 1 kör.
     * A MIX nyelvkombináció elérhetővé tételéhez szükséges. Amennyiben két
     * különböző nyelvkombináció teljesítve van, akkor elérhetővé teszi azt.
     * @param comboList az lehetséges nyelvkombinációk listája
     * @return <b>true</b> ha elérhető a MIX nyelvkombináció<br>
     * <b>false</b> máskülönben
     */
    public boolean countRounds(List<String> comboList) {
        int count = 0;
        boolean show = false;
        
        for (String s : comboList) {
            if (dataContainer.get(s + "-K") != null) {
                data = (Data) dataContainer.get(s + "-K");
                int value = data.getValue();

                if (value >= 1) count++;
                
                if (count >= 2) {
                    show = true;
                }
            }
        }
        return show;
    }
    
    /**
     * Visszaadja a <b>round</b> adattag előző értékét.
     * Az összehasonlításhoz szükséges a megjelenő adatok, sávok reseteléséhez.
     * @return a korábbi körszám
     */
    public int getPreviousRound() {
        return previousRound;
    }
    
    /**
     * Reseteli a {@code round} adattagot.
     * @param round a default értek(0)
     */
    public void resetRound(int round) {
        this.round = round;
    }
    
    /**
     * Növeli a <b>congratulation</b> adattag értékét.
     */
    public void addingCongrat() {
        congratulation++;
    }
    
    /**
     * Visszaadja a <b>congratulation</b> adattag értékét.
     * @return a gratulációk száma
     */
    public int getCongratulation() {
        return congratulation;
    }
    
    /**
     * Az aktuális nyelvkombináció.
     * @param keyListSize a kulcs lista(amiből kérdez) mérete
     * @return az aktuális nyelvkombináció feldolgozottsága %-ban
     */
    public double getActivComboProcessing(int keyListSize) {
        return ((double) learnedIdxs.size() / keyListSize) * 100.0;
    }
    
    /**
     * Az aktuális nyelvkombináció fordítottja.
     * @param keyListSize a kulcs lista(amiből kérdez) mérete
     * @return az aktuális nyelvkombináció fordítottjának feldolgozottsága %-ban
     */
    public double getReverseComboProcessing(int keyListSize) {
        return ((double) learnedIdxsSizeByReverseCombo / keyListSize) * 100.0;
    }
    
    /**
     * Visszaadja a kikérdezetlen szavak számát az aktuális nyelvkombináción.
     * @param keyListSize a kulcs lista mérete
     * @return kikerdezetlen szavak száma
     */
    public int getUnlearned(int keyListSize) {
        return keyListSize - learnedIdxs.size();
    }
}