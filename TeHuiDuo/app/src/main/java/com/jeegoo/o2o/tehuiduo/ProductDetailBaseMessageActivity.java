package com.jeegoo.o2o.tehuiduo;

import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;

public class ProductDetailBaseMessageActivity extends ActionBarActivity implements ProductDetailBaseMessageFragment.OnFragmentInteractionListener{
    private Toolbar mToolbar;
    private Product mProduct;
    private Product tempProduct;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_base_message);
        mPosition = getIntent().getIntExtra(EditProductFragment.EXTRA_POSITION,0);
        mProduct = UserLab.get(this).getCurrentProduct();
        tempProduct = mProduct;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_product_detail_base_message);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if(fragment==null){
            fragment = ProductDetailBaseMessageFragment.newInstance(tempProduct);
            fm.beginTransaction().add(R.id.container,fragment).commit();
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLab.get(ProductDetailBaseMessageActivity.this).getCurrentShop().getProductsSecond().set(mPosition,tempProduct);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail_base_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            UserLab.get(ProductDetailBaseMessageActivity.this).getCurrentShop().getProductsSecond().set(mPosition,tempProduct);
            finish();
            return true;
        }
        if(id == R.id.action_cancle){
            UserLab.get(ProductDetailBaseMessageActivity.this).getCurrentShop().getProductsSecond().set(mPosition,mProduct);
            finish();
            return true;
        }
        if( id == android.R.id.home){
            UserLab.get(ProductDetailBaseMessageActivity.this).getCurrentShop().getProductsSecond().set(mPosition,mProduct);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
