/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.info.ext;

import hu.gaborkolozsy.model.info.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A jó teljesítmény után járó extra gratulációk összege.<br>
 * (De bármilyen más statisztikát is tartalmazhat a jövőben.)
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.info.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
public class Extra extends Info {
    
    /** Icon. */
    private final Icon icon = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/congratulation.png"));

    /**
     * Üres konstruktor.
     */
    public Extra() {}
    
    /** 
     * Konstruktor.
     * @param sumOfCongratulation a gratulációk száma
     */
    public Extra(int sumOfCongratulation) {
        super.content = "<html><body><center>"
                + "<img src=\"" + icon + "\"><br><br>"
                + "<font face=\"Lucida Grande\" size=\"4\""
                + "<b>Gratulációk!</b></font><br><br>"
                + "<font size=\"5\"<b>" + sumOfCongratulation + " / " 
                + "42<b></font></center></body><htmal>";
    }
}
