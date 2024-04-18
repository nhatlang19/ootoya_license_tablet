package com.vn.vietatech.model.dto;

public class MemberRemarkDTO {
    public String posBizDate;
    public String posNo;
    public String orderNo;
    public String extNo;
    public String splited;
    public String memberId;
    public String memberName;
    public String segNo;

    public MemberRemarkDTO(String posBizDate, String posNo, String orderNo, String extNo,
                           String splited, String memberId, String memberName, String segNo) {
        this.posBizDate = posBizDate;
        this.posNo = posNo;
        this.orderNo = orderNo;
        this.extNo = extNo;
        this.splited = splited;
        this.memberId = memberId;
        this.memberName = memberName;
        this.segNo = segNo;
    }
}
