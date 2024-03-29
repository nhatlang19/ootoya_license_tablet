package com.vn.vietatech.combo.adapter;

import java.util.ArrayList;

import com.vn.vietatech.model.Table;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TableListAdapter extends ArrayAdapter<Table> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<Table> values;

    public TableListAdapter(Context context, int textViewResourceId,
                            ArrayList<Table> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Table getItem(int position) {
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
//        	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        	label = (TextView) inflater.inflate(
//                    android.R.layout.simple_dropdown_item_1line, parent, false
//            );
            label.setPadding(10, 29, 10, 29);
        }

        label.setText(values.get(position).getTableNo().trim());
        label.setTextSize(16);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if (convertView != null) {
            label = (TextView) convertView;
        } else {
//        	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        	label = (TextView) inflater.inflate(
//                    android.R.layout.simple_dropdown_item_1line, parent, false
//            );
            label.setPadding(5, 12, 5, 17);
        }

        label.setText(values.get(position).getTableNo().trim());
        label.setTextSize(20);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }
}
