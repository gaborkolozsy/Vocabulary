/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 *
 */
package hu.gaborkolozsy.view;

import hu.gaborkolozsy.controller.ConfigService;
import hu.gaborkolozsy.controller.DataService;
import hu.gaborkolozsy.controller.InfoService;
import hu.gaborkolozsy.controller.LanguagesService;
import hu.gaborkolozsy.controller.ProposalService;
import hu.gaborkolozsy.controller.RaceService;
import hu.gaborkolozsy.controller.VocabularyService;
import hu.gaborkolozsy.view.star.Star;
import hu.gaborkolozsy.view.star.ext.Star1;
import hu.gaborkolozsy.view.star.ext.Star2;
import hu.gaborkolozsy.view.star.ext.Star3;
import hu.gaborkolozsy.view.star.ext.Star4;
import hu.gaborkolozsy.view.star.ext.Star5;
import hu.gaborkolozsy.view.star.ext.Star6;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;

/**
 * Angol és német szókincs bővítésére alkalmas program.<br>
 * 1000 szót tartalmaz.
 *
 * <h2>Leírás</h2>
 * Kézzel szerkesztett {@code .ini} kiterjesztésű fájlokból készít {@code .bin}
 * kiterjesztésű fájlokat az első indítás alkalmával.<br>
 * A továbbiakban onnan tölti be a használni kívánt szószedetet.<br>
 * <p>
 * Az alapból három {@code .bin} kiterjesztésű szószedetből elöször csak kettő
 * érhető el. A kevert <b>MIX</b> nyelvek, "<b>ENG-GER</b>" és "<b>GER-ENG</b>"
 * szószedetek csak akkor, ha a felhasználó teljesített legalább egy-egy kört
 * két különböző nyelvkombináción.<br>
 * A három szószedet fájl hat különböző és elérhető nyelvkombinációt takar. A
 * program kérésre(klikk a "<b>Csere</b>" gombon) megfordítja az aktuális
 * szószedetet.
 * <p>
 * A program, egy egyszer megjelenő üdvözlő ablakkal és alapértelmezetten az
 * "<b>ENG-HUN</b>" nyelvkombinációval indul. Ezt megváltoztatni a
 * "<b>Nyelv</b>" combo box-ból ill. a "<b>Csere</b>" gombra való kattintással
 * lehet.
 * <p>
 * A "<b>Futam</b>" combo box-ból választható ki, hogy egy futam alkalmával
 * mennyi legyen a fordítandó szavak száma.<br>
 * Kezdetben ez csak 5 és 10 lehet. A "folyamatos" jó teljesítmény(<b>T%</b>)
 * eredményeként ezek kiegészülnek a 20, 30, 40, 50 és 100-as tételekkel.<br>
 * pl.: 10-es futam <b>90≤T≤100 3x</b> = 20-as hozzáadva stb.
 *
 * <h2>Használat</h2>
 * A "<b>Start</b>" gombra való kattintás után elindul a kérdezz-felelek.<br>
 * Ha a válasz mezőbe gépelt fordítás helyes, akkor az {@code Enter} lenyomása
 * után <font color="green">zöld</font> színű lesz az. Máskülönben megjelenik a
 * helyes fordítás <font color="red">pirossal</font>.<br>
 * Az {@code Enter} ismételt lenyomásával kérhető a következő fordítandó szó.
 * <p>
 * A rontott szavak a futam végén újra sorra kerülnek. Ez megy mindaddig amig
 * minden a futamban fordításra váró szó helyesen le nem lesz fordítva.<br>
 * A következő futam az "<b>Újra</b>" feliratú gombra való kattintással
 * kezdeményezhető. Ezzel egyidőben resetelésre kerülnek a futam egyes
 * információi.
 * <p>
 * A szavakat körönként(minden tartalmazott szó vagy szóösszetétel helyes
 * fordítása az előre meghatározott alkalommal), itt <b>legalább 2x</b> kell
 * helyesen lefordítani, <b>legalább két különböző</b> futamban.
 *
 * <h2>Szabályok</h2>
 * <ol>
 * <li>Csak ékezet nélküli betűk használhatóak.</li>
 * <li>A '<b>ß</b>' karaktert '<b>ss</b>' karakterrel kell helyetesíteni.</li>
 * </ol>
 *
 * <h2>Jutalmazás</h2>
 * <ol>
 * <li>Nyelvkombinációnként és futamszámonként az első 100%-os teljesítményhez
 * egy felugró kis ablakban gratulál a program. Ebből 42-t lehet összegyüjteni(6
 * nyelvkombináció x 7 választható futamszám). Az addig elért gratulációk számát
 * meg is jeleníti. Ezt a felhasználó le is kérdezheti a "<b>show my
 * congrat</b>" szöveg válasz mezőbe történő bevitele és az {@code Enter}
 * lenyomásával ha a "<b>Start</b>" gombon a "Start" felirat olvasható.
 * </li>
 * <li>A feldolgozottság mértékét a program kis csillagokkal is "jutalmazza".
 * Ezekből négy féle van egy-egy {@code toolTipText}-el. 250 helyes
 * fordításonként kap egyet a felhasználó az ürestől a teljesig minden
 * nyelvkombináción. Így hat megszerezhető kis csillag van 24 féle variációban.
 * A {@code toolTipText}-ek feliratai érdekességeket ill.
 * <b>Romhányi József</b> verseket tartalmaznak részletekben. A teljes változat
 * csak a kör teljesítésekor megkapott csillag után olvasható.
 * </li>
 * </ol>
 *
 * <h2>Gyorsbillentyű</h2>
 * <ol>
 * <li>Ha a kurzor a válasz mezőben van, akkor "<b>Ctrl</b> + <b>J</b>"
 * billentyűkombinációval a felhasználó javaslatot(fordítási, stb.) tehet. A
 * program a javaslatokat {@code .ini} kiterjesztésű fájlba menti
 * nyelvkombinációnként, nyelv váltás és csere alkalmával ill. kilépéskor. A
 * programban megtalálható email címre kattintva a fájl akár mellékletként el is
 * küldhető (ha van beállított email kliens).
 * </li>
 * </ol>
 *
 * <h2>Informálás</h2>
 * A program felugró ablakokban informálja a felhasználót a következőkről:
 * <ol>
 * <li>Eredményr</li>
 * <li>Figyelmeztetés</li>
 * <li>Tanács</li>
 * </ol>
 *
 * <h2>Terv</h2>
 * <ol>
 * <li>Akasztófa játék</li>
 * <li>Nehézségi szintek</li>
 * <li>Saját szószedet feltöltése</li>
 * <li>Gyorsbillentyűre segítség</li>
 * </ol>
 *
 * <h2>Figyelmeztetés</h2>
 * <ol>
 * <li>A "<b>data.bin</b>" fájl törlése az adatok elvesztésével jár!</li>
 * </ol>
 *
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * @version 2.3.0
 *
 * @see hu.gaborkolozsy.controller.DataService
 * @see hu.gaborkolozsy.controller.InfoService
 * @see hu.gaborkolozsy.controller.ConfigService
 * @see hu.gaborkolozsy.controller.LanguagesService
 * @see hu.gaborkolozsy.controller.ProposalService
 * @see hu.gaborkolozsy.controller.RaceService
 * @see hu.gaborkolozsy.controller.VocabularyService
 * @see hu.gaborkolozsy.view.star.Star
 * @see hu.gaborkolozsy.view.star.ext.Star1
 * @see hu.gaborkolozsy.view.star.ext.Star2
 * @see hu.gaborkolozsy.view.star.ext.Star3
 * @see hu.gaborkolozsy.view.star.ext.Star4
 * @see hu.gaborkolozsy.view.star.ext.Star5
 * @see hu.gaborkolozsy.view.star.ext.Star6
 * @see java.awt.Color
 * @see java.awt.Desktop
 * @see java.awt.event.KeyEvent;
 * @see java.awt.event.KeyListener
 * @see java.awt.event.MouseAdapter;
 * @see java.awt.event.MouseEvent
 * @see java.io.File
 * @see java.io.IOException
 * @see java.net.URI;
 * @see java.net.URISyntaxException
 * @see java.util.List
 * @see java.util.concurrent.TimeUnit
 * @see java.util.regex.Pattern
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @see javax.swing.JEditorPane
 * @see javax.swing.JFrame
 * @see javax.swing.JLabel
 * @see javax.swing.JOptionPane
 * @see javax.swing.ToolTipManager
 * @see javax.swing.UIManager
 * @see javax.swing.UnsupportedLookAndFeelException
 * @see javax.swing.event.HyperlinkEvent
 */
public class Vocabulary extends JFrame {

    /**
     * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
     */
    /**
     * Fájlok.
     */
    private static final String ENGLISHFILE = "English-1000.ini";
    private static final String GERMANFILE = "German-1000.ini";
    private static final String DATAFILE = "data.bin";

    /**
     * Ikonok útvonala.
     */
    private static final String PATH = "/hu/gaborkolozsy/icons/";

    /**
     * Szervíz osztályok.
     */
    private static ConfigService cs;
    private static final LanguagesService lcs = new LanguagesService();
    private static DataService ds;
    private static final VocabularyService vs = new VocabularyService();
    private static final InfoService is = new InfoService();
    private static final ProposalService ps = new ProposalService();
    private static RaceService rs;

    /**
     * Amiről fordítunk.
     */
    private static String from = lcs.getENGLISH();

    /**
     * Amire fordítunk.
     */
    private static String to = lcs.getHUNGARIAN();

    /**
     * A fordítandó szó {@code keyList}-ben elfoglalt indexe.
     */
    private static int index;

    /**
     * Az index értékének tárolásához szükséges azonosító. pl: 111-HUNENG
     */
    private static String indexID = "";

    /**
     * Az index maximális értéke egy körben. 1 jelentése: 1x kell jól
     * lefordítani a kiválasztott szót 1 körben
     */
    private static int IDXMAXVALUE = 2;

    /**
     * A körben feldolgozott indexek százalékos értéke.
     */
    private static double fISz;

    /**
     * A körben feldolgozott indexek százalékos előző értéke.
     */
    private static double fISzPrev;

    /**
     * A fordított nyelvkombináción a feldolgozott indexek százalékos értéke.
     */
    private static double fISzByReverseKombo;

    /**
     * A futamban szereplő új indexek száma.
     */
    private static int newIdxInRace;

    /**
     * A fordítandó szó vagy szóösszetétel.
     */
    private static String key;

    /**
     * A futam kombo box kezdö max indexe. (1-nél megjelenö tételek 5;10)
     */
    private static final int RACE_COMBO_BOX_INITIAL_MAX_IDX = 1;
    
    /** 
     * Colors. 
     */
    private static final Color BLACK = Color.black;
    private static final Color MY_GRAY = new Color(153, 153, 153);
    private static final Color MY_BLUE = new Color(51, 102, 255);

    /**
     * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
     */
    
