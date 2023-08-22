package com.stimevgen.storetype2contractor.model;

public record Url(String host, String nameDB, String login, String password) {
    public String getUrl() {
        return "jdbc:sqlserver://" + host + ";"
                + "database=" + nameDB + ";"
                + "user=" + login + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=5;";
    }

}
