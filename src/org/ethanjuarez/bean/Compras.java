/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.bean;

import java.util.Date;

public class Compras {

    private int numeroDocumento;
    private Date fechaDocumento;
    private String descripcion;
    private double totalDocumento;

    // Constructor
    
    public Compras() {
    }

    public Compras(int numeroDocumento, Date fechaDocumento, String descripcion, double totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    // Getters y Setters
    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }
}
