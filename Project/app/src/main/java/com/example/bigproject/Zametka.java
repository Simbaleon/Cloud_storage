package com.example.bigproject;

import java.io.Serializable;

/**
 * Класс заметки
 */
public class Zametka implements Serializable {

    /**
     * Instantiates a new Zametka.
     */
    Zametka(){
        data = "";
        name = "";
        value = "";
        bitmap = "";
    }


    private String data,name, value;
    private String bitmap;

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets bitmap.
     *
     * @return the bitmap
     */
    public String getBitmap() {
        return bitmap;
    }

    /**
     * Sets bitmap.
     *
     * @param bitmap the bitmap
     */
    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }


}
