package com.stimevgen.storetype2contractor.controller;


import com.stimevgen.storetype2contractor.database.DataBase;
import com.stimevgen.storetype2contractor.model.Contractor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EditFormController {
    public TextField uid;
    public ComboBox<String> typeContractor;
    public ComboBox<Contractor> contractor;
    public ComboBox<Contractor> store;
    private DataBase connection;
    private String id;

    public void setIdStore(String idStore) {
        List<Contractor> contractorList = store.getItems().stream().filter(contractor1 -> contractor1.getSkd_UniCode().equals(idStore)).toList();
        store.setValue(contractorList.get(0));
    }

    public void setIdContractor(String idContractor) {
        List<Contractor> storeList = contractor.getItems().stream().filter(contractor1 -> contractor1.getCll_Unicode().equals(idContractor)).toList();
        contractor.setValue(storeList.get(0));
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setDataBase(DataBase dataBase) {
        connection = dataBase;
    }

    @FXML
    void initialize() {
        Callback<ListView<Contractor>, ListCell<Contractor>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Contractor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getShortName());
            }
        };
        contractor.setCellFactory(factory);
        contractor.setButtonCell(factory.call(null));
        store.setCellFactory(factory);
        store.setButtonCell(factory.call(null));
    }

    public void onWrite() {
        Stage stage = (Stage) uid.getScene().getWindow();
        try {
            connection.addData(uid.getText(), typeContractor.getValue(), store.getValue().getSkd_UniCode(), contractor.getValue().getCll_Unicode());
            if (id != null) {
                if (!id.equals("")) {
                    connection.deleteData(id);
                }
            }
        } catch (SQLException e) {
            showMessage("Некорректные данные", Alert.AlertType.ERROR);
        }
        stage.close();
    }

    public void getGUID(MouseEvent event) {
        if (uid.getText().equals("")) {
            uid.setText(connection.getGUID());
        }
    }

    public void reload() {
        typeContractor.setItems(connection.getTypeContractor());
        contractor.setItems(connection.getContractor());
        store.setItems(connection.getContractor());
    }

    private Boolean showMessage(String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Внимание!");
        alert.setHeaderText(text);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }
}
