/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses.ext;

import hu.gaborkolozsy.model.abstractClasses.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Tanácsot ad a felhasználónak a hatékonyabb tanuláshoz.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
public class Advice extends Info {
    
    /** Az icon. */
    private final Icon advisor = new ImageIcon(getClass()
                .getResource("/hu/gaborkolozsy/icons/advise.png"));
    /** A felhasználónév. */
    private final String name = System.getProperty("user.name");
    
    /** Konstruktor. */
    public Advice() {
        super.content = "<html><body><center>"
                + "<img src=\"" + advisor + "\"><br><br>"
                + "<font face=\"Lucida Grande\" size=\"4\""
                + "<b>Tanács!</b></font><br><br>"
                + "A rossz válaszok aránya <b>30%</b> felett van!<br><br>"
                +  name + ", csökkentsd a futam számát<br>"
                + "a hatékonyság növelése érdekében.</center></body></html>";
    }
}
