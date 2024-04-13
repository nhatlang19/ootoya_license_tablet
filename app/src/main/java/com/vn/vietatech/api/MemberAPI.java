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

    public String setMember(Member member) throws Exception {
        setMethod(METHOD_SET_MEMBER);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("memberName", member.memberName.trim());
        params.put("mobilePhone", member.mobile.trim());
        params.put("email", member.email.trim());
        params.put("nationality", member.nationality);
        params.put("dob", member.dob.length() > 0 ? member.dob.replaceAll("-", "").trim() : "");
        params.put("memberClass", member.memberClass);
        params.put("memberGrade", member.memberGrade);
        params.put("taxCode", member.taxCode.length() > 0 ? member.taxCode.trim() : "");
        params.put("company", member.company.length() > 0 ? member.company.trim() : "");
        params.put("companyAdr", member.companyAdr.length() > 0 ? member.companyAdr.trim() : "");

        return callService(params).toString();
    }
}
