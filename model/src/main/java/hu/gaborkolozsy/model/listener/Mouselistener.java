/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import javax.swing.ToolTipManager;

/**
 * {@code MouseListener} objektum.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.awt.event.MouseAdapter
 * @see java.awt.event.MouseEvent
 * @see java.awt.event.MouseListener
 * @see java.util.concurrent.TimeUnit
 * @see javax.swing.ToolTipManager
 * @since 2.0.0
 */
public class Mouselistener {
    
    /** {@code MouseListener} a tooltip text megjelenési idejéhez. */
    private final MouseListener mouseListenerTimeDelay;
    
    /** A {@code MouseListener} konstruktora. */
    public Mouselistener() {
        this.mouseListenerTimeDelay = new MouseAdapter() {
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
        };
    }

    /**
     * Visszaad egy {@code MouseListener} objektumot.
     * @return {@code MouseListener}
     */
    public MouseListener getMouseListenerTimeDelay() {
        return mouseListenerTimeDelay;
    }
}
