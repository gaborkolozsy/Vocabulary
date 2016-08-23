/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses.ext;

import hu.gaborkolozsy.model.abstractClasses.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A hozzáadott futamszámról informál.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 0.1.0
 */
public class AddingItemToRaceComboBox extends Info {
    
    /** Info icon. */
    Icon icon = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/info-big.png"));

    /**
     * Üres konstruktor.
     */
    public AddingItemToRaceComboBox() {}
    
    /**
     * Konstruktor.
     * 
     * @param combo aktuális nyelvkombináció rövídítése
     * @param item a frissen hozzáadott futamszám
     */
    public AddingItemToRaceComboBox(String combo, String item) {
        super.content = "<html><body><center>"
                + "<img src=\"" + icon + "\"><br><br>"
                + "<font face=\"Lucida Grande\" size=\"4\""
                + "<b>Információ!</b></font><br><br>"
                + "Folyamatos jó teljesítmény!<br>"
                + "Mostantól bővült a(z) <b>" + combo + "</b>"
                + " nyelvkombinációból elérhető futamszám.<br><br>"
                + "Hozzáadva: '<b>" + item + "</b>'<br>"
                + "</center></body></html>";
    }
}
