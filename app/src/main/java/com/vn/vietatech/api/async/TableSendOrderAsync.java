package com.vn.vietatech.api.async;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.combo.POSMenuActivity;
import com.vn.vietatech.combo.R;
import com.vn.vietatech.combo.dialog.TransparentProgressDialog;
import com.vn.vietatech.utils.Utils;

public class TableSendOrderAsync extends AsyncTask<String, String, String> {
    private Context mContext;
    private TransparentProgressDialog pd;

    public TableSendOrderAsync(Context context) {
        this.mContext = context;

        pd = new TransparentProgressDialog(mContext, R.drawable.spinner);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String sendNewOrder = params[0];
        String reSendOrder = params[1];

        String resultSend = "";
        try {
            POSMenuActivity act = (POSMenuActivity) mContext;
            resultSend = act.sendOrder(sendNewOrder, reSendOrder);
        } catch (Exception e) {
            return e.getMessage();
        }
        return resultSend;
    }

    @Override
    protected void onPostExecute(String result) {
        pd.dismiss();
        POSMenuActivity act = (POSMenuActivity) mContext;
        if (result.equals("true")) {
            act.backForm();
        } else if (result.equals("false")) {
            Utils.showAlert(mContext, "Bị lỗi khi gửi đơn hàng");
            act.izSendingOrder = false;
        } else {
            Utils.showAlert(mContext, result);
            act.izSendingOrder = false;
        }
        super.onPostExecute(result);
    }
}
