package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.model.MemGrade;
import com.vn.vietatech.model.Promo;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PromoAPI extends AbstractAPI {
    public PromoAPI(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<Promo> data = globalVariable.getPromos();
        if (data == null) {
            try {
                data = getPromotionList();
                globalVariable.setPromos(data);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public ArrayList<Promo> getPromotionList() throws Exception {
        setMethod(METHOD_GET_PROMOTION_LIST);

        ArrayList<Promo> items = new ArrayList<Promo>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Promo row = new Promo();
                row.setCode(tableObject.getProperty("PromoCode").toString());
                row.setDescription(tableObject.getProperty("PackageDesc").toString());

                items.add(row);
            }
        }
        return items;
    }

}
