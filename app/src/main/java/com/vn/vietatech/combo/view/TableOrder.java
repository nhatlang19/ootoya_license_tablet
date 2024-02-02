package com.vn.vietatech.combo.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.vn.vietatech.combo.view.table.DataTable;
import com.vn.vietatech.combo.view.table.MyTable;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Remark;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.utils.Utils;

import java.util.ArrayList;

public class TableOrder extends TableLayout
{
    public static final String colQ = "Q";
    public static final String colP = "P";
    public static final String colName = "Name";
    public static final String colPrice = "Price";
    public static final String colDisc = "Disc";
    public static final String colTotal = "Total";
    public static final String colType = "Type";
    public static final String colItemCode = "ItemCode";
    public static final String colInstruction = "Instruction";
    public static final String colModifier = "Modifier";
    public static final String colMasterCode = "MasterCode";
    public static final String colCombo = "Combo";
    public static final String colHidden = "Hidden";
    public static final String colSegNo = "SegNo";
    public static final String colpCode = "p.Code";
    public static final String colpClass = "p.Class";
    public static final String colpPkgPrice = "p.PkgPrice";
    public static final String colpPkgQty = "p.PkgQty";
    public static final String colpPkgItems = "p.PkgItems";
    public static final String colpBlanket = "p.Blanket";
    public static final String colTax = "Tax";
    public static final String colTaxAmt = "TaxAmt";
    public static String STATUS_DATATABLE_NO_DATA = "No Data";
    public static String STATUS_DATATABLE_SEND_ALL = "Send All";
    public static String STATUS_DATATABLE_RESEND = "Bạn muốn Gửi Lại order ?";
    private String[] headers = new String[]{
            colQ,
            colP,
            colName,
            colPrice,
            colDisc,
            colTotal,
            colType,
            colItemCode,
            colInstruction,
            colModifier,
            colMasterCode,
            colCombo,
            colHidden,
            colSegNo,
            colpCode,
            colpClass,
            colpPkgPrice,
            colpPkgQty,
            colpPkgItems,
            colpBlanket,
            colTax,
            colTaxAmt
    };
    private Integer[] headerWidth = new Integer[]{
            80, //Q
            60, //P
            420, //Name
            170, //OPrice
            170, //P.Price
            170, //Total
            150, //Type
            220, //ItemCode
            300, //Instruction
            200, //Modifier
            250, //MasterCode
            180, //Combo
            170, //Hiden
            170, //SeqNo
            200, //p.Code
            230, //p.Class
            250, //p.PkgPrice
            250, //p.PkgQty
            250, //p.PkgItem
            250, //p.Blanket
            100, //Tax
            250, //TaxAmt
    };
    private Integer[] headerGravity = new Integer[]{
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.LEFT,
            Gravity.RIGHT,
            Gravity.RIGHT,
            Gravity.RIGHT,
            Gravity.LEFT,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.LEFT,
            Gravity.RIGHT,
            Gravity.RIGHT,};
    private MyTable table;

    public TableOrder(Context context, LinearLayout parent)
    {
        super(context);

        setLayoutParams(parent.getLayoutParams());
        table = new MyTable(context, initDataTable());
    }

    public MyTable getTable()
    {
        return table;
    }

    private ArrayList<DataTable> initDataTable()
    {
        ArrayList<DataTable> data = new ArrayList<DataTable>();
        int size = headers.length;

        for (int i = 0; i < size; i++)
        {
            String name = headers[i];
            int width = headerWidth[i];
            int gravity = headerGravity[i];

            data.add(new DataTable(name, width, gravity));
        }
        return data;
    }

    public int getSegNo()
    {
        return table.getBody().getSegNo();
    }

    public ArrayList<ItemRow> getAllRows()
    {
        return table.getBody().getAllRows();
    }

    public ItemRow getCurrentRow()
    {
        return table.getBody().getCurrentRow();
    }

