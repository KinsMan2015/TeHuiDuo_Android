package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.FileUtils;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CameraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "CrimeCameraFragment";
    public static final String EXTRA_PHOTO_FILENAME = "com.jeegoo.o2o.tehuiduo.photo_filename";
    public static final String EXTRA_PHOTO_PATH = "com.jeegoo.o2o.tehuiduo.photo_path";
    public static final String EXTRA_PHOTO_NAME = "com.jeegoo.o2o.tehuiduo.photo_name";
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private View mProgressContainer;
    private String tempFileName;
    private boolean isZuoZhengImage;
    private int index;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what){
                case 0x0:
//                    imageView.setImageDrawable(PictureUtils.getScaledDrawable(getActivity(), tempFileName));
                    return true;
            }
            return false;
        }
    });
    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            mProgressContainer.setVisibility(View.VISIBLE);
        }
    };
    private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String username = UserLab.get(getActivity()).getCurrentUser().getUserName();
            String productId = UserLab.get(getActivity()).getCurrentProduct().getProductId();
            String imageId = "ABCDEF";
            String path = FileUtils.createImageFile(username, productId);
            String imageName =  imageId +index;
            String filename = path + imageName+ ".jpg";
            FileOutputStream os = null;
            boolean success = true;
            try {
                os = new FileOutputStream(new File(filename));
                os.write(data);
            } catch (Exception e) {
                Log.e(TAG, "Error writing to file " + filename, e);
                success = false;
            }
            if(success){
                Log.i(TAG, "JPEG saved at " + filename);
                Intent i = new Intent();
                i.putExtra(EXTRA_PHOTO_FILENAME,filename);
                i.putExtra(EXTRA_PHOTO_PATH,path);
                i.putExtra(EXTRA_PHOTO_NAME,imageName);
                i.putExtra(CreateProductFragment.EXTRA_ZUOZHENG_IMAGE, isZuoZhengImage);
                i.putExtra(CreateProductFragment.EXTRA_IMAGE_INDEX,index);
                getActivity().setResult(Activity.RESULT_OK,i);
            }else{
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            mProgressContainer.setVisibility(View.INVISIBLE);
            getActivity().finish();
        }
    };
    private Camera.Size getBestSpportedSize(List<Camera.Size> sizes,int width,int height){
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width*bestSize.height;
        for(Camera.Size s:sizes){
            int area = s.width*s.height;
            if(area>largestArea){
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }
    protected void setDisplayOrientation(Camera camera, int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod(
                    "setDisplayOrientation", new Class[] { int.class });
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[] { angle });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isZuoZhengImage = getActivity().getIntent().getBooleanExtra(CreateProductFragment.EXTRA_ZUOZHENG_IMAGE, false);
        index = getActivity().getIntent().getIntExtra(CreateProductFragment.EXTRA_IMAGE_INDEX,0);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_camera, container, false);
        mProgressContainer = v.findViewById(R.id.crime_camera_progressContainer);
        mProgressContainer.setVisibility(View.INVISIBLE);
        ImageButton takePictureButton = (ImageButton) v.findViewById(R.id.camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCamera !=null){
                    mCamera.takePicture(mShutterCallback,null,mJpegCallback);
                }
            }
        });
        mSurfaceView = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
        final SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(mCamera !=null){
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        Log.e(TAG,"Error setting up preview diaplay");
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                if(mCamera == null) return;

                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size s = getBestSpportedSize(parameters.getSupportedPreviewSizes(),i1,i2);
                parameters.setPreviewSize(s.width,s.height);
                s = getBestSpportedSize(parameters.getSupportedPictureSizes(),i1,i2);
                parameters.setPictureSize(s.width, s.height);
                if (Integer.parseInt(Build.VERSION.SDK) >= 8) {// 判断系统版本是否大于等于2.2
                    setDisplayOrientation(mCamera, 90);// 旋转90，前提是当前页portrait，纵向
                } else { // 系统版本在2.2以下的采用下面的方式旋转
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        parameters.set("orientation", "portrait");
                        parameters.set("rotation", 90);
                    }
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        parameters.set("orientation", "landscape");
                        parameters.set("rotation", 90);
                    }
                }
                mCamera.setParameters(parameters);
                try {
                    mCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG,"Could not start preview",e);
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if(mCamera !=null){
                    mCamera.stopPreview();
                }
            }
        });
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
            mCamera = Camera.open(0);
        }else{
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCamera!=null){
            mCamera.release();
            mCamera = null;
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
