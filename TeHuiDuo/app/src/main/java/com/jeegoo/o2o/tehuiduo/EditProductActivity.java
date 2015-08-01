package com.jeegoo.o2o.tehuiduo;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;

public class EditProductActivity extends AppCompatActivity implements EditProductFragment.OnFragmentInteractionListener {
    private Product mProduct;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        mProduct = UserLab.get(this).getCurrentProduct();
        mPosition = getIntent().getIntExtra(ProductDetailActivity.EXTRA_POSITION,0);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_edit_product);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = EditProductFragment.newInstance(mProduct,mPosition);
            fm.beginTransaction().add(R.id.container, fragment).commit();
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLab.get(EditProductActivity.this).getCurrentShop().setProduct(mPosition,mProduct,"1");
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sure) {
            UserLab.get(EditProductActivity.this).getCurrentShop().setProduct(mPosition,mProduct,"1");
            finish();
            return true;
        }
        if(id== R.id.action_delete){
            UserLab.get(this).getCurrentShop().getProductsFromState("1").remove(mPosition);
            finish();
            return true;
        }
        if(id == R.id.action_cancle){
            finish();
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
