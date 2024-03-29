package com.vn.vietatech.model;

public class Setting {
    private String _serverIP;
    private String _storeNo;
    private String _posGroup;
    private String _posId;
    private String _vat;
    private String type;
    private String _serviceTax;
    private String section;

    public Setting() {
        _serverIP = "";
        _vat = "1";
        type = "1";
        _serviceTax = "7";
        section = "";
    }

    public String getSection() {
        return section == null ? "" : section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getServiceTax() {
        return _serviceTax;
    }

    public void setServiceTax(String _serviceTax) {
        this._serviceTax = _serviceTax;
    }

    public String getServerIP() {
        return _serverIP;
    }

    public void setServerIP(String _serverIP) {
        this._serverIP = _serverIP;
    }

    public String getStoreNo() {
        return _storeNo;
    }

    public void setStoreNo(String _storeNo) {
        this._storeNo = _storeNo;
    }

    public String getPosGroup() {
        return _posGroup;
    }

    public void setPosGroup(String _posGroup) {
        this._posGroup = _posGroup;
    }

    public String getPosId() {
        return _posId;
    }

    public void setPosId(String _posId) {
        this._posId = _posId;
    }

    public String getVat() {
        return _vat;
    }

    public void setVat(String _vat) {
        this._vat = _vat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
