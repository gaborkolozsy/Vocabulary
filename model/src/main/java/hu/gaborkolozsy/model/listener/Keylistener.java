/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.listener;

import hu.gaborkolozsy.model.Proposal;
import hu.gaborkolozsy.model.ProposalBox;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ListIterator;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * {@code KeyListener} objektum.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Proposal
 * @see hu.gaborkolozsy.model.ProposalBox
 * @see java.awt.event.KeyEvent
 * @see java.awt.event.KeyListener
 * @see java.util.ListIterator
 * @see java.util.regex.Pattern
 * @see javax.swing.Icon
 * @see javax.swing.ImageIcon
 * @see javax.swing.JOptionPane
 * @since 2.2.1
 */
public class Keylistener {
    
    /** Billentyüzet figyelö objektum. */
    private final KeyListener keyListener;
    /** Az ideiglenesen bevezetett javaslat tároló a teszteléshez. */
    private final ProposalBox proposalBox = new ProposalBox();

    /**
     * Konstruktor.<br>
     * <b>Ctrl + J</b> billentyűkombinációt figyeli a felhasználó javaslatainak 
     * megtételéhez.
     */
    public Keylistener() {
        this.keyListener = new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                
                if (e.isControlDown() && 
                    e.getKeyChar() != 'j' &&
                    e.getKeyCode() == KeyEvent.VK_J) {  // or 74
                    
                    Icon icon = new ImageIcon(getClass()
                        .getResource("/hu/gaborkolozsy/icons/pencil.png"));

                    /** Itt nem kell de lehetne egy kombo box is. */
                    //String[] empty = {"fordítás", "helyesírás"}; 

                    boolean forma = false;
                    while(!forma) {
                        // ha a vége empty, empty[0], akkor egy kombo box lenne
                        String input = (String) JOptionPane.showInputDialog(
                                null, "<html><body>"
                              + "<b>problema=valami magyarazat </b>"
                              + "</body></html>", 
                                "Javaslat", JOptionPane.OK_CANCEL_OPTION, icon,
                                null, null);

                        if (input != null) {
                            if (Pattern.matches("\\S+=\\S.*", input)) {
                                proposalBox.addProposal(new Proposal(input));
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
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }
    
    /**
     * Visszaad egy {@code KeyListener} objektumot.
     * @return {@code KeyListener}
     */
    public KeyListener getKeylistener() {
        return keyListener;
    }
    
    /**
     * Visszaad egy javaslat tárolót.
     * @return {@code ProposalBox}
     */
    public ProposalBox getProposalBox() {
        ProposalBox temporary = new ProposalBox();
        if (!proposalBox.isProposalListEmpty()) {
            ListIterator<Proposal> it = proposalBox.getProposalList()
                                                   .listIterator();
            while (it.hasNext()) {
                temporary.addProposal(it.next()); 
            }
            proposalBox.clearProposalList();
        }
        return temporary;
    }
}
