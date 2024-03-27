package com.vn.vietatech.combo;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.vietatech.api.MemberAPI;
import com.vn.vietatech.combo.adapter.MemberListAdapter;
import com.vn.vietatech.combo.view.tab.FragmenTab;
import com.vn.vietatech.model.Member;
import com.vn.vietatech.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MemberFragment extends DialogFragment {

    Button btnCloseMember;
    Button btnMemberSearch;
    TextView lbTitleMember;
    TextView txtMemberKeyword;

    ListView lstMembers;

    Context mContext;

    MemberListAdapter memberListAdapter;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_members, container, false);

        mContext = view.getContext();

        btnCloseMember = (Button) view.findViewById(R.id.btnCloseMember);
        btnMemberSearch = (Button) view.findViewById(R.id.btnMemberSearch);
        lbTitleMember = (TextView) view.findViewById(R.id.lbTitleMember);
        lbTitleMember.setText("Search Member");

        txtMemberKeyword = (EditText) view.findViewById(R.id.txtMemberKeyword);
        lstMembers = (ListView) view.findViewById(R.id.lstMembers);


        registerEvents();

        try {
            ArrayList<Member> members = new MemberAPI(mContext).getMembers(txtMemberKeyword.getText().toString().trim());
            memberListAdapter = new MemberListAdapter(view.getContext(), members);
            lstMembers.setAdapter(memberListAdapter);

        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(mContext,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void registerEvents() {
        btnCloseMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertMemberRemake();
                insertMember();
                dismiss();
            }
        });

        btnMemberSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Member> members = new MemberAPI(mContext).getMembers(txtMemberKeyword.getText().toString().trim());
                    memberListAdapter.clear();
                    memberListAdapter.addAll(members);
                    memberListAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    System.out.println("messages:" + ex.getMessage());
                    Toast.makeText(mContext,
                            ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        lstMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memberListAdapter.disableAll();
                Objects.requireNonNull(memberListAdapter.getItem(position)).checked = true;
                memberListAdapter.notifyDataSetChanged();;
            }
        });
    }

    private void insertMemberRemake()
    {
        try {
            POSMenuActivity activity = (POSMenuActivity) this.getActivity();
            activity.insertMemberRemake(new Member("AAA", "AAA"));
        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(mContext,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertMember()
    {
        try {
            POSMenuActivity activity = (POSMenuActivity) this.getActivity();
            activity.insertMember(new Member("AAA", "AAA"));
        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(mContext,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}