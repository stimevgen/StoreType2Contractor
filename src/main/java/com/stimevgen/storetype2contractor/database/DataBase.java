package com.stimevgen.storetype2contractor.database;

import com.stimevgen.storetype2contractor.model.Contractor;
import com.stimevgen.storetype2contractor.model.StoreType2Contractor;
import com.stimevgen.storetype2contractor.model.Url;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;

public class DataBase {
    public Url url;
    public StoreType2Contractor storeType2Contractor;

    public DataBase(Url url) {
        this.url = url;
    }

    public boolean connecting() {

        try (Connection ignored = DriverManager.getConnection(url.getUrl())) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getHost() {
        return url.host();
    }

    public String getNameDB() {
        return url.nameDB();
    }

    public ObservableList<StoreType2Contractor> getData(String name) {
        ObservableList<StoreType2Contractor> observableListValue = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url.getUrl());
             Statement statement = connection.createStatement()) {
            String filter = (name != null?"where c.Cll_ShortName like '%"+name+"%' ":" ");
            String selectSql = """
                    select\s
                    \tID_STORE_TYPE_2_CONTRACTOR,
                    \tc.Cll_ShortName,
                    \tID_STORE_TYPE,
                    \tID_STORE_EXTERNAL,
                    \ts2c.ID_CONTRACTOR,
                    \ts2c.ID_STORE
                    from [OL].[STORE_TYPE_2_CONTRACTOR] s2c
                    join cll c on c.Cll_UniCode = s2c.ID_CONTRACTOR
                    """+ filter + """                    
                    order by
                    \tID_CONTRACTOR""";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                observableListValue.add(new StoreType2Contractor(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(6),
                        resultSet.getString(1)));
            }
            return observableListValue;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<String> getTypeContractor() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url.getUrl());
             Statement statement = connection.createStatement()) {
            String selectSql = "select distinct ID_STORE_TYPE from [OL].[STORE_TYPE_2_CONTRACTOR]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                observableList.add(resultSet.getString(1));
            }
            return observableList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Contractor> getContractor() {
        ObservableList<Contractor> observableList = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url.getUrl());
             Statement statement = connection.createStatement()) {
            String selectSql = "select Skd_UniCode, Skd_ShortName, Skd_Value0_01 from skd";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                observableList.add(new Contractor(resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(2)));
            }
            return observableList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addData(String ID_STORE_EXTERNAL, String ID_STORE_TYPE, String ID_STORE, String ID_CONTRACTOR) throws SQLException {
        Connection connection = DriverManager.getConnection(url.getUrl());
        Statement statement = connection.createStatement();
        String sql = "insert into ol.STORE_TYPE_2_CONTRACTOR (ID_STORE_EXTERNAL,ID_STORE_TYPE,ID_STORE,ID_CONTRACTOR) " +
                "values('" + ID_STORE_EXTERNAL + "','" + ID_STORE_TYPE + "'," + ID_STORE + "," + ID_CONTRACTOR + ")";
        statement.execute(sql);
    }

    public void deleteData(String id) {
        try (Connection connection = DriverManager.getConnection(url.getUrl());
             Statement statement = connection.createStatement()) {
            statement.execute("delete from ol.STORE_TYPE_2_CONTRACTOR where ID_STORE_TYPE_2_CONTRACTOR = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGUID() {
        try (Connection connection = DriverManager.getConnection(url.getUrl());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select NEWID()");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
