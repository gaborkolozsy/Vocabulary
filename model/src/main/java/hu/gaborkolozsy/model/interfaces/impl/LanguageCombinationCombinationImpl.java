/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.interfaces.impl;

import hu.gaborkolozsy.model.Languages;
import hu.gaborkolozsy.model.interfaces.Combination;
import java.util.ArrayList;
import java.util.List;

/**
 * A programból elérhető nyelvkombinációk.
 * <blockquote>
 * <table border=1 style="border-color: black" summary="Adat tárolási módok">
 *  <tr>
 *      <th style="color:black">nyelvkombinaciok</th>
 *      <th style="color:black">ENGLISH</th>
 *      <th style="color:black">GERMAN</th>
 *      <th style="color:black">HUNGARIAN</th>
 *  </tr>
 *  <tr>
 *      <td style="color:black"><b>ENGLISH</b></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td style="color:red"><center>ENG-GER²</center></td>
 *      <td style="color:red"><center>ENG-HUN¹</center></td>
 *  </tr>
 *  <tr>
 *      <td style="color:black"><b>GERMAN</b></td>
 *      <td style="color:red"><center>GER-ENG²</center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *      <td style="color:red"><center>GER-HUN</center></td>
 *  </tr>
 *  <tr>
 *      <td style="color:black"><b>HUNGARIAN</b></td>
 *      <td style="color:red"><center>HUN-ENG</center></td>
 *      <td style="color:red"><center>HUN-GER</center></td>
 *      <td style="color:red"><center><b>x</b></center></td>
 *  </tr>
 * </table>
 * </blockquote>
 * <ol>
 *  <li>Alapértelmezett nyelvkombináció.</li>
 *  <li>Csak feltételekkel érhető el.</li>
 * </ol>
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Languages;
 * @see hu.gaborkolozsy.model.interfaces.Combination;
 * @see java.util.ArrayList;
 * @see java.util.List;
 * @since 2.0.0
 */
public abstract class LanguageCombinationCombinationImpl extends Languages implements Combination {

    /** Alapertelmezett nyelvkombináció. */
    protected final String DEFAULT;

    /** Az aktuális nyelvkombináció. */
    protected String currentCombo;
    
    /** Az elérhető nyelvkombinációk listája. */
    protected List<String> comboList = new ArrayList<>();
    
    /**
     * Konstuktor.
     */
    public LanguageCombinationCombinationImpl() {
        this.DEFAULT = "ENG-HUN";
        this.currentCombo = DEFAULT;
    }

    /**
     * Visszaadja az alapértelmezett nyelvkombinációt.
     * @return alapértelmezett nyelvkombináció
     */
    public String getDefaultCombo() {
        return DEFAULT;
    }
    
    /**
     * Visszaadja az aktuális nyelvkombinációt.
     * @return az aktuális nyelvkombináció
     */
    public String getCurrentCombo() {
        return currentCombo;
    }
    
    /**
     * Beállítja a nyelvkombinációt.
     * @param from amiről fordítunk
     * @param to amire fordítunk  
     */
    public void setCurrentCombo(String from, String to) {
        currentCombo = from + "-" + to;
    }
    
    /**
     * Visszaadja az aktuális nyelvkombináció fordítottját.
     * @return az aktuális nyelvkombonáció fordítottja
     */
    @Override
    public String reverseCombo() {
        String[] str = currentCombo.split("-");
        return str[1] + "-" + str[0];
    }
    
    /**
     * Visszaadja az elérhető nyelvkombinációk listáját.
     * @return nyelvkombinációk listája
     */
    public List<String> getList() {
        return comboList;
    }
}