    public int getCurrentIndex()
    {
        return table.getBody().getCurrentIndex();
    }

    public ItemRow getRowIndex(int index)
    {
        return table.getBody().getRowIndex(index);
    }

    public boolean createNewRow(Item item)
    {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();
        int index = -1;
        if (!item.getItemType().equals("C") && !item.getItemType().equals("M"))
        {
            for (int i = listRow.size() - 1; i >= 0; i--)
            {
                ItemRow row = listRow.get(i);
                if (row.getCurrentItem().getItemCode().equals(item.getItemCode()) && !row.getCurrentItem().getItemType().equals("M"))
                {
                    index = i;
                    break;
                }
            }
        }
        if (index != -1)
        {
            // update quality
            TextView txtStatus = (TextView) getColumnByRow(index, colP);
            if (txtStatus != null
                    && !txtStatus.getText().equals(Item.STATUS_OLD)
                    && !txtStatus.getText().equals(Item.STATUS_CANCEL))
            {

                table.getBody().clearBgRow();
                table.getBody().getAllRows().get(index)
                        .setBackgroundColor(Color.parseColor("#edf0fe"));
                table.getBody().setCurrentIndex(index);

                TextView txtQ = (TextView) getColumnByRow(index, colQ);
                int q = Integer.parseInt(txtQ.getText().toString()) + Integer.parseInt(item.getQty().trim());
                txtQ.setText(String.valueOf(q));

                getCurrentRow().getCurrentItem().setQty(String.valueOf(q));
                return false;
            }
            else
            {
                table.getBody().addRow(item);
            }
        }
        else
        {
            table.getBody().addRow(item);
        }
        return true;
    }

    public boolean sub()
    {
        boolean delete = false;
        TextView txtStatus = (TextView) getColumnCurrentRow(colP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtItemCode = (TextView) getColumnCurrentRow(colType);
            if (txtItemCode != null && (txtItemCode.getText().equals("N")
                    || txtItemCode.getText().equals("R")
                    || txtItemCode.getText().equals("C")
                    || txtItemCode.getText().equals("M")))
            {
                TextView txtQty = (TextView) getColumnCurrentRow(colQ);
                if (txtQty != null)
                {
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    if (qty - 1 <= 0)
                    {
                        ItemRow row = getCurrentRow();
                        if (row != null)
                        {
                            table.getBody().removeView(row);
                            delete = true;
                        }
                    }
                    else
                    {
                        String sQty = (qty - 1) + "";
                        txtQty.setText(sQty);
                        getCurrentRow().getCurrentItem().setQty(sQty);
                    }
                }
            }
        }
        return delete;
    }

