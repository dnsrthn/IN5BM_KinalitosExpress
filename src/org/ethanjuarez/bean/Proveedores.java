/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.bean;

public class Proveedores {

    private int codigoProveedor;
    private String nitProveedores;
    private String nombresProveedores;
    private String apellidosProveedores;
    private String direccionProveedor;
    private String razonSocial;
    private String contactoPrincipal;
    private String paginaWeb;

    public Proveedores() {
    }

    public Proveedores(int codigoProveedor, String nitProveedores, String nombresProveedores, String apellidosProveedores, String direccionProveedor, String razonSocial, String contactoPrincipal, String paginaWeb) {
        this.codigoProveedor = codigoProveedor;
        this.nitProveedores = nitProveedores;
        this.nombresProveedores = nombresProveedores;
        this.apellidosProveedores = apellidosProveedores;
        this.direccionProveedor = direccionProveedor;
        this.razonSocial = razonSocial;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNitProveedores() {
        return nitProveedores;
    }

    public void setNitProveedores(String nitProveedores) {
        this.nitProveedores = nitProveedores;
    }

    public String getNombresProveedores() {
        return nombresProveedores;
    }

    public void setNombresProveedores(String nombresProveedores) {
        this.nombresProveedores = nombresProveedores;
    }

    public String getApellidosProveedores() {
        return apellidosProveedores;
    }

    public void setApellidosProveedores(String apellidosProveedores) {
        this.apellidosProveedores = apellidosProveedores;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

   
}
