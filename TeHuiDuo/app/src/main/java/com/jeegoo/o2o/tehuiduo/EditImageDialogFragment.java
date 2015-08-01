package com.jeegoo.o2o.tehuiduo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.jeegoo.o2o.tehuiduo.bean.ToneLayer;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.FileUtils;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;
import com.rey.material.widget.Slider;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by root on 15-7-12.
 */
public class EditImageDialogFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener, Slider.OnPositionChangeListener {
    private static final String TAG = "EditImageFragment";
    public static final String EXTRA_IMAGE_PATH = "com.bignerdranch.android.criminalintent.image_path";
    public static final String ARG_INDEX = "index";
    private ImageView mImageView;
    private Button mSaveButton, mCancleButton;
    private Uri mImageUri;
    private Bitmap mImageBitmap;
    private ToneLayer mToneLayer;
    private int mIndex;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ToneLayer.FLAG_SATURATION:
                    //  bitmapDrawable = new BitmapDrawable(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_SATURATION));`
                    mImageView.setImageBitmap(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_SATURATION));
                    // mImageView.setImageDrawable(bitmapDrawable);
                    return true;
                case ToneLayer.FLAG_LUM:
                    //  bitmapDrawable = new BitmapDrawable(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_LUM));
                    mImageView.setImageBitmap(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_LUM));
                    //  mImageView.setImageDrawable(bitmapDrawable);
                    return true;
                case ToneLayer.FLAG_HUE:
                    // bitmapDrawable = new BitmapDrawable(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_HUE));
                    mImageView.setImageBitmap(mToneLayer.handleImage(mImageBitmap, ToneLayer.FLAG_HUE));
                    // mImageView.setImageDrawable(bitmapDrawable);
                    return true;
            }
            return false;
        }
    });

    public static EditImageDialogFragment newInstance(String imagePath,int index) {
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE_PATH, imagePath);
        args.putInt(ARG_INDEX,index);
        EditImageDialogFragment fragment = new EditImageDialogFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getString(EXTRA_IMAGE_PATH)!=null){
            mImageUri = Uri.parse(getArguments().getString(EXTRA_IMAGE_PATH));
        }
        mIndex = getArguments().getInt(ARG_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_image, container, false);
        mToneLayer = new ToneLayer(getActivity());
        mImageView = (ImageView) v.findViewById(R.id.img_view);
        String path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
        if(path!=null){
            mImageBitmap = PictureUtils.getThumbNailBitmap(path);
        }
        if (mImageBitmap != null) {
            mImageView.setImageBitmap(mImageBitmap);
        }
       // Picasso.with(getActivity()).load(new File(path)).into(mImageView);
        ((LinearLayout) v.findViewById(R.id.tone_view)).addView(mToneLayer.getParentView());
        ArrayList<Slider> seekBars = mToneLayer.getSliders();
        for (int i = 0, size = seekBars.size(); i < size; i++) {
            //     seekBars.get(i).setOnSeekBarChangeListener(this);
            seekBars.get(i).setOnPositionChangeListener(this);
        }
        mSaveButton = (Button) v.findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = null;
                String username = UserLab.get(getActivity()).getCurrentUser().getUserName();
                String productId = UserLab.get(getActivity()).getCurrentProduct().getProductId();
                if(mToneLayer.getBmp()!=null) {
                    path = PictureUtils.savePhotoToSDCard(mToneLayer.getBmp(), FileUtils.createImageFile(username, productId), "ABCDEF" + (mIndex + 1));
                    UserLab.get(getActivity()).getCurrentProduct().setImageNameId(mIndex,path);
                }
                dismiss();
            }
        });
        mCancleButton = (Button) v.findViewById(R.id.cancleButton);
        mCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int flag = (Integer) seekBar.getTag();
        Message msg = new Message();
        switch (flag) {
            case ToneLayer.FLAG_SATURATION:
                mToneLayer.setSaturation(progress);
                msg.what = ToneLayer.FLAG_SATURATION;
                mHandler.sendMessage(msg);
                break;
            case ToneLayer.FLAG_LUM:
                mToneLayer.setLum(progress);
                msg.what = ToneLayer.FLAG_LUM;
                mHandler.sendMessage(msg);
                break;
            case ToneLayer.FLAG_HUE:
                mToneLayer.setHue(progress);
                msg.what = ToneLayer.FLAG_HUE;
                mHandler.sendMessage(msg);
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
        int flag = (Integer) slider.getTag();
        Message msg = new Message();
        switch (flag) {
            case ToneLayer.FLAG_SATURATION:
                mToneLayer.setSaturation(i);
                msg.what = ToneLayer.FLAG_SATURATION;
                mHandler.sendMessage(msg);
                break;
            case ToneLayer.FLAG_LUM:
                mToneLayer.setLum(i);
                msg.what = ToneLayer.FLAG_LUM;
                mHandler.sendMessage(msg);
                break;
            case ToneLayer.FLAG_HUE:
                mToneLayer.setHue(i);
                msg.what = ToneLayer.FLAG_HUE;
                mHandler.sendMessage(msg);
                break;
        }
    }
}
