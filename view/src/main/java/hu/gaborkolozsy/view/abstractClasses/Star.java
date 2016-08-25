/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.view.abstractClasses;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A kör meghatározott szakaszaiban megjelenö csillagok és a hozzájuk 
 * tartozó feliratok.
 * 
 * @author Kolozsy Gábor
 * @version 0.1.0
 * 
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 */
public abstract class Star {
    
    /** Az első felirat. */
    protected String toolTipText1;
    /** A második felirat. */
    protected String toolTipText2;
    /** A harmadik felirat. */
    protected String toolTipText3;
    /** A negyedik felirat. */
    protected String toolTipText4;

    /** 
     * Visszaadja az első feliratot.
     * @return toolTipText1
     */
    public String getToolTipText1() {
        return toolTipText1;
    }

    /** 
     * Visszaadja a második feliratot.
     * @return toolTipText2
     */
    public String getToolTipText2() {
        return toolTipText2;
    }

    /** 
     * Visszaadja a harmadik feliratot.
     * @return toolTipText3
     */
    public String getToolTipText3() {
        return toolTipText3;
    }

    /** 
     * Visszaadja a negyedik feliratot.
     * @return toolTipText4
     */
    public String getToolTipText4() {
        return toolTipText4;
    }
    
    /**
     * Visszaad egy icont a paraméterben meghatározott névvel.
     * @param nev az {@code Icon} neve
     * @return {@code Icon}
     */
    public Icon getIcon(String nev) {
        return new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/" + nev + ".png"));
    }
}
