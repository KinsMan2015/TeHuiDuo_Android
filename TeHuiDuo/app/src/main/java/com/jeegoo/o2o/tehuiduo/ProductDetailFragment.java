package com.jeegoo.o2o.tehuiduo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "ProductDetailFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PRODUCT = "product";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Product mProduct;
    private ArrayList<String> imageUri;
    private ArrayList<ImageView> mImageViews;
    private ImageView mProductImage0, mProductImage1, mProductImage2, mProductImage3, mProductImage4;
    private TextView mProductName,mProductPrice,mProductLoaction,mProductKinds,mProductState,mProductStartDate,mProductEndDate,mProductDescribe;
    private OnFragmentInteractionListener mListener;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
         //   mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
        mImageViews = new ArrayList<>();
        mProduct = UserLab.get(getActivity()).getCurrentProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        //  final CardView cardView = (CardView) inflater.inflate(R.layout.list_item_card_detail_base_message, container, false);
        final CardView imageCardView = (CardView) v.findViewById(R.id.imageCardView);
        imageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //getActivity().startActivity(new Intent(getActivity(), ProductDetailImageActivity.class));
                    }
                });
                animator.start();
            }
        });
        mProductImage1 = (ImageView) imageCardView.findViewById(R.id.product_image1);
        mProductImage2 = (ImageView) imageCardView.findViewById(R.id.product_image2);
        mProductImage3 = (ImageView) imageCardView.findViewById(R.id.product_image3);
        mProductImage4 = (ImageView) imageCardView.findViewById(R.id.product_image4);
        mImageViews.add(mProductImage1);
        mImageViews.add(mProductImage2);
        mImageViews.add(mProductImage3);
        mImageViews.add(mProductImage4);
        if(mProduct.getImageNameId()!=null) {
            imageUri = mProduct.getImageNameId();
            try {
                for(int i = 0;i<imageUri.size();i++){
                    if(imageUri.get(i)!=null){
                        mImageViews.get(i).setImageBitmap(PictureUtils.getThumbNailBitmap(imageUri.get(i)));
//                        Picasso.with(getActivity()).load(new File(imageUri.get(i))).into(mImageViews.get(i));
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        final CardView baseMessageCardView = (CardView) v.findViewById(R.id.baseMessageCardView);
        baseMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(baseMessageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                       // getActivity().startActivity(new Intent(getActivity(), ProductDetailBaseMessageActivity.class));
                    }
                });
                animator.start();
            }
        });
        mProductName = (TextView) baseMessageCardView.findViewById(R.id.productName);
        mProductName.setText(mProduct.getProductName());
        mProductPrice = (TextView) baseMessageCardView.findViewById(R.id.productPrice);
        mProductPrice.setText(mProduct.getProductPrice());
        mProductLoaction = (TextView) baseMessageCardView.findViewById(R.id.productLocation);
        mProductLoaction.setText(mProduct.getProductLocation());
        mProductKinds = (TextView) baseMessageCardView.findViewById(R.id.productkinds);
        mProductKinds.setText(mProduct.getProductKinds());
        mProductStartDate = (TextView) baseMessageCardView.findViewById(R.id.productStartDate);
        mProductStartDate.setText(mProduct.getProductStartDateTime());
        mProductEndDate = (TextView) baseMessageCardView.findViewById(R.id.productEndDate);
        mProductEndDate.setText(mProduct.getProductEndDateTime());
        mProductDescribe = (TextView) baseMessageCardView.findViewById(R.id.productDescribe);
        mProductDescribe.setText(mProduct.getProductDescribe());
        final CardView styleMessageCardView = (CardView) v.findViewById(R.id.styleMessageCardView);
        styleMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(styleMessageCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getActivity().startActivity(new Intent(getActivity(), ProductDetailStyleMessageActivity.class));
                    }
                });
                animator.start();
            }
        });
        final CardView zuozhengImageCardView = (CardView) v.findViewById(R.id.zuozhengImageCardView);
        zuozhengImageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(zuozhengImageCardView, "translationZ", 20, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //getActivity().startActivity(new Intent(getActivity(), ProductDetailStyleMessageActivity.class));
                        }
                    });
                    animator.start();
                }
            }
        });
        mProductImage0 = (ImageView) zuozhengImageCardView.findViewById(R.id.product_image0);
        if(mProduct.getImageDefaultId()!=null) {
//            Picasso.with(getActivity()).load(new File(mProduct.getImageDefaultId())).into(mProductImage0);
            mProductImage0.setImageBitmap(PictureUtils.getThumbNailBitmap(mProduct.getImageDefaultId()));
        }
        return v;
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
