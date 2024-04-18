package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Order;
import com.vn.vietatech.model.dto.MemberRemarkDTO;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderAPI extends AbstractAPI {

    public OrderAPI(Context context) throws Exception {
        super(context);
    }

    public int getNewOrderNumberByPOS(String POSId)
            throws NumberFormatException, Exception {
        setMethod(METHOD_GET_NEW_ORDER_BY_POS);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("POSId", POSId);

        return Integer.parseInt(callService(params).toString());
    }

    public ArrayList<Item> getEditOrderNumberByPOS(String orderNo, String posNo,
                                                   String extNo) throws Exception {
        setMethod(METHOD_GET_EDIT_ORDER_BY_POS);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        params.put("posNo", posNo);
        params.put("extNo", extNo);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        System.out.println(soapObject.toString());

        ArrayList<Item> items = new ArrayList<Item>();
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Item item = new Item();
                item.setQty(tableObject.getProperty("Qty").toString());
                item.setSplited(tableObject.getProperty("Splited").toString());
                item.setPrintStatus(tableObject.getProperty("Status").toString());
                item.setOrgPrice(tableObject.getProperty("OrgPrice").toString());
                item.setPromoPrice(tableObject.getProperty("PromoPrice").toString());
                item.setDistAmt(tableObject.getProperty("DiscountAmt").toString());
                item.setSubTotal(tableObject.getProperty("SubTotal").toString());
                if (tableObject.hasProperty("Instruction")) {
                    item.setInstruction(tableObject.getProperty("Instruction").toString());
                }
                item.setPromoCode(tableObject.getProperty("PromoCode").toString());
                item.setPromoClass(tableObject.getProperty("PromoClass").toString());
                if (tableObject.hasProperty("ProInstruct")) {
                    item.setPromoDesc(tableObject.getProperty("ProInstruct").toString());
                }
//                item.setPkgPrice(tableObject.getProperty("PkgPrice").toString());
                item.setPkgQty(tableObject.getProperty("PkgQty").toString());
                item.setTotal(tableObject.getProperty("TotAmt").toString());
                item.setTax(tableObject.getProperty("Tax").toString());
                item.setTaxAmt(tableObject.getProperty("TaxAmt").toString());
                item.setSptax(tableObject.getProperty("SPTax").toString());
                item.setSpTaxAmt(tableObject.getProperty("SPTaxAmt").toString());
                item.setServeTax(tableObject.getProperty("ServTax").toString());
                item.setServeTaxAmt(tableObject.getProperty("ServTaxAmt").toString());
                item.setSubcatg(tableObject.getProperty("SubCatg").toString());

                item.setPrintStatus(tableObject.getProperty("Status").toString());
                item.setItemName(tableObject.getProperty("RecptDesc").toString());
                item.setItemType(tableObject.getProperty("ItemType").toString());
                item.setItemCode(tableObject.getProperty("ItemCode").toString());
                item.setModifier(tableObject.getProperty("Modifier").toString());
                item.setMasterCode(tableObject.getProperty("MasterCode").toString());
                item.setSegNo(tableObject.getProperty("SeqNo").toString());
                item.setPkgItems(tableObject.getProperty("PkgItems").toString());
                item.setBrand(tableObject.getProperty("Brand").toString());
                item.setMemberId(tableObject.getProperty("MemberId").toString());
                item.setMemberName(tableObject.getProperty("MemberName").toString());

                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<Order> getOrderEditType(String POSBizDate,
                                             String currentTable) throws Exception {
        setMethod(METHOD_GET_ORDER_EDIT_TYPE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("POSBizDate", POSBizDate);
        params.put("currentTable", currentTable);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        ArrayList<Order> orders = new ArrayList<Order>();
        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Order order = new Order();
                order.setOrdExt(tableObject.getProperty("OrdExt").toString());
                order.setPosPerScode(tableObject.getProperty("PosPerScode").toString());

                orders.add(order);
            }
        }
        return orders;
    }

    public String updateMemberRemark(MemberRemarkDTO dto)
            throws NumberFormatException, Exception {
        setMethod(METHOD_UPDATE_MEMBER_REMARK);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("posBizDate", dto.posBizDate);
        params.put("posNo", dto.posNo);
        params.put("orderNo", dto.orderNo);
        params.put("extNo", dto.extNo);
        params.put("splited", dto.splited);
        params.put("memberId", dto.memberId);
        params.put("memberName", dto.memberName);
        params.put("seqNo", dto.segNo);

        return callService(params).toString();
    }
}
