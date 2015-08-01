package com.jeegoo.o2o.tehuiduo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment creatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(fragment==null){
            fragment = creatFragment();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
    }


}
