/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librerias;

import CRUD.CRUDLibreria;
import DBConection.Conection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jorge
 */
public class AdminController {
    
    @FXML
    Label userName;
    @FXML
    Label sal;
    @FXML
    Label leg;
    @FXML
    TextField saldoText;
    @FXML
    private TableView<Libros> table;
    @FXML
    private TableColumn<Libros, String> tctitles;
    @FXML
    private TableColumn<Libros, String> tcauthors;
    @FXML
    private TableColumn<Libros, String> tcsisbn;
    @FXML
    private TableColumn<Libros, Double> tcprice;
    @FXML
    private TableColumn<Libros, Integer> tcid;
    @FXML
    private TableColumn<Libros, Integer> tcstock;
    private ArrayList<Libros> selcionados = new ArrayList();

    @FXML
    private void initialize() {
        userName.setText("Administrador: "+CRUDLibreria.name);
        this.sal.setText("Saldo: "+CRUDLibreria.saldo+"€");
        getProducts();
    }

    @FXML
    private void switchToPrimary() throws IOException {
        this.selcionados.clear();
        App.setRoot("primary");

    }

    @FXML
    private void getProducts() {
        String sql = "SELECT * FROM books ";
        ResultSet rs = null;
        ObservableList<Libros> obs = FXCollections.observableArrayList();
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            con.commit();
            while (rs.next()) {
                if (rs.getInt("stock") > 0) {
                    obs.add(new Libros(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getDouble("price"), rs.getString("ibsn"),rs.getInt("stock")));
                    this.table.setItems(obs);
                    this.tctitles.setCellValueFactory(new PropertyValueFactory("title"));
                    this.tcauthors.setCellValueFactory(new PropertyValueFactory("author"));
                    this.tcsisbn.setCellValueFactory(new PropertyValueFactory("ISBN"));
                    this.tcprice.setCellValueFactory(new PropertyValueFactory("price"));
                    this.tcid.setCellValueFactory(new PropertyValueFactory("id"));
                    this.tcstock.setCellValueFactory(new PropertyValueFactory("stock"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la carga de datos");
        }

    }

    @FXML
    private void añadirCarrito() {
        if (this.table != null) {
            List<Libros> tablaPokemon = this.table.getSelectionModel().getSelectedItems();
            final Libros libro = tablaPokemon.get(0);
            this.selcionados.add(libro);
            String num = " " + this.selcionados.size() + " ";
            leg.setText(num.trim());
        }

    }

    @FXML
    private void carrito() throws SQLException {
       boolean bandera = CRUDLibreria.transacion(selcionados);
       if(bandera){
        this.selcionados.clear();
        leg.setText("0");
       }
    }
    @FXML
    private void setSaldo(){
        
        CRUDLibreria.ingresarSaldo(Double.parseDouble(saldoText.getText()));
        sal.setText("Saldo: "+CRUDLibreria.saldo+"€");
    }
}