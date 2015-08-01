package com.jeegoo.o2o.tehuiduo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends SingleFragmentActivity implements CameraFragment.OnFragmentInteractionListener{

    @Override
    protected Fragment creatFragment() {
        Fragment fragment = CameraFragment.newInstance();
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
