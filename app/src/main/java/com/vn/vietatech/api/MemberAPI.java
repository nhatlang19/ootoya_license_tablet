package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Member;
import com.vn.vietatech.model.Section;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberAPI extends AbstractAPI {

    public MemberAPI(Context context) throws Exception {
        super(context);
    }

    public ArrayList<Member> getMembers(String keyword) throws Exception {
        setMethod(METHOD_GET_MEMBER);
        ArrayList<Member> members = new ArrayList<Member>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keySearch", keyword);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject table = (SoapObject) webServiceResponse
                        .getProperty(i);

                Member member = new Member();
                member.memberId = (table.getProperty("MemberId").toString());
                member.memberName = (table.getProperty("MemberName").toString());

                members.add(member);
            }
        }
        return members;
    }
}
