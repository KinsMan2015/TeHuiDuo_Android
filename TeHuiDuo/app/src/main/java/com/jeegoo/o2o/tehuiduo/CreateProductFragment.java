package com.jeegoo.o2o.tehuiduo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.adapter.ListProductStyleRecyclerViewAdapter;
import com.jeegoo.o2o.tehuiduo.adapter.MyAdapter;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.StyleMessage;
import com.jeegoo.o2o.tehuiduo.util.DateUtils;
import com.jeegoo.o2o.tehuiduo.util.ImageLoader;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Date;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoaderFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProductFragment extends Fragment {
    public static final String EXTRA_IMAGE_URI = "image_uri";
    public static final String EXTRA_ZUOZHENG_IMAGE = "zuozheng_image";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_PRODUCT = "product";
    public static final int REQUEST_IMAGE_URI = 0;
    public static final int REQUEST_START_DATE = 1;
    public static final int REQUEST_START_TIME = 2;
    public static final int REQUEST_END_DATE = 3;
    public static final int REQUEST_END_TIME = 4;
    private static final int REQUEST_TAKE_PICTURE = 5;
    private static final int REQUEST_CHOOSE_PICTURE = 6;
    private static final String TAG = "CreateProductFragment";
    private static final String ARG_PRODUCT = "product";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private ArrayList<String> imageUri = new ArrayList<String>();
    private int IMAGE_COUNT = 0;
    private int IMAGE_INDEX = -1;
    private String mImageFile;
    private String mImagePath;
    private String mImageName;
    private boolean isBooleanRun = false;
    private boolean mImageStates[] = new boolean[]{false, false, false, false, false, false};
    private Product mProduct;
    private ArrayList<StyleMessage> mStyleMessages;
    private CubeImageView mProductImage0, mProductImage1, mProductImage2, mProductImage3, mProductImage4;
    private ArrayList<CubeImageView> mImageViews;
    private MaterialEditText mProductName;
    private MaterialEditText mProductPrice;
    private MaterialEditText mProductLocation;
    private MaterialEditText mProductKinds;
    private MaterialEditText mProductStartDate;
    private MaterialEditText mProductStartTime;
    private MaterialEditText mProductEndDate;
    private MaterialEditText mProductEndTime;
    private MaterialEditText mProductDescribe;
    private RecyclerView mRecyclerView;
    private FrameLayout mEmptyFrameLayout;
    private Button mAddStyleMessageButton;
    private OnFragmentInteractionListener mListener;
    private in.srain.cube.image.ImageLoader imageLoader;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProductFragment newInstance(Product product) {
        CreateProductFragment fragment = new CreateProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    private void savePhoto(final int requestCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (requestCode == REQUEST_TAKE_PICTURE) {
                    Bitmap bitmap = PictureUtils.decodeFileAsBitmap(mImageFile);
                    Bitmap newBitmap = PictureUtils.getRotateBitmap(bitmap);
                    PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 75, 75), mImagePath + "75*75/", mImageName + "75*75");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 180, 180), mImagePath + "180*180/", mImageName + "180*180");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 400, 400), mImagePath + "400*400/", mImageName + "400*400");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(newBitmap, 2000, 2000), mImagePath + "2000*2000/", mImageName + "2000*2000");
                    PictureUtils.savePhotoToSDCard(newBitmap, mImagePath, mImageName);
                } else {
                    Bitmap bitmap = PictureUtils.decodeFileAsBitmap(mImageFile);
                    PictureUtils.savePhotoToSDCard(bitmap, mImagePath, mImageName);
                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap, 75, 75), mImagePath + "75*75/", mImageName + "75*75");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap, 180, 180), mImagePath + "180*180/", mImageName + "180*180");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap, 400, 400), mImagePath + "400*400/", mImageName + "400*400");

                    PictureUtils.savePhotoToSDCard(PictureUtils.zoomBitmap(bitmap, 2000, 2000), mImagePath + "2000*2000/", mImageName + "2000*2000");
                    PictureUtils.savePhotoToSDCard(bitmap, mImagePath, mImageName);
                }
            }


        }).start();
    }

    private void showChooseDialog(final boolean isZuozhengImage, final int index) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        MaterialDialog materialDialog = builder.title(R.string.dialog_title).items(getResources().getStringArray(R.array.dialog_items)).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog materialDialog, int i, String s) {
                if (i == 0) {
                    //Toast.makeText(getActivity(), "替换", Toast.LENGTH_SHORT).show();
                    if (isZuozhengImage) {
                        showDialog(getResources().getString(R.string.dialog_content_choose), isZuozhengImage, index);
                        mImageViews.get(index).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_crop_original_black_48dp));
                        MyAdapter.mSelectedZuoZhengImage.clear();
                        mImageStates[index] = false;
                    } else {
                        showDialog(getResources().getString(R.string.dialog_content_choose), isZuozhengImage, index);
                        //  imageUri.remove(index - 1);
                        IMAGE_INDEX = index;
                        mImageViews.get(index+1).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_crop_original_black_48dp));
                        MyAdapter.mSelectedImage.remove(index);
                        mImageStates[index+1] = false;
                    }
                } else if (i == 1) {
                    //Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                    if (isZuozhengImage) {
                        mImageViews.get(index).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_crop_original_black_48dp));
                        MyAdapter.mSelectedZuoZhengImage.clear();
                        mImageStates[index] = false;
                    } else {
                        IMAGE_INDEX = index;
                        mImageViews.get(index+1).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_crop_original_black_48dp));
                        MyAdapter.mSelectedImage.remove(index);
                        mImageStates[index+1] = false;
                    }

                }
            }
        }).build();
        materialDialog.show();
    }

    private void showDialog(String content, final boolean isZuozhengImage, final int index) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        MaterialDialog materialDialog = builder.title(R.string.dialog_title_image).content(content).positiveText(R.string.dialog_postive_text).negativeText(R.string.dialog_negative_text).callback(new MaterialDialog.Callback() {
            @Override
            public void onPositive(MaterialDialog materialDialog) {
//                Intent intent = new Intent(getActivity(),ImageChooseActivity.class);
//                startActivityForResult(intent, REQUEST_TAKE_BIG_PICTURE);//or TAKE_SMALL_PICTURE
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                intent.putExtra(EXTRA_IMAGE_INDEX, index);
                startActivityForResult(intent, REQUEST_TAKE_PICTURE);
            }

            @Override
            public void onNegative(MaterialDialog materialDialog) {
                Intent intent = new Intent(getActivity(), ImageChooseActivity.class);
                intent.putExtra(EXTRA_ZUOZHENG_IMAGE, isZuozhengImage);
                startActivityForResult(intent, REQUEST_CHOOSE_PICTURE);
            }
        }).build();
        materialDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
        mImageViews = new ArrayList<CubeImageView>();
        imageLoader = ImageLoaderFactory.create(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_product, container, false);
        final CardView imageCardView = (CardView) v.findViewById(R.id.imageCardView);
        imageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animator.start();

            }
        });
        mProductImage1 = (CubeImageView) imageCardView.findViewById(R.id.product_image1);
        mProductImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageStates[1] == false) {
                    showDialog(getResources().getString(R.string.dialog_content_choose), false, 1);
                } else {
                    showChooseDialog(false, 0);
                }
            }
        });
        mProductImage2 = (CubeImageView) imageCardView.findViewById(R.id.product_image2);
        mProductImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageStates[2] == false) {
                    showDialog(getResources().getString(R.string.dialog_content_choose), false, 2);
                } else {
                    showChooseDialog(false, 1);
                }

            }
        });
        mProductImage3 = (CubeImageView) imageCardView.findViewById(R.id.product_image3);
        mProductImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageStates[3] == false) {
                    showDialog(getResources().getString(R.string.dialog_content_choose), false, 3);
                } else {
                    showChooseDialog(false, 2);
                }
            }
        });
        mProductImage4 = (CubeImageView) imageCardView.findViewById(R.id.product_image4);
        mProductImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageStates[4] == false) {
                    showDialog(getResources().getString(R.string.dialog_content_choose), false, 4);
                } else {
                    showChooseDialog(false, 3);
                }
            }
        });
        final CardView baseMessageCardView = (CardView) v.findViewById(R.id.baseMessageCardView);
        baseMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(baseMessageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animator.start();
            }
        });
        mProductName = (MaterialEditText) baseMessageCardView.findViewById(R.id.productNameEditText);
        mProductPrice = (MaterialEditText) baseMessageCardView.findViewById(R.id.productPriceEditText);
        mProductLocation = (MaterialEditText) baseMessageCardView.findViewById(R.id.productLocationEditText);
        mProductKinds = (MaterialEditText) baseMessageCardView.findViewById(R.id.productKindsEditText);
        mProductStartDate = (MaterialEditText) baseMessageCardView.findViewById(R.id.startDateEditText);
        mProductStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(CreateProductFragment.this, REQUEST_START_DATE);
                    dialog.show(fm, DIALOG_DATE);
                }
            }
        });
        mProductStartTime = (MaterialEditText) baseMessageCardView.findViewById(R.id.startTimeEditText);
        mProductStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    TimePickerFragment dialog = TimePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(CreateProductFragment.this, REQUEST_START_TIME);
                    dialog.show(fm, DIALOG_TIME);
                }
            }
        });
        mProductEndDate = (MaterialEditText) baseMessageCardView.findViewById(R.id.endDateEditText);
        mProductEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(CreateProductFragment.this, REQUEST_END_DATE);
                    dialog.show(fm, DIALOG_DATE);
                }
            }
        });
        mProductEndTime = (MaterialEditText) baseMessageCardView.findViewById(R.id.EndTimeEditText);
        mProductEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    TimePickerFragment dialog = TimePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(CreateProductFragment.this, REQUEST_END_TIME);
                    dialog.show(fm, DIALOG_TIME);
                }
            }
        });
        mProductDescribe = (MaterialEditText) baseMessageCardView.findViewById(R.id.productDescribeEditText);
        mProductDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mProduct.setProductDescribe(mProductDescribe.getText().toString());
            }
        });
        final CardView styleMessageCardView = (CardView) v.findViewById(R.id.styleMessageCardView);
        styleMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(styleMessageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                });
                animator.start();
            }
        });
        mEmptyFrameLayout = (FrameLayout) styleMessageCardView.findViewById(R.id.emptyFrame);
        mAddStyleMessageButton = (Button) styleMessageCardView.findViewById(R.id.addStyleMessage);
        mAddStyleMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ProductDetailStyleMessageActivity.class));
            }
        });
        mStyleMessages = mProduct.getStyleMessages();
        StyleMessage styleMessage = new StyleMessage(mProduct.getProductId());
        for (int i = 0; i < 3; i++) {
            styleMessage.setStyleName("运动款式");
            styleMessage.setColor("酒红色");
            styleMessage.setSize("39-44");
            styleMessage.setCount("20");
            mStyleMessages.add(styleMessage);
        }
        if (mStyleMessages.size() == 0) {

            mEmptyFrameLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyFrameLayout.setVisibility(View.INVISIBLE);
        }
        mRecyclerView = (RecyclerView) styleMessageCardView.findViewById(R.id.style_message_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new ListProductStyleRecyclerViewAdapter(getActivity(), mStyleMessages));

        final CardView zuozhengImageView = (CardView) v.findViewById(R.id.zuozhengImageCardView);
        zuozhengImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(zuozhengImageView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // showDialog(getResources().getString(R.string.dialog_content_choose));
                    }
                });
                animator.start();

            }
        });
        mProductImage0 = (CubeImageView) zuozhengImageView.findViewById(R.id.product_image0);
        mProductImage0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageStates[0] == false) {
                    showDialog(getResources().getString(R.string.dialog_content_choose), true, 0);
                } else {
                    showChooseDialog(true, 0);
                }
            }
        });
        mImageViews.add(mProductImage0);
        mImageViews.add(mProductImage1);
        mImageViews.add(mProductImage2);
        mImageViews.add(mProductImage3);
        mImageViews.add(mProductImage4);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        if (mStyleMessages.size() == 0) {

            mEmptyFrameLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyFrameLayout.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mProduct.setProductName(mProductName.getText().toString());
        mProduct.setProductPrice(mProductPrice.getText().toString());
        mProduct.setProductLocation(mProductLocation.getText().toString());
        mProduct.setProductKinds(mProductKinds.getText().toString());
        mProduct.setProductDescribe(mProductDescribe.getText().toString());
        mProduct.setProductTime(mProductStartDate.getText().toString() + " " + mProductStartTime.getText().toString() + " 到 " +
                mProductEndDate.getText().toString() + " " + mProductEndTime.getText().toString());
        mProduct.setProductStartDateTime(mProductStartDate.getText().toString() + " " + mProductStartTime.getText().toString());
        mProduct.setProductEndDateTime(mProductEndDate.getText().toString() + " " + mProductEndTime.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_START_DATE:
                Date startDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mProduct.setStartDate(startDate);
                mProductStartDate.setText(DateUtils.format(mProduct.getStartDate(), "yyyy'年' M'月' dd'日'"));
                break;
            case REQUEST_START_TIME:
                Date startTime = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mProduct.setStartTime(startTime);
                mProductStartTime.setText(DateUtils.format(mProduct.getStartTime(), "HH':' mm"));
                break;
            case REQUEST_END_DATE:
                Date endDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mProduct.setEndDate(endDate);
                mProductEndDate.setText(DateUtils.format(mProduct.getEndDate(), "yyyy'年' M'月' dd'日'"));
                break;
            case REQUEST_END_TIME:
                Date endTime = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mProduct.setEndTime(endTime);
                mProductEndTime.setText(DateUtils.format(mProduct.getEndTime(), "HH':' mm"));
                break;
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
                    // savePhoto(REQUEST_TAKE_PICTURE);
                    if (filename != null) {
                        mProduct.setImageDefaultId(filename);
                        mImageStates[0] = true;
                        Log.i(TAG, "filename: " + filename);
                        mImageViews.get(0).setImageBitmap(PictureUtils.getThumbNailBitmap(filename));
                    }
                } else {
                    String filename = data.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
                    String path = data.getStringExtra(CameraFragment.EXTRA_PHOTO_PATH);
                    String imageName = data.getStringExtra(CameraFragment.EXTRA_PHOTO_NAME);
                    mImageFile = filename;
                    mImagePath = path;
                    mImageName = imageName;
                    // savePhoto(REQUEST_TAKE_PICTURE);
                    int index = data.getIntExtra(EXTRA_IMAGE_INDEX, 0);
                    if (filename != null) {
                        Log.i(TAG, "filename: " + filename);
                        imageUri.add(IMAGE_COUNT, filename);
                        IMAGE_COUNT++;
                        mImageStates[index] = true;
                        mImageViews.get(index).setImageBitmap(PictureUtils.getThumbNailBitmap(mImageFile));
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
                    for (String imageUri : uri) {
                        mImageFile = imageUri;
                        mImagePath = imageUri.substring(0, imageUri.lastIndexOf("/"));
                        mImageName = imageUri.substring(imageUri.lastIndexOf("/") + 1, imageUri.length());                    // savePhoto(REQUEST_CHOOSE_PICTURE);
                    }
                    for (int i = 0; i < uri.size(); i++) {
                        if (uri.get(i) != null) {
                            mProduct.setImageDefaultId(uri.get(i));
                            mImageStates[i] = true;
                            mImageViews.get(0).setImageBitmap(PictureUtils.getThumbNailBitmap(mProduct.getImageDefaultId()));
                        }
                    }
                } else {
                    ArrayList<String> uri = data.getStringArrayListExtra(EXTRA_IMAGE_URI);
                    for (String imageUri : uri) {
                        mImageFile = imageUri;
                        mImagePath = imageUri.substring(0, imageUri.lastIndexOf("/"));
                        mImageName = imageUri.substring(imageUri.lastIndexOf("/") + 1, imageUri.length());                   //  savePhoto(REQUEST_CHOOSE_PICTURE);
                    }

                    try {
                        if (uri != null) {
                            for (int i = 0; i < uri.size(); i++) {
                                if (uri.get(i) != null) {
                                        if(!(imageUri.contains(uri.get(i)))) {
                                            imageUri.add(IMAGE_COUNT, uri.get(i));
                                            IMAGE_COUNT++;
                                            Log.i(TAG, "IMAGEINDEX is " + IMAGE_INDEX + "uri.get(index is )" + uri.get(i));
                                        }else{
                                            if(IMAGE_INDEX!=-1) {
                                                Log.i(TAG, "IMAGEINDEX is " + IMAGE_INDEX + "imageuri.size is " + imageUri.size() + "uri.size is " + uri.size());
                                                imageUri.set(IMAGE_INDEX, uri.get(uri.size()-1));
                                                IMAGE_INDEX = -1;
                                            }
                                        }
                                }
                            }
                            mProduct.setImageNameId(imageUri);
                            for (int i = 0; i < mImageViews.size() - 1; i++) {
                                if (imageUri.get(i) != null) {
                                    if (mImageStates[i + 1] == false) {
                                        mImageStates[i + 1] = true;
                                        //  mImageViews.get(i + 1).setImageBitmap(PictureUtils.getThumbNailBitmap(imageUri.get(i)));
                                        // ImageLoader.getInstance().loadImage(imageUri.get(i),mImageViews.get(i+1));
                                        // mImageViews.get(i+1).loadImage(imageLoader,imageUri.get(i));
                                        mImageViews.get(i + 1).setImageBitmap(ImageLoader.getInstance().decodeSampledBitmapFromResource(imageUri.get(i), 120, 120));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
