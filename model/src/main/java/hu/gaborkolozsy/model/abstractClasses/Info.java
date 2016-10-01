/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model.abstractClasses;

/**
 * Egy információs ablak tartalma.
 * 
 * @author Kolozsy Gábor
 * 
 * @see hu.gaborkolozsy.model.abstractClasses.ext.About
 * @see hu.gaborkolozsy.model.abstractClasses.ext.AddingItemToRaceComboBox
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Advice
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Congratulation
 * @see hu.gaborkolozsy.model.abstractClasses.ext.CutItemFromRaceComboBox
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Extra
 * @see hu.gaborkolozsy.model.abstractClasses.ext.Greeting
 * @since 2.0.0
 */
public abstract class Info {
    
    /** Az információs ablak tartalma. */
    protected String content;
    
    /** 
     * Visszaadja a <b>content</b> adattagot {@code String} formájába.
     * @return <b>content</b>
     */
    public String getContent() {
        return content;
    }

    /** 
     * Beállitja a <b>content</b> adattagot.
     * @param content a szöveges tartalom
     */
    public void setContent(String content) {
        this.content = content;
    }
}
