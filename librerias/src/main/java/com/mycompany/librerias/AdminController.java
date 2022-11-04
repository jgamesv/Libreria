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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    Button nuevo;
    @FXML
    Button aceptar;
    @FXML
    Button cancelar;
    @FXML
    TextField id;
    @FXML
    TextField isbn;
    @FXML
    TextField titulo;
    @FXML
    TextField autor;
    @FXML
    TextField price;
    @FXML
    TextField stock;

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
    @FXML
    private ComboBox tipoDato;
    @FXML
    private TextField palabraBuscar;

    @FXML
    private void initialize() {
        userName.setText("Administrador: " + CRUDLibreria.name);
        this.tipoDato.setItems(FXCollections.observableArrayList("Titulo", "Autor", "ISBN"));
        this.tipoDato.setValue("Titulo");
        disabled();
        getProducts();
        aceptar.setVisible(false);
        cancelar.setVisible(false);

    }

    @FXML
    private void switchToPrimary() throws IOException {
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
                    obs.add(new Libros(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getDouble("price"), rs.getString("ibsn"), rs.getInt("stock")));
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
    private void mostrardatos() {
        if (this.table != null) {
            List<Libros> tablaPokemon = this.table.getSelectionModel().getSelectedItems();
            final Libros libro = tablaPokemon.get(0);
            String idStr = " " + libro.getId() + " ";
            String priceStr = " " + libro.getPrice() + " ";
            String stockStr = " " + libro.getStock() + " ";

            this.id.setText(idStr.trim());
            this.titulo.setText(libro.getTitle());
            this.autor.setText(libro.getAuthor());
            this.isbn.setText(libro.getISBN());
            this.price.setText(priceStr.trim());
            this.stock.setText(stockStr.trim());
            enabled();
            id.setDisable(true);
        }
        

    }
    @FXML
    private void buscar() {
        String sql = "SELECT * FROM books";
        switch (tipoDato.getValue().toString()) {
            case "Titulo":
                sql = "SELECT * FROM books WHERE title = '" + palabraBuscar.getText() + "';";
                break;
            case "Autor":
                sql = "SELECT * FROM books WHERE author = '" + palabraBuscar.getText() + "';";
                break;
            case "ISBN":
                sql = "SELECT * FROM books WHERE ibsn = '" + palabraBuscar.getText() + "';";
                break;
        }
               ResultSet rs = null;
        ObservableList<Libros> obs = FXCollections.observableArrayList();
        try {
            Connection con = Conection.getConection();
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            con.commit();
            while (rs.next()) {
                if (rs.getInt("stock") > 0) {
                    obs.add(new Libros(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getDouble("price"), rs.getString("ibsn"), rs.getInt("stock")));
                    this.table.setItems(obs);
                    this.tctitles.setCellValueFactory(new PropertyValueFactory("title"));
                    this.tcauthors.setCellValueFactory(new PropertyValueFactory("author"));
                    this.tcsisbn.setCellValueFactory(new PropertyValueFactory("ISBN"));
                    this.tcprice.setCellValueFactory(new PropertyValueFactory("price"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la carga de datos");
        }

    }
    private void disabled() {
        this.id.setDisable(true);
        this.titulo.setDisable(true);
        this.autor.setDisable(true);
        this.isbn.setDisable(true);
        this.price.setDisable(true);
        this.stock.setDisable(true);
    }

    private void enabled() {
        this.id.setDisable(false);
        this.titulo.setDisable(false);
        this.autor.setDisable(false);
        this.isbn.setDisable(false);
        this.price.setDisable(false);
        this.stock.setDisable(false);
    }

    private void clear() {
        this.id.clear();
        this.titulo.clear();
        this.autor.clear();
        this.isbn.clear();
        this.price.clear();
        this.stock.clear();
    }

    @FXML
    private void nueva() {
        clear();
        enabled();
        aceptar.setVisible(true);
        cancelar.setVisible(true);
        nuevo.setVisible(false);
    }

    @FXML
    private void modify() {
        CRUDLibreria.modificar(Integer.parseInt(this.id.getText()), this.titulo.getText(), this.autor.getText(), this.isbn.getText(), Double.parseDouble(this.price.getText()), Integer.parseInt(this.stock.getText()));
        clear();
        disabled();
        this.getProducts();

    }

    @FXML
    private void insert() {

        CRUDLibreria.insert(Integer.parseInt(this.id.getText()), this.titulo.getText(), this.autor.getText(), this.isbn.getText(), Double.parseDouble(this.price.getText()), Integer.parseInt(this.stock.getText()));
        clear();
        disabled();
        this.getProducts();

    }

    @FXML
    private void cancel() {
        clear();
        disabled();
        nuevo.setVisible(true);
        aceptar.setVisible(false);
        cancelar.setVisible(false);


    }

    @FXML
    private void deleted() {
        CRUDLibreria.eliminar(Integer.parseInt(this.id.getText()));
        clear();
        disabled();
        this.getProducts();
    }

}
