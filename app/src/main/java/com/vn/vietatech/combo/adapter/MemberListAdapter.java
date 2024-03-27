package com.vn.vietatech.combo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vn.vietatech.combo.R;
import com.vn.vietatech.model.Member;

import java.util.ArrayList;

public class MemberListAdapter extends ArrayAdapter<Member> {

    private Context context;
    // Your custom values for the spinner (Session)
    private ArrayList<Member> values;

    public TextView txtMemName;
    public TextView txtMemId;
    public CheckBox cbMem;


    public MemberListAdapter(@NonNull Context context, ArrayList<Member> values) {
        super(context, R.layout.member_item_layout, values);
        this.context = context;
        this.values = values;

    }

    public int getCount() {
        return values.size();
    }

    public Member getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void disableAll()
    {
        for (int i = 0; i < getCount(); i++) {
            values.get(i).checked = false;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);;
            View view = inflater.inflate(R.layout.member_item_layout, null, true);
            txtMemName = (TextView) view.findViewById(R.id.txtMemName);
            txtMemId = (TextView) view.findViewById(R.id.txtMemId);
            cbMem = (CheckBox) view.findViewById(R.id.cbMem);

            Member member = values.get(position);
            txtMemName.setText(member.memberName);
            txtMemId.setText(member.memberId);
            cbMem.setClickable(false);
            cbMem.setChecked(member.checked);
            return view;
        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(context,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }
}
