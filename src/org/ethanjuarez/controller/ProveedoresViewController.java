/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ethanjuarez.bean.Proveedores;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Principal;

/**
 * FXML Controller class
 *
 * @author Catherine
 */
public class ProveedoresViewController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;

    private enum Operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private Operaciones tipoDeOperaciones = Operaciones.NINGUNO;
    @FXML
    private TableView<Proveedores> tblProveedores;
    @FXML
    private Button btnRegresar;
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
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TextField txtNITProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtApellidoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colNITProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colApellidoProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoPrincipal;
    @FXML
    private TableColumn colPaginaWeb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void menuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new Proveedores(
                            result.getInt("codigoProveedor"),
                            result.getString("nitProveedores"),
                            result.getString("nombresProveedores"),
                            result.getString("apellidosProveedores"),
                            result.getString("direccionProveedor"),
                            result.getString("razonSocial"),
                            result.getString("contactoPrincipal"),
                            result.getString("paginaWeb")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedores"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedores"));
        colApellidoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedores"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
