/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.view.star.ext;

import hu.gaborkolozsy.view.star.Star;

/**
 * A GER-ENG nyelvkombináció csillagának feliratai.
 * 
 * @author Kolozsy Gábor
 * @version 0.1.0
 * 
 * @see hu.gaborkolozsy.view.star.Star
 */
public class Star6 extends Star {
    
    /**
     * Konstruktor.
     */
    public Star6() {
        super.toolTipText1 = "<html><body>"
                + "<center><b>Ez az!</b></center><br>"
                + "SÍRFELIRAT<br><br>"
                + "ZEBRA<br>"
                + "Egy tévedés áldozata vagyok.<br>"
                + "Az elefánt átkelt rajtam gyalog.<br>"
                + "Folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText2 = "<html><body>"
                + "<center><b>Gyerünk!</b></center><br>"
                + "SÍRFELIRAT<br><br>"
                + "GÖDÉNY<br>"
                + "Ha volna sírkövem, megtudnád belőle,<br>"
                + "Azért nincs, mert azt is elittam előre.<br>"
                + "Folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText3 = "<html><body>"
                + "<center><b>Már nem kell sok!</b></center><br>"
                + "SÍRFELIRAT<br><br>"
                + "TYÚK<br>"
                + "Csábos voltam csitri jérce koromtól.<br>"
                + "Az érckakas rámugrott a toronyból.<br>"
                + "Folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText4 = "<html><body>"
                + "<center><b>Gratulálok!</b></center><br>"
                + "SÍRFELIRAT<br><br>"
                + "VAKONDOK<br>"
                + "Feltemettek. Azt se tudom, kicsodák.<br>"
                + "Most felülről szagolom az ibolyát.<br><br>"
                + "<center><img src=\"" + super.getIcon("b6_smile") + "\">"
                + "</center></body></html>";
    }
}
