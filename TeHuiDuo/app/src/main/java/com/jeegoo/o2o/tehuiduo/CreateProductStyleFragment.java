package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.jeegoo.o2o.tehuiduo.adapter.ColorSpinnerAdapter;
import com.jeegoo.o2o.tehuiduo.adapter.SizeSpinnerAdapter;
import com.jeegoo.o2o.tehuiduo.model.ColorTable;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.SizeTable;
import com.jeegoo.o2o.tehuiduo.model.StyleMessage;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateProductStyleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateProductStyleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProductStyleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_STYLE_MESSAGE = "style_message";
    // TODO: Rename and change types of parameters
    private MaterialEditText mStyleMessageEditText;
    private MaterialEditText mStyleCountEditText;
    private Spinner mColorSpinner;
    private Spinner mSizeSpinner;
    private ImageButton mAddColorImageButton;
    private ImageButton mAddSizeImageButton;
    private OnFragmentInteractionListener mListener;
    private Product mProduct;
    private StyleMessage mStyleMessage;
    private ArrayList<ColorTable> mColorTables;
    private ArrayList<SizeTable> mSizeTables;

    public CreateProductStyleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateProductStyleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProductStyleFragment newInstance(StyleMessage styleMessage) {
        CreateProductStyleFragment fragment = new CreateProductStyleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STYLE_MESSAGE,styleMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStyleMessage = (StyleMessage) getArguments().getSerializable(ARG_STYLE_MESSAGE);
        }
        mProduct = UserLab.get(getActivity()).getCurrentProduct();
        mColorTables = mProduct.getColorTables();
        mSizeTables = mProduct.getSizeTables();
        for(int i =0;i<10;i++){
            SizeTable sizeTable = new SizeTable();
            sizeTable.setSizeName("美国码:" + 34+i);
            mSizeTables.add(sizeTable);
        }
        for(int i =0;i<10;i++){
            ColorTable colorTable = new ColorTable();
            colorTable.setColor("#223456");
            mColorTables.add(colorTable);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_product_style, container, false);
        mStyleMessageEditText = (MaterialEditText) v.findViewById(R.id.productStyleEditText);
        mStyleMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mStyleMessage.setStyleName(mStyleMessageEditText.getText().toString());
            }
        });
        mStyleCountEditText = (MaterialEditText) v.findViewById(R.id.productStyleCountEditText);
        mStyleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mStyleMessage.setCount(mStyleCountEditText.getText().toString());
            }
        });
        mColorSpinner = (Spinner) v.findViewById(R.id.colorSpinner);
        ColorSpinnerAdapter colorSpinnerAdapter = new ColorSpinnerAdapter(getActivity(),mColorTables);
        mColorSpinner.setAdapter(colorSpinnerAdapter);
        mColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string  = mColorTables.get(position).getColorName();
                mStyleMessage.setColor(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSizeSpinner = (Spinner) v.findViewById(R.id.sizeSpinner);
//        ArrayList<String> styleName = new ArrayList<>();
//        for(SizeTable sizeTable:mSizeTables){
//            styleName.add(sizeTable.getSizeName());
//        }
//
//        String[] mItems =  styleName.toArray(new String[styleName.size()]);
        SizeSpinnerAdapter sizeSpinnerAdapter = new SizeSpinnerAdapter(getActivity(),mSizeTables);
        mSizeSpinner.setAdapter(sizeSpinnerAdapter);
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = mSizeTables.get(position).getSizeName();
                mStyleMessage.setSize(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mAddColorImageButton = (ImageButton) v.findViewById(R.id.addColorImageButton);
        mAddColorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddColorActivity.class);
                startActivity(intent);
            }
        });
        mAddSizeImageButton = (ImageButton) v.findViewById(R.id.addSizeImageButton);
        mAddSizeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        ( (ColorSpinnerAdapter)mColorSpinner.getAdapter()).notifyDataSetChanged();
        ((SizeSpinnerAdapter)mSizeSpinner.getAdapter()).notifyDataSetChanged();
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
