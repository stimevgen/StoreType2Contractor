module com.stimevgen.storetype2contractor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires com.google.gson;

    opens com.stimevgen.storetype2contractor to javafx.graphics;
    exports com.stimevgen.storetype2contractor;
    exports com.stimevgen.storetype2contractor.controller;
    exports com.stimevgen.storetype2contractor.model;
    exports com.stimevgen.storetype2contractor.database;
    opens com.stimevgen.storetype2contractor.controller to javafx.fxml;
}