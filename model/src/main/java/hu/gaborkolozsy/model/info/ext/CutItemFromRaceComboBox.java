/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.info.ext;

import hu.gaborkolozsy.model.info.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @deprecated 
 * Az eltávolított futamszámról informál.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.info.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
@Deprecated
public class CutItemFromRaceComboBox extends Info {
    
    /** Info icon. */
    Icon icon = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/info-big.png"));

    /**
     * Üres konstruktor.
     */
    public CutItemFromRaceComboBox() {}
    
    /**
     * Konstruktor.
     * 
     * @param combo aktuális nyelvkombináció rövídítése
     * @param item az eltávolított futamszám
     */
    public CutItemFromRaceComboBox(String combo, String item) {
        super.content = "<html><body><center>"
                + "<img src=\"" + icon + "\"><br><br>"
                + "<font face=\"Lucida Grande\" size=\"4\""
                + "<b>Információ!</b></font><br><br>"
                + "A kikérdezetlen szavak száma kevesebb,<br>"
                + " mint a legmagasabb választható futamszám!<br>"
                + "Mostantól szűkült a(z) <b>" + combo + "</b>"
                + " nyelvkombinációból elérhető futamszám.<br><br>"
                + "Legmagasabb: '<b>" + item + "</b>'<br>"
                + "</center></body></html>";
    }
}
