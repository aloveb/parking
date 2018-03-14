package com.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DataService {

    @Autowired(required = false)
    DataSource dataSource;

    public Statement getStatement(){
        Statement newStatement = null;
        try {
            Connection connection =  dataSource.getConnection();
            newStatement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newStatement;
    }
}
