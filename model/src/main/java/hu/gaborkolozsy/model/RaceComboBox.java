/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.Serializable;

/**
 * A futam combo box legnagyobb indexe egy adott nyelvkombináción.<br>
 * Valamint visszaadja a futam combo box valamely tételét a kiválasztott 
 * indexnél.
 * <blockquote>
 *  <table border=1 style="border-color:black" summary="A futambox tételei">
 *   <tr>
 *      <th style="color:#000000">item</th>
 *      <th style="color:#000000">szükséges teljesítmény</th>
 *   </tr>
 *   <tr>
 *      <td><center><b>5</b></center></td>
 *      <td style="color:red"><b>default</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>10</b></center></td>
 *      <td style="color:red"><b>default</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>20</b></center></td>
 *      <td style="color:red"><b>3x legalább 90% a 10-es futamon</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>30</b></center></td>
 *      <td style="color:red"><b>3x legalább 90% a 20-es futamon</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>40</b></center></td>
 *      <td style="color:red"><b>3x legalább 90% a 30-es futamon</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>50</b></center></td>
 *      <td style="color:red"><b>3x legalább 90% a 40-es futamon</b></td>
 *   </tr>
 *   <tr>
 *      <td><center><b>100</b></center></td>
 *      <td style="color:red"><b>3x legalább 90% a 50-es futamon</b></td>
 *   </tr>
 *  </table>
 * </blockquote>
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.RaceComboBoxContainerImpl
 * @see java.io.Serializable
 * @since 2.0.0
 */
public class RaceComboBox implements Serializable {
    
    /** Serial version. */
    private static final long serialVersionUID = 1L;
    /** A futam legnagyobb indexe. */
    private int maxIndex;

    /**
     * Üres konstruktor.
     * @see #getItems(int)
     */
    public RaceComboBox() {}
    
    /**
     * Konstruktor.
     * 
     * @param maxIndex a futam legnagyobb indexe
     */
    public RaceComboBox(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    /**
     * Visszaadja a futam legnagyobb indexét. 
     * @return maxIndex
     */
    public int getMaxIndex() {
        return maxIndex;
    }

    /**
     * Beállítja a futam legnagyobb indexét.
     * @param maxIndex a legnagyobb index
     */
    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    /**
     * Visszaadja a futam tételeit.
     * @param index a futam indexe
     * @return item
     */
    public String getItems(int index) {
        String item = "0";
        switch(index) {
            case 0: item = "5"; break;
            case 1: item = "10"; break;
            case 2: item = "20"; break;
            case 3: item = "30"; break;
            case 4: item = "40"; break;
            case 5: item = "50"; break;
            case 6: item = "100"; break;
        }
        return item;
    }
}
