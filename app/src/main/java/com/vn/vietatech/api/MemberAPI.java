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

    public ArrayList<Member> searchMembers(String keyword) throws Exception {
        setMethod(METHOD_SEARCH_MEMBER);
        ArrayList<Member> members = new ArrayList<Member>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keySearch", keyword);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            if (webServiceResponse != null) {
                for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                    SoapObject table = (SoapObject) webServiceResponse
                            .getProperty(i);

                    Member member = new Member();
                    member.memberId = (table.getProperty("MemberId").toString());
                    member.memberName = (table.getProperty("MemberName").toString());

                    members.add(member);
                }
            }
        }
        return members;
    }

    public ArrayList<Member> checkDuplicateName(String memberName) throws Exception {
        setMethod(METHOD_CHECK_MEMBER_TRUNG_TEN);
        ArrayList<Member> members = new ArrayList<Member>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("memberName", memberName);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            if (webServiceResponse != null) {
                for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                    SoapObject table = (SoapObject) webServiceResponse
                            .getProperty(i);

                    Member member = new Member();
                    member.memberName = (table.getProperty("FullName").toString());

                    members.add(member);
                }
            }
        }
        return members;
    }

    public Member getMember(String keyword) throws Exception {
        setMethod(METHOD_GET_MEMBER);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keySearch", keyword);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");

        Member member = new Member();
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            SoapObject tableObject = (SoapObject) webServiceResponse
                    .getProperty("Table");
            member.memberId = (tableObject.getProperty("MemberId").toString());
            member.memberName = (tableObject.getProperty("MemberName").toString());

        }
        return member;
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
