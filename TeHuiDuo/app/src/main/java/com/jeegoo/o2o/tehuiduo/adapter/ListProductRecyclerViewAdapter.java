package com.jeegoo.o2o.tehuiduo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.ProductDetailActivity;
import com.jeegoo.o2o.tehuiduo.R;
import com.jeegoo.o2o.tehuiduo.model.Product;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.PictureUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xintong on 2015/7/14.
 */
public class ListProductRecyclerViewAdapter extends RecyclerView.Adapter<ListProductRecyclerViewAdapter.ViewHolder> {
    private static final String  TAG = "ListProductAdapter";
    public static final String EXTRA_POSITION= "position";
    public static final String EXTRA_PRODUCT = "product";
    private Context mContext;
    private ArrayList<Product> mProducts;

    public ListProductRecyclerViewAdapter(Context mContext,ArrayList<Product> mProducts) {
        this.mContext = mContext;
        this.mProducts = mProducts;
    }

    @Override
    public ListProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_shop, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ListProductRecyclerViewAdapter.ViewHolder holder, final int position) {
        final View view = holder.mView;
        ImageView zuozhengImage = (ImageView) view.findViewById(R.id.zuozhengIamgheView);
        String uri = mProducts.get(position).getImageDefaultId();
        if(uri!=null){
//            Picasso.with(mContext).load(new File(mProducts.get(position).getImageDefaultId())).into(zuozhengImage);
            zuozhengImage.setImageBitmap(PictureUtils.getThumbNailBitmap(mProducts.get(position).getImageDefaultId()));
        }
        TextView productName = (TextView) view.findViewById(R.id.productNameTextView);
        productName.setText(mProducts.get(position).getProductName());
        TextView productId = (TextView) view.findViewById(R.id.productIdTextView);
        productId.setText(mProducts.get(position).getProductId());
        TextView productDescribe = (TextView) view.findViewById(R.id.productDescribeTextView);
        productDescribe.setText(mProducts.get(position).getProductDescribe());
        TextView productTime = (TextView) view.findViewById(R.id.productTimeTextView);
        productTime.setText(mProducts.get(position).getProductTime());
        view.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        UserLab.get(mContext).setCurrentProduct(mProducts.get(holder.getPosition()));
                        Intent intent = new Intent(mContext,ProductDetailActivity.class);
                        intent.putExtra(EXTRA_POSITION, holder.getPosition());
                        intent.putExtra(EXTRA_PRODUCT,mProducts.get(holder.getPosition()));
                        mContext.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }
    }
}