    /**
     * Creates new form window.
     */
    public Vocabulary() {
        initComponents();
        
        if (!new File(DATAFILE).exists()) {
            welcomeWindow();

            // initial values for stored data
            ds = new DataService(lcs.getList());

            if (!new File("ENG-HUN.bin").exists()) {
                iniToBin();
            }
        } else {
            // initial values by default combo
            try {
                ds = new DataService(DATAFILE,
                        lcs.getDefaultCombo(),
                        lcs.reverseCombo());
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        vocabularyUploadFromBin();
        initRaceComboBox();
        setStars();
        setInfosInWindow();

        // add MIX if possible
        if (ds.countRounds(lcs.getList())) {
            languageComboBox.addItem("MIX");
        }

        /**
         * key listener (Ctrl + J) for proposal.
         */
        answerTxtField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown()
                        && e.getKeyChar() != 'j'
                        && e.getKeyCode() == KeyEvent.VK_J) {  // or 74

                    Icon icon = new ImageIcon(getClass()
                            .getResource("/hu/gaborkolozsy/icons/pencil.png"));

                    /**
                     * Itt nem kell de lehetne egy kombo box is.
                     */
                    //String[] empty = {"fordítás", "helyesírás"};
                    boolean forma = false;
                    while (!forma) {
                        // a végén ... empty, empty[0]); az egy kombo box lenne
                        String input = (String) JOptionPane.showInputDialog(
                                null, "<html><body>"
                                + "<b>problema=valami magyarazat </b>"
                                + "</body></html>",
                                "Javaslat", JOptionPane.OK_CANCEL_OPTION, icon,
                                null, null);

                        if (input != null) {
                            if (Pattern.matches("\\S+=\\S.*", input)) {
                                ps.addProposal(input);
                                forma = true;
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Rossz formátum",
                                        "Hiba", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            forma = true;
                        }
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPane = new javax.swing.JTabbedPane();
        practicePanel = new javax.swing.JPanel();
        raceLabel = new javax.swing.JLabel();
        raceComboBox = new javax.swing.JComboBox();
        fromLabel = new javax.swing.JLabel();
        swapButton = new javax.swing.JButton();
        toLabel = new javax.swing.JLabel();
        languageLabel = new javax.swing.JLabel();
        languageComboBox = new javax.swing.JComboBox();
        goodLabel = new javax.swing.JLabel();
        goodResultLabel = new javax.swing.JLabel();
        goodPercentResultLabel = new javax.swing.JLabel();
        badLabel = new javax.swing.JLabel();
        badResultLabel = new javax.swing.JLabel();
        badPercentResultLabel = new javax.swing.JLabel();
        askLabel = new javax.swing.JLabel();
        restResultLabel = new javax.swing.JLabel();
        answerTxtField = new javax.swing.JTextField();
        doneLabel = new javax.swing.JLabel();
        doneResultLabel = new javax.swing.JLabel();
        roundLabel = new javax.swing.JLabel();
        roundResultLabel = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        star1 = new javax.swing.JLabel();
        star2 = new javax.swing.JLabel();
        star3 = new javax.swing.JLabel();
        star4 = new javax.swing.JLabel();
        star5 = new javax.swing.JLabel();
        star6 = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        helpLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField57 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField70 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jTextField73 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jTextField82 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jTextField85 = new javax.swing.JTextField();
        jTextField86 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField88 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jTextField93 = new javax.swing.JTextField();
        jTextField94 = new javax.swing.JTextField();
        jTextField95 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jTextField97 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jTextField99 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jTextField101 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        jTextField104 = new javax.swing.JTextField();
        jTextField105 = new javax.swing.JTextField();
        jTextField106 = new javax.swing.JTextField();
        jTextField107 = new javax.swing.JTextField();
        jTextField108 = new javax.swing.JTextField();
        jTextField109 = new javax.swing.JTextField();
        jTextField110 = new javax.swing.JTextField();
        jTextField111 = new javax.swing.JTextField();
        jTextField112 = new javax.swing.JTextField();
        jTextField113 = new javax.swing.JTextField();
        jTextField114 = new javax.swing.JTextField();
        jTextField115 = new javax.swing.JTextField();
        jTextField116 = new javax.swing.JTextField();
        jTextField117 = new javax.swing.JTextField();
        jTextField118 = new javax.swing.JTextField();
        jTextField119 = new javax.swing.JTextField();
        jTextField120 = new javax.swing.JTextField();
        jTextField121 = new javax.swing.JTextField();
        jTextField122 = new javax.swing.JTextField();
        jTextField123 = new javax.swing.JTextField();
        jTextField124 = new javax.swing.JTextField();
        jTextField125 = new javax.swing.JTextField();
        jTextField126 = new javax.swing.JTextField();
        jTextField127 = new javax.swing.JTextField();
        jTextField128 = new javax.swing.JTextField();
        jTextField129 = new javax.swing.JTextField();
        jTextField130 = new javax.swing.JTextField();
        jTextField131 = new javax.swing.JTextField();
        jTextField132 = new javax.swing.JTextField();
        jTextField133 = new javax.swing.JTextField();
        jTextField134 = new javax.swing.JTextField();
        jTextField135 = new javax.swing.JTextField();
        jTextField136 = new javax.swing.JTextField();
        jTextField137 = new javax.swing.JTextField();
        jTextField138 = new javax.swing.JTextField();
        jTextField139 = new javax.swing.JTextField();
        jTextField140 = new javax.swing.JTextField();
        jTextField141 = new javax.swing.JTextField();
        jTextField142 = new javax.swing.JTextField();
        jTextField143 = new javax.swing.JTextField();
        jTextField144 = new javax.swing.JTextField();
        jTextField145 = new javax.swing.JTextField();
        jTextField146 = new javax.swing.JTextField();
        jTextField147 = new javax.swing.JTextField();
        jTextField148 = new javax.swing.JTextField();
        jTextField149 = new javax.swing.JTextField();
        jTextField150 = new javax.swing.JTextField();
        jTextField151 = new javax.swing.JTextField();
        jTextField152 = new javax.swing.JTextField();
        jTextField153 = new javax.swing.JTextField();
        jTextField154 = new javax.swing.JTextField();
        jTextField155 = new javax.swing.JTextField();
        jTextField156 = new javax.swing.JTextField();
        jTextField157 = new javax.swing.JTextField();
        jTextField158 = new javax.swing.JTextField();
        jTextField159 = new javax.swing.JTextField();
        jTextField160 = new javax.swing.JTextField();
        jTextField161 = new javax.swing.JTextField();
        jTextField162 = new javax.swing.JTextField();
        jTextField163 = new javax.swing.JTextField();
        jTextField164 = new javax.swing.JTextField();
        jTextField165 = new javax.swing.JTextField();
        jTextField166 = new javax.swing.JTextField();
        jTextField167 = new javax.swing.JTextField();
        jTextField168 = new javax.swing.JTextField();
        jTextField169 = new javax.swing.JTextField();
        jTextField170 = new javax.swing.JTextField();
        jTextField171 = new javax.swing.JTextField();
        jTextField172 = new javax.swing.JTextField();
        jTextField173 = new javax.swing.JTextField();
        jTextField174 = new javax.swing.JTextField();
        jTextField175 = new javax.swing.JTextField();
        jTextField176 = new javax.swing.JTextField();
        jTextField177 = new javax.swing.JTextField();
        jTextField178 = new javax.swing.JTextField();
        jTextField179 = new javax.swing.JTextField();
        jTextField180 = new javax.swing.JTextField();
        jTextField181 = new javax.swing.JTextField();
        jTextField182 = new javax.swing.JTextField();
        jTextField183 = new javax.swing.JTextField();
        jTextField184 = new javax.swing.JTextField();
        jTextField185 = new javax.swing.JTextField();
        jTextField186 = new javax.swing.JTextField();
        jTextField187 = new javax.swing.JTextField();
        jTextField188 = new javax.swing.JTextField();
        jTextField189 = new javax.swing.JTextField();
        jTextField190 = new javax.swing.JTextField();
        jTextField191 = new javax.swing.JTextField();
        jTextField192 = new javax.swing.JTextField();
        jTextField193 = new javax.swing.JTextField();
        jTextField194 = new javax.swing.JTextField();
        jTextField195 = new javax.swing.JTextField();
        jTextField196 = new javax.swing.JTextField();
        jTextField197 = new javax.swing.JTextField();
        jTextField198 = new javax.swing.JTextField();
        jTextField199 = new javax.swing.JTextField();
        jTextField200 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vocabulary");
        setMinimumSize(new java.awt.Dimension(900, 280));

        TabbedPane.setBackground(new java.awt.Color(255, 249, 236));
        TabbedPane.setForeground(new java.awt.Color(51, 102, 255));
        TabbedPane.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N

        practicePanel.setBackground(new java.awt.Color(255, 249, 236));
        practicePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        raceLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        raceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/run.png"))); // NOI18N
        raceLabel.setText(" Futam");
        raceLabel.setToolTipText("az új szavak száma ebben a futamban");

        raceComboBox.setBackground(new java.awt.Color(255, 249, 236));
        raceComboBox.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        raceComboBox.setForeground(new java.awt.Color(51, 102, 255));
        raceComboBox.setToolTipText("");
        raceComboBox.setCursor(new java.awt.Cursor(12));
        raceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raceComboBoxActionPerformed(evt);
            }
        });

        fromLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/flag-gb.png"))); // NOI18N

        swapButton.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        swapButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/change.png"))); // NOI18N
        swapButton.setText(" Csere");
        swapButton.setCursor(new java.awt.Cursor(12));
        swapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swapButtonActionPerformed(evt);
            }
        });

        toLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/flag-hu.png"))); // NOI18N

        languageLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        languageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/flag-gb2.png"))); // NOI18N
        languageLabel.setText(" Nyelv");
        languageLabel.setMaximumSize(new java.awt.Dimension(79, 22));
        languageLabel.setMinimumSize(new java.awt.Dimension(79, 22));
        languageLabel.setPreferredSize(new java.awt.Dimension(79, 22));

        languageComboBox.setBackground(new java.awt.Color(255, 249, 236));
        languageComboBox.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        languageComboBox.setForeground(new java.awt.Color(51, 102, 255));
        languageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GB", "DE" }));
        languageComboBox.setCursor(new java.awt.Cursor(12));
        languageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboBoxActionPerformed(evt);
            }
        });

        goodLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        goodLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/good.png"))); // NOI18N
        goodLabel.setText(" Helyes");

        goodResultLabel.setForeground(new java.awt.Color(0, 153, 0));
        goodResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goodResultLabel.setText("0");

        goodPercentResultLabel.setForeground(new java.awt.Color(0, 153, 0));
        goodPercentResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goodPercentResultLabel.setText("0%");

        badLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        badLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/bad.png"))); // NOI18N
        badLabel.setText(" Rossz");

        badResultLabel.setForeground(new java.awt.Color(255, 0, 0));
        badResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        badResultLabel.setText("0");

        badPercentResultLabel.setForeground(new java.awt.Color(255, 0, 0));
        badPercentResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        badPercentResultLabel.setText("0%");

        askLabel.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        askLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        askLabel.setText("kérdés");
        askLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        askLabel.setMaximumSize(new java.awt.Dimension(200, 62));
        askLabel.setMinimumSize(new java.awt.Dimension(200, 62));
        askLabel.setName(""); // NOI18N
        askLabel.setPreferredSize(new java.awt.Dimension(200, 62));

        restResultLabel.setFont(new java.awt.Font("Chalkduster", 0, 18)); // NOI18N
        restResultLabel.setForeground(new java.awt.Color(51, 102, 255));
        restResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        restResultLabel.setText(" ");
        restResultLabel.setToolTipText("a maradék szavak száma");

        answerTxtField.setBackground(new java.awt.Color(255, 249, 236));
        answerTxtField.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        answerTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answerTxtField.setText("válasz");
        answerTxtField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        answerTxtField.setMaximumSize(new java.awt.Dimension(200, 62));
        answerTxtField.setMinimumSize(new java.awt.Dimension(200, 62));
        answerTxtField.setPreferredSize(new java.awt.Dimension(200, 62));
        answerTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerTxtFieldActionPerformed(evt);
            }
        });

        doneLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        doneLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/info-small.png"))); // NOI18N
        doneLabel.setText(" Kész");

        doneResultLabel.setForeground(new java.awt.Color(51, 102, 255));
        doneResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        doneResultLabel.setMaximumSize(new java.awt.Dimension(70, 16));
        doneResultLabel.setPreferredSize(new java.awt.Dimension(70, 16));

        roundLabel.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        roundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/info-small.png"))); // NOI18N
        roundLabel.setText(" Kör");
        roundLabel.setMaximumSize(new java.awt.Dimension(77, 22));
        roundLabel.setPreferredSize(new java.awt.Dimension(77, 22));

        roundResultLabel.setForeground(new java.awt.Color(51, 102, 255));
        roundResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roundResultLabel.setText(" ");

        exitButton.setFont(new java.awt.Font("Chalkduster", 0, 11)); // NOI18N
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/quit.png"))); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(12));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        star2.setToolTipText("");

        star3.setToolTipText("");

        star4.setToolTipText("");

        star5.setToolTipText("");

        star6.setToolTipText("");

        startButton.setFont(new java.awt.Font("Chalkduster", 0, 16)); // NOI18N
        startButton.setForeground(new java.awt.Color(51, 102, 255));
        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/lockstart.png"))); // NOI18N
        startButton.setText(" Start");
        startButton.setCursor(new java.awt.Cursor(12));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        helpLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu/gaborkolozsy/icons/help.png"))); // NOI18N
        helpLabel.setToolTipText("Névjegy");
        helpLabel.setCursor(new java.awt.Cursor(12));
        helpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpLabelMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(153, 153, 153));
        jTextField1.setToolTipText("");
        jTextField1.setBorder(null);
        jTextField1.setEnabled(false);
        jTextField1.setFocusCycleRoot(true);
        jTextField1.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField1.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField1.setRequestFocusEnabled(false);

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(153, 153, 153));
        jTextField2.setToolTipText("");
        jTextField2.setBorder(null);
        jTextField2.setEnabled(false);
        jTextField2.setFocusCycleRoot(true);
        jTextField2.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField2.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField2.setRequestFocusEnabled(false);

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(153, 153, 153));
        jTextField3.setToolTipText("");
        jTextField3.setBorder(null);
        jTextField3.setEnabled(false);
        jTextField3.setFocusCycleRoot(true);
        jTextField3.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField3.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField3.setRequestFocusEnabled(false);

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(153, 153, 153));
        jTextField4.setToolTipText("");
        jTextField4.setBorder(null);
        jTextField4.setEnabled(false);
        jTextField4.setFocusCycleRoot(true);
        jTextField4.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField4.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField4.setRequestFocusEnabled(false);

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(153, 153, 153));
        jTextField5.setToolTipText("");
        jTextField5.setBorder(null);
        jTextField5.setEnabled(false);
        jTextField5.setFocusCycleRoot(true);
        jTextField5.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField5.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField5.setRequestFocusEnabled(false);

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(153, 153, 153));
        jTextField6.setToolTipText("");
        jTextField6.setBorder(null);
        jTextField6.setEnabled(false);
        jTextField6.setFocusCycleRoot(true);
        jTextField6.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField6.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField6.setRequestFocusEnabled(false);

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(153, 153, 153));
        jTextField7.setToolTipText("");
        jTextField7.setBorder(null);
        jTextField7.setEnabled(false);
        jTextField7.setFocusCycleRoot(true);
        jTextField7.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField7.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField7.setRequestFocusEnabled(false);

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(153, 153, 153));
        jTextField8.setToolTipText("");
        jTextField8.setBorder(null);
        jTextField8.setEnabled(false);
        jTextField8.setFocusCycleRoot(true);
        jTextField8.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField8.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField8.setRequestFocusEnabled(false);

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(153, 153, 153));
        jTextField9.setToolTipText("");
        jTextField9.setBorder(null);
        jTextField9.setEnabled(false);
        jTextField9.setFocusCycleRoot(true);
        jTextField9.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField9.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField9.setRequestFocusEnabled(false);

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(153, 153, 153));
        jTextField10.setToolTipText("");
        jTextField10.setBorder(null);
        jTextField10.setEnabled(false);
        jTextField10.setFocusCycleRoot(true);
        jTextField10.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField10.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField10.setRequestFocusEnabled(false);

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(153, 153, 153));
        jTextField11.setToolTipText("");
        jTextField11.setBorder(null);
        jTextField11.setEnabled(false);
        jTextField11.setFocusCycleRoot(true);
        jTextField11.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField11.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField11.setRequestFocusEnabled(false);

        jTextField12.setEditable(false);
        jTextField12.setBackground(new java.awt.Color(153, 153, 153));
        jTextField12.setToolTipText("");
        jTextField12.setBorder(null);
        jTextField12.setEnabled(false);
        jTextField12.setFocusCycleRoot(true);
        jTextField12.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField12.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField12.setRequestFocusEnabled(false);

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(153, 153, 153));
        jTextField13.setToolTipText("");
        jTextField13.setBorder(null);
        jTextField13.setEnabled(false);
        jTextField13.setFocusCycleRoot(true);
        jTextField13.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField13.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField13.setRequestFocusEnabled(false);

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(153, 153, 153));
        jTextField14.setToolTipText("");
        jTextField14.setBorder(null);
        jTextField14.setEnabled(false);
        jTextField14.setFocusCycleRoot(true);
        jTextField14.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField14.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField14.setRequestFocusEnabled(false);

        jTextField15.setEditable(false);
        jTextField15.setBackground(new java.awt.Color(153, 153, 153));
        jTextField15.setToolTipText("");
        jTextField15.setBorder(null);
        jTextField15.setEnabled(false);
        jTextField15.setFocusCycleRoot(true);
        jTextField15.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField15.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField15.setRequestFocusEnabled(false);

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(153, 153, 153));
        jTextField16.setToolTipText("");
        jTextField16.setBorder(null);
        jTextField16.setEnabled(false);
        jTextField16.setFocusCycleRoot(true);
        jTextField16.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField16.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField16.setRequestFocusEnabled(false);

        jTextField17.setEditable(false);
        jTextField17.setBackground(new java.awt.Color(153, 153, 153));
        jTextField17.setToolTipText("");
        jTextField17.setBorder(null);
        jTextField17.setEnabled(false);
        jTextField17.setFocusCycleRoot(true);
        jTextField17.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField17.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField17.setRequestFocusEnabled(false);

        jTextField18.setEditable(false);
        jTextField18.setBackground(new java.awt.Color(153, 153, 153));
        jTextField18.setToolTipText("");
        jTextField18.setBorder(null);
        jTextField18.setEnabled(false);
        jTextField18.setFocusCycleRoot(true);
        jTextField18.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField18.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField18.setRequestFocusEnabled(false);

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(153, 153, 153));
        jTextField19.setToolTipText("");
        jTextField19.setBorder(null);
        jTextField19.setEnabled(false);
        jTextField19.setFocusCycleRoot(true);
        jTextField19.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField19.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField19.setRequestFocusEnabled(false);

        jTextField20.setEditable(false);
        jTextField20.setBackground(new java.awt.Color(153, 153, 153));
        jTextField20.setToolTipText("");
        jTextField20.setBorder(null);
        jTextField20.setEnabled(false);
        jTextField20.setFocusCycleRoot(true);
        jTextField20.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField20.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField20.setRequestFocusEnabled(false);

        jTextField21.setEditable(false);
        jTextField21.setBackground(new java.awt.Color(153, 153, 153));
        jTextField21.setToolTipText("");
        jTextField21.setBorder(null);
        jTextField21.setEnabled(false);
        jTextField21.setFocusCycleRoot(true);
        jTextField21.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField21.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField21.setRequestFocusEnabled(false);

        jTextField22.setEditable(false);
        jTextField22.setBackground(new java.awt.Color(153, 153, 153));
        jTextField22.setToolTipText("");
        jTextField22.setBorder(null);
        jTextField22.setEnabled(false);
        jTextField22.setFocusCycleRoot(true);
        jTextField22.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField22.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField22.setRequestFocusEnabled(false);

        jTextField23.setEditable(false);
        jTextField23.setBackground(new java.awt.Color(153, 153, 153));
        jTextField23.setToolTipText("");
        jTextField23.setBorder(null);
        jTextField23.setEnabled(false);
        jTextField23.setFocusCycleRoot(true);
        jTextField23.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField23.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField23.setRequestFocusEnabled(false);

        jTextField24.setEditable(false);
        jTextField24.setBackground(new java.awt.Color(153, 153, 153));
        jTextField24.setToolTipText("");
        jTextField24.setBorder(null);
        jTextField24.setEnabled(false);
        jTextField24.setFocusCycleRoot(true);
        jTextField24.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField24.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField24.setRequestFocusEnabled(false);

        jTextField25.setEditable(false);
        jTextField25.setBackground(new java.awt.Color(153, 153, 153));
        jTextField25.setToolTipText("");
        jTextField25.setBorder(null);
        jTextField25.setEnabled(false);
        jTextField25.setFocusCycleRoot(true);
        jTextField25.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField25.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField25.setRequestFocusEnabled(false);

        jTextField26.setEditable(false);
        jTextField26.setBackground(new java.awt.Color(153, 153, 153));
        jTextField26.setToolTipText("");
        jTextField26.setBorder(null);
        jTextField26.setEnabled(false);
        jTextField26.setFocusCycleRoot(true);
        jTextField26.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField26.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField26.setRequestFocusEnabled(false);

        jTextField27.setEditable(false);
        jTextField27.setBackground(new java.awt.Color(153, 153, 153));
        jTextField27.setToolTipText("");
        jTextField27.setBorder(null);
        jTextField27.setEnabled(false);
        jTextField27.setFocusCycleRoot(true);
        jTextField27.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField27.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField27.setRequestFocusEnabled(false);

        jTextField28.setEditable(false);
        jTextField28.setBackground(new java.awt.Color(153, 153, 153));
        jTextField28.setToolTipText("");
        jTextField28.setBorder(null);
        jTextField28.setEnabled(false);
        jTextField28.setFocusCycleRoot(true);
        jTextField28.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField28.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField28.setRequestFocusEnabled(false);

        jTextField29.setEditable(false);
        jTextField29.setBackground(new java.awt.Color(153, 153, 153));
        jTextField29.setToolTipText("");
        jTextField29.setBorder(null);
        jTextField29.setEnabled(false);
        jTextField29.setFocusCycleRoot(true);
        jTextField29.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField29.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField29.setRequestFocusEnabled(false);

        jTextField30.setEditable(false);
        jTextField30.setBackground(new java.awt.Color(153, 153, 153));
        jTextField30.setToolTipText("");
        jTextField30.setBorder(null);
        jTextField30.setEnabled(false);
        jTextField30.setFocusCycleRoot(true);
        jTextField30.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField30.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField30.setRequestFocusEnabled(false);

        jTextField31.setEditable(false);
        jTextField31.setBackground(new java.awt.Color(153, 153, 153));
        jTextField31.setToolTipText("");
        jTextField31.setBorder(null);
        jTextField31.setEnabled(false);
        jTextField31.setFocusCycleRoot(true);
        jTextField31.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField31.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField31.setRequestFocusEnabled(false);

        jTextField32.setEditable(false);
        jTextField32.setBackground(new java.awt.Color(153, 153, 153));
        jTextField32.setToolTipText("");
        jTextField32.setBorder(null);
        jTextField32.setEnabled(false);
        jTextField32.setFocusCycleRoot(true);
        jTextField32.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField32.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField32.setRequestFocusEnabled(false);

        jTextField33.setEditable(false);
        jTextField33.setBackground(new java.awt.Color(153, 153, 153));
        jTextField33.setToolTipText("");
        jTextField33.setBorder(null);
        jTextField33.setEnabled(false);
        jTextField33.setFocusCycleRoot(true);
        jTextField33.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField33.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField33.setRequestFocusEnabled(false);

        jTextField34.setEditable(false);
        jTextField34.setBackground(new java.awt.Color(153, 153, 153));
        jTextField34.setToolTipText("");
        jTextField34.setBorder(null);
        jTextField34.setEnabled(false);
        jTextField34.setFocusCycleRoot(true);
        jTextField34.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField34.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField34.setRequestFocusEnabled(false);

        jTextField35.setEditable(false);
        jTextField35.setBackground(new java.awt.Color(153, 153, 153));
        jTextField35.setToolTipText("");
        jTextField35.setBorder(null);
        jTextField35.setEnabled(false);
        jTextField35.setFocusCycleRoot(true);
        jTextField35.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField35.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField35.setRequestFocusEnabled(false);

        jTextField36.setEditable(false);
        jTextField36.setBackground(new java.awt.Color(153, 153, 153));
        jTextField36.setToolTipText("");
        jTextField36.setBorder(null);
        jTextField36.setEnabled(false);
        jTextField36.setFocusCycleRoot(true);
        jTextField36.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField36.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField36.setRequestFocusEnabled(false);

        jTextField37.setEditable(false);
        jTextField37.setBackground(new java.awt.Color(153, 153, 153));
        jTextField37.setToolTipText("");
        jTextField37.setBorder(null);
        jTextField37.setEnabled(false);
        jTextField37.setFocusCycleRoot(true);
        jTextField37.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField37.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField37.setRequestFocusEnabled(false);

        jTextField38.setEditable(false);
        jTextField38.setBackground(new java.awt.Color(153, 153, 153));
        jTextField38.setToolTipText("");
        jTextField38.setBorder(null);
        jTextField38.setEnabled(false);
        jTextField38.setFocusCycleRoot(true);
        jTextField38.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField38.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField38.setRequestFocusEnabled(false);

        jTextField39.setEditable(false);
        jTextField39.setBackground(new java.awt.Color(153, 153, 153));
        jTextField39.setToolTipText("");
        jTextField39.setBorder(null);
        jTextField39.setEnabled(false);
        jTextField39.setFocusCycleRoot(true);
        jTextField39.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField39.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField39.setRequestFocusEnabled(false);

        jTextField40.setEditable(false);
        jTextField40.setBackground(new java.awt.Color(153, 153, 153));
        jTextField40.setToolTipText("");
        jTextField40.setBorder(null);
        jTextField40.setEnabled(false);
        jTextField40.setFocusCycleRoot(true);
        jTextField40.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField40.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField40.setRequestFocusEnabled(false);

        jTextField41.setEditable(false);
        jTextField41.setBackground(new java.awt.Color(153, 153, 153));
        jTextField41.setToolTipText("");
        jTextField41.setBorder(null);
        jTextField41.setEnabled(false);
        jTextField41.setFocusCycleRoot(true);
        jTextField41.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField41.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField41.setRequestFocusEnabled(false);

        jTextField42.setEditable(false);
        jTextField42.setBackground(new java.awt.Color(153, 153, 153));
        jTextField42.setToolTipText("");
        jTextField42.setBorder(null);
        jTextField42.setEnabled(false);
        jTextField42.setFocusCycleRoot(true);
        jTextField42.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField42.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField42.setRequestFocusEnabled(false);

        jTextField43.setEditable(false);
        jTextField43.setBackground(new java.awt.Color(153, 153, 153));
        jTextField43.setToolTipText("");
        jTextField43.setBorder(null);
        jTextField43.setEnabled(false);
        jTextField43.setFocusCycleRoot(true);
        jTextField43.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField43.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField43.setRequestFocusEnabled(false);

        jTextField44.setEditable(false);
        jTextField44.setBackground(new java.awt.Color(153, 153, 153));
        jTextField44.setToolTipText("");
        jTextField44.setBorder(null);
        jTextField44.setEnabled(false);
        jTextField44.setFocusCycleRoot(true);
        jTextField44.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField44.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField44.setRequestFocusEnabled(false);

        jTextField45.setEditable(false);
        jTextField45.setBackground(new java.awt.Color(153, 153, 153));
        jTextField45.setToolTipText("");
        jTextField45.setBorder(null);
        jTextField45.setEnabled(false);
        jTextField45.setFocusCycleRoot(true);
        jTextField45.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField45.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField45.setRequestFocusEnabled(false);

        jTextField46.setEditable(false);
        jTextField46.setBackground(new java.awt.Color(153, 153, 153));
        jTextField46.setToolTipText("");
        jTextField46.setBorder(null);
        jTextField46.setEnabled(false);
        jTextField46.setFocusCycleRoot(true);
        jTextField46.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField46.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField46.setRequestFocusEnabled(false);

        jTextField47.setEditable(false);
        jTextField47.setBackground(new java.awt.Color(153, 153, 153));
        jTextField47.setToolTipText("");
        jTextField47.setBorder(null);
        jTextField47.setEnabled(false);
        jTextField47.setFocusCycleRoot(true);
        jTextField47.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField47.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField47.setRequestFocusEnabled(false);

        jTextField48.setEditable(false);
        jTextField48.setBackground(new java.awt.Color(153, 153, 153));
        jTextField48.setToolTipText("");
        jTextField48.setBorder(null);
        jTextField48.setEnabled(false);
        jTextField48.setFocusCycleRoot(true);
        jTextField48.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField48.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField48.setRequestFocusEnabled(false);

        jTextField49.setEditable(false);
        jTextField49.setBackground(new java.awt.Color(153, 153, 153));
        jTextField49.setToolTipText("");
        jTextField49.setBorder(null);
        jTextField49.setEnabled(false);
        jTextField49.setFocusCycleRoot(true);
        jTextField49.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField49.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField49.setRequestFocusEnabled(false);

        jTextField50.setEditable(false);
        jTextField50.setBackground(new java.awt.Color(153, 153, 153));
        jTextField50.setToolTipText("");
        jTextField50.setBorder(null);
        jTextField50.setEnabled(false);
        jTextField50.setFocusCycleRoot(true);
        jTextField50.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField50.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField50.setRequestFocusEnabled(false);

        jTextField51.setEditable(false);
        jTextField51.setBackground(new java.awt.Color(153, 153, 153));
        jTextField51.setToolTipText("");
        jTextField51.setBorder(null);
        jTextField51.setEnabled(false);
        jTextField51.setFocusCycleRoot(true);
        jTextField51.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField51.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField51.setRequestFocusEnabled(false);

        jTextField52.setEditable(false);
        jTextField52.setBackground(new java.awt.Color(153, 153, 153));
        jTextField52.setToolTipText("");
        jTextField52.setBorder(null);
        jTextField52.setEnabled(false);
        jTextField52.setFocusCycleRoot(true);
        jTextField52.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField52.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField52.setRequestFocusEnabled(false);

        jTextField53.setEditable(false);
        jTextField53.setBackground(new java.awt.Color(153, 153, 153));
        jTextField53.setToolTipText("");
        jTextField53.setBorder(null);
        jTextField53.setEnabled(false);
        jTextField53.setFocusCycleRoot(true);
        jTextField53.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField53.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField53.setRequestFocusEnabled(false);

        jTextField54.setEditable(false);
        jTextField54.setBackground(new java.awt.Color(153, 153, 153));
        jTextField54.setToolTipText("");
        jTextField54.setBorder(null);
        jTextField54.setEnabled(false);
        jTextField54.setFocusCycleRoot(true);
        jTextField54.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField54.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField54.setRequestFocusEnabled(false);

        jTextField55.setEditable(false);
        jTextField55.setBackground(new java.awt.Color(153, 153, 153));
        jTextField55.setToolTipText("");
        jTextField55.setBorder(null);
        jTextField55.setEnabled(false);
        jTextField55.setFocusCycleRoot(true);
        jTextField55.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField55.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField55.setRequestFocusEnabled(false);

        jTextField56.setEditable(false);
        jTextField56.setBackground(new java.awt.Color(153, 153, 153));
        jTextField56.setToolTipText("");
        jTextField56.setBorder(null);
        jTextField56.setEnabled(false);
        jTextField56.setFocusCycleRoot(true);
        jTextField56.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField56.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField56.setRequestFocusEnabled(false);

        jTextField57.setEditable(false);
        jTextField57.setBackground(new java.awt.Color(153, 153, 153));
        jTextField57.setToolTipText("");
        jTextField57.setBorder(null);
        jTextField57.setEnabled(false);
        jTextField57.setFocusCycleRoot(true);
        jTextField57.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField57.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField57.setRequestFocusEnabled(false);

        jTextField58.setEditable(false);
        jTextField58.setBackground(new java.awt.Color(153, 153, 153));
        jTextField58.setToolTipText("");
        jTextField58.setBorder(null);
        jTextField58.setEnabled(false);
        jTextField58.setFocusCycleRoot(true);
        jTextField58.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField58.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField58.setRequestFocusEnabled(false);

        jTextField59.setEditable(false);
        jTextField59.setBackground(new java.awt.Color(153, 153, 153));
        jTextField59.setToolTipText("");
        jTextField59.setBorder(null);
        jTextField59.setEnabled(false);
        jTextField59.setFocusCycleRoot(true);
        jTextField59.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField59.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField59.setRequestFocusEnabled(false);

        jTextField60.setEditable(false);
        jTextField60.setBackground(new java.awt.Color(153, 153, 153));
        jTextField60.setToolTipText("");
        jTextField60.setBorder(null);
        jTextField60.setEnabled(false);
        jTextField60.setFocusCycleRoot(true);
        jTextField60.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField60.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField60.setRequestFocusEnabled(false);

        jTextField61.setEditable(false);
        jTextField61.setBackground(new java.awt.Color(153, 153, 153));
        jTextField61.setToolTipText("");
        jTextField61.setBorder(null);
        jTextField61.setEnabled(false);
        jTextField61.setFocusCycleRoot(true);
        jTextField61.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField61.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField61.setRequestFocusEnabled(false);

        jTextField62.setEditable(false);
        jTextField62.setBackground(new java.awt.Color(153, 153, 153));
        jTextField62.setToolTipText("");
        jTextField62.setBorder(null);
        jTextField62.setEnabled(false);
        jTextField62.setFocusCycleRoot(true);
        jTextField62.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField62.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField62.setRequestFocusEnabled(false);

        jTextField63.setEditable(false);
        jTextField63.setBackground(new java.awt.Color(153, 153, 153));
        jTextField63.setToolTipText("");
        jTextField63.setBorder(null);
        jTextField63.setEnabled(false);
        jTextField63.setFocusCycleRoot(true);
        jTextField63.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField63.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField63.setRequestFocusEnabled(false);

        jTextField64.setEditable(false);
        jTextField64.setBackground(new java.awt.Color(153, 153, 153));
        jTextField64.setToolTipText("");
        jTextField64.setBorder(null);
        jTextField64.setEnabled(false);
        jTextField64.setFocusCycleRoot(true);
        jTextField64.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField64.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField64.setRequestFocusEnabled(false);

        jTextField65.setEditable(false);
        jTextField65.setBackground(new java.awt.Color(153, 153, 153));
        jTextField65.setToolTipText("");
        jTextField65.setBorder(null);
        jTextField65.setEnabled(false);
        jTextField65.setFocusCycleRoot(true);
        jTextField65.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField65.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField65.setRequestFocusEnabled(false);

        jTextField66.setEditable(false);
        jTextField66.setBackground(new java.awt.Color(153, 153, 153));
        jTextField66.setToolTipText("");
        jTextField66.setBorder(null);
        jTextField66.setEnabled(false);
        jTextField66.setFocusCycleRoot(true);
        jTextField66.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField66.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField66.setRequestFocusEnabled(false);

        jTextField67.setEditable(false);
        jTextField67.setBackground(new java.awt.Color(153, 153, 153));
        jTextField67.setToolTipText("");
        jTextField67.setBorder(null);
        jTextField67.setEnabled(false);
        jTextField67.setFocusCycleRoot(true);
        jTextField67.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField67.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField67.setRequestFocusEnabled(false);

        jTextField68.setEditable(false);
        jTextField68.setBackground(new java.awt.Color(153, 153, 153));
        jTextField68.setToolTipText("");
        jTextField68.setBorder(null);
        jTextField68.setEnabled(false);
        jTextField68.setFocusCycleRoot(true);
        jTextField68.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField68.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField68.setRequestFocusEnabled(false);

        jTextField69.setEditable(false);
        jTextField69.setBackground(new java.awt.Color(153, 153, 153));
        jTextField69.setToolTipText("");
        jTextField69.setBorder(null);
        jTextField69.setEnabled(false);
        jTextField69.setFocusCycleRoot(true);
        jTextField69.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField69.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField69.setRequestFocusEnabled(false);

        jTextField70.setEditable(false);
        jTextField70.setBackground(new java.awt.Color(153, 153, 153));
        jTextField70.setToolTipText("");
        jTextField70.setBorder(null);
        jTextField70.setEnabled(false);
        jTextField70.setFocusCycleRoot(true);
        jTextField70.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField70.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField70.setRequestFocusEnabled(false);

        jTextField71.setEditable(false);
        jTextField71.setBackground(new java.awt.Color(153, 153, 153));
        jTextField71.setToolTipText("");
        jTextField71.setBorder(null);
        jTextField71.setEnabled(false);
        jTextField71.setFocusCycleRoot(true);
        jTextField71.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField71.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField71.setRequestFocusEnabled(false);

        jTextField72.setEditable(false);
        jTextField72.setBackground(new java.awt.Color(153, 153, 153));
        jTextField72.setToolTipText("");
        jTextField72.setBorder(null);
        jTextField72.setEnabled(false);
        jTextField72.setFocusCycleRoot(true);
        jTextField72.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField72.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField72.setRequestFocusEnabled(false);

        jTextField73.setEditable(false);
        jTextField73.setBackground(new java.awt.Color(153, 153, 153));
        jTextField73.setToolTipText("");
        jTextField73.setBorder(null);
        jTextField73.setEnabled(false);
        jTextField73.setFocusCycleRoot(true);
        jTextField73.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField73.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField73.setRequestFocusEnabled(false);

        jTextField74.setEditable(false);
        jTextField74.setBackground(new java.awt.Color(153, 153, 153));
        jTextField74.setToolTipText("");
        jTextField74.setBorder(null);
        jTextField74.setEnabled(false);
        jTextField74.setFocusCycleRoot(true);
        jTextField74.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField74.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField74.setRequestFocusEnabled(false);

        jTextField75.setEditable(false);
        jTextField75.setBackground(new java.awt.Color(153, 153, 153));
        jTextField75.setToolTipText("");
        jTextField75.setBorder(null);
        jTextField75.setEnabled(false);
        jTextField75.setFocusCycleRoot(true);
        jTextField75.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField75.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField75.setRequestFocusEnabled(false);

        jTextField76.setEditable(false);
        jTextField76.setBackground(new java.awt.Color(153, 153, 153));
        jTextField76.setToolTipText("");
        jTextField76.setBorder(null);
        jTextField76.setEnabled(false);
        jTextField76.setFocusCycleRoot(true);
        jTextField76.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField76.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField76.setRequestFocusEnabled(false);

        jTextField77.setEditable(false);
        jTextField77.setBackground(new java.awt.Color(153, 153, 153));
        jTextField77.setToolTipText("");
        jTextField77.setBorder(null);
        jTextField77.setEnabled(false);
        jTextField77.setFocusCycleRoot(true);
        jTextField77.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField77.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField77.setRequestFocusEnabled(false);

        jTextField78.setEditable(false);
        jTextField78.setBackground(new java.awt.Color(153, 153, 153));
        jTextField78.setToolTipText("");
        jTextField78.setBorder(null);
        jTextField78.setEnabled(false);
        jTextField78.setFocusCycleRoot(true);
        jTextField78.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField78.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField78.setRequestFocusEnabled(false);

        jTextField79.setEditable(false);
        jTextField79.setBackground(new java.awt.Color(153, 153, 153));
        jTextField79.setToolTipText("");
        jTextField79.setBorder(null);
        jTextField79.setEnabled(false);
        jTextField79.setFocusCycleRoot(true);
        jTextField79.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField79.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField79.setRequestFocusEnabled(false);

        jTextField80.setEditable(false);
        jTextField80.setBackground(new java.awt.Color(153, 153, 153));
        jTextField80.setToolTipText("");
        jTextField80.setBorder(null);
        jTextField80.setEnabled(false);
        jTextField80.setFocusCycleRoot(true);
        jTextField80.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField80.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField80.setRequestFocusEnabled(false);

        jTextField81.setEditable(false);
        jTextField81.setBackground(new java.awt.Color(153, 153, 153));
        jTextField81.setToolTipText("");
        jTextField81.setBorder(null);
        jTextField81.setEnabled(false);
        jTextField81.setFocusCycleRoot(true);
        jTextField81.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField81.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField81.setRequestFocusEnabled(false);

        jTextField82.setEditable(false);
        jTextField82.setBackground(new java.awt.Color(153, 153, 153));
        jTextField82.setToolTipText("");
        jTextField82.setBorder(null);
        jTextField82.setEnabled(false);
        jTextField82.setFocusCycleRoot(true);
        jTextField82.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField82.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField82.setRequestFocusEnabled(false);

        jTextField83.setEditable(false);
        jTextField83.setBackground(new java.awt.Color(153, 153, 153));
        jTextField83.setToolTipText("");
        jTextField83.setBorder(null);
        jTextField83.setEnabled(false);
        jTextField83.setFocusCycleRoot(true);
        jTextField83.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField83.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField83.setRequestFocusEnabled(false);

        jTextField84.setEditable(false);
        jTextField84.setBackground(new java.awt.Color(153, 153, 153));
        jTextField84.setToolTipText("");
        jTextField84.setBorder(null);
        jTextField84.setEnabled(false);
        jTextField84.setFocusCycleRoot(true);
        jTextField84.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField84.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField84.setRequestFocusEnabled(false);

        jTextField85.setEditable(false);
        jTextField85.setBackground(new java.awt.Color(153, 153, 153));
        jTextField85.setToolTipText("");
        jTextField85.setBorder(null);
        jTextField85.setEnabled(false);
        jTextField85.setFocusCycleRoot(true);
        jTextField85.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField85.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField85.setRequestFocusEnabled(false);

        jTextField86.setEditable(false);
        jTextField86.setBackground(new java.awt.Color(153, 153, 153));
        jTextField86.setToolTipText("");
        jTextField86.setBorder(null);
        jTextField86.setEnabled(false);
        jTextField86.setFocusCycleRoot(true);
        jTextField86.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField86.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField86.setRequestFocusEnabled(false);

        jTextField87.setEditable(false);
        jTextField87.setBackground(new java.awt.Color(153, 153, 153));
        jTextField87.setToolTipText("");
        jTextField87.setBorder(null);
        jTextField87.setEnabled(false);
        jTextField87.setFocusCycleRoot(true);
        jTextField87.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField87.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField87.setRequestFocusEnabled(false);

        jTextField88.setEditable(false);
        jTextField88.setBackground(new java.awt.Color(153, 153, 153));
        jTextField88.setToolTipText("");
        jTextField88.setBorder(null);
        jTextField88.setEnabled(false);
        jTextField88.setFocusCycleRoot(true);
        jTextField88.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField88.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField88.setRequestFocusEnabled(false);

        jTextField89.setEditable(false);
        jTextField89.setBackground(new java.awt.Color(153, 153, 153));
        jTextField89.setToolTipText("");
        jTextField89.setBorder(null);
        jTextField89.setEnabled(false);
        jTextField89.setFocusCycleRoot(true);
        jTextField89.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField89.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField89.setRequestFocusEnabled(false);

        jTextField90.setEditable(false);
        jTextField90.setBackground(new java.awt.Color(153, 153, 153));
        jTextField90.setToolTipText("");
        jTextField90.setBorder(null);
        jTextField90.setEnabled(false);
        jTextField90.setFocusCycleRoot(true);
        jTextField90.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField90.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField90.setRequestFocusEnabled(false);

        jTextField91.setEditable(false);
        jTextField91.setBackground(new java.awt.Color(153, 153, 153));
        jTextField91.setToolTipText("");
        jTextField91.setBorder(null);
        jTextField91.setEnabled(false);
        jTextField91.setFocusCycleRoot(true);
        jTextField91.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField91.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField91.setRequestFocusEnabled(false);

        jTextField92.setEditable(false);
        jTextField92.setBackground(new java.awt.Color(153, 153, 153));
        jTextField92.setToolTipText("");
        jTextField92.setBorder(null);
        jTextField92.setEnabled(false);
        jTextField92.setFocusCycleRoot(true);
        jTextField92.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField92.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField92.setRequestFocusEnabled(false);

        jTextField93.setEditable(false);
        jTextField93.setBackground(new java.awt.Color(153, 153, 153));
        jTextField93.setToolTipText("");
        jTextField93.setBorder(null);
        jTextField93.setEnabled(false);
        jTextField93.setFocusCycleRoot(true);
        jTextField93.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField93.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField93.setRequestFocusEnabled(false);

        jTextField94.setEditable(false);
        jTextField94.setBackground(new java.awt.Color(153, 153, 153));
        jTextField94.setToolTipText("");
        jTextField94.setBorder(null);
        jTextField94.setEnabled(false);
        jTextField94.setFocusCycleRoot(true);
        jTextField94.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField94.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField94.setRequestFocusEnabled(false);

        jTextField95.setEditable(false);
        jTextField95.setBackground(new java.awt.Color(153, 153, 153));
        jTextField95.setToolTipText("");
        jTextField95.setBorder(null);
        jTextField95.setEnabled(false);
        jTextField95.setFocusCycleRoot(true);
        jTextField95.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField95.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField95.setRequestFocusEnabled(false);

        jTextField96.setEditable(false);
        jTextField96.setBackground(new java.awt.Color(153, 153, 153));
        jTextField96.setToolTipText("");
        jTextField96.setBorder(null);
        jTextField96.setEnabled(false);
        jTextField96.setFocusCycleRoot(true);
        jTextField96.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField96.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField96.setRequestFocusEnabled(false);

        jTextField97.setEditable(false);
        jTextField97.setBackground(new java.awt.Color(153, 153, 153));
        jTextField97.setToolTipText("");
        jTextField97.setBorder(null);
        jTextField97.setEnabled(false);
        jTextField97.setFocusCycleRoot(true);
        jTextField97.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField97.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField97.setRequestFocusEnabled(false);

        jTextField98.setEditable(false);
        jTextField98.setBackground(new java.awt.Color(153, 153, 153));
        jTextField98.setToolTipText("");
        jTextField98.setBorder(null);
        jTextField98.setEnabled(false);
        jTextField98.setFocusCycleRoot(true);
        jTextField98.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField98.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField98.setRequestFocusEnabled(false);

        jTextField99.setEditable(false);
        jTextField99.setBackground(new java.awt.Color(153, 153, 153));
        jTextField99.setToolTipText("");
        jTextField99.setBorder(null);
        jTextField99.setEnabled(false);
        jTextField99.setFocusCycleRoot(true);
        jTextField99.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField99.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField99.setRequestFocusEnabled(false);

        jTextField100.setEditable(false);
        jTextField100.setBackground(new java.awt.Color(153, 153, 153));
        jTextField100.setToolTipText("");
        jTextField100.setBorder(null);
        jTextField100.setEnabled(false);
        jTextField100.setFocusCycleRoot(true);
        jTextField100.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField100.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField100.setRequestFocusEnabled(false);

        jTextField101.setEditable(false);
        jTextField101.setBackground(new java.awt.Color(153, 153, 153));
        jTextField101.setToolTipText("");
        jTextField101.setBorder(null);
        jTextField101.setEnabled(false);
        jTextField101.setFocusCycleRoot(true);
        jTextField101.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField101.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField101.setRequestFocusEnabled(false);

        jTextField102.setEditable(false);
        jTextField102.setBackground(new java.awt.Color(153, 153, 153));
        jTextField102.setToolTipText("");
        jTextField102.setBorder(null);
        jTextField102.setEnabled(false);
        jTextField102.setFocusCycleRoot(true);
        jTextField102.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField102.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField102.setRequestFocusEnabled(false);

        jTextField103.setEditable(false);
        jTextField103.setBackground(new java.awt.Color(153, 153, 153));
        jTextField103.setToolTipText("");
        jTextField103.setBorder(null);
        jTextField103.setEnabled(false);
        jTextField103.setFocusCycleRoot(true);
        jTextField103.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField103.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField103.setRequestFocusEnabled(false);

        jTextField104.setEditable(false);
        jTextField104.setBackground(new java.awt.Color(153, 153, 153));
        jTextField104.setToolTipText("");
        jTextField104.setBorder(null);
        jTextField104.setEnabled(false);
        jTextField104.setFocusCycleRoot(true);
        jTextField104.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField104.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField104.setRequestFocusEnabled(false);

        jTextField105.setEditable(false);
        jTextField105.setBackground(new java.awt.Color(153, 153, 153));
        jTextField105.setToolTipText("");
        jTextField105.setBorder(null);
        jTextField105.setEnabled(false);
        jTextField105.setFocusCycleRoot(true);
        jTextField105.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField105.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField105.setRequestFocusEnabled(false);

        jTextField106.setEditable(false);
        jTextField106.setBackground(new java.awt.Color(153, 153, 153));
        jTextField106.setToolTipText("");
        jTextField106.setBorder(null);
        jTextField106.setEnabled(false);
        jTextField106.setFocusCycleRoot(true);
        jTextField106.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField106.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField106.setRequestFocusEnabled(false);

        jTextField107.setEditable(false);
        jTextField107.setBackground(new java.awt.Color(153, 153, 153));
        jTextField107.setToolTipText("");
        jTextField107.setBorder(null);
        jTextField107.setEnabled(false);
        jTextField107.setFocusCycleRoot(true);
        jTextField107.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField107.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField107.setRequestFocusEnabled(false);

        jTextField108.setEditable(false);
        jTextField108.setBackground(new java.awt.Color(153, 153, 153));
        jTextField108.setToolTipText("");
        jTextField108.setBorder(null);
        jTextField108.setEnabled(false);
        jTextField108.setFocusCycleRoot(true);
        jTextField108.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField108.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField108.setRequestFocusEnabled(false);

        jTextField109.setEditable(false);
        jTextField109.setBackground(new java.awt.Color(153, 153, 153));
        jTextField109.setToolTipText("");
        jTextField109.setBorder(null);
        jTextField109.setEnabled(false);
        jTextField109.setFocusCycleRoot(true);
        jTextField109.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField109.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField109.setRequestFocusEnabled(false);

        jTextField110.setEditable(false);
        jTextField110.setBackground(new java.awt.Color(153, 153, 153));
        jTextField110.setToolTipText("");
        jTextField110.setBorder(null);
        jTextField110.setEnabled(false);
        jTextField110.setFocusCycleRoot(true);
        jTextField110.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField110.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField110.setRequestFocusEnabled(false);

        jTextField111.setEditable(false);
        jTextField111.setBackground(new java.awt.Color(153, 153, 153));
        jTextField111.setToolTipText("");
        jTextField111.setBorder(null);
        jTextField111.setEnabled(false);
        jTextField111.setFocusCycleRoot(true);
        jTextField111.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField111.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField111.setRequestFocusEnabled(false);

        jTextField112.setEditable(false);
        jTextField112.setBackground(new java.awt.Color(153, 153, 153));
        jTextField112.setToolTipText("");
        jTextField112.setBorder(null);
        jTextField112.setEnabled(false);
        jTextField112.setFocusCycleRoot(true);
        jTextField112.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField112.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField112.setRequestFocusEnabled(false);

        jTextField113.setEditable(false);
        jTextField113.setBackground(new java.awt.Color(153, 153, 153));
        jTextField113.setToolTipText("");
        jTextField113.setBorder(null);
        jTextField113.setEnabled(false);
        jTextField113.setFocusCycleRoot(true);
        jTextField113.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField113.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField113.setRequestFocusEnabled(false);

        jTextField114.setEditable(false);
        jTextField114.setBackground(new java.awt.Color(153, 153, 153));
        jTextField114.setToolTipText("");
        jTextField114.setBorder(null);
        jTextField114.setEnabled(false);
        jTextField114.setFocusCycleRoot(true);
        jTextField114.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField114.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField114.setRequestFocusEnabled(false);

        jTextField115.setEditable(false);
        jTextField115.setBackground(new java.awt.Color(153, 153, 153));
        jTextField115.setToolTipText("");
        jTextField115.setBorder(null);
        jTextField115.setEnabled(false);
        jTextField115.setFocusCycleRoot(true);
        jTextField115.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField115.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField115.setRequestFocusEnabled(false);

        jTextField116.setEditable(false);
        jTextField116.setBackground(new java.awt.Color(153, 153, 153));
        jTextField116.setToolTipText("");
        jTextField116.setBorder(null);
        jTextField116.setEnabled(false);
        jTextField116.setFocusCycleRoot(true);
        jTextField116.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField116.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField116.setRequestFocusEnabled(false);

        jTextField117.setEditable(false);
        jTextField117.setBackground(new java.awt.Color(153, 153, 153));
        jTextField117.setToolTipText("");
        jTextField117.setBorder(null);
        jTextField117.setEnabled(false);
        jTextField117.setFocusCycleRoot(true);
        jTextField117.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField117.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField117.setRequestFocusEnabled(false);

        jTextField118.setEditable(false);
        jTextField118.setBackground(new java.awt.Color(153, 153, 153));
        jTextField118.setToolTipText("");
        jTextField118.setBorder(null);
        jTextField118.setEnabled(false);
        jTextField118.setFocusCycleRoot(true);
        jTextField118.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField118.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField118.setRequestFocusEnabled(false);

        jTextField119.setEditable(false);
        jTextField119.setBackground(new java.awt.Color(153, 153, 153));
        jTextField119.setToolTipText("");
        jTextField119.setBorder(null);
        jTextField119.setEnabled(false);
        jTextField119.setFocusCycleRoot(true);
        jTextField119.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField119.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField119.setRequestFocusEnabled(false);

        jTextField120.setEditable(false);
        jTextField120.setBackground(new java.awt.Color(153, 153, 153));
        jTextField120.setToolTipText("");
        jTextField120.setBorder(null);
        jTextField120.setEnabled(false);
        jTextField120.setFocusCycleRoot(true);
        jTextField120.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField120.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField120.setRequestFocusEnabled(false);

        jTextField121.setEditable(false);
        jTextField121.setBackground(new java.awt.Color(153, 153, 153));
        jTextField121.setToolTipText("");
        jTextField121.setBorder(null);
        jTextField121.setEnabled(false);
        jTextField121.setFocusCycleRoot(true);
        jTextField121.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField121.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField121.setRequestFocusEnabled(false);

        jTextField122.setEditable(false);
        jTextField122.setBackground(new java.awt.Color(153, 153, 153));
        jTextField122.setToolTipText("");
        jTextField122.setBorder(null);
        jTextField122.setEnabled(false);
        jTextField122.setFocusCycleRoot(true);
        jTextField122.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField122.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField122.setRequestFocusEnabled(false);

        jTextField123.setEditable(false);
        jTextField123.setBackground(new java.awt.Color(153, 153, 153));
        jTextField123.setToolTipText("");
        jTextField123.setBorder(null);
        jTextField123.setEnabled(false);
        jTextField123.setFocusCycleRoot(true);
        jTextField123.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField123.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField123.setRequestFocusEnabled(false);

        jTextField124.setEditable(false);
        jTextField124.setBackground(new java.awt.Color(153, 153, 153));
        jTextField124.setToolTipText("");
        jTextField124.setBorder(null);
        jTextField124.setEnabled(false);
        jTextField124.setFocusCycleRoot(true);
        jTextField124.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField124.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField124.setRequestFocusEnabled(false);

        jTextField125.setEditable(false);
        jTextField125.setBackground(new java.awt.Color(153, 153, 153));
        jTextField125.setToolTipText("");
        jTextField125.setBorder(null);
        jTextField125.setEnabled(false);
        jTextField125.setFocusCycleRoot(true);
        jTextField125.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField125.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField125.setRequestFocusEnabled(false);

        jTextField126.setEditable(false);
        jTextField126.setBackground(new java.awt.Color(153, 153, 153));
        jTextField126.setToolTipText("");
        jTextField126.setBorder(null);
        jTextField126.setEnabled(false);
        jTextField126.setFocusCycleRoot(true);
        jTextField126.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField126.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField126.setRequestFocusEnabled(false);

        jTextField127.setEditable(false);
        jTextField127.setBackground(new java.awt.Color(153, 153, 153));
        jTextField127.setToolTipText("");
        jTextField127.setBorder(null);
        jTextField127.setEnabled(false);
        jTextField127.setFocusCycleRoot(true);
        jTextField127.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField127.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField127.setRequestFocusEnabled(false);

        jTextField128.setEditable(false);
        jTextField128.setBackground(new java.awt.Color(153, 153, 153));
        jTextField128.setToolTipText("");
        jTextField128.setBorder(null);
        jTextField128.setEnabled(false);
        jTextField128.setFocusCycleRoot(true);
        jTextField128.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField128.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField128.setRequestFocusEnabled(false);

        jTextField129.setEditable(false);
        jTextField129.setBackground(new java.awt.Color(153, 153, 153));
        jTextField129.setToolTipText("");
        jTextField129.setBorder(null);
        jTextField129.setEnabled(false);
        jTextField129.setFocusCycleRoot(true);
        jTextField129.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField129.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField129.setRequestFocusEnabled(false);

        jTextField130.setEditable(false);
        jTextField130.setBackground(new java.awt.Color(153, 153, 153));
        jTextField130.setToolTipText("");
        jTextField130.setBorder(null);
        jTextField130.setEnabled(false);
        jTextField130.setFocusCycleRoot(true);
        jTextField130.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField130.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField130.setRequestFocusEnabled(false);

        jTextField131.setEditable(false);
        jTextField131.setBackground(new java.awt.Color(153, 153, 153));
        jTextField131.setToolTipText("");
        jTextField131.setBorder(null);
        jTextField131.setEnabled(false);
        jTextField131.setFocusCycleRoot(true);
        jTextField131.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField131.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField131.setRequestFocusEnabled(false);

        jTextField132.setEditable(false);
        jTextField132.setBackground(new java.awt.Color(153, 153, 153));
        jTextField132.setToolTipText("");
        jTextField132.setBorder(null);
        jTextField132.setEnabled(false);
        jTextField132.setFocusCycleRoot(true);
        jTextField132.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField132.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField132.setRequestFocusEnabled(false);

        jTextField133.setEditable(false);
        jTextField133.setBackground(new java.awt.Color(153, 153, 153));
        jTextField133.setToolTipText("");
        jTextField133.setBorder(null);
        jTextField133.setEnabled(false);
        jTextField133.setFocusCycleRoot(true);
        jTextField133.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField133.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField133.setRequestFocusEnabled(false);

        jTextField134.setEditable(false);
        jTextField134.setBackground(new java.awt.Color(153, 153, 153));
        jTextField134.setToolTipText("");
        jTextField134.setBorder(null);
        jTextField134.setEnabled(false);
        jTextField134.setFocusCycleRoot(true);
        jTextField134.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField134.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField134.setRequestFocusEnabled(false);

        jTextField135.setEditable(false);
        jTextField135.setBackground(new java.awt.Color(153, 153, 153));
        jTextField135.setToolTipText("");
        jTextField135.setBorder(null);
        jTextField135.setEnabled(false);
        jTextField135.setFocusCycleRoot(true);
        jTextField135.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField135.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField135.setRequestFocusEnabled(false);

        jTextField136.setEditable(false);
        jTextField136.setBackground(new java.awt.Color(153, 153, 153));
        jTextField136.setToolTipText("");
        jTextField136.setBorder(null);
        jTextField136.setEnabled(false);
        jTextField136.setFocusCycleRoot(true);
        jTextField136.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField136.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField136.setRequestFocusEnabled(false);

        jTextField137.setEditable(false);
        jTextField137.setBackground(new java.awt.Color(153, 153, 153));
        jTextField137.setToolTipText("");
        jTextField137.setBorder(null);
        jTextField137.setEnabled(false);
        jTextField137.setFocusCycleRoot(true);
        jTextField137.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField137.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField137.setRequestFocusEnabled(false);

        jTextField138.setEditable(false);
        jTextField138.setBackground(new java.awt.Color(153, 153, 153));
        jTextField138.setToolTipText("");
        jTextField138.setBorder(null);
        jTextField138.setEnabled(false);
        jTextField138.setFocusCycleRoot(true);
        jTextField138.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField138.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField138.setRequestFocusEnabled(false);

        jTextField139.setEditable(false);
        jTextField139.setBackground(new java.awt.Color(153, 153, 153));
        jTextField139.setToolTipText("");
        jTextField139.setBorder(null);
        jTextField139.setEnabled(false);
        jTextField139.setFocusCycleRoot(true);
        jTextField139.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField139.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField139.setRequestFocusEnabled(false);

        jTextField140.setEditable(false);
        jTextField140.setBackground(new java.awt.Color(153, 153, 153));
        jTextField140.setToolTipText("");
        jTextField140.setBorder(null);
        jTextField140.setEnabled(false);
        jTextField140.setFocusCycleRoot(true);
        jTextField140.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField140.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField140.setRequestFocusEnabled(false);

        jTextField141.setEditable(false);
        jTextField141.setBackground(new java.awt.Color(153, 153, 153));
        jTextField141.setToolTipText("");
        jTextField141.setBorder(null);
        jTextField141.setEnabled(false);
        jTextField141.setFocusCycleRoot(true);
        jTextField141.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField141.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField141.setRequestFocusEnabled(false);

        jTextField142.setEditable(false);
        jTextField142.setBackground(new java.awt.Color(153, 153, 153));
        jTextField142.setToolTipText("");
        jTextField142.setBorder(null);
        jTextField142.setEnabled(false);
        jTextField142.setFocusCycleRoot(true);
        jTextField142.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField142.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField142.setRequestFocusEnabled(false);

        jTextField143.setEditable(false);
        jTextField143.setBackground(new java.awt.Color(153, 153, 153));
        jTextField143.setToolTipText("");
        jTextField143.setBorder(null);
        jTextField143.setEnabled(false);
        jTextField143.setFocusCycleRoot(true);
        jTextField143.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField143.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField143.setRequestFocusEnabled(false);

        jTextField144.setEditable(false);
        jTextField144.setBackground(new java.awt.Color(153, 153, 153));
        jTextField144.setToolTipText("");
        jTextField144.setBorder(null);
        jTextField144.setEnabled(false);
        jTextField144.setFocusCycleRoot(true);
        jTextField144.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField144.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField144.setRequestFocusEnabled(false);

        jTextField145.setEditable(false);
        jTextField145.setBackground(new java.awt.Color(153, 153, 153));
        jTextField145.setToolTipText("");
        jTextField145.setBorder(null);
        jTextField145.setEnabled(false);
        jTextField145.setFocusCycleRoot(true);
        jTextField145.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField145.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField145.setRequestFocusEnabled(false);

        jTextField146.setEditable(false);
        jTextField146.setBackground(new java.awt.Color(153, 153, 153));
        jTextField146.setToolTipText("");
        jTextField146.setBorder(null);
        jTextField146.setEnabled(false);
        jTextField146.setFocusCycleRoot(true);
        jTextField146.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField146.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField146.setRequestFocusEnabled(false);

        jTextField147.setEditable(false);
        jTextField147.setBackground(new java.awt.Color(153, 153, 153));
        jTextField147.setToolTipText("");
        jTextField147.setBorder(null);
        jTextField147.setEnabled(false);
        jTextField147.setFocusCycleRoot(true);
        jTextField147.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField147.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField147.setRequestFocusEnabled(false);

        jTextField148.setEditable(false);
        jTextField148.setBackground(new java.awt.Color(153, 153, 153));
        jTextField148.setToolTipText("");
        jTextField148.setBorder(null);
        jTextField148.setEnabled(false);
        jTextField148.setFocusCycleRoot(true);
        jTextField148.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField148.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField148.setRequestFocusEnabled(false);

        jTextField149.setEditable(false);
        jTextField149.setBackground(new java.awt.Color(153, 153, 153));
        jTextField149.setToolTipText("");
        jTextField149.setBorder(null);
        jTextField149.setEnabled(false);
        jTextField149.setFocusCycleRoot(true);
        jTextField149.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField149.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField149.setRequestFocusEnabled(false);

        jTextField150.setEditable(false);
        jTextField150.setBackground(new java.awt.Color(153, 153, 153));
        jTextField150.setToolTipText("");
        jTextField150.setBorder(null);
        jTextField150.setEnabled(false);
        jTextField150.setFocusCycleRoot(true);
        jTextField150.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField150.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField150.setRequestFocusEnabled(false);

        jTextField151.setEditable(false);
        jTextField151.setBackground(new java.awt.Color(153, 153, 153));
        jTextField151.setToolTipText("");
        jTextField151.setBorder(null);
        jTextField151.setEnabled(false);
        jTextField151.setFocusCycleRoot(true);
        jTextField151.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField151.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField151.setRequestFocusEnabled(false);

        jTextField152.setEditable(false);
        jTextField152.setBackground(new java.awt.Color(153, 153, 153));
        jTextField152.setToolTipText("");
        jTextField152.setBorder(null);
        jTextField152.setEnabled(false);
        jTextField152.setFocusCycleRoot(true);
        jTextField152.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField152.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField152.setRequestFocusEnabled(false);

        jTextField153.setEditable(false);
        jTextField153.setBackground(new java.awt.Color(153, 153, 153));
        jTextField153.setToolTipText("");
        jTextField153.setBorder(null);
        jTextField153.setEnabled(false);
        jTextField153.setFocusCycleRoot(true);
        jTextField153.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField153.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField153.setRequestFocusEnabled(false);

        jTextField154.setEditable(false);
        jTextField154.setBackground(new java.awt.Color(153, 153, 153));
        jTextField154.setToolTipText("");
        jTextField154.setBorder(null);
        jTextField154.setEnabled(false);
        jTextField154.setFocusCycleRoot(true);
        jTextField154.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField154.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField154.setRequestFocusEnabled(false);

        jTextField155.setEditable(false);
        jTextField155.setBackground(new java.awt.Color(153, 153, 153));
        jTextField155.setToolTipText("");
        jTextField155.setBorder(null);
        jTextField155.setEnabled(false);
        jTextField155.setFocusCycleRoot(true);
        jTextField155.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField155.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField155.setRequestFocusEnabled(false);

        jTextField156.setEditable(false);
        jTextField156.setBackground(new java.awt.Color(153, 153, 153));
        jTextField156.setToolTipText("");
        jTextField156.setBorder(null);
        jTextField156.setEnabled(false);
        jTextField156.setFocusCycleRoot(true);
        jTextField156.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField156.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField156.setRequestFocusEnabled(false);

        jTextField157.setEditable(false);
        jTextField157.setBackground(new java.awt.Color(153, 153, 153));
        jTextField157.setToolTipText("");
        jTextField157.setBorder(null);
        jTextField157.setEnabled(false);
        jTextField157.setFocusCycleRoot(true);
        jTextField157.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField157.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField157.setRequestFocusEnabled(false);

        jTextField158.setEditable(false);
        jTextField158.setBackground(new java.awt.Color(153, 153, 153));
        jTextField158.setToolTipText("");
        jTextField158.setBorder(null);
        jTextField158.setEnabled(false);
        jTextField158.setFocusCycleRoot(true);
        jTextField158.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField158.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField158.setRequestFocusEnabled(false);

        jTextField159.setEditable(false);
        jTextField159.setBackground(new java.awt.Color(153, 153, 153));
        jTextField159.setToolTipText("");
        jTextField159.setBorder(null);
        jTextField159.setEnabled(false);
        jTextField159.setFocusCycleRoot(true);
        jTextField159.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField159.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField159.setRequestFocusEnabled(false);

        jTextField160.setEditable(false);
        jTextField160.setBackground(new java.awt.Color(153, 153, 153));
        jTextField160.setToolTipText("");
        jTextField160.setBorder(null);
        jTextField160.setEnabled(false);
        jTextField160.setFocusCycleRoot(true);
        jTextField160.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField160.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField160.setRequestFocusEnabled(false);

        jTextField161.setEditable(false);
        jTextField161.setBackground(new java.awt.Color(153, 153, 153));
        jTextField161.setToolTipText("");
        jTextField161.setBorder(null);
        jTextField161.setEnabled(false);
        jTextField161.setFocusCycleRoot(true);
        jTextField161.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField161.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField161.setRequestFocusEnabled(false);

        jTextField162.setEditable(false);
        jTextField162.setBackground(new java.awt.Color(153, 153, 153));
        jTextField162.setToolTipText("");
        jTextField162.setBorder(null);
        jTextField162.setEnabled(false);
        jTextField162.setFocusCycleRoot(true);
        jTextField162.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField162.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField162.setRequestFocusEnabled(false);

        jTextField163.setEditable(false);
        jTextField163.setBackground(new java.awt.Color(153, 153, 153));
        jTextField163.setToolTipText("");
        jTextField163.setBorder(null);
        jTextField163.setEnabled(false);
        jTextField163.setFocusCycleRoot(true);
        jTextField163.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField163.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField163.setRequestFocusEnabled(false);

        jTextField164.setEditable(false);
        jTextField164.setBackground(new java.awt.Color(153, 153, 153));
        jTextField164.setToolTipText("");
        jTextField164.setBorder(null);
        jTextField164.setEnabled(false);
        jTextField164.setFocusCycleRoot(true);
        jTextField164.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField164.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField164.setRequestFocusEnabled(false);

        jTextField165.setEditable(false);
        jTextField165.setBackground(new java.awt.Color(153, 153, 153));
        jTextField165.setToolTipText("");
        jTextField165.setBorder(null);
        jTextField165.setEnabled(false);
        jTextField165.setFocusCycleRoot(true);
        jTextField165.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField165.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField165.setRequestFocusEnabled(false);

        jTextField166.setEditable(false);
        jTextField166.setBackground(new java.awt.Color(153, 153, 153));
        jTextField166.setToolTipText("");
        jTextField166.setBorder(null);
        jTextField166.setEnabled(false);
        jTextField166.setFocusCycleRoot(true);
        jTextField166.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField166.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField166.setRequestFocusEnabled(false);

        jTextField167.setEditable(false);
        jTextField167.setBackground(new java.awt.Color(153, 153, 153));
        jTextField167.setToolTipText("");
        jTextField167.setBorder(null);
        jTextField167.setEnabled(false);
        jTextField167.setFocusCycleRoot(true);
        jTextField167.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField167.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField167.setRequestFocusEnabled(false);

        jTextField168.setEditable(false);
        jTextField168.setBackground(new java.awt.Color(153, 153, 153));
        jTextField168.setToolTipText("");
        jTextField168.setBorder(null);
        jTextField168.setEnabled(false);
        jTextField168.setFocusCycleRoot(true);
        jTextField168.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField168.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField168.setRequestFocusEnabled(false);

        jTextField169.setEditable(false);
        jTextField169.setBackground(new java.awt.Color(153, 153, 153));
        jTextField169.setToolTipText("");
        jTextField169.setBorder(null);
        jTextField169.setEnabled(false);
        jTextField169.setFocusCycleRoot(true);
        jTextField169.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField169.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField169.setRequestFocusEnabled(false);

        jTextField170.setEditable(false);
        jTextField170.setBackground(new java.awt.Color(153, 153, 153));
        jTextField170.setToolTipText("");
        jTextField170.setBorder(null);
        jTextField170.setEnabled(false);
        jTextField170.setFocusCycleRoot(true);
        jTextField170.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField170.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField170.setRequestFocusEnabled(false);

        jTextField171.setEditable(false);
        jTextField171.setBackground(new java.awt.Color(153, 153, 153));
        jTextField171.setToolTipText("");
        jTextField171.setBorder(null);
        jTextField171.setEnabled(false);
        jTextField171.setFocusCycleRoot(true);
        jTextField171.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField171.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField171.setRequestFocusEnabled(false);

        jTextField172.setEditable(false);
        jTextField172.setBackground(new java.awt.Color(153, 153, 153));
        jTextField172.setToolTipText("");
        jTextField172.setBorder(null);
        jTextField172.setEnabled(false);
        jTextField172.setFocusCycleRoot(true);
        jTextField172.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField172.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField172.setRequestFocusEnabled(false);

        jTextField173.setEditable(false);
        jTextField173.setBackground(new java.awt.Color(153, 153, 153));
        jTextField173.setToolTipText("");
        jTextField173.setBorder(null);
        jTextField173.setEnabled(false);
        jTextField173.setFocusCycleRoot(true);
        jTextField173.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField173.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField173.setRequestFocusEnabled(false);

        jTextField174.setEditable(false);
        jTextField174.setBackground(new java.awt.Color(153, 153, 153));
        jTextField174.setToolTipText("");
        jTextField174.setBorder(null);
        jTextField174.setEnabled(false);
        jTextField174.setFocusCycleRoot(true);
        jTextField174.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField174.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField174.setRequestFocusEnabled(false);

        jTextField175.setEditable(false);
        jTextField175.setBackground(new java.awt.Color(153, 153, 153));
        jTextField175.setToolTipText("");
        jTextField175.setBorder(null);
        jTextField175.setEnabled(false);
        jTextField175.setFocusCycleRoot(true);
        jTextField175.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField175.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField175.setRequestFocusEnabled(false);

        jTextField176.setEditable(false);
        jTextField176.setBackground(new java.awt.Color(153, 153, 153));
        jTextField176.setToolTipText("");
        jTextField176.setBorder(null);
        jTextField176.setEnabled(false);
        jTextField176.setFocusCycleRoot(true);
        jTextField176.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField176.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField176.setRequestFocusEnabled(false);

        jTextField177.setEditable(false);
        jTextField177.setBackground(new java.awt.Color(153, 153, 153));
        jTextField177.setToolTipText("");
        jTextField177.setBorder(null);
        jTextField177.setEnabled(false);
        jTextField177.setFocusCycleRoot(true);
        jTextField177.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField177.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField177.setRequestFocusEnabled(false);

        jTextField178.setEditable(false);
        jTextField178.setBackground(new java.awt.Color(153, 153, 153));
        jTextField178.setToolTipText("");
        jTextField178.setBorder(null);
        jTextField178.setEnabled(false);
        jTextField178.setFocusCycleRoot(true);
        jTextField178.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField178.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField178.setRequestFocusEnabled(false);

        jTextField179.setEditable(false);
        jTextField179.setBackground(new java.awt.Color(153, 153, 153));
        jTextField179.setToolTipText("");
        jTextField179.setBorder(null);
        jTextField179.setEnabled(false);
        jTextField179.setFocusCycleRoot(true);
        jTextField179.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField179.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField179.setRequestFocusEnabled(false);

        jTextField180.setEditable(false);
        jTextField180.setBackground(new java.awt.Color(153, 153, 153));
        jTextField180.setToolTipText("");
        jTextField180.setBorder(null);
        jTextField180.setEnabled(false);
        jTextField180.setFocusCycleRoot(true);
        jTextField180.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField180.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField180.setRequestFocusEnabled(false);

        jTextField181.setEditable(false);
        jTextField181.setBackground(new java.awt.Color(153, 153, 153));
        jTextField181.setToolTipText("");
        jTextField181.setBorder(null);
        jTextField181.setEnabled(false);
        jTextField181.setFocusCycleRoot(true);
        jTextField181.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField181.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField181.setRequestFocusEnabled(false);

        jTextField182.setEditable(false);
        jTextField182.setBackground(new java.awt.Color(153, 153, 153));
        jTextField182.setToolTipText("");
        jTextField182.setBorder(null);
        jTextField182.setEnabled(false);
        jTextField182.setFocusCycleRoot(true);
        jTextField182.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField182.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField182.setRequestFocusEnabled(false);

        jTextField183.setEditable(false);
        jTextField183.setBackground(new java.awt.Color(153, 153, 153));
        jTextField183.setToolTipText("");
        jTextField183.setBorder(null);
        jTextField183.setEnabled(false);
        jTextField183.setFocusCycleRoot(true);
        jTextField183.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField183.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField183.setRequestFocusEnabled(false);

        jTextField184.setEditable(false);
        jTextField184.setBackground(new java.awt.Color(153, 153, 153));
        jTextField184.setToolTipText("");
        jTextField184.setBorder(null);
        jTextField184.setEnabled(false);
        jTextField184.setFocusCycleRoot(true);
        jTextField184.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField184.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField184.setRequestFocusEnabled(false);

        jTextField185.setEditable(false);
        jTextField185.setBackground(new java.awt.Color(153, 153, 153));
        jTextField185.setToolTipText("");
        jTextField185.setBorder(null);
        jTextField185.setEnabled(false);
        jTextField185.setFocusCycleRoot(true);
        jTextField185.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField185.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField185.setRequestFocusEnabled(false);

        jTextField186.setEditable(false);
        jTextField186.setBackground(new java.awt.Color(153, 153, 153));
        jTextField186.setToolTipText("");
        jTextField186.setBorder(null);
        jTextField186.setEnabled(false);
        jTextField186.setFocusCycleRoot(true);
        jTextField186.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField186.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField186.setRequestFocusEnabled(false);

        jTextField187.setEditable(false);
        jTextField187.setBackground(new java.awt.Color(153, 153, 153));
        jTextField187.setToolTipText("");
        jTextField187.setBorder(null);
        jTextField187.setEnabled(false);
        jTextField187.setFocusCycleRoot(true);
        jTextField187.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField187.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField187.setRequestFocusEnabled(false);

        jTextField188.setEditable(false);
        jTextField188.setBackground(new java.awt.Color(153, 153, 153));
        jTextField188.setToolTipText("");
        jTextField188.setBorder(null);
        jTextField188.setEnabled(false);
        jTextField188.setFocusCycleRoot(true);
        jTextField188.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField188.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField188.setRequestFocusEnabled(false);

        jTextField189.setEditable(false);
        jTextField189.setBackground(new java.awt.Color(153, 153, 153));
        jTextField189.setToolTipText("");
        jTextField189.setBorder(null);
        jTextField189.setEnabled(false);
        jTextField189.setFocusCycleRoot(true);
        jTextField189.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField189.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField189.setRequestFocusEnabled(false);

        jTextField190.setEditable(false);
        jTextField190.setBackground(new java.awt.Color(153, 153, 153));
        jTextField190.setToolTipText("");
        jTextField190.setBorder(null);
        jTextField190.setEnabled(false);
        jTextField190.setFocusCycleRoot(true);
        jTextField190.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField190.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField190.setRequestFocusEnabled(false);

        jTextField191.setEditable(false);
        jTextField191.setBackground(new java.awt.Color(153, 153, 153));
        jTextField191.setToolTipText("");
        jTextField191.setBorder(null);
        jTextField191.setEnabled(false);
        jTextField191.setFocusCycleRoot(true);
        jTextField191.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField191.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField191.setRequestFocusEnabled(false);

        jTextField192.setEditable(false);
        jTextField192.setBackground(new java.awt.Color(153, 153, 153));
        jTextField192.setToolTipText("");
        jTextField192.setBorder(null);
        jTextField192.setEnabled(false);
        jTextField192.setFocusCycleRoot(true);
        jTextField192.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField192.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField192.setRequestFocusEnabled(false);

        jTextField193.setEditable(false);
        jTextField193.setBackground(new java.awt.Color(153, 153, 153));
        jTextField193.setToolTipText("");
        jTextField193.setBorder(null);
        jTextField193.setEnabled(false);
        jTextField193.setFocusCycleRoot(true);
        jTextField193.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField193.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField193.setRequestFocusEnabled(false);

        jTextField194.setEditable(false);
        jTextField194.setBackground(new java.awt.Color(153, 153, 153));
        jTextField194.setToolTipText("");
        jTextField194.setBorder(null);
        jTextField194.setEnabled(false);
        jTextField194.setFocusCycleRoot(true);
        jTextField194.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField194.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField194.setRequestFocusEnabled(false);

        jTextField195.setEditable(false);
        jTextField195.setBackground(new java.awt.Color(153, 153, 153));
        jTextField195.setToolTipText("");
        jTextField195.setBorder(null);
        jTextField195.setEnabled(false);
        jTextField195.setFocusCycleRoot(true);
        jTextField195.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField195.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField195.setRequestFocusEnabled(false);

        jTextField196.setEditable(false);
        jTextField196.setBackground(new java.awt.Color(153, 153, 153));
        jTextField196.setToolTipText("");
        jTextField196.setBorder(null);
        jTextField196.setEnabled(false);
        jTextField196.setFocusCycleRoot(true);
        jTextField196.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField196.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField196.setRequestFocusEnabled(false);

        jTextField197.setEditable(false);
        jTextField197.setBackground(new java.awt.Color(153, 153, 153));
        jTextField197.setToolTipText("");
        jTextField197.setBorder(null);
        jTextField197.setEnabled(false);
        jTextField197.setFocusCycleRoot(true);
        jTextField197.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField197.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField197.setRequestFocusEnabled(false);

        jTextField198.setEditable(false);
        jTextField198.setBackground(new java.awt.Color(153, 153, 153));
        jTextField198.setToolTipText("");
        jTextField198.setBorder(null);
        jTextField198.setEnabled(false);
        jTextField198.setFocusCycleRoot(true);
        jTextField198.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField198.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField198.setRequestFocusEnabled(false);

        jTextField199.setEditable(false);
        jTextField199.setBackground(new java.awt.Color(153, 153, 153));
        jTextField199.setToolTipText("");
        jTextField199.setBorder(null);
        jTextField199.setEnabled(false);
        jTextField199.setFocusCycleRoot(true);
        jTextField199.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField199.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField199.setRequestFocusEnabled(false);

        jTextField200.setEditable(false);
        jTextField200.setBackground(new java.awt.Color(153, 153, 153));
        jTextField200.setToolTipText("");
        jTextField200.setBorder(null);
        jTextField200.setEnabled(false);
        jTextField200.setFocusCycleRoot(true);
        jTextField200.setMinimumSize(new java.awt.Dimension(7, 16));
        jTextField200.setPreferredSize(new java.awt.Dimension(2, 8));
        jTextField200.setRequestFocusEnabled(false);

        javax.swing.GroupLayout practicePanelLayout = new javax.swing.GroupLayout(practicePanel);
        practicePanel.setLayout(practicePanelLayout);
        practicePanelLayout.setHorizontalGroup(
            practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addGroup(practicePanelLayout.createSequentialGroup()
                        .addComponent(languageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(fromLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(swapButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toLabel)
                        .addGap(84, 84, 84)
                        .addComponent(raceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(raceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField120, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField131, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField134, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField135, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField136, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField137, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField140, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField141, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField142, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField143, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField145, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField146, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField152, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField154, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField156, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField157, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField158, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField162, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField163, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField164, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField165, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField166, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField167, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField168, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField170, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField171, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField172, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField173, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField174, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField175, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField176, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField177, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField178, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField180, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField181, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField182, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField183, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField184, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField185, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField187, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField188, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField189, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField190, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField192, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField193, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField194, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField195, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField196, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField197, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField198, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField199, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jTextField200, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createSequentialGroup()
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(badLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(goodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(goodResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(badResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(goodPercentResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(badPercentResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(askLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(restResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(answerTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doneLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(practicePanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(roundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roundResultLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doneResultLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(practicePanelLayout.createSequentialGroup()
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(star1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(star2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(star3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(star4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(star5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(star6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
                        .addComponent(helpLabel))
                    .addComponent(jSeparator3))
                .addContainerGap())
        );
        practicePanelLayout.setVerticalGroup(
            practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, practicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(languageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(raceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(raceLabel)
                        .addComponent(swapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fromLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField170, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField171, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField172, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField173, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField174, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField175, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField176, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField177, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField178, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField180, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField181, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField182, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField183, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField184, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField185, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField187, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField188, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField189, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField190, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField192, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField193, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField194, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField195, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField196, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField197, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField198, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField199, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField200, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField135, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField136, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField137, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField140, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField141, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField142, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField143, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField145, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField146, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField152, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField154, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField156, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField157, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField158, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField162, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField163, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField164, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField165, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField166, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField167, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField168, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField120, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField131, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField134, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(practicePanelLayout.createSequentialGroup()
                                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(goodLabel)
                                        .addComponent(doneResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(doneLabel)
                                        .addComponent(goodResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(goodPercentResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(badLabel)
                                            .addComponent(roundResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(roundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(badResultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(badPercentResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(askLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(restResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(answerTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(practicePanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(star5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(star4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(star6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(star1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(star2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(star3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(practicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(helpLabel)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );

        TabbedPane.addTab("Gyakorlás", practicePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * GYAKORLÁS - greeting.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="greeting">
    private void welcomeWindow() {
        JEditorPane ep = new JEditorPane("text/html", is.getGreeting());
        ep.addHyperlinkListener((HyperlinkEvent e) -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                /**
                 * Process the click event on the link. (for example with
                 * java.awt.Desktop.getDesktop().browse())
                 */
                try {
                    // email
                    Desktop.getDesktop().mail(new URI("mailto:"
                            + e.getDescription()
                            + "?subject=Vocabulary&body=Hello!"));
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ep.setEditable(false);
        ep.setBackground(getBackground());
        JOptionPane.showMessageDialog(rootPane, ep, "Greeting",
                JOptionPane.PLAIN_MESSAGE);
    }// </editor-fold>

    /**
     * GYAKORLÁS - once .ini to .bin.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="once to .bin">
    private void iniToBin() {
        String[] iniFile = {ENGLISHFILE, GERMANFILE};
        String HUN = lcs.getHUNGARIAN();
        String ENG = lcs.getENGLISH();
        String GER = lcs.getGERMAN();
        String[] fileName = {ENG + "-" + HUN + ".bin",
            GER + "-" + HUN + ".bin",
            ENG + "-" + GER + ".bin"};
        try {
            for (int i = 0; i < iniFile.length; i++) {
                cs = new ConfigService(iniFile[i], fileName[i]);
            }
            // MIX
            cs = new ConfigService(iniFile[0], iniFile[1], fileName[2]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }// </editor-fold>

    /**
     * GYAKORLÁS - vocabulary upload from .bin.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="vocabulary upload from .bin">
    private void vocabularyUploadFromBin() {
        try {
            vs.setVocabulary(lcs.getCurrentCombo() + ".bin");
            vs.setKeyList();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - choose language.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="choose language">
	private void languageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageComboBoxActionPerformed
            String HUN = lcs.getHUNGARIAN();
            String ENG = lcs.getENGLISH();
            String GER = lcs.getGERMAN();

            /**
             * futam közben nem lehet nyelvet választani.
             */
            if (startButton.getText().equals(" Start")) {
                saveProposalIfIs();

                if (languageComboBox.getSelectedItem().equals("GB")) {
                    setFlags("flag-gb2.png", "flag-gb.png", "flag-hu.png");
                    from = ENG;
                    to = HUN;
                }

                if (languageComboBox.getSelectedItem().equals("DE")) {
                    setFlags("flag-de2.png", "flag-de.png", "flag-hu.png");
                    from = GER;
                    to = HUN;
                }

                if (languageComboBox.getSelectedItem().equals("MIX")) {
                    setFlags("mix.png", "flag-gb.png", "flag-de.png");
                    from = ENG;
                    to = GER;
                }

                lcs.setCurrentCombo(from, to);
                vocabularyUploadFromBin();
                ds.setIdxAndRoundByLanguageSwap(lcs.getCurrentCombo(),
                        lcs.reverseCombo());
                initRaceComboBox();
                setInfosInWindow();

                startButton.requestFocusInWindow();

            } else {
                Icon lock = new ImageIcon(getClass()
                        .getResource(PATH + "lock.png"));
                JOptionPane.showMessageDialog(rootPane,
                        "Csak \"Start\" előtt lehet nyelvet váltani!",
                        "Warning", JOptionPane.PLAIN_MESSAGE, lock);

                if (from.equals(ENG) || to.equals(ENG)) {
                    languageComboBox.setSelectedIndex(0);
                } else if (from.equals(GER) || to.equals(GER)) {
                    languageComboBox.setSelectedIndex(1);
                } else if (from.equals(ENG) && to.equals(GER)) {
                    languageComboBox.setSelectedIndex(2);
                } else if (from.equals(GER) && to.equals(ENG)) {
                    languageComboBox.setSelectedIndex(2);
                }
            }
	}// </editor-fold>//GEN-LAST:event_languageComboBoxActionPerformed

    /**
     * GYAKORLÀS - set flags.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="set flags">
    private void setFlags(String flag1, String flag2, String flag3) {
        languageLabel.setIcon(new ImageIcon(getClass()
                .getResource(PATH + flag1)));
        fromLabel.setIcon(new ImageIcon(getClass()
                .getResource(PATH + flag2)));
        toLabel.setIcon(new ImageIcon(getClass()
                .getResource(PATH + flag3)));
    }// </editor-fold>

    /**
     * GYAKORLÀS - start/again.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="start/again">
	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
            int keyListSize = vs.getKeyListSize();
            if (startButton.getText().equals(" Start")) {
                answerTxtField.requestFocusInWindow();

                int max = keyListSize - ds.getLearnedIdxsSize();
                newIdxInRace = Math.min(max,
                        Integer.parseInt(raceComboBox.getSelectedItem() + ""));

                rs = new RaceService(vs.getKeyListSize(),
                        ds.getLearnedIdxs(),
                        newIdxInRace);

                setNextWord();

                // rest words in this race
                restResultLabel.setText((newIdxInRace
                        - Integer.parseInt(goodResultLabel.getText())) + "");

                startButton.setForeground(new Color(0, 153, 0));
                startButton.setIcon(new ImageIcon(getClass().getResource(PATH
                        + "start.png")));
                startButton.setText(" run...");

            } else // race reset
            if (startButton.getText().equals(" Újra")) {
                askLabel.setText("kérdés");
                answerTxtField.setText("válasz");
                answerTxtField.setForeground(BLACK);
                goodResultLabel.setText("0");
                badResultLabel.setText("0");
                goodPercentResultLabel.setText("0%");
                badPercentResultLabel.setText("0%");

                // round reset
                if (ds.getPreviousRound() < ds.getRound()) {
                    doneResultLabel.setText(keyListSize
                            - (ds.getUnlearned(keyListSize)) + "/" + keyListSize);
                    deleteLEDFlag();
                }

                startButton.setForeground(MY_BLUE);
                startButton.setIcon(new ImageIcon(getClass().getResource(PATH
                        + "lockstart.png")));
                startButton.setText(" Start");
            }
	}// </editor-fold>//GEN-LAST:event_startButtonActionPerformed

    /**
     * GYAKORLÀS - answer.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="answer">
	private void answerTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerTxtFieldActionPerformed
            if (startButton.getText().equals(" run...")) {
                if (answerTxtField.getForeground() == BLACK) {
                    if (vs.getVocabularyValue(key).equals(answerTxtField.getText())) {
                        // green
                        answerTxtField
                                .setForeground(Color.getHSBColor(0.33f, 0.99f, 0.5f));

                        rs.addingGoodTranslation();
                        goodResultLabel.setText((int) rs.getGoodTranslation() + "");

                        rs.removeElem(0);

                        if (rs.getGoodTranslation() + rs.getBadTranslation()
                                <= newIdxInRace) {

                            if (ds.getIndexValue(indexID) == IDXMAXVALUE) {
                                ds.addLearnedIdxs(index);
                                ds.removIndexValue(indexID);
                            } else {
                                ds.addingIndexValue(indexID);
                            }

                            int size = ds.getLearnedIdxsSize();
                            if (size % 250 == 0) {
                                chooseStar(lcs.getCurrentCombo(), size, ds.getRound());
                            }
                        }
                    } else {
                        /**
                         * A rontott index értéke 0, ezt még LEGALÁBB 2x ki
                         * fogja kérdezni a körben(1000) ha a következö válasz
                         * jó, akkor 1 lesz az értéke ha utána rontod, akkor
                         * újra 0 és megint 2x jól kell válaszolni, hogy
                         * hozzáadja az (meg)tanultIndexek listához.
                         */
                        ds.resetIndexValue(indexID);

                        // red
                        answerTxtField
                                .setForeground(Color.getHSBColor(0.99f, 0.99f, 0.99f));

                        answerTxtField.setText(vs.getVocabularyValue(key));

                        rs.addingBadTranslation();
                        badResultLabel.setText((int) rs.getBadTranslation() + "");

                        // az index visszairása a futamba(újrakérdezéshez)
                        if (!rs.isEmpty()) {
                            rs.removeElem(0);
                            rs.addElem(index);
                        }
                    }

                    // "LED" line
                    int keyListSize = vs.getKeyListSize();
                    fISz = ds.getActivComboProcessing(keyListSize);
                    if (fISz > fISzPrev) {
                        activLEDs((int) fISz, MY_BLUE);
                    }

                    if (fISz == 100.0) {
                        fISzPrev = 0;
                    } else {
                        fISzPrev = fISz;
                    }

                    doneResultLabel.setText(keyListSize
                            - (ds.getUnlearned(keyListSize)) + "/" + keyListSize);

                    // round done
                    if (ds.getLearnedIdxsSize() == keyListSize) {
                        ds.addingRound();
                        roundResultLabel.setText(ds.getRound() + "");
                        ds.addRoundToDataContainer(lcs.getCurrentCombo());

                        ds.clearLearnedIdxs();
                        ds.addLarnedIdxsToDataContainer(lcs.getCurrentCombo());

                        showLEDFlag();
                    }

                    badPercentResultLabel.setText(rs.getBadTranslationPercent() + "%");
                    goodPercentResultLabel.setText(rs.getGoodTranslationPercent() + "%");

                    restResultLabel.setText((newIdxInRace
                            - Integer.parseInt(goodResultLabel.getText())) + "");

                    // color word (second enter)
                } else if (!rs.isEmpty()) {
                    setNextWord();
                } else {
                    ds.addLarnedIdxsToDataContainer(lcs.getCurrentCombo());

                    startButton.setForeground(BLACK);
                    startButton.setIcon(new ImageIcon(getClass()
                            .getResource(PATH + "replay.png")));
                    startButton.setText(" Újra");

                    int percent = rs.getGoodTranslationPercent();
                    if (percent < 70
                            && raceComboBox.getSelectedIndex() >= 1) {
                        adviceInfo();
                    }

                    int futamSelectedIndex = raceComboBox.getSelectedIndex();
                    String localKey = lcs.getCurrentCombo() + futamSelectedIndex;
                    int performance = ds.getPerformanceValue(localKey);
                    if (percent >= 90 && (performance >= 0 && performance < 3)) {
                        ds.addingPerformanceValue(localKey);

                        if (percent == 100 && 
                            !ds.containsPerformance(localKey + percent)) {

                            ds.addingPerformanceValue(localKey + percent);
                            ds.addingCongrat();
                            congratulationInfo(raceComboBox.getSelectedIndex(),
                                    ds.getCongratulation());
                        }
                    }

                    /**
                     * Ha a 10-es futam 3x 90% vagy e feletti, akkor hozzáadásra
                     * kerül a komboboxhoz a 20-as választható futamszám, stb.
                     */
                    int keyListSize = vs.getKeyListSize();
                    if (ds.getUnlearned(keyListSize) > 100) {
                        if (futamSelectedIndex == raceComboBox.getItemCount() - 1) {
                            performance = ds.getPerformanceValue(localKey);
                            if (performance == 3) {
                                ds.setRaceComboBoxMaxIndex(lcs.getCurrentCombo(),
                                        futamSelectedIndex + 1);
                                setRaceComboBoxItems(ds.getRaceComboBoxMaxIndex(
                                        lcs.getCurrentCombo()));
                                raceComboBoxItemAdditionInfo(raceComboBox.getItemAt(
                                        raceComboBox.getItemCount() - 1) + "");
                            }
                        }
                    } else {
                        /**
                         * 100 alatt csökkenti a futam kombobox tételeit, ha a
                         * kikérdezetlen szavak száma 60, akkor ne lehessen
                         * 100-as futamot választani, stb
                         *
                         * String kombo = ks.getCurrentKombo(); if
                         * (ds.getFutamComboBoxMaxIndex(kombo) > 1) { if
                         * (ds.cutFutamComboBoxMaxIndex(keyListSize, kombo)) {
                         * initFutamComboBox();
                         * futamComboBoxSzukitesInfo(ds.getFutamComboBoxItem(
                         * ds.getFutamComboBoxMaxIndex(kombo))); } }
                         *
                         * Ha lefutna, akkor a következö körökben nem lennének
                         * elérhetöek a már megszerzett futamszámok.
                         */
                    }
                    startButton.requestFocusInWindow();
                    //startButton.doClick();
                }
            }

            // for test
            if (startButton.getText().equals(" Start")) {
                String text = answerTxtField.getText();
                String[] str;
                String kombo = lcs.getCurrentCombo();

                if (Pattern.matches("#answer [1-2]", text)) {
                    str = text.split(" ");
                    IDXMAXVALUE = Integer.parseInt(str[1]);
                }

                if (Pattern.matches("#\\d{3}", text)) {
                    int size = Integer.parseInt(text.substring(1));
                    chooseStar(kombo, size, Math.max(0, ds.getRound()));

                    int i = 0;
                    ds.clearLearnedIdxs();
                    while (size-- > 0) {
                        ds.addLearnedIdxs(i);
                        i++;
                    }
                    setInfosInWindow();
                }

                if (text.equals("#reset")) {
                    IDXMAXVALUE = 2;
                    ds.setRaceComboBoxMaxIndex(kombo, 1);
                    initRaceComboBox();

                    ds.clearLearnedIdxs();
                    ds.addLarnedIdxsToDataContainer(kombo);
                    ds.resetRound(0);
                    ds.addRoundToDataContainer(kombo);

                    doneResultLabel.setText("0/1000");
                    roundResultLabel.setText("0");

                    askLabel.setText("kérdés");
                    answerTxtField.setText("válasz");
                    answerTxtField.setForeground(BLACK);
                    goodResultLabel.setText("0");
                    badResultLabel.setText("0");
                    goodPercentResultLabel.setText("0%");
                    badPercentResultLabel.setText("0%");

                    removeStars(kombo);

                    new Thread(() -> {
                        for (int i = 100; i >= 0; i--) {
                            activLEDs(i, MY_GRAY);

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                                        "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }).start();
                }

                if (Pattern.matches("#race [0-6]", text)) {
                    str = text.split(" ");
                    ds.setRaceComboBoxMaxIndex(kombo, Integer.parseInt(str[1]));
                    initRaceComboBox();
                }

                if (text.equals("#round++")) {
                    ds.addingRound();
                    roundResultLabel.setText(ds.getRound() + "");
                    ds.addRoundToDataContainer(kombo);
                }

                if (Pattern.matches("#add (20|30|40|50|100)", text)) {
                    str = text.split(" ");
                    raceComboBox.addItem(str[1]);
                }

                if (Pattern.matches("#delete (20|30|40|50|100)", text)) {
                    str = text.split(" ");
                    raceComboBox.removeItem(str[1]);
                }

                if (text.equals("#show flag")) {
                    showLEDFlag();
                }

                if (text.equals("#delete flag")) {
                    deleteLEDFlag();
                    setInfosInWindow();
                }

                if (text.equals("#add MIX") && languageComboBox.getItemAt(2) == null) {
                    languageComboBox.addItem("MIX");
                }

                if (text.equals("#delete MIX")) {
                    languageComboBox.removeItem("MIX");
                }

                // a felhasználónak
                if (text.equals("show my congrat")) {
                    showSumOfCongratulation(ds.getCongratulation());
                }
            }
	}// </editor-fold>//GEN-LAST:event_answerTxtFieldActionPerformed

    /**
     * GYAKORLÀS - set next word.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="set next word">
    private void setNextWord() {
        index = rs.getElem(0);
        key = vs.getKeyListElem(index);
        askLabel.setText(key);
        answerTxtField.setText("");
        answerTxtField.setForeground(BLACK);

        // index kezdö érték
        indexID = index + "-" + lcs.getCurrentCombo();
        ds.initialIndexValue(indexID);
    }// </editor-fold>

    /**
     * GYAKORLÀS - advice.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="advice">
    private void adviceInfo() {
        JEditorPane ep = new JEditorPane("text/html", is.getAdvice());
        ep.setEditable(false);
        ep.setBackground(getBackground());
        JOptionPane.showMessageDialog(rootPane, ep, "Advice",
                JOptionPane.PLAIN_MESSAGE);
    }// </editor-fold>

    /**
     * GYAKORLÀS - congratulation.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="congratulation">
    private void congratulationInfo(int index, int gratulacio) {
        InfoService iS = new InfoService(lcs.getCurrentCombo(), index, gratulacio);
        JEditorPane ep = new JEditorPane("text/html", iS.getCongratulation());
        ep.setEditable(false);
        ep.setBackground(getBackground());
        JOptionPane.showMessageDialog(rootPane, ep, "Congratulation",
                JOptionPane.PLAIN_MESSAGE);
    }// </editor-fold>

    /**
     * GYAKORLÀS - show sum of congratulation.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="sum of congratulation">
    private void showSumOfCongratulation(int gratulacio) {
        InfoService iS = new InfoService(gratulacio);
        JEditorPane ep = new JEditorPane("text/html", iS.getExtra());
        ep.setEditable(false);
        ep.setBackground(getBackground());
        JOptionPane.showMessageDialog(rootPane, ep, "Extra",
                JOptionPane.PLAIN_MESSAGE);
    }// </editor-fold>

    /**
     * GYAKORLÀS - by language swap.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="by language swap">
    private void swapButtonActionPerformed(java.awt.event.ActionEvent evt) {
        /**
         * futam közben nem lehet nyelvet választani.
         */
        if (startButton.getText().equals(" Start")) {
            saveProposalIfIs();

            vs.reverseVocabulary();
            vs.setKeyList();

            languageSwap(from, to);
            flagSwap(fromLabel.getIcon(), toLabel.getIcon());

            ds.setIdxAndRoundByLanguageSwap(lcs.getCurrentCombo(), lcs.reverseCombo());

            initRaceComboBox();
            setInfosInWindow();

            startButton.requestFocusInWindow();
        } else {
            /*
            Icon lock = new ImageIcon(getClass().getResource(PATH + "lock.png"));
            JOptionPane.showMessageDialog(rootPane,
                    "Csak \"Start\" előtt lehet a nyelveket felcserélni!",
                    "Warning", JOptionPane.PLAIN_MESSAGE, lock);
            */
            infoWindow("nyelveket felcserélni");
        }
    }// </editor-fold>
    
    private void infoWindow(String text) {
        Icon lock = new ImageIcon(getClass().getResource(PATH + "lock.png"));
        String[] buttons = { "show description", "Yes, I Agree!" };
        int rc = JOptionPane.showOptionDialog(rootPane, "Csak \"Start\" előtt lehet a " + text + "!", "Confirmation",
            JOptionPane.WARNING_MESSAGE, 0, lock, buttons, buttons[1]);
    }

    /**
     * GYAKORLÀS - save proposal.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="save proposal">
    private static void saveProposalIfIs() {
        try {
            ps.save(lcs.getCurrentCombo());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - language swap.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="language swap">
    private void languageSwap(String fromWhat, String toWhat) {
        from = toWhat; // needed elsewhere
        to = fromWhat;
        lcs.setCurrentCombo(from, to);
    }// </editor-fold>

    /**
     * GYAKORLÀS - flag swap.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="flag swap">
    private void flagSwap(Icon fromWhat, Icon toWhat) {
        fromLabel.setIcon(toWhat);
        toLabel.setIcon(fromWhat);
    }// </editor-fold>

    /**
     * GYAKORLÀS - race combo box blocked.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="race combo box blocked">
	private void raceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raceComboBoxActionPerformed
            if (startButton.getText().equals(" run...") || 
                startButton.getText().equals(" Újra")) {

                raceComboBox.setSelectedItem(newIdxInRace + "");
                Icon lock = new ImageIcon(getClass().getResource(PATH + "lock.png"));
                JOptionPane.showMessageDialog(rootPane,
                        "Csak \"Start\" előtt válthatsz futamszámot!",
                        "Warning", JOptionPane.PLAIN_MESSAGE, lock);
            }
	}// </editor-fold>//GEN-LAST:event_raceComboBoxActionPerformed

    /**
     * GYAKORLÀS - race combo box initialization.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="race combo box initialization">
    private void initRaceComboBox() {
        String kombo = lcs.getCurrentCombo();
        int maxIndex = ds.getRaceComboBoxMaxIndex(kombo);
        if (ds.containsRaceComboBoxBox(kombo) && 
            maxIndex > RACE_COMBO_BOX_INITIAL_MAX_IDX) {
            
            setRaceComboBoxItems(maxIndex);
        } else {
            setRaceComboBoxItems(RACE_COMBO_BOX_INITIAL_MAX_IDX);
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - set race combo box items.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="set race combo box items">
    private void setRaceComboBoxItems(int maxIndex) {
        raceComboBox.removeAllItems();
        for (int i = 0; i <= maxIndex; i++) {
            raceComboBox.addItem(ds.getRaceComboBoxItem(i));
        }
        raceComboBox.setSelectedIndex(raceComboBox.getItemCount() - 2);
    }// </editor-fold>

    /**
     * GYAKORLÀS - info from the race combo box item addition.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="info from the race combo box itme addition">
    private void raceComboBoxItemAdditionInfo(String item) {
        InfoService iS = new InfoService(lcs.getCurrentCombo(), item);
        JEditorPane ep = new JEditorPane("text/html", iS.getAddingItenToRaceComboBox());
        ep.setEditable(false);
        ep.setBackground(getBackground());
        JOptionPane.showMessageDialog(rootPane, ep, "Combo Box Plus",
                JOptionPane.PLAIN_MESSAGE);
    }// </editor-fold>

    /**
     * GYAKORLÀS - window update.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="window update">
    private void setInfosInWindow() {
        int keyListSize = vs.getKeyListSize();
        doneResultLabel.setText(keyListSize - ds.getUnlearned(keyListSize)
                + "/" + keyListSize);

        roundResultLabel.setText(ds.getRound() + "");

        int deleteMax = (int) Math.max(fISz, fISzByReverseKombo);
        fISz = ds.getActivComboProcessing(keyListSize);
        fISzByReverseKombo = ds.getReverseComboProcessing(keyListSize);

        // idöeltolással és "egyszerre" törli/szinezi mindkét oldalt
        new Thread(() -> {
            // delete
            for (int i = deleteMax; i >= 0; i--) {
                activLEDs(i, MY_GRAY);
                passiveLEDs(i, MY_GRAY);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }

            // szinezés 0-tól(ha 0, akkor is bemegy)
            for (int i = 0; i <= fISz; i++) {
                activLEDs(i, MY_BLUE);

                new Thread(() -> {
                    for (int j = 0; j <= fISzByReverseKombo; j++) {
                        passiveLEDs(j, MY_BLUE);

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(rootPane,
                                    ex.getMessage(), "Error",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }).start();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();
    }// </editor-fold>

    /**
     * GYAKORLÀS - hungarian "led" flag.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="hungarian led flag">
    private void showLEDFlag() {
        new Thread(() -> {
            for (int j = 1; j <= 33; j++) {
                activLEDs(j, Color.RED);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(rootPane,
                            e.getMessage(), "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();

        new Thread(() -> {
            for (int k = 34; k <= 67; k++) {
                activLEDs(k, Color.WHITE);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(rootPane,
                            e.getMessage(), "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();

        new Thread(() -> {
            for (int l = 68; l <= 100; l++) {
                activLEDs(l, Color.GREEN);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(rootPane,
                            e.getMessage(), "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();
    }// </editor-fold>

    /**
     * GYAKORLÀS - delete hungarian "led" flag.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="delete hungarian led flag">
    private void deleteLEDFlag() {
        new Thread(() -> {
            for (int i = 50; i >= 1; i--) {
                activLEDs(i, MY_GRAY);

                new Thread(() -> {
                    for (int j = 51; j <= 100; j++) {
                        activLEDs(j, MY_GRAY);

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            JOptionPane.showMessageDialog(rootPane,
                                    e.getMessage(), "Error",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }).start();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(rootPane,
                            e.getMessage(), "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();
    }// </editor-fold>

    /**
     * GYAKORLÀS - activ "led" line(left).
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="activ led line(left)">
    private void activLEDs(int x, Color color) {
        switch (x) {
            case 1: jTextField1.setBackground(color); break;
            case 2: jTextField2.setBackground(color); break;
            case 3: jTextField3.setBackground(color); break;
            case 4: jTextField4.setBackground(color); break;
            case 5: jTextField5.setBackground(color); break;
            case 6: jTextField6.setBackground(color); break;
            case 7: jTextField7.setBackground(color); break;
            case 8: jTextField8.setBackground(color); break;
            case 9: jTextField9.setBackground(color); break;
            case 10: jTextField10.setBackground(color); break;
            case 11: jTextField11.setBackground(color); break;
            case 12: jTextField12.setBackground(color); break;
            case 13: jTextField13.setBackground(color); break;
            case 14: jTextField14.setBackground(color); break;
            case 15: jTextField15.setBackground(color); break;
            case 16: jTextField16.setBackground(color); break;
            case 17: jTextField17.setBackground(color); break;
            case 18: jTextField18.setBackground(color); break;
            case 19: jTextField19.setBackground(color); break;
            case 20: jTextField20.setBackground(color); break;
            case 21: jTextField21.setBackground(color); break;
            case 22: jTextField22.setBackground(color); break;
            case 23: jTextField23.setBackground(color); break;
            case 24: jTextField24.setBackground(color); break;
            case 25: jTextField25.setBackground(color); break;
            case 26: jTextField26.setBackground(color); break;
            case 27: jTextField27.setBackground(color); break;
            case 28: jTextField28.setBackground(color); break;
            case 29: jTextField29.setBackground(color); break;
            case 30: jTextField30.setBackground(color); break;
            case 31: jTextField31.setBackground(color); break;
            case 32: jTextField32.setBackground(color); break;
            case 33: jTextField33.setBackground(color); break;
            case 34: jTextField34.setBackground(color); break;
            case 35: jTextField35.setBackground(color); break;
            case 36: jTextField36.setBackground(color); break;
            case 37: jTextField37.setBackground(color); break;
            case 38: jTextField38.setBackground(color); break;
            case 39: jTextField39.setBackground(color); break;
            case 40: jTextField40.setBackground(color); break;
            case 41: jTextField41.setBackground(color); break;
            case 42: jTextField42.setBackground(color); break;
            case 43: jTextField43.setBackground(color); break;
            case 44: jTextField44.setBackground(color); break;
            case 45: jTextField45.setBackground(color); break;
            case 46: jTextField46.setBackground(color); break;
            case 47: jTextField47.setBackground(color); break;
            case 48: jTextField48.setBackground(color); break;
            case 49: jTextField49.setBackground(color); break;
            case 50: jTextField50.setBackground(color); break;
            case 51: jTextField51.setBackground(color); break;
            case 52: jTextField52.setBackground(color); break;
            case 53: jTextField53.setBackground(color); break;
            case 54: jTextField54.setBackground(color); break;
            case 55: jTextField55.setBackground(color); break;
            case 56: jTextField56.setBackground(color); break;
            case 57: jTextField57.setBackground(color); break;
            case 58: jTextField58.setBackground(color); break;
            case 59: jTextField59.setBackground(color); break;
            case 60: jTextField60.setBackground(color); break;
            case 61: jTextField61.setBackground(color); break;
            case 62: jTextField62.setBackground(color); break;
            case 63: jTextField63.setBackground(color); break;
            case 64: jTextField64.setBackground(color); break;
            case 65: jTextField65.setBackground(color); break;
            case 66: jTextField66.setBackground(color); break;
            case 67: jTextField67.setBackground(color); break;
            case 68: jTextField68.setBackground(color); break;
            case 69: jTextField69.setBackground(color); break;
            case 70: jTextField70.setBackground(color); break;
            case 71: jTextField71.setBackground(color); break;
            case 72: jTextField72.setBackground(color); break;
            case 73: jTextField73.setBackground(color); break;
            case 74: jTextField74.setBackground(color); break;
            case 75: jTextField75.setBackground(color); break;
            case 76: jTextField76.setBackground(color); break;
            case 77: jTextField77.setBackground(color); break;
            case 78: jTextField78.setBackground(color); break;
            case 79: jTextField79.setBackground(color); break;
            case 80: jTextField80.setBackground(color); break;
            case 81: jTextField81.setBackground(color); break;
            case 82: jTextField82.setBackground(color); break;
            case 83: jTextField83.setBackground(color); break;
            case 84: jTextField84.setBackground(color); break;
            case 85: jTextField85.setBackground(color); break;
            case 86: jTextField86.setBackground(color); break;
            case 87: jTextField87.setBackground(color); break;
            case 88: jTextField88.setBackground(color); break;
            case 89: jTextField89.setBackground(color); break;
            case 90: jTextField90.setBackground(color); break;
            case 91: jTextField91.setBackground(color); break;
            case 92: jTextField92.setBackground(color); break;
            case 93: jTextField93.setBackground(color); break;
            case 94: jTextField94.setBackground(color); break;
            case 95: jTextField95.setBackground(color); break;
            case 96: jTextField96.setBackground(color); break;
            case 97: jTextField97.setBackground(color); break;
            case 98: jTextField98.setBackground(color); break;
            case 99: jTextField99.setBackground(color); break;
            case 100: jTextField100.setBackground(color); break;
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - passive "led" line(right).
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="passive led line(right)">
    private void passiveLEDs(int x, Color color) {
        switch (x) {
            case 100: jTextField101.setBackground(color); break;
            case 99: jTextField102.setBackground(color); break;
            case 98: jTextField103.setBackground(color); break;
            case 97: jTextField104.setBackground(color); break;
            case 96: jTextField105.setBackground(color); break;
            case 95: jTextField106.setBackground(color); break;
            case 94: jTextField107.setBackground(color); break;
            case 93: jTextField108.setBackground(color); break;
            case 92: jTextField109.setBackground(color); break;
            case 91: jTextField110.setBackground(color); break;
            case 90: jTextField111.setBackground(color); break;
            case 89: jTextField112.setBackground(color); break;
            case 88: jTextField113.setBackground(color); break;
            case 87: jTextField114.setBackground(color); break;
            case 86: jTextField115.setBackground(color); break;
            case 85: jTextField116.setBackground(color); break;
            case 84: jTextField117.setBackground(color); break;
            case 83: jTextField118.setBackground(color); break;
            case 82: jTextField119.setBackground(color); break;
            case 81: jTextField120.setBackground(color); break;
            case 80: jTextField121.setBackground(color); break;
            case 79: jTextField122.setBackground(color); break;
            case 78: jTextField123.setBackground(color); break;
            case 77: jTextField124.setBackground(color); break;
            case 76: jTextField125.setBackground(color); break;
            case 75: jTextField126.setBackground(color); break;
            case 74: jTextField127.setBackground(color); break;
            case 73: jTextField128.setBackground(color); break;
            case 72: jTextField129.setBackground(color); break;
            case 71: jTextField130.setBackground(color); break;
            case 70: jTextField131.setBackground(color); break;
            case 69: jTextField132.setBackground(color); break;
            case 68: jTextField133.setBackground(color); break;
            case 67: jTextField134.setBackground(color); break;
            case 66: jTextField135.setBackground(color); break;
            case 65: jTextField136.setBackground(color); break;
            case 64: jTextField137.setBackground(color); break;
            case 63: jTextField138.setBackground(color); break;
            case 62: jTextField139.setBackground(color); break;
            case 61: jTextField140.setBackground(color); break;
            case 60: jTextField141.setBackground(color); break;
            case 59: jTextField142.setBackground(color); break;
            case 58: jTextField143.setBackground(color); break;
            case 57: jTextField144.setBackground(color); break;
            case 56: jTextField145.setBackground(color); break;
            case 55: jTextField146.setBackground(color); break;
            case 54: jTextField147.setBackground(color); break;
            case 53: jTextField148.setBackground(color); break;
            case 52: jTextField149.setBackground(color); break;
            case 51: jTextField150.setBackground(color); break;
            case 50: jTextField151.setBackground(color); break;
            case 49: jTextField152.setBackground(color); break;
            case 48: jTextField153.setBackground(color); break;
            case 47: jTextField154.setBackground(color); break;
            case 46: jTextField155.setBackground(color); break;
            case 45: jTextField156.setBackground(color); break;
            case 44: jTextField157.setBackground(color); break;
            case 43: jTextField158.setBackground(color); break;
            case 42: jTextField159.setBackground(color); break;
            case 41: jTextField160.setBackground(color); break;
            case 40: jTextField161.setBackground(color); break;
            case 39: jTextField162.setBackground(color); break;
            case 38: jTextField163.setBackground(color); break;
            case 37: jTextField164.setBackground(color); break;
            case 36: jTextField165.setBackground(color); break;
            case 35: jTextField166.setBackground(color); break;
            case 34: jTextField167.setBackground(color); break;
            case 33: jTextField168.setBackground(color); break;
            case 32: jTextField169.setBackground(color); break;
            case 31: jTextField170.setBackground(color); break;
            case 30: jTextField171.setBackground(color); break;
            case 29: jTextField172.setBackground(color); break;
            case 28: jTextField173.setBackground(color); break;
            case 27: jTextField174.setBackground(color); break;
            case 26: jTextField175.setBackground(color); break;
            case 25: jTextField176.setBackground(color); break;
            case 24: jTextField177.setBackground(color); break;
            case 23: jTextField178.setBackground(color); break;
            case 22: jTextField179.setBackground(color); break;
            case 21: jTextField180.setBackground(color); break;
            case 20: jTextField181.setBackground(color); break;
            case 19: jTextField182.setBackground(color); break;
            case 18: jTextField183.setBackground(color); break;
            case 17: jTextField184.setBackground(color); break;
            case 16: jTextField185.setBackground(color); break;
            case 15: jTextField186.setBackground(color); break;
            case 14: jTextField187.setBackground(color); break;
            case 13: jTextField188.setBackground(color); break;
            case 12: jTextField189.setBackground(color); break;
            case 11: jTextField190.setBackground(color); break;
            case 10: jTextField191.setBackground(color); break;
            case 9: jTextField192.setBackground(color); break;
            case 8: jTextField193.setBackground(color); break;
            case 7: jTextField194.setBackground(color); break;
            case 6: jTextField195.setBackground(color); break;
            case 5: jTextField196.setBackground(color); break;
            case 4: jTextField197.setBackground(color); break;
            case 3: jTextField198.setBackground(color); break;
            case 2: jTextField199.setBackground(color); break;
            case 1: jTextField200.setBackground(color); break;
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - set surprise stars.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="set surprise stars">
    private void setStars() {
        List<String> combos = lcs.getList();
        ds.clearLearnedIdxs();
        combos.stream().map((k) -> {
            ds.setLearnedIdxs(k);
            ds.setRound(k);
            return k;
        }).forEach((k) -> {
            chooseStar(k, ds.getLearnedIdxsSize(), ds.getRound());
        });
    }// </editor-fold>

    /**
     * GYAKORLÀS - choose star.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="choose star">
    private void chooseStar(String kombo, int size, int round) {
        switch (kombo.toUpperCase()) {
            case "ENG-HUN": showStarIf(star1, new Star1(), size, round); break;
            case "GER-HUN": showStarIf(star2, new Star2(), size, round); break;
            case "ENG-GER": showStarIf(star3, new Star3(), size, round); break;
            case "HUN-ENG": showStarIf(star4, new Star4(), size, round); break;
            case "HUN-GER": showStarIf(star5, new Star5(), size, round); break;
            case "GER-ENG": showStarIf(star6, new Star6(), size, round); break;
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - show star if.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="show star if">
    private void showStarIf(JLabel label, Star star, int size, int round) {
        label.addMouseListener(new MouseAdapter() {
            final int defaultDismissTimeout = ToolTipManager.sharedInstance()
                    .getDismissDelay();

            final int dismissDelayMinutes = (int) TimeUnit.MINUTES.toMillis(1);

            @Override
            public void mouseEntered(MouseEvent e) {
                ToolTipManager.sharedInstance()
                        .setDismissDelay(dismissDelayMinutes);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ToolTipManager.sharedInstance()
                        .setDismissDelay(defaultDismissTimeout);
            }
        });

        if (size == 1000 || round >= 1) {
            label.setIcon(star.getIcon("star_full"));
            label.setToolTipText(star.getToolTipText4());
        } else if (size >= 750) {
            label.setIcon(star.getIcon("star_add"));
            label.setToolTipText(star.getToolTipText3());
        } else if (size >= 500) {
            label.setIcon(star.getIcon("star_half"));
            label.setToolTipText(star.getToolTipText2());
        } else if (size >= 250) {
            label.setIcon(star.getIcon("star_empty"));
            label.setToolTipText(star.getToolTipText1());
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - remove stars.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="remove stars">
    private void removeStars(String kombo) {
        switch (kombo) {
            case "ENG-HUN": star1.setIcon(null); break;
            case "GER-HUN": star2.setIcon(null); break;
            case "ENG-GER": star3.setIcon(null); break;
            case "HUN-ENG": star4.setIcon(null); break;
            case "HUN-GER": star5.setIcon(null); break;
            case "GER-ENG": star6.setIcon(null); break;
        }
    }// </editor-fold>

    /**
     * GYAKORLÀS - about.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="about">
	private void helpLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpLabelMouseClicked
            JEditorPane ep = new JEditorPane("text/html", is.getAbout());
            ep.addHyperlinkListener((HyperlinkEvent e) -> {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    /**
                     * Process the click event on the link. (for example with
                     * java.awt.Desktop.getDesktop().browse())
                     */
                    try {
                        // url
                        if (e.getURL() != null) {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } else {
                            // email
                            Desktop.getDesktop()
                                    .mail(new URI("mailto:" + e.getDescription()
                                            + "?subject=Vocabulary&body=Hello!"));
                        }
                    } catch (IOException | URISyntaxException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            ep.setEditable(false);
            ep.setBackground(getBackground());
            //JScrollPane jsp=new JScrollPane(ep);
            JOptionPane.showMessageDialog(rootPane, ep, "Vocabulary",
                    JOptionPane.PLAIN_MESSAGE);
	}// </editor-fold>//GEN-LAST:event_helpLabelMouseClicked

    /**
     * GYAKORLÀS - exit.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="exit">
	private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
            System.exit(0);
	}// </editor-fold>//GEN-LAST:event_exitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    //<editor-fold defaultstate="collapsed" desc="main">
    public static void main(String args[]) {

        /*
         * Set the Mac OS X / Windows or Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc="Look and feel setting">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel.
		 * For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        String laf = "Nimbus";
        String os = System.getProperty("os.name");

        if (os.startsWith("Win")) {
            laf = "Windows";
        }
        if (os.equals("Mac OS X")) {
            laf = "Mac OS X";
        }

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (laf.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }//</editor-fold>

        /**
         * First show splash and than create and display the form.
         */
        java.awt.EventQueue.invokeLater(() -> {
            new Vocabulary().setVisible(true);
        });

        // by exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ds.saveAllData(DATAFILE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.PLAIN_MESSAGE);
            }
            saveProposalIfIs();
        }));

    }//</editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JTextField answerTxtField;
    private javax.swing.JLabel askLabel;
    private javax.swing.JLabel badLabel;
    private javax.swing.JLabel badPercentResultLabel;
    private javax.swing.JLabel badResultLabel;
    private javax.swing.JLabel doneLabel;
    private javax.swing.JLabel doneResultLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JLabel goodLabel;
    private javax.swing.JLabel goodPercentResultLabel;
    private javax.swing.JLabel goodResultLabel;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField101;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField105;
    private javax.swing.JTextField jTextField106;
    private javax.swing.JTextField jTextField107;
    private javax.swing.JTextField jTextField108;
    private javax.swing.JTextField jTextField109;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField110;
    private javax.swing.JTextField jTextField111;
    private javax.swing.JTextField jTextField112;
    private javax.swing.JTextField jTextField113;
    private javax.swing.JTextField jTextField114;
    private javax.swing.JTextField jTextField115;
    private javax.swing.JTextField jTextField116;
    private javax.swing.JTextField jTextField117;
    private javax.swing.JTextField jTextField118;
    private javax.swing.JTextField jTextField119;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField120;
    private javax.swing.JTextField jTextField121;
    private javax.swing.JTextField jTextField122;
    private javax.swing.JTextField jTextField123;
    private javax.swing.JTextField jTextField124;
    private javax.swing.JTextField jTextField125;
    private javax.swing.JTextField jTextField126;
    private javax.swing.JTextField jTextField127;
    private javax.swing.JTextField jTextField128;
    private javax.swing.JTextField jTextField129;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField130;
    private javax.swing.JTextField jTextField131;
    private javax.swing.JTextField jTextField132;
    private javax.swing.JTextField jTextField133;
    private javax.swing.JTextField jTextField134;
    private javax.swing.JTextField jTextField135;
    private javax.swing.JTextField jTextField136;
    private javax.swing.JTextField jTextField137;
    private javax.swing.JTextField jTextField138;
    private javax.swing.JTextField jTextField139;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField140;
    private javax.swing.JTextField jTextField141;
    private javax.swing.JTextField jTextField142;
    private javax.swing.JTextField jTextField143;
    private javax.swing.JTextField jTextField144;
    private javax.swing.JTextField jTextField145;
    private javax.swing.JTextField jTextField146;
    private javax.swing.JTextField jTextField147;
    private javax.swing.JTextField jTextField148;
    private javax.swing.JTextField jTextField149;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField150;
    private javax.swing.JTextField jTextField151;
    private javax.swing.JTextField jTextField152;
    private javax.swing.JTextField jTextField153;
    private javax.swing.JTextField jTextField154;
    private javax.swing.JTextField jTextField155;
    private javax.swing.JTextField jTextField156;
    private javax.swing.JTextField jTextField157;
    private javax.swing.JTextField jTextField158;
    private javax.swing.JTextField jTextField159;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField160;
    private javax.swing.JTextField jTextField161;
    private javax.swing.JTextField jTextField162;
    private javax.swing.JTextField jTextField163;
    private javax.swing.JTextField jTextField164;
    private javax.swing.JTextField jTextField165;
    private javax.swing.JTextField jTextField166;
    private javax.swing.JTextField jTextField167;
    private javax.swing.JTextField jTextField168;
    private javax.swing.JTextField jTextField169;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField170;
    private javax.swing.JTextField jTextField171;
    private javax.swing.JTextField jTextField172;
    private javax.swing.JTextField jTextField173;
    private javax.swing.JTextField jTextField174;
    private javax.swing.JTextField jTextField175;
    private javax.swing.JTextField jTextField176;
    private javax.swing.JTextField jTextField177;
    private javax.swing.JTextField jTextField178;
    private javax.swing.JTextField jTextField179;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField180;
    private javax.swing.JTextField jTextField181;
    private javax.swing.JTextField jTextField182;
    private javax.swing.JTextField jTextField183;
    private javax.swing.JTextField jTextField184;
    private javax.swing.JTextField jTextField185;
    private javax.swing.JTextField jTextField186;
    private javax.swing.JTextField jTextField187;
    private javax.swing.JTextField jTextField188;
    private javax.swing.JTextField jTextField189;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField190;
    private javax.swing.JTextField jTextField191;
    private javax.swing.JTextField jTextField192;
    private javax.swing.JTextField jTextField193;
    private javax.swing.JTextField jTextField194;
    private javax.swing.JTextField jTextField195;
    private javax.swing.JTextField jTextField196;
    private javax.swing.JTextField jTextField197;
    private javax.swing.JTextField jTextField198;
    private javax.swing.JTextField jTextField199;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField200;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private javax.swing.JTextField jTextField99;
    private javax.swing.JComboBox languageComboBox;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JPanel practicePanel;
    private javax.swing.JComboBox raceComboBox;
    private javax.swing.JLabel raceLabel;
    private javax.swing.JLabel restResultLabel;
    private javax.swing.JLabel roundLabel;
    private javax.swing.JLabel roundResultLabel;
    private javax.swing.JLabel star1;
    private javax.swing.JLabel star2;
    private javax.swing.JLabel star3;
    private javax.swing.JLabel star4;
    private javax.swing.JLabel star5;
    private javax.swing.JLabel star6;
    private javax.swing.JButton startButton;
    private javax.swing.JButton swapButton;
    private javax.swing.JLabel toLabel;
    // End of variables declaration//GEN-END:variables
}
