package com.vn.vietatech.combo.adapter;

import java.util.ArrayList;

import com.vn.vietatech.model.Section;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SectionAdapter extends ArrayAdapter<Section> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<Section> values;

    public SectionAdapter(Context context, int textViewResourceId,
                          ArrayList<Section> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Section getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);

        if (convertView != null) {
            label = (TextView) convertView;
        } else {
        	/*LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        	label = (TextView) inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );*/
            label.setPadding(10, 27, 10, 27);
        }

        label.setText(values.get(position).getName());
        label.setTypeface(Typeface.DEFAULT_BOLD);
        label.setTextSize(16);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if (convertView != null) {
            label = (TextView) convertView;
        } else {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            label = (TextView) inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
        label.setText(values.get(position).getName());
        label.setTextSize(18);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }
}
