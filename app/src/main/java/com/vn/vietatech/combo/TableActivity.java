package com.vn.vietatech.combo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.vn.vietatech.api.SectionAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.api.UserApi;
import com.vn.vietatech.combo.adapter.SectionAdapter;
import com.vn.vietatech.combo.adapter.TableAdapter;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.SalesCode;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Setting;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.utils.SettingUtil;

import java.io.IOException;
import java.util.ArrayList;

public class TableActivity extends AppCompatActivity implements
        OnItemSelectedListener {
    public static final int REFRESH_TABLE = 1;

    public static final String KEY_SELECTED_TABLE = "selectedTable";
    public static final String KEY_SECTION_ID = "sectionID";
    public static final String KEY_SECTION_NAME = "sectionName";
    public static final String KEY_SELECTED_SCODE = "selectedSalesCode";
    public static final String KEY_PRICE_LEVEL = "priceLevel";
    public static final String KEY_REFRESH_CODE = "refresh_code";
    public static final String KEY_STATUS = "statusTable";
    public static final String KEY_TABLE_GROUP = "tableGroup";
    public static final String KEY_TABLE_GROUP_NAME = "tableGroupName";

    public static final String KEY_ORD = "ord";
    public static final String KEY_EXT = "ext";
    public static final String KEY_POS = "pos";
    public static final String KEY_PER = "per";
    public static final String KEY_SCODE = "salesCode";


    private static final int TIMER_LIMIT = 15000; // 10 seconds
    private Spinner spin;
    private GridView gridview;
    private ArrayList<Section> sections;
    private Section selectedSection;
    private SectionAdapter sectionAdapter;
    private TableAdapter tableAdapter = null;
    private Handler handler;
    private Runnable runnable;
    private boolean startDelay = false;

    private Context context = this;
    MyApplication globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        globalVariable = (MyApplication) getApplicationContext();

        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnClose = findViewById(R.id.btnClose);

        gridview = findViewById(R.id.gridMainMenu);
        spin = findViewById(R.id.spinSession);

        runnable = new Runnable() {
            public void run() {
                gridview.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                startDelay = false;
            }
        };
        handler = new Handler();

        // load all sections
        loadSections();

        // set title from global variable
        this.setTitle(globalVariable.getCashier().getName());

        gridview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reloadWaitScreen();
                return false;
            }
        });

        // close
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // refresh tables
        btnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        // load waiting_ootoya screen
        reloadWaitScreen();
    }

    /**
     * load waiting_ootoya screen
     */
    private void reloadWaitScreen() {
        if (startDelay) {
            handler.removeCallbacks(this.runnable);
        }
        handler.postDelayed(this.runnable, TIMER_LIMIT);
        startDelay = true;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        reloadWaitScreen();

        return super.onTouchEvent(event);
    }

    /**
     * load all sections
     */
    private void loadSections() {
        spin.setOnItemSelectedListener(this);
        try {
            Setting setting = SettingUtil.read(this);
            sections = globalVariable.getSections(setting.getSection());
            if (sections == null) {
                sections = new SectionAPI(getApplicationContext()).getSection(setting.getSection());
                globalVariable.setSections(setting.getSection(), sections);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        sectionAdapter = new SectionAdapter(this,
                android.R.layout.simple_spinner_item, sections);
        spin.setAdapter(sectionAdapter);

        if (sectionAdapter.getCount() != 0) {
            spin.setSelection(0);
        }
    }

    /**
     * Open New/Edit form
     *
     */
    public void startNewActivity(Table selectedTable, Cashier tableGroup, SalesCode salesCode) {
        Intent myIntent = new Intent(this, POSMenuActivity.class);
        myIntent.putExtra(KEY_SELECTED_TABLE, selectedTable.getTableNo());
        myIntent.putExtra(KEY_SECTION_ID, selectedSection.getId());
        myIntent.putExtra(KEY_SECTION_NAME, selectedSection.getName());
        myIntent.putExtra(KEY_SELECTED_SCODE, salesCode.getCode());
        myIntent.putExtra(KEY_PRICE_LEVEL, salesCode.getPriceLevel());
        myIntent.putExtra(KEY_TABLE_GROUP, tableGroup.getId());
        myIntent.putExtra(KEY_TABLE_GROUP_NAME, tableGroup.getName());
        myIntent.putExtra(KEY_STATUS, Table.ACTION_ADD);
        startActivityForResult(myIntent, REFRESH_TABLE);
    }

    public void startEditActivity(Table selectedTable, Cashier tableGroup, Order order, SalesCode salesCode) {
        Intent myIntent = new Intent(this, POSMenuActivity.class);
        myIntent.putExtra(KEY_SELECTED_TABLE, selectedTable.getTableNo());
        myIntent.putExtra(KEY_SECTION_ID, selectedSection.getId());
        myIntent.putExtra(KEY_SECTION_NAME, selectedSection.getName());
        myIntent.putExtra(KEY_TABLE_GROUP, tableGroup.getId());
        myIntent.putExtra(KEY_TABLE_GROUP_NAME, tableGroup.getName());
        myIntent.putExtra(KEY_SELECTED_SCODE, salesCode.getCode());
        myIntent.putExtra(KEY_PRICE_LEVEL, salesCode.getPriceLevel());
        myIntent.putExtra(KEY_STATUS, Table.ACTION_EDIT);
        myIntent.putExtra(KEY_ORD, order.getOrd());
        myIntent.putExtra(KEY_EXT, order.getExt());
        myIntent.putExtra(KEY_POS, order.getPos());
        myIntent.putExtra(KEY_PER, order.getPer());
        myIntent.putExtra(KEY_SCODE, order.getSalesCode());

        startActivityForResult(myIntent, REFRESH_TABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int refresh_code = data.getIntExtra(KEY_REFRESH_CODE, REFRESH_TABLE);
        String tableNo = data.getExtras().getString(KEY_SELECTED_TABLE);
        if (resultCode == RESULT_OK && refresh_code == REFRESH_TABLE) {
            refresh();

            MyApplication tmp = (MyApplication) getApplicationContext();
            Cashier cashier = tmp.getCashier();
            try {
                new TableAPI(context).updateTableStatus(
                        Table.STATUS_CLOSE, cashier.getId(), tableNo);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * refresh tables
     */
    public void refresh() {
        reloadWaitScreen();

        gridview.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        tableAdapter = new TableAdapter(this, selectedSection);
        // load all pages
        gridview.setAdapter(tableAdapter);
    }

    /**
     * group table
     */
    public void groupTable(Table table, Cashier tableGroup) {
        try {
            final Cashier cashier = globalVariable.getCashier();
            new TableAPI(context).updateTableStatus(Table.STATUS_CLOSE, cashier.getId(), table.getTableNo());
            new TableAPI(context).groupTable(table.getTableNo(), tableGroup.getName());
        } catch (Exception e) {
        }
        refresh();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        selectedSection = sectionAdapter.getItem(position);
        refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
}
