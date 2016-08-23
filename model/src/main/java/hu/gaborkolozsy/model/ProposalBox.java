/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Proposal} objektumok tárolója.
 * A felhasználó által tett javaslatok tárolása annak mentéséig.
 * Átmeneti megoldás a teszteléskor fellelt hibák dokumentálásának 
 * megkönnyítésére.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Proposal
 * @see java.util.List
 * @see java.util.ArrayList
 * @since 2.0.0
 */
public class ProposalBox {
    
    /** A {@code Proposal} objektumok tárolója. */
    private final List<Proposal> proposalList = new ArrayList<>();

    /**
     * Hozzáad egy {@code Proposal} objektumot a tárolóhoz.
     * @param proposal {@code Proposal} objektum
     */
    public void addProposal(Proposal proposal) {
        proposalList.add(proposal);
    }
    
    /**
     * Visszaad egy {@code Proposal} listát. 
     * @return egy {@code Proposal} lista
     */
    public List<Proposal> getProposalList() {
        return proposalList;
    }
    
    /**
     * Igazat ad vissza ha a <b>proposalList</b> lista üres, hamisat máskülönben.
     * @return <b>true</b> ha a lista nem tartalmaz elemet
     */
    public boolean isProposalListEmpty() {
        return proposalList.isEmpty();
    }
    
    /**
     * Kiüriti a <b>proposalList</b> listát.
     */
    public void clearProposalList() {
        proposalList.clear();
    }
}
