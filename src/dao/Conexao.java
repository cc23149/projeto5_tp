package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://regulus.cotuca.unicamp.br:1433;databaseName=BD23149;encrypt=true;trustServerCertificate=true";
        String user = "BD23149";
        String password = "BD23149";
        return DriverManager.getConnection(url, user, password);
    }
}
