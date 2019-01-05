/**
 *
 */
package com.hawkore.ignite.examples.entities.simple;

import java.io.Serializable;

/**
 * Entity 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int key;
    
    private String data;
    
    /**
     * 
     */
    public Entity() {
       
    }

    /**
     * 
     * @param key
     * @param data
     */
    public Entity(int key, String data) {
        super();
        this.key = key;
        this.data = data;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }
    
    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }
    
    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
    
    /**
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }
    
    
}
