package com.jeegoo.o2o.tehuiduo;

import android.content.Intent;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.adapter.ListProductRecyclerViewAdapter;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;


public class ProductDetailActivity extends ActionBarActivity implements ProductDetailFragment.OnFragmentInteractionListener {
    private static final String TAG = "ProductDetailActivity";
    public static final String EXTRA_PRODUCT = "product";
    public static final String EXTRA_POSITION = "position";
    private int mPosition;
    private Product mProduct;
    private void showDialog(String content) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        MaterialDialog materialDialog = builder.title(R.string.dialog_title_image).content(content).positiveText(R.string.action_delete).negativeText(android.R.string.cancel).callback(new MaterialDialog.Callback() {
            @Override
            public void onPositive(MaterialDialog materialDialog) {
                UserLab.get(ProductDetailActivity.this).getCurrentShop().deleteProduct(mPosition,mProduct.getProductState());
                finish();
            }

            @Override
            public void onNegative(MaterialDialog materialDialog) {

            }
        }).build();
        materialDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mPosition = getIntent().getIntExtra(ListProductRecyclerViewAdapter.EXTRA_POSITION,0);
        mProduct = UserLab.get(this).getCurrentProduct();
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(mProduct.getProductName()+"("+mProduct.getProductState()+")");
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if(fragment == null) {
            fragment = ProductDetailFragment.newInstance();
            fm.beginTransaction().add(R.id.container, fragment).commit();
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this,EditProductActivity.class);
                intent.putExtra(EXTRA_POSITION,mPosition);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(ProductDetailActivity.this,EditProductActivity.class);
            intent.putExtra(EXTRA_PRODUCT,mProduct);
            intent.putExtra(EXTRA_POSITION,mPosition);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.action_delete){
            showDialog(getResources().getString(R.string.dialog_content_delete));
            return true;
        }
        if(id==R.id.action_cancle){
            finish();
            return true;
        }
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
