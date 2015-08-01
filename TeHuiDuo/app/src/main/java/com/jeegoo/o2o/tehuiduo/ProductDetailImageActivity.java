package com.jeegoo.o2o.tehuiduo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailImageActivity extends ActionBarActivity implements ProductImageFragment.OnFragmentInteractionListener {
    private static final String TAG = "ImageActivity";
    public static final String EXTRA_IMAGE_URI = "image_uri";
    public static final String EXTRA_ZUOZHENG_IMAGE = "zuozheng_image";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    private static final int REQUEST_TAKE_PICTURE = 5;
    private static final int REQUEST_CHOOSE_PICTURE = 6;
    private int IMAGE_COUNT = 0;
    private String mImageFile;
    private String mImagePath;
    private String mImageName;


    private ArrayList<String> mImageUri;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private int mIndex = -1;
    private int mCommond = -1;
    private boolean isFirst = true;
    private Product mProduct;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mFloatingActionButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_save_white_48dp));
                    return true;
            }
            return false;
        }
    });

    private void savePhoto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = PictureUtils.decodeFileAsBitmap(mImageFile);
                Bitmap newBitmap = PictureUtils.getRotateBitmap(bitmap);
                PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 75, 75), mImagePath + "75*75/", mImageName + "75*75");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 180, 180), mImagePath + "180*180/", mImageName + "180*180");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 400, 400), mImagePath + "400*400/", mImageName + "400*400");

                PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 2000, 2000), mImagePath + "2000*2000/", mImageName + "2000*2000");
                PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
            }
        }).start();
    }
    private void showDialog(String content, final boolean isZuozhengImage, final int index) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ProductDetailImageActivity.this);
        MaterialDialog materialDialog = builder.title(R.string.dialog_title_image).content(content).positiveText(R.string.dialog_postive_text).negativeText(R.string.dialog_negative_text).callback(new MaterialDialog.Callback() {
            @Override
            public void onPositive(MaterialDialog materialDialog) {
//                Intent intent = new Intent(getActivity(),ImageChooseActivity.class);
//                startActivityForResult(intent, REQUEST_TAKE_BIG_PICTURE);//or TAKE_SMALL_PICTURE
                Intent intent = new Intent(ProductDetailImageActivity.this, CameraActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                intent.putExtra(EXTRA_IMAGE_INDEX, index);
                startActivityForResult(intent, REQUEST_TAKE_PICTURE);
            }

            @Override
            public void onNegative(MaterialDialog materialDialog) {
                Intent intent = new Intent(ProductDetailImageActivity.this, ImageChooseActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                startActivityForResult(intent, REQUEST_CHOOSE_PICTURE);
            }
        }).build();
        materialDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_image);
        //mProduct = (Product) getIntent().getSerializableExtra(EditProductFragment.EXTRA_PRODUCT);
        mProduct = UserLab.get(this).getCurrentProduct();
        if(mProduct.getImageNameId()!=null) {
            mImageUri = mProduct.getImageNameId();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_product_detail_image);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ProductDetailImageActivity.FragmentAdapter adapter =
                new ProductDetailImageActivity.FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        if(mProduct.getImageNameId()!=null){
            for(int i = 0;i<mProduct.getImageNameId().size();i++){
                String path = mProduct.getImageNameId().get(i);
                ProductImageFragment fragment = ProductImageFragment.newInstance(path, i);
                fragments.add(fragment);
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        }
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getAdapter().getCount() == 4) {
                    if (isFirst) {
                        isFirst = false;
                        Message msg = new Message();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                        Snackbar.make(view, R.string.snackbar_message, Snackbar.LENGTH_LONG)
                                .setAction(R.string.snackbar_action_title, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mProduct.setImageNameId(mImageUri);
                                        finish();
                                    }
                                }).show();
                    } else {
//                        Intent i = new Intent();
//                        if(mImageUri!=null) {
//                            i.putExtra(CreateProductFragment.EXTRA_IMAGE_URI, mImageUri);
//                        }
//                        setResult(Activity.RESULT_OK, i);
                        mProduct.setImageNameId(mImageUri);
                        finish();
                    }

                }
                if (mViewPager.getAdapter().getCount() < 4) {

                    showDialog(getResources().getString(R.string.dialog_content_choose),false,mViewPager.getAdapter().getCount()+1);
                }
            }

        });
        if (mViewPager.getAdapter().getCount() == 0) {
            showDialog(getResources().getString(R.string.dialog_content_warning),false,1);
        }

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
                    savePhoto();
                } else {
                    String filename = data.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
                    String path = data.getStringExtra(CameraFragment.EXTRA_PHOTO_PATH);
                    String imageName = data.getStringExtra(CameraFragment.EXTRA_PHOTO_NAME);
                    mImageFile = filename;
                    mImagePath = path;
                    mImageName = imageName;
                    savePhoto();
                    int index = data.getIntExtra(EXTRA_IMAGE_INDEX, 0);
                    if (filename != null) {
                        Log.i(TAG, "filename: " + filename);
                        mImageUri.add(IMAGE_COUNT, filename);
                        IMAGE_COUNT++;
                        ProductImageFragment fragment = ProductImageFragment.newInstance(filename, (fragments.size() + 1));
                        fragments.add(fragment);
                        mViewPager.getAdapter().notifyDataSetChanged();
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
                    Log.i(TAG, "isZuoZhengImage is " + uri.size());
                    for (int i = 0; i < uri.size(); i++) {
                        if (uri.get(i) != null) {
                            mProduct.setImageDefaultId(uri.get(i));
//                            mImageViews.get(0).setImageBitmap(PictureUtils.decodeFileAsBitmap(mProduct.getImageDefaultId()));
                        }
                    }
                } else {
                    ArrayList<String> uri = data.getStringArrayListExtra(EXTRA_IMAGE_URI);
                    try {
                        if (uri != null) {
                            for (int i = 0; i < uri.size(); i++) {
                                if (uri.get(i) != null) {
                                    mImageUri.add(IMAGE_COUNT, uri.get(i));
                                    IMAGE_COUNT++;
                                    ProductImageFragment fragment = ProductImageFragment.newInstance(uri.get(i), (fragments.size() + 1));
                                    fragments.add(fragment);
                                    mViewPager.getAdapter().notifyDataSetChanged();
                                }
                            }
                            //mProduct.setImageNameId(mImageUri);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            mProduct.setImageNameId(mImageUri);
            finish();
            return true;
        }
        if (id == R.id.action_cancle) {
            finish();
            return true;
        }
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int commond,int index) {
        switch (commond){
            case 0:
//                mCommond = commond;
//                mIndex = index;
//                showDialog(getResources().getString(R.string.dialog_content_choose));
                break;
            case 1:
//                fragments.remove(index);
//                mViewPager.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


    }
}
