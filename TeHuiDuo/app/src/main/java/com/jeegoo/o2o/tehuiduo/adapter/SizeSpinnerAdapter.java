package com.jeegoo.o2o.tehuiduo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.R;
import com.jeegoo.o2o.tehuiduo.model.ColorTable;
import com.jeegoo.o2o.tehuiduo.model.SizeTable;

import java.util.ArrayList;

/**
 * Created by xintong on 2015/7/28.
 */
public class SizeSpinnerAdapter extends BaseAdapter {
    Context mContext;
    private ArrayList<SizeTable> mSizeTables;

    public SizeSpinnerAdapter(Context context,  ArrayList<SizeTable> sizeTables) {
        this.mContext = context;
        this.mSizeTables = sizeTables;
    }

    @Override
    public int getCount() {
        return mSizeTables.size();
    }

    @Override
    public Object getItem(int position) {
        return  mSizeTables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater .inflate(R.layout.size_spinner_item, null);
        if(convertView!=null)
        {

            TextView sizeName=(TextView)convertView.findViewById(R.id.sizeName);

            sizeName.setText(mSizeTables.get(position).getSizeName());
        }
        return convertView;
    }
}
