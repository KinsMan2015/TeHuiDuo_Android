package com.jeegoo.o2o.tehuiduo.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeegoo.o2o.tehuiduo.R;
import com.jeegoo.o2o.tehuiduo.model.ColorTable;

import java.util.ArrayList;

/**
 * Created by xintong on 2015/7/28.
 */
public class ColorSpinnerAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<ColorTable> mColorTables;

    public ColorSpinnerAdapter(Context context, ArrayList<ColorTable> colorTables) {
        this.mContext = context;
        this.mColorTables = colorTables;
    }

    @Override
    public int getCount() {
        return mColorTables.size();
    }

    @Override
    public Object getItem(int position) {
       return  mColorTables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater .inflate(R.layout.color_spinner_item, null);
        if(convertView!=null)
        {
            ImageView imageView=(ImageView)convertView.findViewById(R.id.colorImage);
            TextView  colorName=(TextView)convertView.findViewById(R.id.colorName);
            imageView.setBackground(new ColorDrawable(Color.parseColor(mColorTables.get(position).getColor())));
            colorName.setText(mColorTables.get(position).getColorName());
        }
        return convertView;
    }
}
