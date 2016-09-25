/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses.ext;

import hu.gaborkolozsy.model.abstractClasses.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A program névjegye használati utasítással.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.Info
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @since 2.0.0
 */
public class About extends Info {
    
    /** Az icon. */
    private final Icon vocabulary = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/Vocabulary.png"));
    /** Csillag icon. */
    private final Icon star = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/star_little.png"));
    /** Smile icon. */
    private final Icon smile = new ImageIcon(getClass()
            .getResource("/hu/gaborkolozsy/icons/y_smile.png"));
    /** A <b>programozas-oktatas</b> linkje. */
    private final String link = "http://www.programozas-oktatas.hu/x/java-tavoktatas/";
    /** A program verziószáma. */
    private String version = "2.2.1";
    /** Az utolsó verzió dátuma. */
    private String date = "2016.08.09";
    private final String first = "2016.06.13";
    private final String second = "2016.07.27";
    private final String third = "2016.07.30";
    private final String fourth = "2016.08.07";

    /** Konstruktor. */
    public About() {
        super.content = "<html><body>"
                + "<center><img src=\"" + vocabulary + "\"></center><br>"
                + "<center><font face=\"Lucide Grande\" size=\"4\">"
                + "<span style=\"font-weight:bold\">"
                + "Vocabulary</span></font></center><br>"
                + "<center><font face=\"Lucida Grande\" size=\"2\">"
                + version + " (" + date + ") verzió</font></center><br>"
                + "<b>Használati utasítás:</b>"
                + "<ul>"
                +     "<li>válaszd ki a nyelvet(cseréld ha akarod)"
                +       " és a futam számát</li>"
                +     "<li>klikkelj a <code>Start</code> gombra</li>"
                +     "<li>gépeld be a fordítást és nyomj "
                +         "<code>enter</code>-t</li>"
                +     "<ul>"
                +       "<li>a helyes válasz "
                +           "<font color=\"rgb(0,153,0)\">zöld</font></li>"
                +       "<li>a rossz "
                +           "<font color=\"rgb(255,0,0)\">piros</font> színű</li>"
                +     "</ul>"
                +     "<li>nyomj <code>enter</code>-t a következő kérdéshez</li>"
                + "</ul>"
                + ""
                + "<b>Szabályok:</b>"
                + "<ul>"
                +     "<li>csak ékezet nélküli karaktereket használj</li>"
                +     "<li>a 'ß' karaktert \"ss\" karakterlánccal "
                +         "helyettesítsd</li>"
                +     "<li>a rontott válaszokat a program a futam végén "
                +         "újra kikérdezi</li>"
                +     "<li>egy kérdést egy körben legalább 2x kell helyesen "
                +         "megválaszolni<b>¹</b></li>"
                + "</ul>"
                + ""
                + "<b>Extrák:</b>"
                + "<ul>"
                +     "<li>megszerzhető <img src=\"" + smile + "\"> "
                +         "6 teljes " + "<img src=\"" + star + "\"></li>"
                +     "<li>két különböző teljes kör után elérhetővé válnak "
                +         "az Angol-Német<br>"
                +         "ill. Német-Angol nyelvkombinációk "
                +         "- <code><b>MIX</b></code> - is</li>"
                +     "<li>1 kör után az aktuális nyelven elérhető az "
                +         "\"akasztófa\" játék(terv)</li>"
                + "</ul>"
                + ""
                + "<b>Ha érdekel a programozás:</b><br>"
                + "<a href=\'" + link + "\'>" 
                + "www.programozas-oktatas.hu</a><br><br>"
                + ""
                + "<b>1</b>: a futam végi újrakérdezés válaszai nem számítanak"
                + "<br><br><br>"
                + ""
                + "<center><font face=\"Lucida Grande\" size=\"2\">"
                + "Copyright © 2016, Gábor Kolozsy.<br>"
                + "All rights reserved.<br>"
                + "<a href='kolozsygabor@gamil.com'>kolozsygabor@gmail.com</a>"
                + "</font></center></body></html>";
    }

    /** 
     * Visszaadja a <b>date</b> adattagot {@code String} formájába.
     * @return date
     */
    public String getDate() {
        return date;
    }

    /** 
     * Beállitja a <b>date</b> adattagot.
     * @param date az utolsó módosítás dátuma
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /** 
     * Visszaadja a <b>version</b> adattagot {@code String} formájába.
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /** 
     * Beállitja a <b>version</b> adattagot.
     * @param version a program verziója
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
