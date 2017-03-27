package com.lee.myapplication.spiner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lee.myapplication.R;

/**
 * Created by alvinlee on 16/7/18.
 */
public class OptionsAdapter extends BaseAdapter {
    private Activity activity = null;
    private String str[];

    public OptionsAdapter(Activity activity, String str[]) {
        this.activity = activity;
        this.str = str;
    }

    @Override
    public int getCount() {
        return str.length;
    }


    @Override
    public Object getItem(int position) {
        return str[position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.option_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(str[position]);
        return convertView;
    }


    class ViewHolder {
        TextView textView;
    }
}


