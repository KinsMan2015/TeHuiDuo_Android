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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {
    //    public static final String EXTRA_IMAGE_URI = "image_uri";
    public static final String EXTRA_PRODUCT = "product";
    public static final String EXTRA_POSITION = "position";
    //    public static final int REQUEST_IMAGE_URI = 0;
//    public static final int REQUEST_START_DATE = 1;
//    public static final int REQUEST_START_TIME = 2;
//    public static final int REQUEST_END_DATE = 3;
//    public static final int REQUEST_END_TIME = 4;
//    private static final int REQUEST_TAKE_BIG_PICTURE = 5;
//    private static final int REQUEST_CHOOSE_BIG_PICTURE = 6;
//    private static final String TAG = "CreateProductFragment";
    public static final String ARG_POSITION = "position";
    private static final String ARG_PRODUCT = "product";
    //    private static final String DIALOG_DATE = "date";
//    private static final String DIALOG_TIME = "time";
//    File takeImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zhycheng.jpg");
//    File chooseImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "bxt.jpg");
//    private Uri takeImageUri = Uri.fromFile(takeImageFile);
//    private Uri chooseImageUri = Uri.fromFile(chooseImageFile);
    private Product mProduct;
    private int mPosition;
    //    private String imageUri[];
//    private ImageView mProductImage0,mProductImage1, mProductImage2, mProductImage3, mProductImage4;
//    private MaterialEditText mProductName;
//    private MaterialEditText mProductPrice;
//    private MaterialEditText mProductLocation;
//    private MaterialEditText mProductKinds;
//    private MaterialEditText mProductStartDate;
//    private MaterialEditText mProductStartTime;
//    private MaterialEditText mProductEndDate;
//    private MaterialEditText mProductEndTime;
//    private MaterialEditText mProductDescribe;
    private OnFragmentInteractionListener mListener;

    public EditProductFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProductFragment newInstance(Product product, int position) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
            mPosition = getArguments().getInt(ARG_POSITION);
        }
        mProduct = UserLab.get(getActivity()).getCurrentProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_product, container, false);
        final CardView imageCardView = (CardView) v.findViewById(R.id.imageCardView);
                imageCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imageCardView, "translationZ", 20, 0);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                Intent intent = new Intent(getActivity(), ProductDetailImageActivity.class);
                                intent.putExtra(EXTRA_POSITION, mPosition);
                                startActivity(intent);
                            }
                        });
                        animator.start();

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
                        Intent intent = new Intent(getActivity(), ProductDetailBaseMessageActivity.class);
                        intent.putExtra(EXTRA_POSITION, mPosition);
                        startActivity(intent);
                    }
                });
                animator.start();
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
                        Intent intent = new Intent(getActivity(), ProductDetailStyleMessageActivity.class);
                        intent.putExtra(EXTRA_POSITION, mPosition);
                        startActivity(intent);
                    }
                });
                animator.start();
            }
        });
        final CardView zuozhengCardView = (CardView) v.findViewById(R.id.zuoZhengImageCardView);
        zuozhengCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(zuozhengCardView, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(getActivity(),ProductZuoZhengImageActivity.class);
                        startActivity(intent);
                    }
                });
                animator.start();

            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
