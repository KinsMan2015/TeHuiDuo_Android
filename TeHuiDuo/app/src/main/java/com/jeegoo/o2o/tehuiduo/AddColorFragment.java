package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.jeegoo.o2o.tehuiduo.model.ColorTable;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddColorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddColorFragment extends Fragment {
    private String TAG = "AddColorFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialEditText mColorEditText;
    private GridView mColorTableGridView;
    private OnFragmentInteractionListener mListener;
    private Product mProduct;
    private ArrayList<ColorTable> mColorTables;
    private void updateColorList(List<ColorTable> colorTableList,ArrayList<HashMap<String,Object>> imagelist){
        for(ColorTable colorTable:colorTableList){
            HashMap<String,Object> map = new HashMap<>();
            map.put("image", colorTable.getColor());
            imagelist.add(map);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddColorFragment newInstance() {
        AddColorFragment fragment = new AddColorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mProduct = UserLab.get(getActivity()).getCurrentProduct();
        mColorTables = mProduct.getColorTables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_color, container, false);
        mColorEditText = (MaterialEditText) v.findViewById(R.id.productColorEditText);
        mColorTableGridView = (GridView) v.findViewById(R.id.colorTableGridView);
        final List<ColorTable> colorTableList = new ArrayList<>();
        ColorTable colorTable = new ColorTable();
        colorTable.setColorName("淡蓝色");
        colorTable.setColor("#009fd6");
        colorTableList.add(colorTable);
        colorTableList.add(colorTable);
        colorTableList.add(colorTable);
        colorTableList.add(colorTable);
        colorTableList.add(colorTable);
        colorTableList.add(colorTable);
        ArrayList<HashMap<String,Object>> imagelist = new ArrayList<>();
        updateColorList(colorTableList,imagelist);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), imagelist, R.layout.grid_item_color, new String[] {"image"}, new int[]{R.id.image});
        mColorTableGridView.setAdapter(simpleAdapter);
        mColorTableGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String colorName = colorTableList.get(position).getColorName();
                if(!mColorEditText.getText().toString().equals(colorName)) {
                    ColorTable colorTable = colorTableList.get(position);
                    mColorEditText.setText(colorName);
                    mColorTables.add(colorTable);
                }
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

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
