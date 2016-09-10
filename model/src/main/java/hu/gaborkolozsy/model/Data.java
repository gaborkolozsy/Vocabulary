/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Három különböző tipusú adat lehetséges.
 * <blockquote>
 * <table border=1 style="border-color:black" summary="Adattárolási módok">
 *  <tr>
 *      <th style="color:black">model</th>
 *      <th style="color:black">container</th>
 *      <th>{@code Map}</th>
 *      <th>{@code List}</th>
 *      <th>{@code Integer}</th>
 *      <th>{@code DataContainerImpl}</th>
 *  </tr>
 *  <tr>
 *      <td><b>{@code RaceComboBox}</b></td>
 *      <td><b>{@code RaceComboBoxContainerImpl}</b></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td><center></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 *  <tr>
 *      <td><b>{@code IndexValue}</b></td>
 *      <td><b>{@code IndexValueContainerImpl}</b></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td><center></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 *  <tr>
 *      <td><b>{@code Performance}</b></td>
 *      <td><b>{@code PerformanceContainerImpl}</b></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td><center></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 *  <tr>
 *      <td><center><b>---</b></center></td>
 *      <td><b>{@code learnedIdxs}</b></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 *  <tr>
 *      <td><center><b>---</b></center></td>
 *      <td><b>{@code round}</b></td>
 *      <td><center></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 *  <tr>
 *      <td><center><b>---</b></center></td>
 *      <td><b>{@code congratulation}</b></td>
 *      <td><center></center></td>
 *      <td><center></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 * </table>
 * </blockquote>
 * <p>
 * - Az {@code Data} objektumokat a {@code DataContainerImpl} objektum tárolja.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.interfaces.Container
 * @see hu.gaborkolozsy.model.interfaces.impl.DataContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.IndexValueContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.PerformanceContainerImpl
 * @see hu.gaborkolozsy.model.interfaces.impl.RaceComboBoxContainerImpl
 * @see java.io.Serializable
 * @see java.util.ArrayList
 * @see java.util.Collections
 * @see java.util.HashMap
 * @see java.util.List
 * @see java.util.Map
 * @since 2.0.0
 */
public class Data implements Serializable {
    
    /** Serial version. */
    private static final long serialVersionUID = 1L;
    /** A kulccsal megjelölt adatokhoz. */
    private Map<String, Integer> map = new HashMap<>();
    /** Az indexekhez. */
    private List<Integer> list = new ArrayList<>();
    /** A gratulációkhoz. */
    private int value;

    /**
     * Üres konstruktor.
     */
    public Data() {}
    
    /**
     * Egyparaméteres konstruktor a mappáknak.
     * @param map mappa
     */
    public Data(Map<String, Integer> map) {
        this.map = map;
    }

    /**
     * Egyparaméteres konstruktor a listáknak.
     * @param list lista
     */
    public Data(List<Integer> list) {
        this.list = list;
    }
    
    /**
     * Egyparaméteres konstruktor az értékeknek.
     * @param ertek value
     */
    public Data(int ertek) {
        this.value = ertek;
    }
    
    /**
     * Visszaadja a <b>map</b> adattagot.
     * @return map
     */
    public Map<String, Integer> getMap() {
        return map;
    }

    /**
     * Beállítja a <b>map</b> adattagot.
     * @param map mappa
     */
    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
    
    /**
     * Visszaadja a <b>list</b> adattagot.
     * @return list
     */
    public List<Integer> getList() {
        Collections.sort(list);
        return list;
    }

    /**
     * Beállítja a <b>list</b> adattagot.
     * @param list lista
     */
    public void setList(List<Integer> list) {
        this.list = list;
    }
    
    /**
     * Visszaadja a <b>value</b> adattagot.
     * @return value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Beállítja az <b>value</b> adattagot.
     * @param value érték
     */
    public void setValue(int value) {
        this.value = value;
    }
}
