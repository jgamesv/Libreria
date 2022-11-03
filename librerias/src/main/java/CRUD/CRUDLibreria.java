/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import DBConection.Conection;
import com.mycompany.librerias.Libros;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Jorge
 */
public class CRUDLibreria {

    static public int id;
    static public String name;
    static public double saldo;

    static public int logIn(String user, String pass) {

        String sql = "SELECT * FROM users WHERE name='" + user + "' AND passwd='" + pass + "' ";
        ResultSet rs = null;
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            con.commit();

            if (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                saldo = rs.getInt("saldo");
                return rs.getInt("rol");
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    static public void updateStock(String libro) {

        String sql = "UPDATE books SET stock = stock-1 WHERE title = '" + libro + "'; ";
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            st.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static public void insertPedido(int id_libro) {

        String sql = "INSERT INTO books_list (\"id_user\", \"id_book\") VALUES (" + id + "," + id_libro + "); ";
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            st.executeQuery(sql);

        } catch (SQLException e) {
            
        }

    }

    static public boolean updateSaldo(double precio) {

        if (saldo > precio) {
            String sql = "UPDATE users SET saldo = saldo-" + precio + " WHERE id = " + id + "; ";
            try {
                Connection con = Conection.getConection();
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                saldo -= precio;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
        static public void ingresarSaldo(double ingresarsaldo) {

            String sql = "UPDATE users SET saldo = saldo +" + ingresarsaldo + " WHERE id = " + id + ";";
            try {
                Connection con = Conection.getConection();
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                con.commit();
                saldo += ingresarsaldo;
                
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }
    static public void getSaldo() {

        String sql = "SELECT * FROM users WHERE id=" + id + ";";
        ResultSet rs = null;
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                saldo = rs.getInt("saldo");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public boolean transacion(ArrayList<Libros> libros) throws SQLException {
        Connection con = Conection.getConection();
        if (CRUDLibreria.saldo != 0.0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Carrito");
            alert.setContentText(libros.toString() + "\n¿Quieres realizar el pedido?");
            Optional<ButtonType> action = alert.showAndWait();
            for (Libros libro : libros) {
                CRUDLibreria.updateStock(libro.getTitle());
                 boolean bandera =CRUDLibreria.updateSaldo(libro.getPrice());
                 
                if(!bandera){
                    con.rollback();
                    Alert noMoney = new Alert(Alert.AlertType.INFORMATION);
                    noMoney.setHeaderText(null);
                    noMoney.setTitle("No hay Saldo");
                    noMoney.setContentText("Error al comprar no hay suficiciente saldo");
                    noMoney.show();
                    getSaldo();
                    return false;
                }
                CRUDLibreria.insertPedido(libro.getId());
                
                
            }
            if (action.get() == ButtonType.OK) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        }
        return false;

    }
    public static void modificar(int id, String title, String author, String ISBN,double price, int stock ){

            String sql = "UPDATE books SET  title='" +title + "', author='"+author+"', ibsn='"+ ISBN+"', price="+price+", stock="+stock+"   WHERE id = " + id + "; ";
            try {
                Connection con = Conection.getConection();
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                con.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

        

    }
        public static void insert(int id, String title, String author, String ISBN, double price, int stock ){

            String sql = "INSERT INTO books  VALUES ("+ id + ", '" +title + "', '"+author+"', '"+ ISBN+"', "+price+", "+stock+"); ";
            try {
                Connection con = Conection.getConection();
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                con.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            public static void eliminar(int id){

            String sql = "DELETE FROM books WHERE id= "+id;
            try {
                Connection con = Conection.getConection();
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                con.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

        

    }
}
