/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.view.abstractClasses.ext;

import hu.gaborkolozsy.view.abstractClasses.Star;

/**
 * A ENG-HUN nyelvkombináció csillagának feliratai.
 * 
 * @author Kolozsy Gábor
 * @version 0.1.0
 * 
 * @see hu.gaborkolozsy.view.abstractClasses.Star
 */
public class Star1 extends Star {
    
    /**
     * Konstruktor.
     */
    public Star1() {
        super.toolTipText1 = "<html><body>"
                + "<center><b>Ez az!</b></center><br>"
                + "A középkori Angliában az emberek nem szexelhettek,<br>"
                + "csak a király jóváhagyásával (kivétel a királyi család).<br><br>"
                + "folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText2 = "<html><body>"
                + "<center><b>Gyerünk!</b></center><br>"
                + "A középkori Angliában az emberek nem szexelhettek,<br>"
                + "csak a király jóváhagyásával (kivétel a királyi család).<br>"
                + "Mikor egy család gyereket akart, "
                + "akkor a királyhoz kellett fordulniuk kérelemért,<br>"
                + "folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText3 = "<html><body>"
                + "<center><b>Már nem kell sok!</b></center><br>"
                + "A középkori Angliában az emberek nem szexelhettek,<br>"
                + "csak a király jóváhagyásával (kivétel a királyi család).<br>"
                + "Mikor egy család gyereket akart, "
                + "akkor a királyhoz kellett fordulniuk kérelemért,<br>"
                + "aki küldött nekik egy táblát, amit az ajtóra kellett "
                + "kitűzniük, amikor házas életet éltek.<br><br>"
                + "A táblán ez állt:<br>"
                + "folyt köv... <img src=\"" + super.getIcon("y_smile") + "\">"
                + "</body></html>";
        super.toolTipText4 = "<html><body>"
                + "<center><b>Egy lépés!</b></center><br>"
                + "A középkori Angliában az emberek nem szexelhettek,<br>"
                + "csak a király jóváhagyásával (kivétel a királyi család).<br>"
                + "Mikor egy család gyereket akart, "
                + "akkor a királyhoz kellett fordulniuk kérelemért,<br>"
                + "aki küldött nekik egy táblát, amit az ajtóra kellett "
                + "kitűzniük, amikor házas életet éltek.<br><br>"
                + "A táblán ez állt:<br>"
                + "Fornication Under Consent of the King. ( F. U. C. K. )<br>"
                + "(Paráználkodás a király engedélyével)<br>"
                + "Innen ered a mai \"fuck\" angol szó.<br>"
                + "<center><img src=\"" + super.getIcon("b1_smile") + "\"></center>"
                + "</body></html>";
    }
}
