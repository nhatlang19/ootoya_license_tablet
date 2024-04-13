package com.vn.vietatech.model;

public class Member {
    public String memberId;
    public String memberName;

    public String mobile;
    public String email;
    public String dob;
    public String taxCode;
    public String company;
    public String companyAdr;
    public String nationality;
    public String memberClass;
    public String memberGrade;

    public boolean checked;

    public Member() {
        this.checked = false;
        this.memberId = "";
        this.memberName = "";
    }
    public Member(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.checked = false;
    }
}