    public void plus()
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtItemCode = (TextView) getColumnCurrentRow(colType);
            if (txtItemCode != null && (txtItemCode.getText().equals("N")
                    || txtItemCode.getText().equals("R") || txtItemCode.getText().equals("C") || txtItemCode.getText().equals("M")))
            {
                TextView txtQty = (TextView) getColumnCurrentRow(TableOrder.colQ);
                if (txtQty != null)
                {
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    String sQty = (qty + 1) + "";
                    txtQty.setText(sQty);

                    getCurrentRow().getCurrentItem().setQty(sQty);
                }
            }
        }
    }

    public void removeRow()
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colP);
        TextView txtItemCode = (TextView) getColumnCurrentRow(colType);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            int currentIndex = getCurrentIndex();
            ItemRow currentRow = getRowIndex(currentIndex);
            // remove current row
            if (currentRow != null)
            {
                table.getBody().removeView(currentRow);
            }
            if (txtItemCode != null && txtItemCode.getText().equals("C"))
            {
                do
                {
                    ItemRow row = getRowIndex(currentIndex);
                    if (row != null)
                    {
                        Item item = row.getCurrentItem();
                        if (item.getItemType().toString().equals("M"))
                        {
                            table.getBody().removeView(row);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
                } while (true);
            }
        }
    }

    public String getRemark(Remark selectedRemark)
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colP);
        String instruction = null;
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtInstruction = (TextView) getColumnCurrentRow(colInstruction);
            if (txtInstruction != null)
            {
                if (!selectedRemark.getName().isEmpty())
                {
                    instruction = txtInstruction.getText().toString();
                    if (instruction.length() != 0)
                    {
                        instruction = instruction + ";"
                                + selectedRemark.getName();
                    }
                    else
                    {
                        instruction = selectedRemark.getName();
                    }
                }
                else
                {
                    instruction = "";
                }
            }
        }
        return instruction;
    }

    public void insertRemark(String instruction)
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtInstruction = (TextView) getColumnCurrentRow(colInstruction);
            txtInstruction.setText(instruction);
            getCurrentRow().getCurrentItem().setInstruction(instruction);
        }
    }

    public Object getColumnCurrentRow(String name)
    {
        return table.getBody().getColumnCurrentRow(name);
    }

    public Object getColumnByRow(int index, String name)
    {
        return table.getBody().getColumnByRow(index, name);
    }

    public String getAllTotal()
    {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();

        int total = 0;
        for (int i = listRow.size() - 1; i >= 0; i--)
        {
            ItemRow itemRow = listRow.get(i);
            Item itemData = itemRow.getCurrentItem();

            TextView txtQ = (TextView) getColumnByRow(i, colQ);
            int q = Integer.parseInt(txtQ.getText().toString());
            String promotion = itemData.getOnPromotion();
            TextView txtPrice = (TextView) getColumnByRow(i, colPrice);
            TextView txtDiscount = (TextView) getColumnByRow(i, colDisc);
            TextView txtTotal = (TextView) getColumnByRow(i, colTotal);
            TextView txtTax = (TextView) getColumnByRow(i, colTax);
            TextView txtTaxAmt = (TextView) getColumnByRow(i, colTaxAmt);
            TextView txtStatus = (TextView) getColumnByRow(i, colP);

            String stat = txtStatus.getText().toString();

            int totAmt = Integer.parseInt(txtTotal.getText().toString());
            int taxAmt = Integer.parseInt(txtTax.getText().toString());

            if (stat.equals(""))
            {
                totAmt = Integer.parseInt(txtPrice.getText().toString()) * q - Integer.parseInt(txtDiscount.getText().toString());
                taxAmt = totAmt * Integer.parseInt(txtTax.getText().toString()) / 100;
                txtTotal.setText(String.valueOf(totAmt));
                txtTaxAmt.setText(String.valueOf(taxAmt));
            }
            getRowIndex(i).getCurrentItem().setTotal(String.valueOf(totAmt));
            getRowIndex(i).getCurrentItem().setTaxAmt(String.valueOf(taxAmt));
            //int t = Integer.parseInt(txtTotal.getText().toString());
            total += totAmt;
        }
        return Utils.formatPrice(total);
    }


    public String checkStatus(String tableStatus)
    {
        String result = null;
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();
        if (listRow.size() == 0)
        {
            result = STATUS_DATATABLE_NO_DATA;
        }
        else
        {
            if (tableStatus.equals(Table.ACTION_EDIT))
            {
                boolean isNew = false;
                for (int i = listRow.size() - 1; i >= 0; i--)
                {
                    TextView txtStatus = (TextView) getColumnByRow(i, colP);
                    if (txtStatus != null
                            && !txtStatus.getText().equals(Item.STATUS_OLD)
                            && !txtStatus.getText().equals(Item.STATUS_CANCEL))
                    {
                        isNew = true;
                        break;
                    }
                }

                if (isNew)
                {
                    result = STATUS_DATATABLE_SEND_ALL;
                }
                else
                { // resend
                    result = STATUS_DATATABLE_RESEND;
                }
            }
        }

        return result;
    }

    @Override
    public String toString()
    {
        return table.toString();
    }
}
