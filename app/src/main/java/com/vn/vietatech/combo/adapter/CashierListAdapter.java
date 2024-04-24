package com.vn.vietatech.combo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.SalesCode;
import com.vn.vietatech.model.Table;

import java.util.ArrayList;

public class CashierListAdapter extends ArrayAdapter<Cashier> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<Cashier> values;

    public CashierListAdapter(Context context, int textViewResourceId,
                              ArrayList<Cashier> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Cashier getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int findByGroupName(String group) {
        for (int i = 0; i < getCount(); i++) {
            Cashier tmp = getItem(i);
            if (tmp.getName().equals(group)) {
                return i;
            }
        }
        return 0;
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

        label.setText(values.get(position).getName().trim());
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

        label.setText(values.get(position).getName().trim());
        label.setTextSize(20);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }
}
