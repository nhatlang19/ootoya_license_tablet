package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.model.MemClass;
import com.vn.vietatech.model.Nationality;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class MemClassAPI extends AbstractAPI {
    public MemClassAPI(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<MemClass> data = globalVariable.getMemClasses();
        if (data == null) {
            try {
                data = getMemClasses();
                globalVariable.setMemClasses(data);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public ArrayList<MemClass> getMemClasses() throws Exception {
        setMethod(METHOD_GET_MEMCLASS);

        ArrayList<MemClass> items = new ArrayList<MemClass>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                MemClass row = new MemClass();
                row.setCode(tableObject.getProperty("Code").toString());
                row.setDescription(tableObject.getProperty("Description").toString());

                items.add(row);
            }
        }
        return items;
    }

}
