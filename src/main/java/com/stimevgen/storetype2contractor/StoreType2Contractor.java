package com.stimevgen.storetype2contractor;

import com.stimevgen.storetype2contractor.controller.DbSettingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

import java.util.Objects;


public class StoreType2Contractor extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreType2Contractor.class.getResource("dbSetting-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DbSettingController dbSettingController = fxmlLoader.getController();
        dbSettingController.setStage(stage);
        stage.getIcons().add(new Image(Objects.requireNonNull(StoreType2Contractor.class.getResource("ico/logo.png")).toString()));
        stage.setTitle("Связка подраздлений для выгрузки в 1с!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}