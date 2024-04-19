package com.vn.vietatech.model;

public class Order {
	private String ordExt;
	private String posPerScode;

	private String memberId;
	private String memberName;
	
	public Order() {
		setOrdExt("0_0");
		setPosPerScode("0_0_0");
		memberId = "";
		memberName = "";
	}

	public String getOrd() {
		String[] list = getOrdExt().split("_");
		return list[0];
	}

	public String getExt() {
		String[] list = getOrdExt().split("_");
		if(list.length < 2) 
			return "0";
		return list[1];
	}


	public String getPos() {
		String[] list = getPosPerScode().split("_");
		return list[0];
	}

	public String getPer() {
		String[] list = getPosPerScode().split("_");
		return list[1];
	}
	
	public String getSalesCode() {
		String[] list = getPosPerScode().split("_");
		return list[2];
	}

	public String getOrdExt() {
		return ordExt;
	}

	public void setOrdExt(String ordExt) {
		this.ordExt = ordExt;
	}

	public String getPosPerScode() {
		return posPerScode;
	}

	public void setPosPerScode(String posPerScode) {
		this.posPerScode = posPerScode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
