/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 *
 */
package hu.gaborkolozsy.model.interfaces;

import java.util.List;

/**
 * Nyelvpárok interface. 
 * 
 * @author Kolozsy Gábor (kolozsygabor@gmail.com)
 * 
 * @see hu.gaborkolozsy.model.interfaces.impl.LanguageCombinationCombinationImpl
 * @since 2.3.0
 */
public interface Combination {
    
    /**
     * Megfordít egy {@code String}-et.
     * @return a megfordított {@code String}
     */
    String reverseCombo();
    
    /**
     * Listát készít.
     * @param list
     * @return a kész lista
     */
    List<String> makeList(List<String> list);
}
