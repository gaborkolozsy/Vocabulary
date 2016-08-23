/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

/**
 * A felhasználó javaslata.
 * Ez lehet fordítási, helyesírási vagy egyéb javaslat.<br><br>
 * 
 * Átmeneti megoldás a teszteléskor fellelt hibák dokumentálásának
 * megkönnyítésére.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.ProposalBox
 * @since 2.0.0
 */
public class Proposal {
    
    /** A content. */
    private final String content;

    /**
     * Konstruktor.
     * 
     * @param proposal a felhasználó javaslata
     */
    public Proposal(String proposal) {
        this.content = proposal;
    }

    /**
     * Visszaadja a <b>content</b> adattagot.
     * @return <b>content</b>
     */
    public String getContent() {
        return content;
    }
}
