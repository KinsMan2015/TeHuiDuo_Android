package com.jeegoo.o2o.tehuiduo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeegoo.o2o.tehuiduo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends CommonAdapter<String> {
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static ArrayList<String> mSelectedImage = new ArrayList<String>();
    public static ArrayList<String> mSelectedZuoZhengImage = new ArrayList<String>();
    private Context mContext;
    /**
     * 文件夹路径
     */
    private String mDirPath;
    private Button mImageButton;
    private boolean isZuoZhengImage;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId, Button savebutton,
                     Boolean isZuoZhengImage, String dirPath) {
        super(context, mDatas, itemLayoutId);
        mContext = context;
        this.mDirPath = dirPath;
        this.mImageButton = savebutton;
        this.isZuoZhengImage = isZuoZhengImage;
    }

    private void updateImage(ArrayList<String> selectedImages, ImageView mImageView, ImageView mSelect, String item,boolean isZuoZhengImage) {

        // 已经选择过该图片
        if (selectedImages.contains(mDirPath + "/" + item)) {
            selectedImages.remove(mDirPath + "/" + item);
            mSelect.setImageResource(R.drawable.picture_unselected);
            mImageView.setColorFilter(null);
            if (isZuoZhengImage) {
                mImageButton.setText("确认"+"("+selectedImages.size() + "/" + "1"+")");
            } else {
                mImageButton.setText("确认"+"("+selectedImages.size() + "/" + "4"+")");
            }
        } else       // 未选择该图片
        {
            if(isZuoZhengImage){
                if(selectedImages.size()==1){
                    Toast.makeText(mContext, R.string.toast_max_zuozheng_images, Toast.LENGTH_SHORT).show();
                }else {
                    selectedImages.add(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                    mImageButton.setText("确认"+ "("+selectedImages.size() + "/" + "1"+")");
                }
            }else{
                if(selectedImages.size()==4){
                    Toast.makeText(mContext, R.string.toast_max_images, Toast.LENGTH_SHORT).show();
                }else {
                    selectedImages.add(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                    mImageButton.setText("确认"+"("+selectedImages.size() + "/" + "4"+")");
                }
            }
        }
    }

    @Override
    public void convert(final com.jeegoo.o2o.tehuiduo.util.ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
        //设置no_selected
        helper.setImageResource(R.id.id_item_select,
                R.drawable.picture_unselected);
        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);
        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                if (isZuoZhengImage) {
                    updateImage(mSelectedZuoZhengImage, mImageView, mSelect, item,isZuoZhengImage);
                } else {
                    updateImage(mSelectedImage, mImageView, mSelect, item,isZuoZhengImage);
                }


            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.drawable.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }

    }
}
