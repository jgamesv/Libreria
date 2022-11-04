/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author jgame
 */
public class Conection {

    private static Connection conn = null;
    private String url;
    private String usuario;
    private String password;

    public Conection() {
        //String url = "jdbc:postgresql://localhost:5432/libreria";
        //String usuario = "postgres";
        //String password = "4Ud3f3kD";

        try {
            Properties file = new Properties();
            file.load(new FileInputStream(new File("./src/main/java/DBConection/Properties.properties")));
            url = file.getProperty("url");
            usuario = file.getProperty("user");
            password = file.getProperty("pass");
            conn = DriverManager.getConnection(url, usuario, password);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static public Connection getConection() throws SQLException {
        if (conn == null) {
            new Conection();
        }

        return conn;
    }
}
