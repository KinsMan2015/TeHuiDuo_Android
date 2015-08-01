package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductImageFragment extends Fragment {
    private static final String TAG = "ProductDetailImageF";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_INDEX = "index";
    private static final String IMAGE_FILE = "image_file";
    private static final String DIALOG_IMAGE_EDIT = "image_edit";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mImageUri;
    private int mIndex;
    private OnFragmentInteractionListener mListener;
    private ImageView mImageView;
    public ProductImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductImageFragment newInstance(String path,int index) {
        ProductImageFragment fragment = new ProductImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_FILE, path);
        args.putInt(ARG_INDEX,index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mImageUri = getArguments().getString(IMAGE_FILE);
            mIndex = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_detail_image, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        if(mImageUri!=null) {
            mImageView.setImageBitmap(PictureUtils.getThumbNailBitmap(mImageUri));
        }else{
        }
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageUri != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Log.i(TAG, "mImageUri is " + mImageUri);
                    EditImageDialogFragment.newInstance(mImageUri, mIndex).show(fm, DIALOG_IMAGE_EDIT);
                }
            }
        });
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mImageUri != null) {
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
                    MaterialDialog materialDialog = builder.title(R.string.dialog_title).items(getResources().getStringArray(R.array.dialog_items)).itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, int i, String s) {
                            if (i == 0) {
                                Toast.makeText(getActivity(), "替换", Toast.LENGTH_SHORT).show();
                                onButtonPressed(i, mIndex);
                            } else if (i == 1) {
                                Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                                onButtonPressed(i, mIndex);


                            }
                        }
                    }).build();
                    materialDialog.show();
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        if(mImageUri!=null) {
//            Picasso.with(getActivity()).load(new File(mImageUri)).into(mImageView);
//        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int commond,int index) {
        if (mListener != null) {
            mListener.onFragmentInteraction(commond,index);
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
        public void onFragmentInteraction(int commond,int index);
    }

}
