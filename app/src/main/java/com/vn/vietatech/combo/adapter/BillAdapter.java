package com.vn.vietatech.combo.adapter;

import java.util.ArrayList;

import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.Table;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BillAdapter extends ArrayAdapter<Order> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<Order> values;

    public BillAdapter(Context context, int textViewResourceId,
                       ArrayList<Order> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Order getItem(int position) {
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
            label.setTextSize(18);
            label.setTypeface(Typeface.DEFAULT_BOLD);
        }

        label.setText(values.get(position).getOrdExt().trim());
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

        label.setText(values.get(position).getOrdExt().trim());
        label.setTextSize(18);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }
}
