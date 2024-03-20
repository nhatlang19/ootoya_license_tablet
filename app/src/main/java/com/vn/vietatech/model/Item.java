package com.vn.vietatech.model;

import com.vn.vietatech.utils.Utils;

import java.util.ArrayList;

public class Item {
    public static final String STATUS_OLD = "#";
    public static final String STATUS_CANCEL = "C";
    private static final String SEPARATE = "|";
    private String id;
    private String splited;
    private String qty;
    private String printStatus;
    private String itemName;
    private String orgPrice;
    private String total;
    private String itemType;
    private String itemCode;
    private String modifierInt;
    private String masterCode;
    private String comboPack;

    public String getComboClass() {
        return comboClass;
    }

    public void setComboClass(String comboClass) {
        this.comboClass = comboClass;
    }

    private String comboClass;
    private String weightItem;
    private String hidden;
    private String instruction;
    private String segNo;
    private String onPromotion;
    private ArrayList<Remark> remarks;
    private String promoCode;
    private String promoItem;
    private String promoPrice;
    private String promoDesc;
    private String promoClass;
    private String proInstruct;
    private String discType;
    private String discId;
    private String promoQty;
    private String startDate;
    private String endDate;
    private String minQty;
    private String maxQty;
    private String reCurr;
    private String pkgQty1;
    private String minTrxAmt;
    private String maxTrxAmt;
    private String Ratio;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
    private String pkgPrice;
    private String pkgQty;
    private String pkgItems;
    private String blanket;
    private String discountable;
    private String subcatg;
    private String sptax;
    private String tax;
    private String taxAmt;
    private String brand;
    private String memberId;
    private String memberName;
    private String jocketWallet;
    private String cashVoucher;
    private String discountVoucher;
    private String discountMember;
    private String distAmt;
    private String subTotal;
    private String serveTax;
    private String serveTaxAmt;
    private String spTaxAmt;
    private ArrayList<ItemCombo> itemCombo;
    private ArrayList<ItemModifier> itemModifiers;
    private int numberClick;
    public Item() {
        id = " ";
        qty = "1";
        splited = "0";
        printStatus = " ";
        itemName = " ";
        orgPrice = "0";
        setPromoPrice("0");
        total = "0";
        itemType = " ";
        itemCode = " ";
        modifierInt = "0";
        masterCode = "";
        comboPack = "N";
        comboClass = "";
        hidden = " ";
        instruction = "";
        onPromotion = "N";
        setRemarks(new ArrayList<Remark>());
        setItemCombo(new ArrayList<ItemCombo>());
        setItemModifiers(new ArrayList<ItemModifier>());
        promoCode = "0";
        promoPrice = "0";
        promoDesc = "0";
        promoQty = "0";
        promoClass = "0";
        startDate = "";
        endDate = "";
        minQty = "";
        maxQty = "";
        pkgPrice = "0";
        pkgQty = "0";
        pkgItems = "0";
        blanket = "";
        tax = "0";
        taxAmt = "0";
        sptax = "0";
        subcatg = "";
        brand = "";

        setNumberClick(0);
        segNo = "0";
        memberId = "";
        memberName = "";
        jocketWallet = "";
        cashVoucher = "";
        discountVoucher = "";
        discountMember = "";
        proInstruct = "";
        discType = "";
        discId = "";
    }

    public String getServeTax() {
        return serveTax;
    }

    public void setServeTax(String serveTax) {
        float tmp = Float.parseFloat(serveTax);
        this.serveTax = String.valueOf((int) tmp);
    }

    public String getProInstruct() {
        return proInstruct;
    }

    public void setProInstruct(String proInstruct) {
        this.proInstruct = proInstruct;
    }

    public String getDiscType() {
        return discType;
    }

    public void setDiscType(String discType) {
        this.discType = discType;
    }

    public String getDiscId() {
        return discId;
    }

    public void setDiscId(String discId) {
        this.discId = discId;
    }

