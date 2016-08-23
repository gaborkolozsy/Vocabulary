/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses.ext;

import hu.gaborkolozsy.model.abstractClasses.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Jár minden nyelvkombináción a futamonkénti első 100%-os teljesítmény után. 
 * 42 összesen(6 nyelvkombináció x 7 futamszám). 
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
public class Congratulation extends Info {
    
    /** Az icon. */
    private final Icon icon = new ImageIcon(getClass()
                .getResource("/hu/gaborkolozsy/icons/congratulation.png"));
    /** A felhasználónév. */
    private final String name = System.getProperty("user.name");

    /**
     * Üres konstruktor.
     */
    public Congratulation() {}
    
    /** 
     * Konstruktor.
     * 
     * @param combo nyelvkombináció
     * @param index a futam kombobox kiválasztott indexe
     * @param sumOfCongrat a gratulációk száma
     */
    public Congratulation(String combo, int index, int sumOfCongrat) {
        super.content = "<html><body><center>"
                + "<img src=\"" + icon + "\"><br><br>"
                + "<font face=\"Lucida Grande\" size=\"4\""
                + "<b>Gratulálok " + name + "!</b></font><br><br>"
                + " Az első <b>100%</b>-os teljesítményed "
                + "a(z) <b>" + combo + "</b> nyelvkombinácó <br>" 
                + goodForm(index) + " futamában.<br><br>"
                + "<font size=\"5\"<b>" + sumOfCongrat + " / " 
                + "42<b></font></center></body><htmal>";
    }

    /**
     * Visszaadja a futamszám nyelvtanilag helyes, 
     * mondatba illeszthető formáját.
     * 
     * @param index a futam kiválasztott indexe
     * @return futamszam
     */
    private String goodForm(int index) {
        String raceNumber = "";
        switch(index) {
            case 0: raceNumber = "5-ös"; break;
            case 1: raceNumber = "10-es"; break;
            case 2: raceNumber = "20-as"; break;
            case 3: raceNumber = "30-as"; break;
            case 4: raceNumber = "40-es"; break;
            case 5: raceNumber = "50-es"; break;
            case 6: raceNumber = "100-as"; break;
        }
        return raceNumber;
    }
}
