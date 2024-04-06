package com.vn.vietatech.combo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vn.vietatech.api.MemberAPI;
import com.vn.vietatech.combo.adapter.MemClassAdapter;
import com.vn.vietatech.combo.adapter.MemGradeAdapter;
import com.vn.vietatech.combo.adapter.MemberListAdapter;
import com.vn.vietatech.combo.adapter.NationalityAdapter;
import com.vn.vietatech.model.Member;

import java.util.ArrayList;
import java.util.Objects;

public class MemberFragment extends DialogFragment {

    Button btnCloseMember;
    Button btnMemberSearch;
    TextView lbTitleMember;
    TextView txtMemberKeyword;

    ListView lstMembers;

    Context mContext;

    MemberListAdapter memberListAdapter;

    Member selectedMember;

    LinearLayout lnSearch;
    LinearLayout lnList;
    LinearLayout lnFormAdd;
    Button btnAddMember;
    Button btnCloseFormAdd;
    Button btnSaveFormAdd;

    Spinner spinMemberNationality;
    Spinner spinMemberType;
    Spinner spinMemberGrade;
    MemClassAdapter memClassAdapter;
    MemGradeAdapter memGradeAdapter;
    NationalityAdapter nationalityAdapter;


    boolean isGlobal;

    public MemberFragment() {
        super();
        this.isGlobal = false;
        this.selectedMember = null;
    }

    public MemberFragment(boolean isGLobal) {
        this.isGlobal = isGLobal;
        this.selectedMember = null;
    }

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
        lnSearch = (LinearLayout) view.findViewById(R.id.lnSearch);
        lnList = (LinearLayout) view.findViewById(R.id.lnList);
        lnFormAdd = (LinearLayout) view.findViewById(R.id.lnFormAdd);
        btnAddMember = (Button) view.findViewById(R.id.btnAddMember);
        btnSaveFormAdd = (Button) view.findViewById(R.id.btnSaveFormAdd);
        btnCloseFormAdd = (Button) view.findViewById(R.id.btnCloseFormAdd);
        spinMemberNationality = (Spinner) view.findViewById(R.id.spinMemberNationality);
        spinMemberType = (Spinner) view.findViewById(R.id.spinMemberType);
        spinMemberGrade = (Spinner) view.findViewById(R.id.spinMemberGrade);

        final MyApplication globalVariable = (MyApplication) mContext
                .getApplicationContext();

        memClassAdapter = new MemClassAdapter(mContext,
                android.R.layout.simple_spinner_item, globalVariable.getMemClasses());
        spinMemberType.setAdapter(memClassAdapter);
        memGradeAdapter = new MemGradeAdapter(mContext,
                android.R.layout.simple_spinner_item, globalVariable.getMemGrades());
        spinMemberGrade.setAdapter(memGradeAdapter);
        nationalityAdapter = new NationalityAdapter(mContext,
                android.R.layout.simple_spinner_item, globalVariable.getNationalities());
        spinMemberNationality.setAdapter(nationalityAdapter);


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
                if (isGlobal) {
                    insertMember();
                } else {
                    insertMemberRemake();
                }

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

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnSearch.setVisibility(View.GONE);
                lnList.setVisibility(View.GONE);

                lnFormAdd.setVisibility(View.VISIBLE);
            }
        });

        btnCloseFormAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnSearch.setVisibility(View.VISIBLE);
                lnList.setVisibility(View.VISIBLE);

                lnFormAdd.setVisibility(View.GONE);
            }
        });

        btnSaveFormAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lstMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memberListAdapter.disableAll();

                selectedMember = memberListAdapter.getItem(position);

                Objects.requireNonNull(memberListAdapter.getItem(position)).checked = true;
                memberListAdapter.notifyDataSetChanged();
                ;
            }
        });
    }

    private void insertMemberRemake() {
        try {
            if (this.selectedMember != null) {
                POSMenuActivity activity = (POSMenuActivity) this.getActivity();
                assert activity != null;
                activity.insertMemberRemake(selectedMember);
            }
        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(mContext,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertMember() {
        try {
            if (this.selectedMember != null) {
                POSMenuActivity activity = (POSMenuActivity) this.getActivity();
                assert activity != null;
                activity.insertMember(selectedMember);
            }
        } catch (Exception ex) {
            System.out.println("messages:" + ex.getMessage());
            Toast.makeText(mContext,
                    ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}