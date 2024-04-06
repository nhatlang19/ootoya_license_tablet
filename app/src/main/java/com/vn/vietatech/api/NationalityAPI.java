package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.model.Nationality;
import com.vn.vietatech.model.SalesCode;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class NationalityAPI extends AbstractAPI {
    public NationalityAPI(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<Nationality> data = globalVariable.getNationalities();
        if (data == null) {
            try {
                data = getNationality();
                globalVariable.setNationalities(data);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public ArrayList<Nationality> getNationality() throws Exception {
        setMethod(METHOD_GET_NATIONALITY);

        ArrayList<Nationality> items = new ArrayList<Nationality>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Nationality row = new Nationality();
                row.setCode(tableObject.getProperty("Code").toString());
                row.setDescription(tableObject.getProperty("Description").toString());

                items.add(row);
            }
        }
        return items;
    }

}
