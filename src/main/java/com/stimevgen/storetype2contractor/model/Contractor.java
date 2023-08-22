package com.stimevgen.storetype2contractor.model;

import javafx.beans.property.SimpleStringProperty;

public class Contractor {
    private final SimpleStringProperty Skd_UniCode;
    private final SimpleStringProperty Cll_Unicode;
    private final SimpleStringProperty ShortName;

    public Contractor(String Skd_UniCode, String Cll_Unicode, String ShortName) {
        this.Skd_UniCode = new SimpleStringProperty(Skd_UniCode);
        this.Cll_Unicode = new SimpleStringProperty(Cll_Unicode);
        this.ShortName = new SimpleStringProperty(ShortName);
    }

    public String getSkd_UniCode() {
        return Skd_UniCode.get();
    }

    public String getCll_Unicode() {
        return Cll_Unicode.get();
    }

    public String getShortName() {
        return ShortName.get();
    }

}
