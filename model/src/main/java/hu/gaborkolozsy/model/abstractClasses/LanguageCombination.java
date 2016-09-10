/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses;

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
 * @since 2.0.0
 */
public abstract class LanguageCombination {
    
    /** A magyar nyelv rövidítése. */
    public final String HUNGARIAN = "HUN";
    /** Az angol nyelv rövidítése. */
    public final String ENGLISH = "ENG";
    /** A német nyelv rövidítése. */
    public final String GERMAN = "GER";
    /** Alapertelmezett nyelvkombináció. */
    public final String DEFAULT = "ENG-HUN";
    /** Az aktuális nyelvkombináció. */
    public String currentCombo = DEFAULT;

    /**
     * Visszaadja a magyar nyelv rövidítését.
     * @return "HUN"
     */
    public abstract String getHUNGARIAN();
    
    /**
     * Visszaadja az angol nyelv rövidítését.
     * @return "ENG"
     */
    public abstract String getENGLISH();
    
    /**
     * Visszaadja a német nyelv rövidítését.
     * @return "GER"
     */
    public abstract String getGERMAN();
    
    /**
     * Visszaadja az alapértelmezett nyelvkombinációt.
     * @return az alapértelmezett nyelvkombináció
     */
    public abstract String getDefaultCombo();
    
    /**
     * Visszaadja az aktuális nyelvkombinációt.
     * @return az aktuális nyelvkombináció
     */
    public abstract String getCurrentCombo();

    /**
     * Beállítja az aktuális nyelvkombinációt.
     * @param from amiről fordítunk
     * @param to amire fordítunk
     */
    public abstract void setCurrentCombo(String from, String to);
}