    public String getPromoQty() {
        return promoQty;
    }

    public void setPromoQty(String promoQty) {
        this.promoQty = promoQty;
    }

    public String getDistAmt() {
        return distAmt;
    }

    public void setDistAmt(String distAmt) {
        float tmp = Float.parseFloat(distAmt);
        this.distAmt = String.valueOf((int) tmp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        if (instruction.contains("anyType{}")) {
            instruction = " ";
        }
        this.instruction = instruction;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getComboPack() {
        return comboPack.trim();
    }

    public void setComboPack(String comboPack) {
        this.comboPack = comboPack;
    }

    public String getMasterCode() {
        return masterCode;
    }

    public void setMasterCode(String masterCode) {
        this.masterCode = masterCode;
    }

    public String getModifier() {
        return modifierInt;
    }

    public void setModifier(String modifierInt) {
        this.modifierInt = modifierInt;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        float iorgTotal = Float.parseFloat(total);
        this.total = String.valueOf((int) iorgTotal);
    }

    public String getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(String orgPrice) {
        float iorgPrice = Float.parseFloat(orgPrice);
        this.orgPrice = String.valueOf((int) iorgPrice);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrintStatus() {
        if (printStatus.trim().isEmpty()) {
            return printStatus;
        } else if (printStatus.equals("9")) {
            printStatus = STATUS_CANCEL;
        } else {
            printStatus = STATUS_OLD;
        }
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public boolean isNewItem() {
        return !(getPrintStatus().equals(STATUS_CANCEL) || getPrintStatus().equals(STATUS_OLD));
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public ArrayList<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(ArrayList<Remark> remarks) {
        this.remarks = remarks;
    }

    public String getSplited() {
        return splited;
    }

    public void setSplited(String splited) {
        this.splited = splited;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        float ipromoPrice = Float.parseFloat(promoPrice);
        this.promoPrice = String.valueOf((int) ipromoPrice);
    }

    public String getOnPromotion() {
        return onPromotion.trim();
    }

    public void setOnPromotion(String onPromotion) {
        this.onPromotion = onPromotion;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        if (promoCode.contains("anyType{}")) {
            promoCode = "";
        }
        this.promoCode = promoCode;
    }

    public String getSegNo() {
        return segNo;
    }

    public void setSegNo(String segNo) {
        this.segNo = segNo;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public String getPromoClass() {
        return promoClass;
    }

    public void setPromoClass(String promoClass) {
        this.promoClass = promoClass;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }

    public String getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(String maxQty) {
        this.maxQty = maxQty;
    }

    public String getPkgQty() {
        return pkgQty;
    }

    public void setPkgQty(String pkgQty) {
        this.pkgQty = pkgQty;
    }

    public String getPkgItems() {
        return pkgItems;
    }

    public void setPkgItems(String pkgItems) {
        this.pkgItems = pkgItems;
    }

    public String getBlanket() {
        return blanket;
    }

    public void setBlanket(String blanket) {
        this.blanket = blanket;
    }

    public String getDiscountable() {
        return discountable;
    }

    public void setDiscountable(String discountable) {
        this.discountable = discountable;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        float tmp = Float.parseFloat(taxAmt);
        this.taxAmt = String.valueOf((int) tmp);
    }

    public ArrayList<ItemCombo> getItemCombo() {
        return itemCombo;
    }

    public void setItemCombo(ArrayList<ItemCombo> itemCombo) {
        this.itemCombo = itemCombo;
    }

    public ArrayList<ItemModifier> getItemModifiers() {
        return itemModifiers;
    }

    public void setItemModifiers(ArrayList<ItemModifier> itemModifiers) {
        this.itemModifiers = itemModifiers;
    }

    public int getNumberClick() {
        return numberClick;
    }

    public void setNumberClick(int numberClick) {
        this.numberClick = numberClick;
    }

    public String getPkgPrice() {
        return pkgPrice;
    }

    public void setPkgPrice(String pkgPrice) {
        float ipkgPrice = Float.parseFloat(pkgPrice);
        this.pkgPrice = String.valueOf((int) ipkgPrice);
    }



    public String getWeightItem() {
        return weightItem;
    }

    public void setWeightItem(String weightItem) {
        this.weightItem = weightItem;
    }

    public String getSubcatg() {
        return subcatg;
    }

    public void setSubcatg(String subcatg) {
        this.subcatg = subcatg;
    }

    public String getSptax() {
        return sptax;
    }

    public void setSptax(String sptax) {
        if (sptax == null) {
            sptax = "0";
        }
        this.sptax = sptax;
    }

    public String getPromoItem() {
        return promoItem;
    }

    public void setPromoItem(String promoItem) {
        this.promoItem = promoItem;
    }

    public String getReCurr() {
        return reCurr;
    }

    public void setReCurr(String reCurr) {
        this.reCurr = reCurr;
    }

    public String getPkgQty1() {
        return pkgQty1;
    }

    public void setPkgQty1(String pkgQty1) {
        this.pkgQty1 = pkgQty1;
    }

    public String getMinTrxAmt() {
        return minTrxAmt;
    }

    public void setMinTrxAmt(String minTrxAmt) {
        this.minTrxAmt = minTrxAmt;
    }

    public String getMaxTrxAmt() {
        return maxTrxAmt;
    }

    public void setMaxTrxAmt(String maxTrxAmt) {
        this.maxTrxAmt = maxTrxAmt;
    }

    public String getRatio() {
        return Ratio;
    }

    public void setRatio(String ratio) {
        Ratio = ratio;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getPromoDay(String promoDay) {
        switch (promoDay) {
            case "MON" -> {
                return getMon();
            }
            case "TUE" -> {
                return getTue();
            }
            case "WED" -> {
                return getWed();
            }
            case "THU" -> {
                return getThu();
            }
            case "FRI" -> {
                return getFri();
            }
            case "SAT" -> {
                return getSat();
            }
            case "SUN" -> {
                return getSun();
            }
        }
        return getMon();
    }

    public boolean hasPromotion() {
        return this.onPromotion.equals("Y");
    }


    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        float tmp = Float.parseFloat(subTotal);
        this.subTotal = String.valueOf((int) tmp);
    }

    public String getServeTaxAmt() {
        return serveTaxAmt;
    }

    public void setServeTaxAmt(String serveTaxAmt) {
        float tmp = Float.parseFloat(serveTaxAmt);
        this.serveTaxAmt = String.valueOf((int) tmp);
    }

    public String getSpTaxAmt() {
        return spTaxAmt;
    }

    public void setSpTaxAmt(String spTaxAmt) {
        float tmp = Float.parseFloat(spTaxAmt);
        this.spTaxAmt = String.valueOf((int) tmp);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand.contains("anyType{}")) {
            brand = "";
        }
        this.brand = brand;
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

    public String getJocketWallet() {
        return jocketWallet;
    }

    public void setJocketWallet(String jocketWallet) {
        this.jocketWallet = jocketWallet;
    }

    public String getCashVoucher() {
        return cashVoucher;
    }

    public void setCashVoucher(String cashVoucher) {
        this.cashVoucher = cashVoucher;
    }

    public String getDiscountVoucher() {
        return discountVoucher;
    }

    public void setDiscountVoucher(String discountVoucher) {
        this.discountVoucher = discountVoucher;
    }

    public String getDiscountMember() {
        return discountMember;
    }

    public void setDiscountMember(String discountMember) {
        this.discountMember = discountMember;
    }

    public void autoCalculate(double serviceTax) {
        String promoCode = this.hasPromotion() ? this.getPromoCode() : "";
        int promoClass = this.hasPromotion() ? Integer.valueOf(this.getPromoClass()) : 0;
        int pkgQty = this.hasPromotion() ? Integer.valueOf(this.getPkgQty()) : 0;
        int pkgItems = this.hasPromotion() ? Integer.valueOf(this.getPkgItems()) : 0;
        String promoDesc = this.hasPromotion() ? this.getPromoDesc() : "";
        int unitPromoPrice = (int) (this.hasPromotion() ? Math.round(Double.valueOf(this.getPromoPrice())) : Math.round(Double.valueOf(this.getOrgPrice())));

        int soLuong = 0;
        int promoQty = 0;
        if (promoClass == (int) PromoClass.MuaMtangN) {
            promoQty = numberClick * pkgItems;
            soLuong = numberClick * (pkgItems + pkgQty);
        } else {
            soLuong = numberClick;
        }

        String comboClass = ""; // Cái combo class bình thường = 'emtyp nhé'
        int taxRate = Integer.valueOf(getTax());
        int spTaxRate = Integer.valueOf(getSptax());
        int unitSellPrice = (int) Math.round(Double.valueOf(getOrgPrice()));

        int distAmt = 0;
        if (this.hasPromotion() && promoClass == PromoClass.MuaMtangN) {
            distAmt = (unitSellPrice - unitPromoPrice) * promoQty;
        } else {
            distAmt = soLuong * (unitSellPrice - unitPromoPrice);
        }

        double subTotal = soLuong * unitSellPrice - distAmt;
        double serveTaxAmt = subTotal * serviceTax * 0.01;
        double spTaxAmt = (subTotal + serveTaxAmt) * spTaxRate * 0.01;
        double total = subTotal + spTaxAmt + serveTaxAmt;
        boolean taxInclude = false;
        double taxAmt = Utils.taxAmtCalculate(taxInclude, taxRate, total);

        setComboClass(comboClass);
        setPromoCode(promoCode);
        setPromoQty(String.valueOf(promoQty));
        setSubTotal(String.valueOf((int) subTotal));
        setServeTaxAmt(String.valueOf((int) serveTaxAmt));
        setSpTaxAmt(String.valueOf((int) spTaxAmt));
        setTotal(String.valueOf((int) total));
        setTaxAmt(String.valueOf((int) taxAmt));
        setDistAmt(String.valueOf((int) distAmt));
        setServeTax(String.valueOf((int) serviceTax));

        setQty(String.valueOf(soLuong));
    }

    @Override
    public String toString() {
        String result = "";
        result += qty + SEPARATE;
        result += printStatus.trim() + SEPARATE;
        result += itemName + SEPARATE;
        result += promoPrice + SEPARATE;
        result += orgPrice + SEPARATE;
        result += distAmt + SEPARATE;
        result += subTotal + SEPARATE;
        result += itemType + SEPARATE;
        result += itemCode + SEPARATE;
        result += modifierInt + SEPARATE;
        result += masterCode + SEPARATE;
        result += comboClass + SEPARATE;
        result += instruction + SEPARATE;
        result += segNo + SEPARATE;
        result += promoCode + SEPARATE;
        result += promoClass + SEPARATE;
        result += promoQty + SEPARATE;
        result += pkgQty + SEPARATE;
        result += pkgItems + SEPARATE;
        result += proInstruct + SEPARATE;
        result += discType + SEPARATE;
        result += discId + SEPARATE;
        result += total + SEPARATE;
        result += tax + SEPARATE;
        result += taxAmt + SEPARATE;
        result += sptax + SEPARATE;
        result += spTaxAmt + SEPARATE;
        result += serveTax + SEPARATE;
        result += serveTaxAmt + SEPARATE;
        result += subcatg + SEPARATE;
        result += brand.trim() + SEPARATE;
        result += memberId.trim() + SEPARATE;
        result += memberName.trim() + SEPARATE;
        result += jocketWallet.trim() + SEPARATE; // jocker
        result += cashVoucher.trim() + SEPARATE; // voucherCash
        result += discountVoucher.trim() + SEPARATE;
        result += discountMember.trim();

        return result;
    }
}
