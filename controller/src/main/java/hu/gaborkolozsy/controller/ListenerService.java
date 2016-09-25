/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.listener.Mouselistener;
import java.awt.event.MouseListener;

/**
 * Esemény figyelők.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.awt.event.MouseListener
 * @see javax.swing.event.HyperlinkListener
 * @since 2.0.0
 */
public class ListenerService {
    
    /** Egér figyelő objektum. */
    private final Mouselistener mouseListener = new Mouselistener();
    
    /**
     * Visszaad egy {@code MouseListener} objektumot.
     * @return {@code MouseListener}
     */
    public MouseListener getMouseListenerTimeDelay() {
        return mouseListener.getMouseListenerTimeDelay();
    }
}