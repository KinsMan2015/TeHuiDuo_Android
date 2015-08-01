package com.jeegoo.o2o.tehuiduo.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by xintong on 2015/7/17.
 */
public class UserLab {
    private static UserLab sUserLab;
    private ArrayList<User> mUsers;
    private Context mContext;
    private User mCurrentUser;
    private Shop mCurrentShop;
    private Product mCurrentProduct;
    private UserLab(Context context){
        mContext = context;
        mUsers = new ArrayList<User>();
        mCurrentUser = new User();
        mCurrentShop = new Shop();
        mCurrentProduct = new Product();
    }
    public static UserLab get(Context context){
        if(sUserLab==null){
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }
    public void addUser(User user){
        mUsers.add(user);
    }
    public void delete(User user){
        mUsers.remove(user);
    }
    public ArrayList<User> getUsers(){
        return mUsers;
    }
    public User getUser(String username){
        for(User user:mUsers){
            if(user.getUserName().equals(username)){
                return  user;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }

    public Shop getCurrentShop() {
        return mCurrentShop;
    }

    public void setCurrentShop(Shop currentShop) {
        mCurrentShop = currentShop;
    }

    public Product getCurrentProduct() {
        Log.i("UserLab is " , "This is current product" + mCurrentProduct.getProductId());
        return mCurrentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        mCurrentProduct = currentProduct;
    }
}
