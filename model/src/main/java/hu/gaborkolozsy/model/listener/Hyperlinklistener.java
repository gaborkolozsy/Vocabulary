/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.listener;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * {@code HyperlinkListener} objektum.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.awt.Desktop
 * @see java.io.IOException
 * @see java.net.URI
 * @see java.net.URISyntaxException
 * @see javax.swing.JOptionPane
 * @see javax.swing.event.HyperlinkEvent
 * @see javax.swing.event.HyperlinkListener
 * @since 2.0.0
 */
public class Hyperlinklistener {
    
    /** {@code HyperlinkListener}. */
    private final HyperlinkListener hyperLinkListener;
    
    /** A {@code HyperlinkListener} konstruktora. */
    public Hyperlinklistener() {
        this.hyperLinkListener = (HyperlinkEvent e) -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                /** Process the click event on the link.
                 * (for example with java.awt.Desktop.getDesktop().browse())
                 */
                try {
                    // ha url, akkor megnyitja az oldalt a böngészöbe
                    if (e.getURL() != null) {
                        Desktop.getDesktop()
                                .browse(e.getURL().toURI());
                    } else {
                        // ha nem, akkor a email klienst nyitja meg
                        Desktop.getDesktop()
                                .mail(new URI("mailto:" + e.getDescription()
                                        + "?subject=Vocabulary&body=Hello!"));
                    }
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    /**
     * Visszaad egy {@code HyperlinkListener} objektumot.
     * @return {@code HyperlinkListener}
     */
    public HyperlinkListener getHyperLinkListener() {
        return hyperLinkListener;
    }
}
