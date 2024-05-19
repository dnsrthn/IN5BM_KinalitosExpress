/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.ethanjuarez.system.Principal;

/**
 *
 * @author User
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnProgramadorScreen;
    @FXML
    MenuItem btnTiposProd;
    @FXML
    MenuItem btnCompras;
    @FXML
    MenuItem btnProveedores;
    @FXML
    MenuItem btnCargoEmpleado;
    @FXML
    MenuItem btnProductos;
    @FXML
    MenuItem btnEmpleados;
    @FXML
    MenuItem btnFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void menuClientesView() {
        escenarioPrincipal.menuClientesView();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        if (event.getSource() == btnProgramadorScreen) {
            escenarioPrincipal.programadorView();
        }
        if (event.getSource() == btnTiposProd) {
            escenarioPrincipal.tipoProductosView();
        }
        if (event.getSource() == btnCompras) {
            escenarioPrincipal.comprasView();
        }
        if (event.getSource() == btnProveedores) {
            escenarioPrincipal.proveedoresView();
        }
        if (event.getSource() == btnProductos) {
            escenarioPrincipal.productosView();
        }
        if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.cargoEmpleadoView();
        }
        if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.empleadosView();
        }
        if (event.getSource() == btnFactura) {
            escenarioPrincipal.facturasView();
        }

    }
}
