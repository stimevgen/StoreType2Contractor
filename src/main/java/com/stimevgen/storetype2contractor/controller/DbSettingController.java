package com.stimevgen.storetype2contractor.controller;

import com.google.gson.Gson;
import com.stimevgen.storetype2contractor.StoreType2Contractor;
import com.stimevgen.storetype2contractor.database.DataBase;
import com.stimevgen.storetype2contractor.model.Url;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class DbSettingController {
    private Stage stage;
    public Url url;
    public DataBase dataBase;
    public TextField nameServ;
    public TextField password;
    public TextField login;
    public TextField nameDB;
    public Label status;
    private File file;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void initialize() throws IOException {
        url = getUrlFromConfig();
        if (url!=null){
            nameServ.setText(url.host());
            login.setText(url.login());
            password.setText(url.password());
            nameDB.setText(url.nameDB());
        }

    }

    private Url getUrlFromConfig() throws IOException {
        Gson gson = new Gson();
        file = new File(System.getProperty("user.dir")+"/config.property");
        if (!file.exists()){
            InputStream inputStream = StoreType2Contractor.class.getResourceAsStream("config.property");
            url = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), Url.class);
            writeFile(file,new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
        }else{
            FileInputStream inputStream = new FileInputStream(file);
            url = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), Url.class);
            inputStream.close();
        }
        return url;
    }

    private void saveConfig() throws IOException {
        Gson gson = new Gson();;
        writeFile(file,gson.toJson(new Url(nameServ.getText(), nameDB.getText(), login.getText(), password.getText()), Url.class));
    }

    private void writeFile(File file,String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();
    }

    public void enter() throws IOException {
        saveConfig();
        dataBase = new DataBase(new Url(nameServ.getText(), nameDB.getText(), login.getText(), password.getText()));
        if (dataBase.connecting()) {
            FXMLLoader fxmlLoader = new FXMLLoader(StoreType2Contractor.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ApplicationController helloController = fxmlLoader.getController();
            helloController.setConnection(dataBase);
            stage.getIcons().add(new Image(Objects.requireNonNull(StoreType2Contractor.class.getResource("ico/logo.png")).toString()));
            stage.setTitle("Связка подраздлений для выгрузки в 1с!");
            stage.setScene(scene);
            stage.show();
            helloController.reload();
        } else {
            status.setText("Ошибка");
        }
    }

    public void close() {
        System.exit(0);
    }
}
