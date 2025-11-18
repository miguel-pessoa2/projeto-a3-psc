package org.example.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection createConnection(){

        String URL = "jdbc:mysql://localhost:3306/projeto";
        String USER = "root";
        String PASSWORD = "274277mm";

        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(SQLException exc){
            System.out.println("Erro ao criar conexão: " + exc.getMessage());
            return null;
        }
    }
}
