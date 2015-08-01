package com.jeegoo.o2o.tehuiduo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jeegoo.o2o.tehuiduo.ProductDetailActivity;
import com.jeegoo.o2o.tehuiduo.R;
import com.jeegoo.o2o.tehuiduo.model.StyleMessage;
import com.jeegoo.o2o.tehuiduo.model.UserLab;

import java.util.ArrayList;

/**
 * Created by xintong on 2015/7/14.
 */
public class ListProductCardStyleRecyclerViewAdapter extends RecyclerView.Adapter<ListProductCardStyleRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StyleMessage> mStyleMessages;
    public ListProductCardStyleRecyclerViewAdapter(Context mContext,ArrayList<StyleMessage> mStyleMessages) {
        this.mContext = mContext;
        this.mStyleMessages = mStyleMessages;
    }

    @Override
    public ListProductCardStyleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_product_detail_style_message, parent, false);

        return new ViewHolder(view);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ListProductCardStyleRecyclerViewAdapter.ViewHolder holder, int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //mContext.startActivity(new Intent(mContext, ProductDetailActivity.class));
                    }
                });
                animator.start();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showDialog(mContext.getResources().getString(R.string.dialog_content_delete_style),holder.getPosition());
                    }
                });
                animator.start();
                return true;
            }
        });
        TextView style = (TextView) view.findViewById(R.id.style);
        style.setText("款式:" + mStyleMessages.get(position).getStyleName());
        TextView color = (TextView) view.findViewById(R.id.color);
        color.setText("颜色:" + mStyleMessages.get(position).getColor());
        TextView size = (TextView) view.findViewById(R.id.size);
        size.setText("尺寸:"+mStyleMessages.get(position).getSize());
        TextView count = (TextView) view.findViewById(R.id.count);
        count.setText("数量:"+mStyleMessages.get(position).getCount());
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
    private void showDialog(String content, final int index) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder((Activity) mContext);
        MaterialDialog materialDialog = builder.title(R.string.dialog_title_image).content(content).positiveText(R.string.action_delete).negativeText(android.R.string.cancel).callback(new MaterialDialog.Callback() {
            @Override
            public void onPositive(MaterialDialog materialDialog) {
            mStyleMessages.remove(index);
                notifyDataSetChanged();

            }

            @Override
            public void onNegative(MaterialDialog materialDialog) {

            }
        }).build();
        materialDialog.show();
    }
}