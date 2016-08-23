/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.Serializable;

/**
 * A fordítandó szavak listában elfoglalt helyének megfelelő indexek értéke.
 * <blockquote>
 * <table border=1 style="color:black" summary="Index lehetséges értékei">
 *  <tr>
 *      <th style="color:black"><em>option</em></th>
 *      <th style="color:black">initial</th>
 *      <th style="color:green">good translation</th>
 *      <th style="color:red">bad translation</th>
 *  </tr>
 *  <tr>
 *      <td style="color:black"><b>value</b></td>
 *      <td style="color:black"><center>1</center></td>
 *      <td style="color:green"><center>2</center></td>
 *      <td style="color:red"><center>0</center></td>
 *   </tr>
 * </table>
 * </blockquote>
 * <ul>
 *     <li>Kezdő érték 1.</li> 
 *     <li>Jó fordítás esetén az index értéke eggyel nő.</li>
 *     <li>Rossz fordítás esetén az érték 0(nulla).</li>
 * </ul>
 * <h2>Megjegyzés:</h2>
 * <ol>
 *  <li>Hogy az index hozzáadásra kerüljön a körben már "megtanult szavak" 
 *      indexeinek listájához, legalább 2x kell jól lefordítani azt.</li>
 *  <li>Ha az érték 2, akkor jó fordítás esetén az index törlésre kerül az 
 *      {@code IndexValueContainerImpl}-ből és hozzáadásra kerül a "megtanult szavak"
 *      indexeinek tárolójához({@code learnedIdxs} lista).</li>
 *  <li>Rossz fordítás esetén az index értéke akkor is 0(nulla) lesz 
 *      ha már legalább egyszer jó volt a fordítás(index értéke 1 vagy 2).</li>
 * </ol>
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.IndexValueContainerImpl
 * @see java.io.Serializable
 * @since 2.0.0
 */
public class IndexValue implements Serializable {
    
    /** Serial version. */
    private static final long serialVersionUID = 1L;
    /** Az index értéke. */
    private int value;

    /**
     * Konstruktor.
     * 
     * @param value az index értéke(0,1,2)
     */
    public IndexValue(int value) {
        this.value = value;
    }

    /**
     * Visszaadja az index értékét(0,1,2).
     * @return value - az index értéke
     */
    public int getValue() {
        return value;
    }

    /**
     * Beállítja az index értékét(0,1,2).
     * @param value az index értéke
     */
    public void setValue(int value) {
        this.value = value;
    }
}
