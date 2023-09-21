package com.stimevgen.storetype2contractor.model;

import javafx.beans.property.SimpleStringProperty;

public class StoreType2Contractor {
    private final SimpleStringProperty ID_STORE_TYPE_2_CONTRACTOR;
    private final SimpleStringProperty Cll_ShortName;
    private final SimpleStringProperty ID_STORE_TYPE;
    private final SimpleStringProperty ID_STORE_EXTERNAL;
    private final SimpleStringProperty ID_STORE;
    private final SimpleStringProperty ID_CONTRACTOR;
    private final SimpleStringProperty Skd_ShortName;

    public StoreType2Contractor(String Cll_ShortName,String Skd_ShortName, String ID_STORE_TYPE, String ID_STORE_EXTERNAL, String ID_STORE, String ID_STORE_TYPE_2_CONTRACTOR,String ID_CONTRACTOR) {
        this.Cll_ShortName = new SimpleStringProperty(Cll_ShortName);
        this.Skd_ShortName = new SimpleStringProperty(Skd_ShortName);
        this.ID_STORE_EXTERNAL = new SimpleStringProperty(ID_STORE_EXTERNAL);
        this.ID_STORE_TYPE = new SimpleStringProperty(ID_STORE_TYPE);
        this.ID_STORE = new SimpleStringProperty(ID_STORE);
        this.ID_STORE_TYPE_2_CONTRACTOR = new SimpleStringProperty(ID_STORE_TYPE_2_CONTRACTOR);
        this.ID_CONTRACTOR = new SimpleStringProperty(ID_CONTRACTOR);
    }

    public String getID_STORE() {
        return ID_STORE.get();
    }

    public SimpleStringProperty cll_ShortNameProperty() {
        return Cll_ShortName;
    }

    public SimpleStringProperty skd_ShortNameProperty() {
        return Skd_ShortName;
    }

    public SimpleStringProperty ID_STORE_TYPEProperty() {
        return ID_STORE_TYPE;
    }

    public SimpleStringProperty ID_STORE_EXTERNALProperty() {
        return ID_STORE_EXTERNAL;
    }

    public SimpleStringProperty ID_STORE_TYPE_2_CONTRACTORProperty() {
        return ID_STORE_TYPE_2_CONTRACTOR;
    }

    public SimpleStringProperty ID_CONTRACTORProperty() {
        return ID_CONTRACTOR;
    }
}
