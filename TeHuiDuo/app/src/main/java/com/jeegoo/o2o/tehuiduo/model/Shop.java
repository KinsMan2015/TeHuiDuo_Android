package com.jeegoo.o2o.tehuiduo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xintong on 2015/7/17.
 */
public class Shop implements Serializable{
    private String mProductState[];
    private String mShopId;
    private String mShopName;
    private String mShopAddress;
    private ArrayList<Product> mProductsFirst;
    private ArrayList<Product> mProductsSecond;
    private ArrayList<Product> mProductsThird;
    public Shop(){
        mProductState = new String[]{"0","1","2"};
        mProductsFirst = new ArrayList<Product>();
        mProductsSecond = new ArrayList<Product>();
        mProductsThird = new ArrayList<Product>();
    }
    public Shop(String id){
        mShopId = id;
        mProductState = new String[]{"0","1","2"};
        mProductsFirst = new ArrayList<Product>();
        mProductsSecond = new ArrayList<Product>();
        mProductsThird = new ArrayList<Product>();
    }
    public void addProduct(Product product,String productState){
        if(productState.equals("0")){
            mProductsFirst.add(product);
        }
        if(productState.equals("1")){
            mProductsSecond.add(product);
        }
        if(productState.equals("2")){
            mProductsThird.add(product);
        }
    }
    public void deleteProduct(int index,String productState){

        if(productState.equals("1")){
            mProductsSecond.remove(index);
        }
    }
    public void setProduct(int index,Product product,String productState){
        if(productState.equals("0")){
            mProductsFirst.set(index,product);
        }
        if(productState.equals("1")){
            mProductsSecond.set(index, product);
        }
        if(productState.equals("2")){
            mProductsThird.set(index,product);
        }
    }
    public Product getProduct(String id,String productState){
        if(productState.equals("0")){
            for(Product product:mProductsFirst){
                if(product.getProductId().equals(id)){
                    return product;
                }
            }
        }
        if(productState.equals("1")){
            for(Product product:mProductsSecond){
                if(product.getProductId().equals(id)){
                    return product;
                }
            }
        }
        if(productState.equals("2")){
            for(Product product:mProductsThird){
                if(product.getProductId().equals(id)){
                    return product;
                }
            }
        }
        return null;
    }

    public ArrayList<Product> getProductsFromState(String productState){
        if(productState.equals("0")){
            return mProductsFirst;
        }
        if(productState.equals("1")){
            return mProductsSecond;
        }
        if(productState.equals("2")){
            return mProductsThird;
        }
        return null;
    }
    public String[] getProductState() {
        return mProductState;
    }

    public void setProductState(String[] productState) {
        mProductState = productState;
    }

    public ArrayList<Product> getProductsThird() {
        return mProductsThird;
    }

    public void setProductsThird(ArrayList<Product> productsThird) {
        mProductsThird = productsThird;
    }

    public ArrayList<Product> getProductsSecond() {
        return mProductsSecond;
    }

    public void setProductsSecond(ArrayList<Product> productsSecond) {
        mProductsSecond = productsSecond;
    }

    public ArrayList<Product> getProductsFirst() {
        return mProductsFirst;
    }

    public void setProductsFirst(ArrayList<Product> productsFirst) {
        mProductsFirst = productsFirst;
    }

    public String getShopAddress() {
        return mShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        mShopAddress = shopAddress;
    }

    public String getShopName() {
        return mShopName;
    }

    public void setShopName(String shopName) {
        mShopName = shopName;
    }

    public String getShopId() {
        return mShopId;
    }

}
