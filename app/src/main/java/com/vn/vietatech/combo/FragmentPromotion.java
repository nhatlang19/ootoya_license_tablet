package com.vn.vietatech.combo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.vn.vietatech.combo.adapter.PromotionAdapter;
import com.vn.vietatech.combo.adapter.SalesCodeAdapter;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Promotion;
import com.vn.vietatech.model.SalesCode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentPromotion extends DialogFragment {
    private ArrayList<Item> items;
    private Button btnPromoOk;
    private Button btnPromoCancel;
    private Spinner spinPromotion;
    private PromotionAdapter promotionAdapter;
    private Promotion selectedPromo = null;

    private int selectedIndex = 0;

    private int numberClick = 1;

    private ArrayList<Promotion> promotions;

    public FragmentPromotion() {
        // Required empty public constructor
    }

    public FragmentPromotion(ArrayList<Promotion> promotions, ArrayList<Item> items, int numberClick) {
        this.promotions = promotions;
        this.items = items;
        this.numberClick = numberClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);

        btnPromoOk = (Button) view.findViewById(R.id.btnPromoOk);
        btnPromoCancel = (Button) view.findViewById(R.id.btnPromoCancel);
        spinPromotion = (Spinner) view.findViewById(R.id.spinPromotion);

        promotionAdapter = new PromotionAdapter(view.getContext(),
                android.R.layout.simple_spinner_item, this.promotions);
        spinPromotion.setAdapter(promotionAdapter);

        registerEvents();
        return view;
    }

    private void registerEvents() {
        btnPromoCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPromoOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addItem();
                dismiss();
            }
        });

        spinPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                selectedPromo = promotionAdapter.getItem(position);
                selectedIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPromo = promotionAdapter.getItem(0);
                selectedIndex = 0;
            }
        });
    }

    private void addItem()
    {
        int index = selectedIndex > 0 ? selectedIndex - 1 : 0;
        Item item = items.get(index);
        if (selectedIndex == 0) {
            item.setOnPromotion("N");
        }
        item.setNumberClick(numberClick);
        POSMenuActivity activity = (POSMenuActivity) this.getActivity();
        activity.addItem(item);
    }
}