package com.example.entity;


public class Compra {

    private String clienteTipo;

    private double totalCompra;

    private int cantidadProductos;

    private double descuento = 0.0;

    public Compra(String clienteTipo, double descuento, int cantidadProductos, double totalCompra) {
        this.clienteTipo = clienteTipo;
        this.descuento = descuento;
        this.cantidadProductos = cantidadProductos;
        this.totalCompra = totalCompra;
    }

    public Compra() {
    }

    public String getClienteTipo() {
        return clienteTipo;
    }

    public void setClienteTipo(String clienteTipo) {
        this.clienteTipo = clienteTipo;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotalCondescuento() {
        return totalCompra - (totalCompra * descuento/100);
    }
}
