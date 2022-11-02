/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jgame
 */
public class Conection {

    private static Connection conn = null;
    private String driver;
    private String url;
    private String usuario;
    private String password;

    public Conection() {

        String url = "jdbc:postgresql://localhost:5432/libreria";
        String usuario = "postgres";
        String password = "4Ud3f3kD";

        try {
           conn = DriverManager.getConnection(url, usuario, password);
           conn.setAutoCommit(false);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    static public Connection getConection() throws SQLException {
         if (conn == null){
            new Conection();
            }
  
            return conn;
    }
}
