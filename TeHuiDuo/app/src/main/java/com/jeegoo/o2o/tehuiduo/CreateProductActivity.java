package com.jeegoo.o2o.tehuiduo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.Shop;
import com.jeegoo.o2o.tehuiduo.model.User;
import com.jeegoo.o2o.tehuiduo.model.UserLab;

import java.util.ArrayList;

public class CreateProductActivity extends ActionBarActivity implements CreateProductFragment.OnFragmentInteractionListener{
    private static final String TAG ="create_product_activity";
    public static final String TAG_CEATE_PRODUCT ="create_fragment";
    private String mUserName;
    private String mShopId;
    private String mProductState;
    final Product product = new Product("test");
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        mUserName = getIntent().getStringExtra(ProductListActivity.EXTRA_USERNAME);
        mShopId = getIntent().getStringExtra(ProductListActivity.EXTRA_SHOP_ID);
        mProductState = getIntent().getStringExtra(ProductListActivity.EXTRA_PRODUCT_STATE);
        product.setProductState(mProductState);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_create_product);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        UserLab.get(CreateProductActivity.this).setCurrentProduct(product);
        if(fragment == null) {
            fragment = CreateProductFragment.newInstance(product);
            fm.beginTransaction().add(R.id.container, fragment,TAG_CEATE_PRODUCT).commit();
        }
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(CreateProductActivity.this);
                MaterialDialog materialDialog = builder.title(R.string.dialog_title).items(getResources().getStringArray(R.array.dialog_items_create)).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, int i, String s) {
                        if (i == 0) {
                            Toast.makeText(CreateProductActivity.this, R.string.toast_create_product, Toast.LENGTH_SHORT).show();
                            UserLab.get(CreateProductActivity.this).getUser(mUserName).getShop(mShopId).addProduct(product, mProductState);
                            finish();
                        } else if (i == 1) {
                            Toast.makeText(CreateProductActivity.this, "提交正在开发中", Toast.LENGTH_SHORT).show();
                        }else if(i==2){
                        }
                    }
                }).build();
                materialDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(CreateProductActivity.this, R.string.toast_create_product, Toast.LENGTH_SHORT).show();
            UserLab.get(CreateProductActivity.this).getUser(mUserName).getShop(mShopId).addProduct(product, mProductState);
            Product p =   UserLab.get(CreateProductActivity.this).getUser(mUserName).getShop(mShopId).getProduct("test",mProductState);
            finish();
            return true;
        }
        if (id == R.id.action_cancle) {
            finish();
            return true;
        }
        if(id  == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
