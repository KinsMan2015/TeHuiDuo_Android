package com.jeegoo.o2o.tehuiduo.model;

import java.io.Serializable;

/**
 * Created by xintong on 2015/7/17.
 */
public class StyleMessage implements Serializable{
    private String mProductID;
    private String mStyleName;
    private String mColor;
    private String mSize;
    private String mCount;
    public StyleMessage(){

    }
    public StyleMessage(String id){
        mProductID  = id;
    }
    public String getProductID() {
        return mProductID;
    }
    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getStyleName() {
        return mStyleName;
    }

    public void setStyleName(String styleName) {
        mStyleName = styleName;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }
}
