package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.DateUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailBaseMessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailBaseMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailBaseMessageFragment extends Fragment {
    private static final String ARG_PRODUCT = "product";
    public static final int REQUEST_START_DATE = 1;
    public static final int REQUEST_START_TIME = 2;
    public static final int REQUEST_END_DATE = 3;
    public static final int REQUEST_END_TIME = 4;
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialEditText mProductName;
    private MaterialEditText mProductPrice;
    private MaterialEditText mProductLocation;
    private MaterialEditText mProductKinds;
    private MaterialEditText mProductStartDate;
    private MaterialEditText mProductStartTime;
    private MaterialEditText mProductEndDate;
    private MaterialEditText mProductEndTime;
    private MaterialEditText mProductDescribe;
    private Product mProduct;
    private OnFragmentInteractionListener mListener;

    public ProductDetailBaseMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductDetailBaseMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailBaseMessageFragment newInstance(Product product) {
        ProductDetailBaseMessageFragment fragment = new ProductDetailBaseMessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT,product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail_base_message, container, false);
        mProductName = (MaterialEditText) v.findViewById(R.id.productNameEditText);
        mProductName.setText(mProduct.getProductName());
        mProductPrice = (MaterialEditText) v.findViewById(R.id.productPriceEditText);
        mProductPrice.setText(mProduct.getProductPrice());
        mProductLocation = (MaterialEditText) v.findViewById(R.id.productLocationEditText);
        mProductLocation.setText(mProduct.getProductPrice());
        mProductKinds = (MaterialEditText) v.findViewById(R.id.productKindsEditText);
        mProductKinds.setText(mProduct.getProductKinds());
        mProductStartDate = (MaterialEditText) v.findViewById(R.id.startDateEditText);
        mProductStartDate.setText(DateUtils.format(mProduct.getStartDate(), "yyyy'年' M'月' dd'日'"));
        mProductStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(ProductDetailBaseMessageFragment.this, REQUEST_START_DATE);
                    dialog.show(fm, DIALOG_DATE);
                }
            }
        });
        mProductStartTime = (MaterialEditText) v.findViewById(R.id.startTimeEditText);
        mProductStartTime.setText(DateUtils.format(mProduct.getStartTime(), "HH':' mm"));
        mProductStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    TimePickerFragment dialog = TimePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(ProductDetailBaseMessageFragment.this, REQUEST_START_TIME);
                    dialog.show(fm, DIALOG_TIME);
                }
            }
        });
        mProductEndDate = (MaterialEditText) v.findViewById(R.id.endDateEditText);
        mProductEndDate.setText(DateUtils.format(mProduct.getEndDate(), "yyyy'年' M'月' dd'日'"));
        mProductEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(ProductDetailBaseMessageFragment.this, REQUEST_END_DATE);
                    dialog.show(fm, DIALOG_DATE);
                }
            }
        });
        mProductEndTime = (MaterialEditText) v.findViewById(R.id.EndTimeEditText);
        mProductEndTime.setText(DateUtils.format(mProduct.getEndTime(), "HH':' mm"));
        mProductEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    TimePickerFragment dialog = TimePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(ProductDetailBaseMessageFragment.this, REQUEST_END_TIME);
                    dialog.show(fm, DIALOG_TIME);
                }
            }
        });
        mProductDescribe = (MaterialEditText) v.findViewById(R.id.productDescribeEditText);
        mProductDescribe.setText(mProduct.getProductDescribe());
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
        return v;
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
