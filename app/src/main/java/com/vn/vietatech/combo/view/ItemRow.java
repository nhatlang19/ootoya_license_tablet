package com.vn.vietatech.combo.view;

import com.vn.vietatech.combo.view.table.DataTable;
import com.vn.vietatech.combo.view.table.TableHeader;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Table;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TableRow;
import android.widget.TextView;

public class ItemRow extends TableRow {

	private Context mContext;
	private Item currentItem;

	public ItemRow(Context context) {
		super(context);
		mContext = context;
	}
	
	public Item getCurrentItem() {
		return this.currentItem;
	}

	public void addAllColumns(Item item, TableHeader tblHeader, int segNo) {
		currentItem = item;
		
		// quality
		TextView txtQuality = createColumn(currentItem.getQty(), tblHeader, TableOrder.colnQty);
		this.addView(txtQuality);
		// status
		TextView txtStatus = createColumn(currentItem.getPrintStatus(), tblHeader, TableOrder.colnP);
		this.addView(txtStatus);
		// name
		TextView txtName = createColumn(currentItem.getItemName(), tblHeader, TableOrder.colnItemName);
		this.addView(txtName);
		// P.price
		TextView txtPPrice = createColumn(currentItem.getPromoPrice(), tblHeader, TableOrder.colnGiaKM);
		this.addView(txtPPrice);
		// O.price
		TextView txtOPrice = createColumn(currentItem.getOrgPrice(), tblHeader, TableOrder.colnGiaGoc);
		this.addView(txtOPrice);
		// disc
		TextView txtDisc = createColumn(currentItem.getDistAmt(), tblHeader,TableOrder.colnDisc);
		this.addView(txtDisc);
		// subtotal
		TextView txtSubTotal = createColumn(currentItem.getSubTotal(), tblHeader,TableOrder.colnSubTotal);
		this.addView(txtSubTotal);
		// Instruction
		TextView txtInstruction = createColumn(currentItem.getInstruction(), tblHeader, TableOrder.colnInstruction);
		this.addView(txtInstruction);
		// pCode
		TextView txtCode = createColumn(currentItem.getPromoCode(), tblHeader, TableOrder.colnPCode);
		this.addView(txtCode);
		// Class
		TextView txtClass = createColumn(currentItem.getPromoClass(), tblHeader, TableOrder.colnPClass);
		this.addView(txtClass);
		// PkgQty
		TextView txtPQty = createColumn(currentItem.getPkgQty(), tblHeader, TableOrder.colnPQty);
		this.addView(txtPQty);
		// colnKhuyenMai
		TextView txtKM = createColumn("", tblHeader, TableOrder.colnKhuyenMai);
		this.addView(txtKM);
		// colnDiscId
		TextView txtDiscId = createColumn("", tblHeader, TableOrder.colnDiscId);
		this.addView(txtDiscId);
		// total
		TextView txtTotal = createColumn(currentItem.getTotal(), tblHeader,TableOrder.colnTotal);
		this.addView(txtTotal);
		// tax
		TextView txtTax = createColumn(currentItem.getTax(), tblHeader, TableOrder.colnTax);
		this.addView(txtTax);
		// SPAmt
		TextView txtTaxAmt = createColumn(currentItem.getTaxAmt(), tblHeader, TableOrder.colnTaxAmt);
		this.addView(txtTaxAmt);
		// SPTax
		TextView txtSPTax = createColumn(currentItem.getSptax(), tblHeader, TableOrder.colnSPTax);
		this.addView(txtSPTax);
		// SPAmt
		TextView txtSPAmt = createColumn(currentItem.getTaxAmt(), tblHeader, TableOrder.colnSPAmt);
		this.addView(txtSPAmt);
		// ServAmt
		TextView txtServAmt = createColumn(currentItem.getServeTaxAmt(), tblHeader, TableOrder.colnServAmt);
		this.addView(txtServAmt);
		// SubCatg
		TextView txtSubCatg = createColumn(currentItem.getSubcatg(), tblHeader, TableOrder.colnSubCatg);
		this.addView(txtSubCatg);
		// Brand
		TextView txtBrand = createColumn(currentItem.getBrand(), tblHeader, TableOrder.colnBrand);
		this.addView(txtBrand);
		// colnMemberId
		TextView colnMemberId = createColumn(currentItem.getMemberId(), tblHeader, TableOrder.colnMemberId);
		this.addView(colnMemberId);
		// colnMemberName
		TextView colnMemberName = createColumn(currentItem.getMemberName(), tblHeader, TableOrder.colnMemberName);
		this.addView(colnMemberName);
		// colnJocketWallet
		TextView colnJocketWallet = createColumn(currentItem.getJocketWallet(), tblHeader, TableOrder.colnJocketWallet);
		this.addView(colnJocketWallet);
		// colnCashVoucher
		TextView colnCashVoucher = createColumn(currentItem.getCashVoucher(), tblHeader, TableOrder.colnCashVoucher);
		this.addView(colnCashVoucher);
		// colnDiscountVoucher
		TextView colnDiscountVoucher = createColumn(currentItem.getDiscountVoucher(), tblHeader, TableOrder.colnDiscountVoucher);
		this.addView(colnDiscountVoucher);
		// colnDiscountMember
		TextView colnDiscountMember = createColumn(currentItem.getDiscountMember(), tblHeader, TableOrder.colnDiscountMember);
		this.addView(colnDiscountMember);
		// colnType
		TextView colnType = createColumn(currentItem.getItemType(), tblHeader, TableOrder.colnType);
		this.addView(colnType);
		// colnSegNo
		int segNoItem = Integer.parseInt(currentItem.getSegNo());
		TextView txtSegNo = null;
		if(segNoItem == 0) {
			txtSegNo = createColumn(String.valueOf(segNo), tblHeader, TableOrder.colnSegNo);
			currentItem.setSegNo(String.valueOf(segNo));
		} else {
			txtSegNo = createColumn(currentItem.getSegNo(), tblHeader, TableOrder.colnSegNo);
		}
		this.addView(txtSegNo);
	}

	private TextView createColumn(String item, TableHeader tblHeader, String columnName) {
		DataTable data = tblHeader.getColHeader(columnName);
		
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(new LayoutParams(data.getColWidth(), LayoutParams.WRAP_CONTENT));
		textView.setPadding(20, 5, 20, 5);
		textView.setGravity(data.getColGravity());
		textView.setText(item.trim());
		textView.setTextSize(14);
		textView.setTextColor(Color.BLACK);

		return textView;
	}
	
	@Override
	public String toString() {
		return currentItem.toString();
	}
}
