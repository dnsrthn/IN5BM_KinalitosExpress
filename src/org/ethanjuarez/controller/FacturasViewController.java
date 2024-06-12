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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.ethanjuarez.bean.Empleados;
import org.ethanjuarez.bean.Factura;
import org.ethanjuarez.system.Main;

public class FacturasViewController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private TableView<Factura> tblEmpleados;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNoFact;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTotalFact;
    @FXML
    private TextField txtFechaFact;
    @FXML
    private TableColumn colNocFact;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotalFact;
    @FXML
    private TableColumn colFechaFact;
    @FXML
    private TableColumn colClienteId;
    @FXML
    private TableColumn colCargoEmpleado;
    @FXML
    private ComboBox cmbCienteId;
    @FXML
    private ComboBox cmbCargoEmpleado;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private ImageView imgEliminar;

    public void menuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
