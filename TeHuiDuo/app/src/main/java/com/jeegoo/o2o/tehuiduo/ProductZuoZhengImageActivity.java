package com.jeegoo.o2o.tehuiduo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;

import java.util.ArrayList;

public class ProductZuoZhengImageActivity extends AppCompatActivity implements ProductImageFragment.OnFragmentInteractionListener{
    public static final String EXTRA_IMAGE_URI = "image_uri";
    public static final String EXTRA_ZUOZHENG_IMAGE = "zuozheng_image";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    private static final int REQUEST_TAKE_PICTURE = 5;
    private static final int REQUEST_CHOOSE_PICTURE = 6;
    private String mImageFile;
    private String mImagePath;
    private String mImageName;
    private Product mProduct;
    private void savePhoto(final int requestCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(requestCode==REQUEST_TAKE_PICTURE){
                Bitmap bitmap = PictureUtils.decodeFileAsBitmap(mImageFile);
                Bitmap newBitmap = PictureUtils.getRotateBitmap(bitmap);
                PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 75, 75), mImagePath + "75*75/", mImageName + "75*75");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 180, 180), mImagePath + "180*180/", mImageName + "180*180");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 400, 400), mImagePath + "400*400/", mImageName + "400*400");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 2000, 2000), mImagePath + "2000*2000/", mImageName + "2000*2000");
                PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
            }else{
                    Bitmap bitmap = PictureUtils.decodeFileAsBitmap(mImageFile);
                    PictureUtils.savePhotoToSDCard(bitmap , mImagePath, mImageName);
                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap , 75, 75), mImagePath + "75*75/", mImageName + "75*75");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap , 180, 180), mImagePath + "180*180/", mImageName + "180*180");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap , 400, 400), mImagePath + "400*400/", mImageName + "400*400");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap , 2000, 2000), mImagePath + "2000*2000/", mImageName + "2000*2000");
                    PictureUtils.savePhotoToSDCard(bitmap , mImagePath, mImageName);
                }

        }}).start();
    }

    private void showDialog(String content, final boolean isZuozhengImage, final int index) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ProductZuoZhengImageActivity.this);
        MaterialDialog materialDialog = builder.title(R.string.dialog_title_image).content(content).positiveText(R.string.dialog_postive_text).negativeText(R.string.dialog_negative_text).callback(new MaterialDialog.Callback() {
            @Override
            public void onPositive(MaterialDialog materialDialog) {
//                Intent intent = new Intent(getActivity(),ImageChooseActivity.class);
//                startActivityForResult(intent, REQUEST_TAKE_BIG_PICTURE);//or TAKE_SMALL_PICTURE
                Intent intent = new Intent(ProductZuoZhengImageActivity.this, CameraActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                intent.putExtra(EXTRA_IMAGE_INDEX, index);
                startActivityForResult(intent, REQUEST_TAKE_PICTURE);
            }

            @Override
            public void onNegative(MaterialDialog materialDialog) {
                Intent intent = new Intent(ProductZuoZhengImageActivity.this, ImageChooseActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                startActivityForResult(intent, REQUEST_CHOOSE_PICTURE);
            }
        }).build();
        materialDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_zuo_zheng_image);
        mProduct = UserLab.get(this).getCurrentProduct();
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_product_zuo_zheng_image);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        if(mProduct.getImageDefaultId()==null){
            showDialog(getResources().getString(R.string.dialog_content_zuozheng_warning),true,0);
        }else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.container);
            if (fragment == null) {
                fragment = ProductImageFragment.newInstance(mProduct.getImageDefaultId(), 0);
                fm.beginTransaction().add(R.id.container, fragment).commit();
            }
        }
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProduct.setImageDefaultId(mImageFile);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_zuo_zheng_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_zuozheng_image) {
            if(mImageFile!=null||mProduct.getImageDefaultId()!=null) {
                Toast.makeText(this,R.string.dialog_content_zuozheng_max,Toast.LENGTH_SHORT).show();
            }else{
                showDialog(getResources().getString(R.string.dialog_content_zuozheng_warning),true,0);
            }
            return true;
        }
        if (id == R.id.action_save) {
            mProduct.setImageDefaultId(mImageFile);
            finish();
            return true;
        }
        if (id == R.id.action_cancle) {
            finish();
            return true;
        }
        if (id == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_TAKE_PICTURE:
                boolean isTakeZuoZhengImage = false;
                try {
                    isTakeZuoZhengImage = data.getBooleanExtra(EXTRA_ZUOZHENG_IMAGE, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isTakeZuoZhengImage) {
                    String filename = data.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
                    String path = data.getStringExtra(CameraFragment.EXTRA_PHOTO_PATH);
                    String imageName = data.getStringExtra(CameraFragment.EXTRA_PHOTO_NAME);
                    mImageFile = filename;
                    mImagePath = path;
                    mImageName = imageName;
                    savePhoto(REQUEST_TAKE_PICTURE);
                    FragmentManager fm = getSupportFragmentManager();
                    Fragment fragment = fm.findFragmentById(R.id.container);
                    if (fragment == null) {
                        fragment = ProductImageFragment.newInstance(mImageFile, 0);
                        fm.beginTransaction().add(R.id.container, fragment).commit();
                    }
                }
                break;
            case REQUEST_CHOOSE_PICTURE:
                boolean isChooseZuoZhengImage = false;
                try {
                    isChooseZuoZhengImage = data.getBooleanExtra(EXTRA_ZUOZHENG_IMAGE, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isChooseZuoZhengImage) {
                    ArrayList<String> uri = data.getStringArrayListExtra(EXTRA_IMAGE_URI);
                    for(String imageUri: uri){
                        mImageFile = imageUri;
                        mImagePath = imageUri.substring(0,imageUri.lastIndexOf("/"));
                        mImageName = imageUri.substring(imageUri.lastIndexOf("/")+1,imageUri.length());                     savePhoto(REQUEST_CHOOSE_PICTURE);
                    }
                    savePhoto(REQUEST_CHOOSE_PICTURE);
                    for (int i = 0; i < uri.size(); i++) {
                        if (uri.get(i) != null) {
                            FragmentManager fm = getSupportFragmentManager();
                            Fragment fragment = fm.findFragmentById(R.id.container);
                            if (fragment == null) {
                                fragment = ProductImageFragment.newInstance(uri.get(i), 0);
                                fm.beginTransaction().add(R.id.container, fragment).commit();
                            }
                        }
                    }
                }
                break;
        }

    }
    @Override
    public void onFragmentInteraction(int commond, int index) {

    }
}
