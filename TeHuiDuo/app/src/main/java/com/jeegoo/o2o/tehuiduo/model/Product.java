package com.jeegoo.o2o.tehuiduo.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xintong on 2015/7/17.
 */
public class Product implements Serializable {
    private static final String TAG = "Product";
    private String mProductId;
    private String mProductName;
    private String mProductPrice;
    private String mProductLocation;
    private String mProductState;
    private ArrayList<String> mImageNameId;
    private String mImageDefaultId;
    private String mProductKinds;
    private String mProductTime;
    private String mProductStartDateTime;
    private String mProductEndDateTime;
    private Date mStartDate;
    private Date mStartTime;
    private Date mEndDate;
    private Date mEndTime;
    private String mProductDescribe;
    private ArrayList<StyleMessage> mStyleMessages;
    private ArrayList<ColorTable> mColorTables;
    private ArrayList<SizeTable> mSizeTables;
    public Product(){
        mStyleMessages = new ArrayList<StyleMessage>();
        mColorTables = new ArrayList<>();
        mSizeTables = new ArrayList<>();
        mImageNameId = new ArrayList<>();
    }
    public Product(String id) {
        mProductId = id;
        mStyleMessages = new ArrayList<StyleMessage>();
        mColorTables = new ArrayList<>();
        mSizeTables = new ArrayList<>();
        mImageNameId = new ArrayList<>();
    }

    public void addStyleMessage(StyleMessage styleMessage) {
        mStyleMessages.add(styleMessage);
    }

    public StyleMessage getStyleMessage(String id, String styleName) {
        for (StyleMessage styleMessage : mStyleMessages) {
            if (styleMessage.getProductID().equals(id) && styleMessage.getStyleName().equals(styleName)) {
                return styleMessage;
            }
        }
        return null;
    }

    public String getProductId() {
        return mProductId;
    }


    public ArrayList<StyleMessage> getStyleMessages() {
        return mStyleMessages;
    }

    public void setStyleMessages(ArrayList<StyleMessage> styleMessages) {
        mStyleMessages = styleMessages;
    }

    public ArrayList<SizeTable> getSizeTables() {
        return mSizeTables;
    }

    public void setSizeTables(ArrayList<SizeTable> sizeTables) {
        mSizeTables = sizeTables;
    }

    public ArrayList<ColorTable> getColorTables() {
        return mColorTables;
    }

    public void setColorTables(ArrayList<ColorTable> colorTables) {
        mColorTables = colorTables;
    }

    public String getProductDescribe() {
        return mProductDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        mProductDescribe = productDescribe;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Date endTime) {
        mEndTime = endTime;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        mStartTime = startTime;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public String getProductEndDateTime() {
        return mProductEndDateTime;
    }

    public void setProductEndDateTime(String productEndDateTime) {
        mProductEndDateTime = productEndDateTime;
    }

    public String getProductStartDateTime() {
        return mProductStartDateTime;
    }

    public void setProductStartDateTime(String productStartDateTime) {
        mProductStartDateTime = productStartDateTime;
    }

    public String getProductTime() {
     return mProductTime;
    }

    public void setProductTime(String productTime) {
        mProductTime = productTime;
    }

    public String getProductKinds() {
        return mProductKinds;
    }

    public void setProductKinds(String productKinds) {
        mProductKinds = productKinds;
    }

    public String getImageDefaultId() {
        return mImageDefaultId;
    }

    public void setImageDefaultId(String imageDefaultId) {
        mImageDefaultId = imageDefaultId;
    }

    public ArrayList<String> getImageNameId() {
        return mImageNameId;
    }

    public void setImageNameId(ArrayList<String> imageNameId) {
        mImageNameId = imageNameId;
    }

    public void setImageNameId(int index,String id){
        mImageNameId.add(index,id);
        Log.i(TAG,"index is id is  " + index + "   " + id);
        Log.i(TAG,"mImageNameId is " + mImageNameId.get(index));
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductLocation() {
        return mProductLocation;
    }

    public void setProductLocation(String productLocation) {
        mProductLocation = productLocation;
    }

    public String getProductState() {
        return mProductState;
    }

    public void setProductState(String productState) {
        mProductState = productState;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }
}
