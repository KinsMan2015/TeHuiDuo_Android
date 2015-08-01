package com.jeegoo.o2o.tehuiduo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jeegoo.o2o.tehuiduo.model.Shop;
import com.jeegoo.o2o.tehuiduo.model.UserLab;

import java.util.ArrayList;
import java.util.List;


public class ProductListActivity extends ActionBarActivity implements ProductListFragment.OnFragmentInteractionListener {
    private static final String TAG = "ProductListActivity";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_SHOP_ID = "shop_id";
    public static final String EXTRA_PRODUCT_STATE = "product_state";
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String mUserName;
    private String mShopId;
    private Shop mShop;
    private String[] mProductsState;
    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener()
                {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    private void setupViewPager(){
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.tab_title1));
        titles.add(getResources().getString(R.string.tab_title2));
        titles.add(getResources().getString(R.string.tab_title3));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(ProductListFragment.newInstance(mUserName,mProductsState[0]));
        fragments.add(ProductListFragment.newInstance(mUserName,mProductsState[1]));
        fragments.add(ProductListFragment.newInstance(mUserName,mProductsState[2]));
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserName = getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME);
        mShopId = getIntent().getStringExtra(LoginActivity.EXTRA_SHOP_ID);
       // mShop = UserLab.get(ProductListActivity.this).getUser(mUserName).getShop(mShopId);
        mShop = UserLab.get(ProductListActivity.this).getCurrentShop();
        mProductsState = mShop.getProductState();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar.setTitle(mShop.getShopName());
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);
        LayoutInflater inflater =LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.navigation_content,null);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_contact:
                        Intent intent = new Intent(ProductListActivity.this, ContactUsActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductListActivity.this,CreateProductActivity.class);
                i.putExtra(EXTRA_USERNAME,mUserName);
                i.putExtra(EXTRA_SHOP_ID,mShopId);
                i.putExtra(EXTRA_PRODUCT_STATE,mProductsState[1]);
                startActivity(i);
            }
        });
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(ProductListActivity.this,CreateProductActivity.class);
            i.putExtra(EXTRA_USERNAME,mUserName);
            i.putExtra(EXTRA_SHOP_ID,mShopId);
            i.putExtra(EXTRA_PRODUCT_STATE,mProductsState[1]);
            startActivity(i);
            return true;
        }
        if(item.getItemId() == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
