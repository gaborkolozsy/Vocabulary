/*
 * Copyright (c) 2016, Gábor Kolozsy. All rights reserved.
 * 
 */
package hu.gaborkolozsy.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Can query the value by the specified key from the {@code .ini} file.
 * 
 * @author Kolozsy Gábor
 * 
 * @see java.io.File
 * @see java.io.FileInputStream
 * @see java.io.FileNotFoundException
 * @see java.io.FileOutputStream
 * @see java.io.IOException
 * @see java.util.Properties
 * @see java.util.Set
 * @since 0.1.0
 */
public class Config {
    
    /**
     * A new {@code Properties} object.
     */
    private final Properties properties = new Properties();
    
    /**
     * Save a key-value pair in the specified config file.
     * @param key the key
     * @param value the value
     * @param fileName the file name
     * @param text the sort text as a <code>String</code>
     * @throws IOException if data not stored
     */
    public void saveValue(String key, String value, String fileName, String text) 
            throws IOException {
        
        properties.setProperty(key, value);
        properties.store(new FileOutputStream(fileName), text);
    }
    
    /**
     * Return the key set.
     * @param fileName the file name
     * @return the key set from the specified file
     * @throws IOException if data not loaded
     */
    public Set<Object> getSet(String fileName) throws IOException {
        properties.load(new FileInputStream(fileName));
        return properties.keySet();
    }
    
    /**
     * Returns the value by the specified key from the specified file. 
     * @param key the key
     * @param fileName the file name
     * @return the value by the specified key as a {@code String}
     * @throws IOException if data not loaded
     */
    public String getValue(String key, String fileName) throws IOException {
        properties.load(new FileInputStream(fileName));
        return properties.getProperty(key);
    }
    
    /**
     * @deprecated 
     * Returns <b>true</b> if and only if the specified object is a key 
     * in this hashtable by specified file.
     * @param key the key
     * @param fileName the file name
     * @return <b>true</b> if the properties list contains this specified key<br>
     * <b>false</b> otherwise
     * @throws FileNotFoundException if file not exists
     * @throws IOException if data not loaded
     * @throws NullPointerException if the key is {@code null}
     */
    @Deprecated
    public boolean isKey(String key, String fileName) throws IOException {
        properties.load(new FileInputStream(fileName));
        return properties.containsKey(key);
    }
    
    /**
     * @deprecated 
     * Return <b>true</b> if the specified file exists, <b>false</b> otherwise
     * @param fileName the file name
     * @return <b>true</b> if the specified file exists
     */
    @Deprecated
    public boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }
    
    /**
     * Clear this properties.
     */
    public void clear() {
        properties.clear();
    }
}
