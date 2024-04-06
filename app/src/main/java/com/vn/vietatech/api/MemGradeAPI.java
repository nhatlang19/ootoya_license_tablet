package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.model.MemClass;
import com.vn.vietatech.model.MemGrade;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class MemGradeAPI extends AbstractAPI {
    public MemGradeAPI(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<MemGrade> data = globalVariable.getMemGrades();
        if (data == null) {
            try {
                data = getMemGrades();
                globalVariable.setMemGrades(data);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public ArrayList<MemGrade> getMemGrades() throws Exception {
        setMethod(METHOD_GET_MEMGRADE);

        ArrayList<MemGrade> items = new ArrayList<MemGrade>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                MemGrade row = new MemGrade();
                row.setCode(tableObject.getProperty("Code").toString());
                row.setDescription(tableObject.getProperty("Description").toString());

                items.add(row);
            }
        }
        return items;
    }

}
