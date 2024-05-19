/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.bean;

public class DetalleFactura {
    private int codigoDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int numeroFactura;

    public DetalleFactura() {
    }
    
    

    public DetalleFactura(int codigoDetalleFactura, double precioUnitario, int cantidad, int numeroFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFactura = numeroFactura;
    }

    // getters y setters

    public int getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
}
