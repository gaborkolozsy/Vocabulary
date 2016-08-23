/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.ProposalBox;
import hu.gaborkolozsy.model.listener.Hyperlinklistener;
import hu.gaborkolozsy.model.listener.Keylistener;
import hu.gaborkolozsy.model.listener.Mouselistener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.event.HyperlinkListener;

/**
 * Esemény figyelők.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.listener.Hyperlinklistener
 * @see hu.gaborkolozsy.model.listener.Keylistener
 * @see hu.gaborkolozsy.model.listener.Mouselistener
 * @see java.awt.event.KeyListener
 * @see java.awt.event.MouseListener
 * @see javax.swing.event.HyperlinkListener
 * @since 2.0.0
 */
public class ListenerService {
    
    /** Hiperlink figyelő objektum. */
    private final Hyperlinklistener hyperlinkListener = new Hyperlinklistener();
    /** Egér figyelő objektum. */
    private final Mouselistener mouseListener = new Mouselistener();
    /** Billentyűzetfigyelő objektum. */
    private final Keylistener keyListener = new Keylistener();
    
    /**
     * Visszaad egy {@code HyperlinkListener} objektumot.
     * @return {@code HyperlinkListener}
     */
    public HyperlinkListener getHyperlinkListener() {
        return hyperlinkListener.getHyperLinkListener();
    }
    
    /**
     * Visszaad egy {@code MouseListener} objektumot.
     * @return {@code MouseListener}
     */
    public MouseListener getMouseListenerTimeDelay() {
        return mouseListener.getMouseListenerTimeDelay();
    }
    
    /**
     * Visszaad egy {@code KeyListener} objektumot.
     * @return {@code KeyListener}
     */
    public KeyListener getKeyListener() {
        return keyListener.getKeylistener();
    }
    
    /**
     * Visszaad egy {@code ProposalBox} objektumot.
     * @return {@code ProposalBox}
     */
    public ProposalBox getProposalBox() { 
        return keyListener.getProposalBox();
    }
}