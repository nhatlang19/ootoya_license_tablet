package com.vn.vietatech.api;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Set;

public class AbstractAPI extends AsyncTask<String, String, String> {
    protected Context mContext;
    protected SoapObject request;

    protected static String METHOD_GET_SECTION = "GetSection";
    protected static String METHOD_GET_MEMBER = "GetMember";
    protected static String METHOD_SEARCH_MEMBER = "SearchMember";

    protected static String METHOD_SET_MEMBER = "SetMember";
    protected static String METHOD_GET_TABLELIST = "GetTableListAllSection";
    protected static String METHOD_GET_TABLE_BY_SECTION = "GetTableListBySection";
    protected static String METHOD_GET_USER = "GetUser";
    protected static String METHOD_GET_USER_LIST = "GetUserList";
    protected static String METHOD_UPDATE_TABLE_STATUS = "UpdateTableStatus";
    protected static String METHOD_GET_POS_MENU = "GetPOSMenu";
    protected static String METHOD_GET_SUB_MENU = "GetSubMenu";
    protected static String METHOD_IS_SQL_CONNECTED = "IsSQLConnected";
    protected static String METHOD_IS_VALID_BIZDATE = "IsValidBizDate";
    protected static String METHOD_IS_KIT_EXITS = "IsKitFolderExist";
    protected static String METHOD_CREATE_KIT_FOLDER = "CreateKITFolder";
    protected static String METHOD_GET_ITEM = "GetItemBySubMenuSelected_CheckPromo";
    protected static String METHOD_GET_ITEM_COMBO = "GetItemComboBySubMenuSelected";
    protected static String METHOD_GET_ITEM_MODIFIER = "GetModifierByModifierItem";
    protected static String METHOD_GET_ORDER_EDIT_TYPE = "GetOrderEditType";
    protected static String METHOD_GET_NEW_ORDER_BY_POS = "GetNewOrderNumberByPOS";
    protected static String METHOD_GET_EDIT_ORDER_BY_POS = "GetEditOrderNumberByPOS";
    protected static String METHOD_GET_EDIT_ORDER_BY_POS_HEADER = "GetEditOrderNumberByPOS_Header";
    protected static String METHOD_UPDATE_MEMBER_REMARK = "UpdateMemberRemark";
    protected static String METHOD_GET_POS_BIZ_DATE = "GetPOSBizDate";
    protected static String METHOD_GET_REMARK_BY_ITEM = "GetRemarkByItem";
    protected static String METHOD_GET_STATUS_MOVE_TABLE = "GetStatusOfMoveTable";
    protected static String METHOD_MOVE_TABLE = "MoveTable";
    protected static String METHOD_GROUP_TABLE = "GroupTable";
    protected static String METHOD_SEND_ORDER = "SendOrder";
    protected static String METHOD_GET_TIME_SERVER = "GetTimeServer";
    protected static String METHOD_GET_SALES_CODE = "GetSalesCode";
    protected static String METHOD_GET_NATIONALITY = "GetNationality";
    protected static String METHOD_GET_MEMCLASS = "GetMemClass";
    protected static String METHOD_GET_MEMGRADE = "GetMemGrade";
    protected static String METHOD_GET_PROMOTION_LIST = "GetPromotionList";
    protected static String METHOD_CHECK_MEMBER_TRUNG_TEN = "CheckMemberTrungTen";

    protected static String NAMESPACE;
    protected static String SERVER_IP;
    protected static String URL;

    private String method = "";

    protected String getMethod() {
        return method;
    }

    protected void setMethod(String method) {
        this.method = method;
    }


    public AbstractAPI(Context context) throws Exception {
        mContext = context;

        SERVER_IP = SettingUtil.read(mContext).getServerIP();
        NAMESPACE = "http://tempuri.org/";
        URL = "http://" + SERVER_IP + "/V6BOService/V6BOService.asmx";

        if (!Utils.isNetworkAvailable(context)) {
            throw new Exception("No Internet Connection");
        }
    }

    protected String getSoapAction() {
        return NAMESPACE + getMethod();
    }

    protected Object callService() throws Exception {
        request = new SoapObject(NAMESPACE, getMethod());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(getSoapAction(), envelope);
            if (envelope.bodyIn instanceof SoapFault) {
                String str = ((SoapFault) envelope.bodyIn).faultstring;
                throw new Exception(str);
            } else {
                return envelope.getResponse();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected Object callService(HashMap<String, String> params) throws Exception {

        request = new SoapObject(NAMESPACE, getMethod());

        // Get keys.
        Set<String> keys = params.keySet();
        // Loop over String keys.
        for (String key : keys) {
            request.addProperty(key, params.get(key).toString());
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(getSoapAction(), envelope);
            if (envelope.bodyIn instanceof SoapFault) {
                String str = ((SoapFault) envelope.bodyIn).faultstring;
                throw new Exception(str);
            } else {
                return envelope.getResponse();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    /**
     * check connect SQL
     *
     * @return boolean
     * @throws Exception
     */
    public String isSQLConnected() throws Exception {
        setMethod(METHOD_IS_SQL_CONNECTED);
        return callService().toString();
    }

    /**
     * check exists KIT folder
     *
     * @return boolean
     * @throws Exception
     */
    public boolean isKitFolderExist() throws Exception {
        setMethod(METHOD_IS_KIT_EXITS);
        return Boolean.parseBoolean(callService().toString());
    }

    /**
     * create KIT folder
     *
     * @return boolean
     * @throws Exception
     */
    public boolean createKitFolder() throws Exception {
        setMethod(METHOD_CREATE_KIT_FOLDER);
        return Boolean.parseBoolean(callService().toString());
    }

    /**
     * get time from server
     *
     * @return String
     * @throws Exception
     */
    public String getTimeServer() throws Exception {
        setMethod(METHOD_GET_TIME_SERVER);
        return callService().toString();
    }

    public String isValidBizDate() throws Exception {
        setMethod(METHOD_IS_VALID_BIZDATE);
        return callService().toString();
    }

    public String getPOSBizDate(String posNo) throws Exception {
        setMethod(METHOD_GET_POS_BIZ_DATE);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("posNo", posNo);

        return this.callService(params).toString();
    }
}
