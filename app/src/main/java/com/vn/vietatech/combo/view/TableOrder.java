package com.vn.vietatech.combo.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.combo.view.table.DataTable;
import com.vn.vietatech.combo.view.table.MyTable;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Member;
import com.vn.vietatech.model.Remark;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.model.dto.MemberRemarkDTO;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class TableOrder extends TableLayout
{
    public static final String colnP = "P";
    public static final String colnQty = "Qty";
    public static final String colnType = "Type";
    public static final String colnSegNo = "SegNo";

    public static final String colnItemName = "Item#";
    public static final String colnGiaKM = "Promo Price";
    public static final String colnGiaGoc = "Org Price";
    public static final String colnDisc = "Disc";
    public static final String colnSubTotal = "SubTotal";
    public static final String colnInstruction = "Instruction";
    public static final String colnTT = "TT";
    public static final String colnPCode = "p.Code";
    public static final String colnPClass = "p.Class";
    public static final String colnPQty = "p.Qty";
    public static final String colnKhuyenMai = "Khuyến mãi";
    public static final String colnDiscId = "DiscId";
    public static final String colnTotal = "Total";
    public static final String colnTax = "Tax";
    public static final String colnTaxAmt = "TaxAmt";
    public static final String colnSPTax = "SPTax";
    public static final String colnSPAmt = "SPAmt";
    public static final String colnServAmt = "ServAmt";
    public static final String colnSubCatg = "SubCatg";
    public static final String colnBrand = "Brand";
    public static final String colnMemberId = "MemberID";
    public static final String colnMemberName = "MemberName";
    public static final String colnJocketWallet = "JockerWallet";
    public static final String colnCashVoucher = "CashVoucher";
    public static final String colnDiscountVoucher = "DiscountVoucher";
    public static final String colnDiscountMember = "DiscountMember";

    public static String STATUS_DATATABLE_NO_DATA = "No Data";
    public static String STATUS_DATATABLE_SEND_ALL = "Send All";
    public static String STATUS_DATATABLE_RESEND = "Bạn muốn Gửi Lại order ?";
    private String[] headers = new String[]{
            colnQty,
            colnP,
            colnItemName,
            colnGiaKM,
            colnGiaGoc,
            colnDisc,
            colnSubTotal,
            colnInstruction,
            colnPCode,
            colnPClass,
            colnPQty,
            colnKhuyenMai,
            colnDiscId,
            colnTotal,
            colnTax,
            colnTaxAmt,
            colnSPTax,
            colnSPAmt,
            colnServAmt,
            colnSubCatg,
            colnBrand,
            colnMemberId,
            colnMemberName,
            colnJocketWallet,
            colnCashVoucher,
            colnDiscountVoucher,
            colnDiscountMember,
            colnType,
            colnSegNo
    };
    private Integer[] headerWidth = new Integer[]{
            100,
            80,
            420,
            200,
            250,
            200,
            200,
            300, // colnInstruction
            200,
            100,
            100,
            400, // khuyen mai
            150,
            200,
            200,
            200,
            200,
            200,
            200,
            200,
            200,
            200, // memberId
            400, // memberName
            200,
            200,
            220,
            220,
            100,
            100
    };
    private Integer[] headerGravity = new Integer[]{
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.LEFT,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
            Gravity.CENTER,
    };
    private MyTable table;

    private Context mContext = null;

    public TableOrder(Context context, LinearLayout parent)
    {
        super(context);

        this.mContext = context;

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

    public boolean createNewRow(Item item, boolean skipCal)
    {
        if (!skipCal) {
            try {
                double serviceTax = Double.parseDouble(SettingUtil.read(mContext).getServiceTax());
                item.autoCalculate(serviceTax);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();
        int index = -1;
        if (!item.getPromoDesc().isEmpty() && !item.getPromoDesc().equals("0")) {
            index = -1;
        } else if (!item.getItemType().equals("C") && !item.getItemType().equals("M"))
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
            TextView txtStatus = (TextView) getColumnByRow(index, colnP);
            if (txtStatus != null
                    && !txtStatus.getText().equals(Item.STATUS_OLD)
                    && !txtStatus.getText().equals(Item.STATUS_CANCEL))
            {

                table.getBody().clearBgRow();
                table.getBody().getAllRows().get(index)
                        .setBackgroundColor(Color.parseColor("#edf0fe"));
                table.getBody().setCurrentIndex(index);

                TextView txtQ = (TextView) getColumnByRow(index, colnQty);
                int q = Integer.parseInt(txtQ.getText().toString()) + Integer.parseInt(item.getQty().trim());
                txtQ.setText(String.valueOf(q));

                getCurrentRow().getCurrentItem().setQty(String.valueOf(q));
                try {
                    double serviceTax = Double.parseDouble(SettingUtil.read(mContext).getServiceTax());
                    reUpdateRow(q, serviceTax);
                } catch (IOException e) {

                }

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

    public boolean sub(double serviceTax)
    {
        boolean delete = false;
        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtItemCode = (TextView) getColumnCurrentRow(colnType);
            if (txtItemCode != null && (txtItemCode.getText().equals("N")
                    || txtItemCode.getText().equals("R")
                    || txtItemCode.getText().equals("C")
                    || txtItemCode.getText().equals("M")))
            {
                TextView txtQty = (TextView) getColumnCurrentRow(colnQty);
                TextView txtDiscount = (TextView) getColumnCurrentRow(colnDisc);
                TextView txtPCode = (TextView) getColumnCurrentRow(colnPCode);

                boolean isKM = txtDiscount != null && Integer.parseInt(txtDiscount.getText().toString()) > 0
                        && txtPCode != null && !txtPCode.getText().toString().isEmpty();
                if (isKM) {
                    return false;
                }
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

                        reUpdateRow(qty-1, serviceTax);
                    }
                }
            }
        }
        return delete;
    }

    public void plus(double serviceTax)
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtItemCode = (TextView) getColumnCurrentRow(colnType);
            if (txtItemCode != null && (txtItemCode.getText().equals("N")
                    || txtItemCode.getText().equals("R") || txtItemCode.getText().equals("C") || txtItemCode.getText().equals("M")))
            {
                TextView txtQty = (TextView) getColumnCurrentRow(colnQty);
                TextView txtDiscount = (TextView) getColumnCurrentRow(colnDisc);
                TextView txtPCode = (TextView) getColumnCurrentRow(colnPCode);

                boolean isKM = txtDiscount != null && Integer.parseInt(txtDiscount.getText().toString()) > 0
                        && txtPCode != null && !txtPCode.getText().toString().isEmpty();
                if (isKM) {
                    return;
                }

                if (txtQty != null)
                {
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    String sQty = (qty + 1) + "";
                    txtQty.setText(sQty);

                    getCurrentRow().getCurrentItem().setQty(sQty);

                    reUpdateRow(qty+1, serviceTax);
                }
            }
        }
    }

    public void reUpdateRow(int qty, double serviceTax)
    {
        TextView txtSubTotal = (TextView) getColumnCurrentRow(colnSubTotal);
        TextView txtTaxAmt = (TextView) getColumnCurrentRow(colnTaxAmt);
        TextView txtTotal = (TextView) getColumnCurrentRow(colnTotal);

        Item item = getCurrentRow().getCurrentItem();
        double subTotal = qty * Double.valueOf(item.getOrgPrice());
        double servTaxAmt = subTotal * serviceTax * 0.01;
        double spTaxAmt = (subTotal + servTaxAmt) * Double.valueOf(item.getSptax()) * 0.01;
        double totAmt = subTotal + servTaxAmt + spTaxAmt;
        int taxRate = Integer.valueOf(item.getTax());
        double taxAmt = Utils.taxAmtCalculate(false, taxRate, totAmt);

        getCurrentRow().getCurrentItem().setSubTotal(String.valueOf((int)subTotal));
        getCurrentRow().getCurrentItem().setServeTaxAmt(String.valueOf((int)servTaxAmt));
        getCurrentRow().getCurrentItem().setSpTaxAmt(String.valueOf((int)spTaxAmt));
        getCurrentRow().getCurrentItem().setTaxAmt(String.valueOf((int)taxAmt));
        getCurrentRow().getCurrentItem().setTotal(String.valueOf((int)totAmt));

        txtSubTotal.setText(String.valueOf((int)subTotal));
        txtTaxAmt.setText(String.valueOf((int)taxAmt));
        txtTotal.setText(String.valueOf((int)totAmt));
    }

    public void removeRow()
    {
        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
        TextView txtItemCode = (TextView) getColumnCurrentRow(colnType);
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
        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
        String instruction = null;
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtInstruction = (TextView) getColumnCurrentRow(colnInstruction);
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
        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
        {
            TextView txtInstruction = (TextView) getColumnCurrentRow(colnInstruction);
            txtInstruction.setText(instruction);
            getCurrentRow().getCurrentItem().setInstruction(instruction);
        }
    }

    public void insertMember(Member member, MemberRemarkDTO dto)
    {
//        TextView txtStatus = (TextView) getColumnCurrentRow(colnP);
//        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
//                && !txtStatus.getText().equals(Item.STATUS_CANCEL))
//        {
        TextView txtMemberId = (TextView) getColumnCurrentRow(colnMemberId);
        txtMemberId.setText(member.memberId);
        TextView txtMemberName = (TextView) getColumnCurrentRow(colnMemberName);
        txtMemberName.setText(member.memberName);
        getCurrentRow().getCurrentItem().setMemberId(member.memberId);
        getCurrentRow().getCurrentItem().setMemberName(member.memberName);

        if (getCurrentRow().getCurrentItem().getPrintStatus().equals("#")) {
            try {
                dto.segNo = getCurrentRow().getCurrentItem().getSegNo();
                String ketqua = new OrderAPI(this.mContext).updateMemberRemark(dto);
            } catch (Exception ex) {

            }
        }
//        }
    }

    public Object getColumnCurrentRow(String name)
    {
        return table.getBody().getColumnCurrentRow(name);
    }

    public Object getColumnByRow(int index, String name)
    {
        return table.getBody().getColumnByRow(index, name);
    }

    public String getALlTotalStr()
    {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();

        int total = 0;
        int subTotal = 0;
        int ser = 0;
        int sptax = 0;
        int vat = 0;
        int disc = 0;;
        for (int i = listRow.size() - 1; i >= 0; i--)
        {
            Item item = listRow.get(i).getCurrentItem();
            int subtotalItem = Integer.parseInt(item.getSubTotal());
            int servAmtItem = Integer.parseInt(item.getServeTaxAmt());
            int SPTaxItem = Integer.parseInt(item.getSptax());
            int taxAmtItem = Integer.parseInt(item.getTaxAmt());
            int discItem = Integer.parseInt(item.getDistAmt());

            discItem = 0;

            int SPTAX = (int) ((subtotalItem + servAmtItem) * SPTaxItem * 0.01);

            subTotal += subtotalItem;
            ser += servAmtItem;
            sptax += SPTAX;
            vat += taxAmtItem;
            disc += discItem;
            total += subtotalItem + servAmtItem + SPTAX + taxAmtItem + discItem;
        }

        return String
                .format("SubTotal:\t\t %s VND\n"
                                + "Ser:\t\t %s VND \t\t\t\t SPTax:\t\t %s VND\n"
                                + "VAT:\t\t %s VND \t\t\t\t Disc:\t\t %s VND\n"
                                + "\t\t\t----------------------------\n"
                                + "Tổng:\t\t %s VND",
                        Utils.formatPrice(subTotal),
                        Utils.formatPrice(ser),
                        Utils.formatPrice(sptax),
                        Utils.formatPrice(vat),
                        Utils.formatPrice(disc),  Utils.formatPrice(total));
    }

    public String getAllTotal()
    {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();

        int total = 0;
        for (int i = listRow.size() - 1; i >= 0; i--)
        {
            Item item = listRow.get(i).getCurrentItem();
            int subtotalItem = Integer.parseInt(item.getSubTotal());
            int servAmtItem = Integer.parseInt(item.getServeTaxAmt());
            int SPTaxItem = (int) ((subtotalItem + servAmtItem) * Integer.parseInt(item.getSptax()) * 0.01);
            int taxAmtItem = Integer.parseInt(item.getTaxAmt());
            int discItem = Integer.parseInt(item.getDistAmt());
            discItem = 0;

            total += subtotalItem + servAmtItem + SPTaxItem + taxAmtItem + discItem;
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
                    TextView txtStatus = (TextView) getColumnByRow(i, colnP);
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
