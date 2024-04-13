package com.vn.vietatech.combo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vn.vietatech.model.MemGrade;

import java.util.ArrayList;

public class MemGradeAdapter extends ArrayAdapter<MemGrade> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<MemGrade> values;

    public MemGradeAdapter(Context context, int textViewResourceId,
                           ArrayList<MemGrade> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public int findBySaleCode(String MemGrade) {
        for (int i = 0; i < getCount(); i++) {
            MemGrade tmp = getItem(i);
            if (tmp.getCode().equals(MemGrade)) {
                return i;
            }
        }
        return 0;
    }

    public MemGrade getItem(int position) {
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
            label.setPadding(10, 29, 10, 29);
        }

        label.setTextColor(Color.BLACK);
        label.setTextSize(20);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        label.setText(values.get(position).getDescription());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if (convertView != null) {
            label = (TextView) convertView;
        } else {
            label.setPadding(5, 12, 5, 17);
        }

        label.setText(values.get(position).getDescription());
        label.setTextSize(20);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        return label;
    }
}
