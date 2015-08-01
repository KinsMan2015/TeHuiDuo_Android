package com.jeegoo.o2o.tehuiduo.model;

import java.io.Serializable;

/**
 * Created by xintong on 2015/7/17.
 */
public class ColorTable implements Serializable{
    private String mColorId;
    private String mColorName;
    private String Color;
    public String getColorId() {
        return mColorId;
    }

    public void setColorId(String colorId) {
        mColorId = colorId;
    }

    public String getColorName() {
        return mColorName;
    }

    public void setColorName(String colorName) {
        mColorName = colorName;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
