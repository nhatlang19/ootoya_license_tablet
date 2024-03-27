package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserApi extends AbstractAPI {

    public UserApi(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<Cashier> cashiers = globalVariable.getCashiers();
        if (cashiers == null) {
            try {
                cashiers = getUserList();
                globalVariable.setCashiers(cashiers);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public Cashier login(String username, String password) throws Exception {
        setMethod(METHOD_GET_USER);
        Cashier cashier = new Cashier();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            SoapObject tableObject = (SoapObject) webServiceResponse
                    .getProperty("Table");

            cashier.setId(tableObject.getProperty("CashierID").toString());
            cashier.setName(tableObject.getProperty("CashierName").toString());
            cashier.setPass(tableObject.getProperty("CashierPwd").toString());
            cashier.setUserGroup(tableObject.getProperty("UserGroup").toString());
        }
        return cashier;
    }

    public ArrayList<Cashier> getUserList() throws Exception {
        setMethod(METHOD_GET_USER_LIST);
        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject table = (SoapObject) webServiceResponse
                        .getProperty(i);

                Cashier cashier = new Cashier();
                cashier.setId(table.getProperty("CashierID").toString());
                cashier.setName(table.getProperty("CashierName").toString());

                cashiers.add(cashier);
            }
        }
        return cashiers;
    }
}
