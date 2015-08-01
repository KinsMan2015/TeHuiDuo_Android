package com.jeegoo.o2o.tehuiduo.model;

import java.io.Serializable;

/**
 * Created by xintong on 2015/7/17.
 */
public class SizeTable implements Serializable{
    private String mSizeId;
    private String mSizeKinds;
    private String mSizeName;

    public String getSizeId() {
        return mSizeId;
    }

    public void setSizeId(String sizeId) {
        mSizeId = sizeId;
    }

    public String getSizeKinds() {
        return mSizeKinds;
    }

    public void setSizeKinds(String sizeKinds) {
        mSizeKinds = sizeKinds;
    }

    public String getSizeName() {
        return mSizeName;
    }

    public void setSizeName(String sizeName) {
        mSizeName = sizeName;
    }
}
