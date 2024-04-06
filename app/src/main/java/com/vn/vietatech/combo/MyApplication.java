package com.vn.vietatech.combo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.model.MemClass;
import com.vn.vietatech.model.MemGrade;
import com.vn.vietatech.model.Nationality;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.SalesCode;
import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;

import android.app.Application;

public class MyApplication extends Application {
    private Map<String, ArrayList<Section>> _listSections = new HashMap<>();
    private Cashier _cashier = null;

    private ArrayList<Cashier> cashiers = null;

    private ArrayList<Table> _tables = null;
    private ArrayList<PosMenu> _listPosMenu = null;
    private ArrayList<SalesCode> _salesCode = null;

    private ArrayList<Nationality> nationalities = null;
    private ArrayList<MemClass> memClasses = null;
    private ArrayList<MemGrade> memGrades = null;


    public ArrayList<PosMenu> getListPosMenu() {
        return _listPosMenu;
    }

    public void setListPosMenu(ArrayList<PosMenu> listPosMenu) {
        this._listPosMenu = listPosMenu;
    }

    public ArrayList<Section> getSections(String key) {
        return _listSections.get(key);
    }

    public void setSections(String key, ArrayList<Section> listSections) {
        this._listSections.put(key, listSections);
    }

    public Cashier getCashier() {
        return _cashier;
    }

    public void setCashier(Cashier cashier) {
        this._cashier = cashier;
    }

    public ArrayList<Table> getTables() {
        return this._tables;
    }

    public void setTables(ArrayList<Table> tables) {
        tables.add(0, new Table());
        this._tables = tables;
    }

    public ArrayList<SalesCode> getSalesCode() {
        return _salesCode;
    }

    public void setSalesCode(ArrayList<SalesCode> _salesCode) {
        _salesCode.add(0, new SalesCode());
        this._salesCode = _salesCode;
    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(ArrayList<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public ArrayList<Nationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(ArrayList<Nationality> nationalities) {
        this.nationalities = nationalities;
    }

    public ArrayList<MemClass> getMemClasses() {
        return memClasses;
    }

    public void setMemClasses(ArrayList<MemClass> memClasses) {
        this.memClasses = memClasses;
    }

    public ArrayList<MemGrade> getMemGrades() {
        return memGrades;
    }

    public void setMemGrades(ArrayList<MemGrade> memGrades) {
        this.memGrades = memGrades;
    }
}
