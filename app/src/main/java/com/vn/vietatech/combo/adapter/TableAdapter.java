package com.vn.vietatech.combo.adapter;

import java.util.ArrayList;

import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.combo.TableActivity;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.SalesCode;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.combo.R;
import com.vn.vietatech.utils.Utils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TableAdapter extends BaseAdapter {
    private Context mContext;
    private Section section;
    ArrayList<Table> tables;
    ArrayList<Table> tablesRepeat;
    private Cashier tableGroup = null;
    private Order selectOrder = null;
    private SalesCode selectedSalesCode = null;
    private boolean isClick = false;

    public TableAdapter(Context c, Section currentSection) {
        this.section = currentSection;
        this.mContext = c;

        tables = new ArrayList<Table>();
        try {
            tables = new TableAPI(this.mContext).getTableBySection(section);
        } catch (Exception e) {
            Toast.makeText(this.mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public int getCount() {
        return tables.size();
    }

    public Object getItem(int position) {
        return tables.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button btn;
        final Table table = tables.get(position);

        btn = new Button(mContext);
        btn.setLayoutParams(new GridView.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        btn.setBackgroundResource(table.getColor());
        btn.setOnClickListener(new OnClickListener() {
            final MyApplication globalVariable = (MyApplication) mContext
                    .getApplicationContext();
            Cashier cashier = globalVariable.getCashier();

            @Override
            public void onClick(View view) {
                if (!isClick) {
                    try {
                        tablesRepeat = new TableAPI(mContext).getTableBySection(section);
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    Table tableRepeat = tablesRepeat.get(position);
                    //String openBy = table.getOpenBy().trim();
                    String openBy = tableRepeat.getOpenBy().trim();
                    switch (tableRepeat.getStatus()) {
                        case "A":
                            if (openBy.isEmpty()) {
                                try {
                                    boolean result = new TableAPI(mContext)
                                            .updateTableStatus(Table.STATUS_OPEN,
                                                    cashier.getId(),
                                                    table.getTableNo());
                                    if (!result) {
                                        Toast.makeText(mContext, "Không thể cập nhật trạng thái bàn",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        showOrderForm(table);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Utils.showAlert(mContext, String.format(
                                        "Bàn %s đang được order bởi cashier %s", table
                                                .getTableNo().trim(), table
                                                .getOpenBy().trim()));
                            }
                            break;
                        case "B":
                        case "O":
                            if (openBy.isEmpty()) {
                                try {
                                    boolean result = new TableAPI(mContext)
                                            .updateTableStatus(Table.STATUS_OPEN,
                                                    cashier.getId(),
                                                    table.getTableNo());
                                    if (!result) {
                                        Toast.makeText(mContext,
                                                "Không thể cập nhật trạng thái bàn", Toast.LENGTH_LONG).show();
                                    } else {
                                        table.setAction(Table.ACTION_EDIT);
                                        showOrderForm(table);
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(mContext, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Utils.showAlert(mContext, String.format(
                                        "Bàn %s đang được order bởi cashier %s", table
                                                .getTableNo().trim(), table
                                                .getOpenBy().trim()));
                            }
                            break;

                        case "R":
                            break;
                    }
                }
                isClick = !isClick;
            }

        });

        String title = table.getTableNo().trim();
        if (!table.getDescription2().isEmpty()) {
            title += "/" + table.getDescription2().trim();
        }
        btn.setText(title);
        return btn;
    }

    private void showOrderForm(final Table table) {

        // get order_dialog.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View promptView = layoutInflater.inflate(R.layout.order_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // set order_dialog.xml to be the layout file of the alertdialog builder
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);

        // create an alert dialog
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

        final TextView lbTitle = (TextView) promptView
                .findViewById(R.id.lbTitle);
        final Button btnSave = (Button) promptView.findViewById(R.id.btnSave);
        final Button btnOk = (Button) promptView.findViewById(R.id.btnOk);
        final Button btnCancel = (Button) promptView
                .findViewById(R.id.btnCancel);
        final Spinner spinGroup = (Spinner) promptView
                .findViewById(R.id.spinGroup);
        final TableListAdapter tableListAdapter;
        final CashierListAdapter cashierListAdapter;

        final Spinner spinSalesCode = (Spinner) promptView
                .findViewById(R.id.spinSalesCode);
        final SalesCodeAdapter salesCodeAdapter;

        String title = "Table: " + table.getTableNo().trim() + " => ";
        if (table.isAddNew()) {
            title += "New Order";
//            btnSave.setEnabled(false);
//            btnSave.setTextColor(Color.GRAY);
        } else {
            title += "Edit Order";
        }
        lbTitle.setText(title);

        final MyApplication globalVariable = (MyApplication) mContext
                .getApplicationContext();
        ArrayList<Table> tableList = globalVariable.getTables();
        ArrayList<Cashier> cashiers = globalVariable.getCashiers();
        ArrayList<SalesCode> salesCodes = globalVariable.getSalesCode();
        final Cashier cashier = globalVariable.getCashier();
        if (tableList != null) {
//            tableListAdapter = new TableListAdapter(mContext,
//                    android.R.layout.simple_spinner_item, tableList);
            cashierListAdapter = new CashierListAdapter(mContext,
                    android.R.layout.simple_spinner_item, cashiers);
            spinGroup.setAdapter(cashierListAdapter);

            salesCodeAdapter = new SalesCodeAdapter(mContext,
                    android.R.layout.simple_spinner_item, salesCodes);
            spinSalesCode.setAdapter(salesCodeAdapter);
            int pos = salesCodeAdapter.findBySaleCode(table.getSalesCode());
            spinSalesCode.setSelection(pos);
            spinSalesCode.setEnabled(false);

            spinGroup.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    tableGroup = cashierListAdapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            spinSalesCode
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int position, long id) {
                            selectedSalesCode = salesCodeAdapter
                                    .getItem(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

            String tblGroup = table.getDescription2().trim();
            if (tblGroup.length() != 0) {
                for (int i = 0; i < cashierListAdapter.getCount(); i++) {
                    String findTable = cashierListAdapter.getItem(i).getId()
                            .trim();
                    if (findTable.equals(tblGroup)) {
                        spinGroup.setSelection(i);
                        break;
                    }
                }
            }
        }

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSave.isEnabled()) {
                    alertD.cancel();

                    TableActivity tableActivity = (TableActivity) mContext;
                    tableActivity.groupTable(table, tableGroup);
                }
            }
        });

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                alertD.cancel();

                TableActivity tableActivity = (TableActivity) mContext;
                boolean isNewTable = table.isAddNew();
                if (isNewTable) {
                    tableActivity.startNewActivity(table, tableGroup,
                            selectedSalesCode);
                } else {
                    String POSBizDate = globalVariable.getPosBizDate();
                    try {
                        ArrayList<Order> orders = new OrderAPI(mContext)
                                .getOrderEditType(POSBizDate,
                                        table.getTableNo());
                        int size = orders.size();
                        if (size == 0) {
                            Utils.showAlert(
                                    mContext,
                                    "Error BizDate >> Không thể load lại order. Vui lòng kiểm tra kết ngày.");
                        } else if (size == 1) {
                            tableActivity.startEditActivity(table, tableGroup,
                                    orders.get(0), selectedSalesCode);
                        } else {
                            showSelectBill(table, orders);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new TableAPI(mContext).updateTableStatus(
                            Table.STATUS_CLOSE, cashier.getId(),
                            table.getTableNo());
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

                alertD.cancel();

                TableActivity tableActivity = (TableActivity) mContext;
                tableActivity.refresh();
            }
        });
    }

    private void showSelectBill(final Table table, ArrayList<Order> orders) {

        // get order_dialog.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View promptView = layoutInflater.inflate(R.layout.n_bill, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // set order_dialog.xml to be the layout file of the alertdialog builder
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);

        // create an alert dialog
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

        final TextView lbTitle = (TextView) promptView
                .findViewById(R.id.lbTitle);
        final Button btnSave = (Button) promptView
                .findViewById(R.id.btnSelectOrder);
        final Spinner spinGroup = (Spinner) promptView
                .findViewById(R.id.spinOrders);
        final BillAdapter billAdapter;

        billAdapter = new BillAdapter(mContext,
                android.R.layout.simple_spinner_item, orders);
        spinGroup.setAdapter(billAdapter);
        spinGroup.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectOrder = billAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSave.isEnabled()) {
                    alertD.cancel();

                    if (selectOrder != null) {
                        TableActivity tableActivity = (TableActivity) mContext;
                        tableActivity.startEditActivity(table, tableGroup,
                                selectOrder, selectedSalesCode);
                    } else {
                        Utils.showAlert(mContext, "Vui lòng chọn bill");
                    }
                }
            }
        });

    }
}