package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Remark;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAPI extends AbstractAPI {

    public ItemAPI(Context context) throws Exception {
        super(context);
    }

    public ArrayList<Remark> getRemarkByItem(String itemCode) throws Exception {
        setMethod(METHOD_GET_REMARK_BY_ITEM);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("itemcode", itemCode);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        ArrayList<Remark> remarks = new ArrayList<Remark>();
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Remark remark = new Remark();
                remark.setName(tableObject.getProperty("Description").toString());

                remarks.add(remark);
            }
        }
        return remarks;
    }

    public ArrayList<Item> getItemBySubMenuSelected(String currSubItem, String priceLevel, String qty) throws Exception {
        setMethod(METHOD_GET_ITEM);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currSubItem", currSubItem);
        params.put("priceLevel", priceLevel);
        params.put("qty", qty);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        System.out.println(soapObject);

        ArrayList<Item> items = new ArrayList<Item>();

        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);
                Item item = new Item();

                item.setItemCode(tableObject.getProperty("ItemCode").toString());
                item.setItemName(tableObject.getProperty("RecptDesc").toString());
                item.setOrgPrice(tableObject.getProperty("UnitSellPrice").toString());
                item.setPromoPrice(tableObject.getProperty("PromoPrice").toString());
                item.setComboPack(tableObject.getProperty("ComboPack").toString());
                item.setWeightItem(tableObject.getProperty("WeightItem").toString());
                item.setDiscountable(tableObject.getProperty("Discountable").toString());
                item.setBrand(tableObject.getProperty("Brand").toString());
                item.setSubcatg(tableObject.getProperty("SubCatg").toString());
                item.setTax(tableObject.getProperty("Tax").toString());
                item.setSptax(tableObject.getProperty("SpTax").toString());
                item.setOnPromotion(tableObject.getProperty("OnPromotion").toString());
                if (item.getOnPromotion().equals("Y")) {
                    item.setPromoItem(tableObject.getProperty("PromoItem").toString());
                    item.setPromoCode(tableObject.getProperty("PromoCode").toString());
                    item.setPromoDesc(tableObject.getProperty("PromoDesc").toString());
                    item.setPromoClass(tableObject.getProperty("PromoClass").toString());
                    item.setStartDate(tableObject.getProperty("StartDate").toString());
                    item.setEndDate(tableObject.getProperty("EndDate").toString());
                    item.setMinQty(tableObject.getProperty("MinSellQty").toString());
                    item.setMaxQty(tableObject.getProperty("MaxSellQty").toString());
//                    item.setPkgPrice(tableObject.getProperty("PkgPrice").toString());
                    item.setPkgQty(tableObject.getProperty("PkgQty").toString());
                    item.setPkgQty1(tableObject.getProperty("PkgQty1").toString());
                    item.setPkgItems(tableObject.getProperty("PkgItems").toString());
//                    item.setBlanket(tableObject.getProperty("Blanket").toString());

                    item.setReCurr(tableObject.getProperty("ReCurr").toString());
                    item.setMinTrxAmt(tableObject.getProperty("MinTrxAmt").toString());
                    item.setMaxTrxAmt(tableObject.getProperty("MaxTrxAmt").toString());
                    item.setRatio(tableObject.getProperty("Ratio").toString());
                    item.setMon(tableObject.getProperty("MON").toString());
                    item.setTue(tableObject.getProperty("TUE").toString());
                    item.setWed(tableObject.getProperty("WED").toString());
                    item.setThu(tableObject.getProperty("THU").toString());
                    item.setFri(tableObject.getProperty("FRI").toString());
                    item.setSat(tableObject.getProperty("SAT").toString());
                    item.setSun(tableObject.getProperty("SUN").toString());
                }

                if (tableObject.hasProperty("Modifier")) {
                    item.setModifier(tableObject.getProperty("Modifier").toString());
                }

                items.add(item);
            }
        }
        return items;
    }
}
