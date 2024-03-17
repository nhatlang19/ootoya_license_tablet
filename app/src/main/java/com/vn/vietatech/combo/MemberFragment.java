package com.vn.vietatech.combo;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.vn.vietatech.combo.databinding.FragmentMembersBinding;
import com.vn.vietatech.combo.view.tab.FragmenTab;
import com.vn.vietatech.model.Member;
import com.vn.vietatech.utils.Utils;

public class MemberFragment extends DialogFragment {

    Button btnCloseMember;
    TextView lbTitleMember;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_members, container, false);

        btnCloseMember = (Button) view.findViewById(R.id.btnCloseMember);
        lbTitleMember = (TextView) view.findViewById(R.id.lbTitleMember);
        lbTitleMember.setText("Search Member");
        registerEvents();
        return view;
    }

    private void registerEvents() {
        btnCloseMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMember();
                dismiss();
            }
        });
    }

    private void insertMember()
    {
        POSMenuActivity activity = (POSMenuActivity) this.getActivity();
        activity.insertMember(new Member("AAA", "AAA"));
    }
}