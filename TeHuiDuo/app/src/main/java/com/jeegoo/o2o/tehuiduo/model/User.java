package com.jeegoo.o2o.tehuiduo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xintong on 2015/7/17.
 */
public class User implements Serializable{
    private String mUserName;
    private String mPassWord;
    private Date mDate;
    private String mToken;
    private ArrayList<Shop> mShops;
    public User(){
        mShops = new ArrayList<Shop>();
    }
    public User(String username,String password ){
        this.mUserName = username;
        this.mPassWord = password;
        mShops = new ArrayList<Shop>();
    }
    public void addShop(Shop shop){
        mShops.add(shop);
    }
    public Shop getShop(String id){
        for(Shop shop:mShops){
            if(shop.getShopId().equals(id)){
                return shop;
            }
        }
        return null;
    }
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public ArrayList<Shop> getShops() {
        return mShops;
    }

    public void setShops(ArrayList<Shop> shops) {
        mShops = shops;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }
}
