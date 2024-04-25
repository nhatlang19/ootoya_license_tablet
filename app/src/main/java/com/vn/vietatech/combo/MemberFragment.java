package com.vn.vietatech.combo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.vn.vietatech.api.PosMenuAPI;
import com.vn.vietatech.combo.adapter.MemClassAdapter;
import com.vn.vietatech.combo.adapter.MemGradeAdapter;
import com.vn.vietatech.combo.adapter.MemberListAdapter;
import com.vn.vietatech.combo.adapter.NationalityAdapter;
import com.vn.vietatech.model.Member;
import com.vn.vietatech.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
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
    Button btnCheckDuplicate;

    Spinner spinMemberNationality;
    Spinner spinMemberType;
    Spinner spinMemberGrade;
    MemClassAdapter memClassAdapter;
    MemGradeAdapter memGradeAdapter;
    NationalityAdapter nationalityAdapter;

    EditText txtMemberName;
    EditText txtMemberMobile;
    EditText txtMemberEmail;
    EditText txtMemberBirth;
    EditText txtMemberTax;
    EditText txtMemberCompanyName;
    EditText txtMemberCompanyAddress;



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
        btnCheckDuplicate = (Button) view.findViewById(R.id.btnCheckDuplicate);
        spinMemberNationality = (Spinner) view.findViewById(R.id.spinMemberNationality);
        spinMemberType = (Spinner) view.findViewById(R.id.spinMemberType);
        spinMemberGrade = (Spinner) view.findViewById(R.id.spinMemberGrade);
        txtMemberName = (EditText) view.findViewById(R.id.txtMemberName);
        txtMemberMobile = (EditText) view.findViewById(R.id.txtMemberMobile);
        txtMemberEmail = (EditText) view.findViewById(R.id.txtMemberEmail);
        txtMemberBirth = (EditText) view.findViewById(R.id.txtMemberBirth);
        txtMemberTax = (EditText) view.findViewById(R.id.txtMemberTax);
        txtMemberCompanyName = (EditText) view.findViewById(R.id.txtMemberCompanyName);
        txtMemberCompanyAddress = (EditText) view.findViewById(R.id.txtMemberCompanyAddress);

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
//            ArrayList<Member> members = new MemberAPI(mContext).getMembers(txtMemberKeyword.getText().toString().trim());
            ArrayList<Member> members = new ArrayList<>();
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
        spinMemberType.setSelection(0, true);
        spinMemberGrade.setSelection(0, true);
        spinMemberNationality.setSelection(0, true);

        txtMemberBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtMemberBirth.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(dayOfMonth).toString());
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

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
                    ArrayList<Member> members = new MemberAPI(mContext).searchMembers(txtMemberKeyword.getText().toString().trim());
                    memberListAdapter.clear();
                    memberListAdapter.addAll(members);
                    memberListAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    ArrayList<Member> members = new ArrayList<>();
                    memberListAdapter.clear();
                    memberListAdapter.addAll(members);
                    memberListAdapter.notifyDataSetChanged();
                }
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnSearch.setVisibility(View.GONE);
                lnList.setVisibility(View.GONE);
                btnAddMember.setVisibility(View.GONE);
                btnCloseMember.setVisibility(View.GONE);

                lnFormAdd.setVisibility(View.VISIBLE);
                btnCloseFormAdd.setVisibility(View.VISIBLE);
                btnSaveFormAdd.setVisibility(View.VISIBLE);
                btnCheckDuplicate.setVisibility(View.VISIBLE);
            }
        });

        btnCloseFormAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnSearch.setVisibility(View.VISIBLE);
                lnList.setVisibility(View.VISIBLE);
                btnAddMember.setVisibility(View.VISIBLE);
                btnCloseMember.setVisibility(View.VISIBLE);

                lnFormAdd.setVisibility(View.GONE);
                btnCloseFormAdd.setVisibility(View.GONE);
                btnSaveFormAdd.setVisibility(View.GONE);
                btnCheckDuplicate.setVisibility(View.GONE);
            }
        });

        btnCheckDuplicate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String memberName = txtMemberName.getText().toString();
                    if (!memberName.isEmpty()) {
                        ArrayList<Member> members = new MemberAPI(mContext).checkDuplicateName(memberName);
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < members.size(); i++) {
                            result.append(members.get(i).memberName).append("\n");
                        }
                        Utils.showAlert(mContext, result.toString());
                    } else {
                        Utils.showAlert(mContext, "Không có tên trùng");
                    }
                } catch (Exception e) {
                    System.out.println("messages:" + e.getMessage());
                    Utils.showAlert(mContext, "Không có tên trùng");
                }
            }
        });

        btnSaveFormAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member member = new Member();
                member.memberName = txtMemberName.getText().toString();
                member.mobile = txtMemberMobile.getText().toString();
                member.email = txtMemberEmail.getText().toString();
                member.dob = txtMemberBirth.getText().toString();
                member.taxCode = txtMemberTax.getText().toString();
                member.company = txtMemberCompanyName.getText().toString();
                member.companyAdr = txtMemberCompanyAddress.getText().toString();
                member.nationality = nationalityAdapter.getItem(spinMemberNationality.getSelectedItemPosition()).getCode();
                member.memberClass = memClassAdapter.getItem(spinMemberType.getSelectedItemPosition()).getCode();
                member.memberGrade = memGradeAdapter.getItem(spinMemberGrade.getSelectedItemPosition()).getCode();
                try {
                    if (!member.memberName.isEmpty()) {
                        String ketqua = new MemberAPI(mContext).setMember(member);
                        Utils.showAlert(mContext, ketqua);
                        txtMemberName.setText("");
                        txtMemberMobile.setText("");
                        txtMemberEmail.setText("");
                        txtMemberBirth.setText("");
                        txtMemberTax.setText("");
                        txtMemberCompanyName.setText("");
                        txtMemberCompanyAddress.setText("");
                    } else {
                        Utils.showAlert(mContext, "MemberName không thể rỗng");
                    }
                } catch (Exception e) {
                    System.out.println("messages:" + e.getMessage());
//                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    Utils.showAlert(mContext, "Khổng thể save");
                }
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