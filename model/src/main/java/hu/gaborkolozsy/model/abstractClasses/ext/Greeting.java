/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses.ext;

import hu.gaborkolozsy.model.abstractClasses.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Üdvözlő ablak.<br>
 * Csak az első indításkor jelenik meg.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
public class Greeting extends Info {
    
    /** Az icon. */
    private final Icon spartan = new ImageIcon(getClass()
                .getResource("/hu/gaborkolozsy/icons/spartan.png"));
    /** A felhasználónév. */
    private final String name = System.getProperty("user.name");
    /** A help icon. */
    private final Icon questionMark = new ImageIcon(getClass()
                .getResource("/hu/gaborkolozsy/icons/help.png"));
    /** A smile icon. */
    private final Icon smile = new ImageIcon(getClass()
                .getResource("/hu/gaborkolozsy/icons/y_smile.png"));
    
    /** Konstruktor. */
    public Greeting() {
        super.content = "<html><body><center>"
                + "<img src=\"" + spartan +"\"><br><br>"
                + "<font face=\"Lucida Grande\" size\"4\">"
                + "<b>Helló " + name + "</b>! </font>"
                + "<img src=\"" + smile + "\"><br><br>"
                + "Kérlek olvasd el a használati utasítást<br>"
                + "(programablak <img src=\""+ questionMark + "\">  "
                + "icon)!<br><br><br>"
                + "<font face=\"Lucida Grande\" size=\"2\">"
                + "Copyright © 2016, Gábor Kolozsy.<br>"
                + "All rights reserved.<br>"
                + "<a href='kolozsygabor@gamil.com'>kolozsygabor@gmail.com</a>"
                + "</font></center></body></html>";
    }
}
