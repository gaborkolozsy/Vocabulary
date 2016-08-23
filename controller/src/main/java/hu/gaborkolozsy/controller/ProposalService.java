/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.controller;

import hu.gaborkolozsy.model.Config;
import hu.gaborkolozsy.model.Proposal;
import hu.gaborkolozsy.model.ProposalBox;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code Proposal} objektumok kezelése.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.Config
 * @see hu.gaborkolozsy.model.Proposal
 * @see hu.gaborkolozsy.model.ProposalBox
 * @see java.io.IOException
 * @see java.time.LocalDateTime
 * @see java.time.format.DateTimeFormatter
 * @since 2.0.0
 */
public class ProposalService {
    
    /** {@code Config} objektum. */
    private final Config config = new Config();
    /** Az ideiglenesen bevezetett javaslat tároló a teszteléshez. */
    private ProposalBox proposalBox = new ProposalBox();
    
    /**
     * Beállítja a {@code ProposalBox} objektumot.
     * @param proposalBox a felhasználó javaslatait tartalmazó tároló
     */
    public void setProposalBox(ProposalBox proposalBox) {
        this.proposalBox = proposalBox;
    }
    
    /**
     * Menti a javaslatokat kilépéskor vagy nyelvek cseréje ill váltásakor.
     * <br><br>
     * Mac OS X-el ellentétben Windows operációs rendszeren a fájlnevek 
     * nem tartalmazhatják a következő karakterek egyikét sem:<br>
     * <b>{@code \ / * ? " :  < > |}</b>
     * @param combo a nyelvkombináció
     * @throws IOException fájl hiba esetén
     */
    public void save(String combo) throws IOException {
        config.clear();
        String file = "proposal(" + combo + ", " + LocalDateTime.now()
            .format(DateTimeFormatter
            .ofPattern("yyyy.MM.dd HHmmss")) + ").ini";
        
        for (Proposal p : proposalBox.getProposalList()) {
            String[] data = p.getContent().split("=");
            config.saveValue(data[0], data[1], file, "proposal");
        }
        
        proposalBox.clearProposalList();
    }
}
