package com.stimevgen.storetype2contractor.controller;

import com.stimevgen.storetype2contractor.StoreType2Contractor;
import com.stimevgen.storetype2contractor.database.DataBase;

import java.awt.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Optional;

public class ApplicationController {
    public Label stateConnection;
    public Label dataBase;
    public Label host;
    @FXML
    public TableView<com.stimevgen.storetype2contractor.model.StoreType2Contractor> tableS2C;
    @FXML
    public TableColumn<com.stimevgen.storetype2contractor.model.StoreType2Contractor, String> Cll_ShortName;
    @FXML
    public TableColumn<com.stimevgen.storetype2contractor.model.StoreType2Contractor, String> Skd_ShortName;
    @FXML
    public TableColumn<com.stimevgen.storetype2contractor.model.StoreType2Contractor, String> ID_STORE_TYPE;
    @FXML
    public TableColumn<com.stimevgen.storetype2contractor.model.StoreType2Contractor, String> ID_STORE_EXTERNAL;
    public Button edit;
    public Button delete;
    public Button update;
    public Button add;
    public ToolBar panelButton;
    public TextField findData;
    private DataBase connection;

    public void setConnection(DataBase connection) {
        this.connection = connection;
    }

    @FXML
    void initialize() {
        loadIconFromButtonToolbar();
        tableS2C.getSelectionModel().setCellSelectionEnabled(true);
        Cll_ShortName.setCellValueFactory(cell -> cell.getValue().cll_ShortNameProperty());
        ID_STORE_TYPE.setCellValueFactory(cell -> cell.getValue().ID_STORE_TYPEProperty());
        ID_STORE_EXTERNAL.setCellValueFactory(cell -> cell.getValue().ID_STORE_EXTERNALProperty());
        Skd_ShortName.setCellValueFactory(cell -> cell.getValue().skd_ShortNameProperty());
    }

    private void loadIconFromButtonToolbar() {
        edit.graphicProperty().set(new ImageView(new Image(StoreType2Contractor.class.getResource("ico/tabPanel/edit.png").toString())));
        delete.graphicProperty().set(new ImageView(new Image(StoreType2Contractor.class.getResource("ico/tabPanel/delete.png").toString())));
        update.graphicProperty().set(new ImageView(new Image(StoreType2Contractor.class.getResource("ico/tabPanel/update.png").toString())));
        add.graphicProperty().set(new ImageView(new Image(StoreType2Contractor.class.getResource("ico/tabPanel/add.png").toString())));
    }

    public void pressedCopy(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.C) {
            String value = tableS2C.getSelectionModel().getSelectedCells().get(0).getTableColumn().getCellObservableValue(0).getValue().toString();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
        }
    }

    public void onClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            ObservableList<Window> windows = Stage.getWindows();
            for (Window window : windows) {
                if (((Stage) window).getTitle().equals("Изменить связку!")) {
                    window.requestFocus();
                    return;
                }
            }
            editLink();
        }
    }

    public void editLink() throws IOException {
        int index = tableS2C.getSelectionModel().getSelectedIndex();
        showEditForm("Изменить связку!", tableS2C.getSelectionModel());
        updateView(null);
        tableS2C.getSelectionModel().select(index, Cll_ShortName);
    }

    public void addLink(MouseEvent event) throws IOException {
        showEditForm("Добавить связку!", null);
        updateView(event);
    }

    public void updateView(MouseEvent event) {
        tableS2C.getItems().clear();
        tableS2C.setItems(connection.getData(findData.getText()));
        tableS2C.requestFocus();
    }

    public void deleteLik(MouseEvent event) {
        if (!tableS2C.getSelectionModel().isEmpty()) {
            if (showMessage("Вы действительно хотите удалить запись?", Alert.AlertType.CONFIRMATION)) {
                connection.deleteData(tableS2C.getSelectionModel().getSelectedItem().ID_STORE_TYPE_2_CONTRACTORProperty().get());
            }
            updateView(event);
            tableS2C.requestFocus();
            return;
        }
        showMessage("Выделите строку для удаления!", Alert.AlertType.INFORMATION);
    }

    private void showEditForm(String title, TableView.TableViewSelectionModel<com.stimevgen.storetype2contractor.model.StoreType2Contractor> selectionModel) throws IOException {
        FXMLLoader formEdit = new FXMLLoader(StoreType2Contractor.class.getResource("edit-form.fxml"));
        Scene scene = new Scene(formEdit.load());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        EditFormController controllerEditBook = formEdit.getController();
        controllerEditBook.setDataBase(connection);
        controllerEditBook.reload();
        if (!(selectionModel == null)) {
            if (!selectionModel.isEmpty()) {
                controllerEditBook.setId(selectionModel.getSelectedItem().ID_STORE_TYPE_2_CONTRACTORProperty().get());
                controllerEditBook.setIdStore(selectionModel.getSelectedItem().getID_STORE());
                controllerEditBook.setIdContractor(selectionModel.getSelectedItem().ID_CONTRACTORProperty().get());
                controllerEditBook.uid.setText(selectionModel.getSelectedItem().ID_STORE_EXTERNALProperty().get());
                controllerEditBook.typeContractor.setValue(selectionModel.getSelectedItem().ID_STORE_TYPEProperty().get());
            } else {
                showMessage("Не выделены строка для редактирования!", Alert.AlertType.INFORMATION);
                return;
            }
        }
        stage.showAndWait();
    }

    private Boolean showMessage(String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Внимание!");
        alert.setHeaderText(text);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }

    public void reload() {
        this.dataBase.setText(this.dataBase.getText() + " " + connection.getNameDB());
        this.host.setText(this.host.getText() + " " + connection.getHost());
        this.stateConnection.setText(connection.connecting() ? this.stateConnection.getText() + " Подключено" : this.stateConnection.getText() + " Нет подключения");
        updateView(null);
    }

    public void findData(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            tableS2C.getItems().clear();
            tableS2C.setItems(connection.getData(findData.getText()));
        }
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onAbout(ActionEvent actionEvent) {
        showMessage("Создана Подоксёновым Евгением Сергеевичем в 2023 году!", Alert.AlertType.INFORMATION);
    }
}