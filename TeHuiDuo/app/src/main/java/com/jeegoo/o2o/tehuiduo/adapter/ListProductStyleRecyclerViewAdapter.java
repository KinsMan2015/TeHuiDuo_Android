package com.jeegoo.o2o.tehuiduo.adapter;

/**
 * Created by xintong on 2015/7/28.
 */

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
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.ProductDetailStyleMessageActivity;
import com.jeegoo.o2o.tehuiduo.R;
import com.jeegoo.o2o.tehuiduo.model.StyleMessage;

import java.util.ArrayList;


public class ListProductStyleRecyclerViewAdapter extends RecyclerView.Adapter<ListProductStyleRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StyleMessage> mStyleMessages;

    public ListProductStyleRecyclerViewAdapter(Context mContext, ArrayList<StyleMessage> mStyleMessages) {
        this.mContext = mContext;
        this.mStyleMessages = mStyleMessages;
    }

    @Override
    public ListProductStyleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product_detail_style_message, parent, false);
        return new ListProductStyleRecyclerViewAdapter.ViewHolder(view);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ListProductStyleRecyclerViewAdapter.ViewHolder holder, int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mContext.startActivity(new Intent(mContext, ProductDetailStyleMessageActivity.class));
                    }
                });
                animator.start();
            }
        });
        TextView style = (TextView) view.findViewById(R.id.style);
        if (mStyleMessages.get(position).getStyleName().equals("") || mStyleMessages.get(position).getStyleName() == null) {

        } else {
            style.setText(mStyleMessages.get(position).getStyleName());
        }
        TextView color = (TextView) view.findViewById(R.id.color);
        if (mStyleMessages.get(position).getColor().equals("") || mStyleMessages.get(position).getColor() == null) {

        } else {
            color.setText(" + " + mStyleMessages.get(position).getColor());
        }
        TextView size = (TextView) view.findViewById(R.id.size);

        if (mStyleMessages.get(position).getSize().equals("") || mStyleMessages.get(position).getSize() == null) {

        } else {
            size.setText(" + " + mStyleMessages.get(position).getSize());
        }
        TextView count = (TextView) view.findViewById(R.id.count);
        if (mStyleMessages.get(position).getCount().equals("") || mStyleMessages.get(position).getCount() == null) {

        } else {
            count.setText(" + " + mStyleMessages.get(position).getCount());
        }
    }

    @Override
    public int getItemCount() {
        return mStyleMessages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}