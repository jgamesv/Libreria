/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librerias;

/**
 *
 * @author Jorge
 */
public class Libros {
    private int id;
    private String title;
    private String author;
    private double price;
    private String ISBN;
    private int stock;

    public Libros( int id, String title, String author, double price, String ISBN,int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.ISBN = ISBN;
        this.stock = stock;
    }

    public Libros() {
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "El libro " + title + " de " + author + " por " + price + " €";
    }
    
    
}
