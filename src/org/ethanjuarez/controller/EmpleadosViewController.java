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
import org.ethanjuarez.bean.Compras;
import org.ethanjuarez.bean.Empleados;
import org.ethanjuarez.system.Main;

/**
 * FXML Controller class
 *
 * @author Catherine
 */
public class EmpleadosViewController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private TableView<Empleados> tblEmpleados;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoEmp;
    @FXML
    private TextField txtNombresEmp;
    @FXML
    private TextField txtApellidosEmp;
    @FXML
    private TextField txtSueldoEmp;
    @FXML
    private TextField txtDireccionEmp;
    @FXML
    private TextField txtTurnoEmp;
    @FXML
    private TableColumn colCodigoEmp;
    @FXML
    private TableColumn colNombresEmp;
    @FXML
    private TableColumn colApellidosEmp;
    @FXML
    private TableColumn colSueldoEmp;
    @FXML
    private TableColumn colDireccionEmp;
    @FXML
    private TableColumn colTurnoEmp;
    @FXML
    private TableColumn colCargoEmpleado;
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
