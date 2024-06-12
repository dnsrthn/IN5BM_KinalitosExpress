/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.ethanjuarez.bean.Compras;
import org.ethanjuarez.bean.DetalleCompra;
import org.ethanjuarez.bean.Productos;
import org.ethanjuarez.system.Main;

/**
 * FXML Controller class
 *
 * @author Catherine
 */
public class DetalleComprasController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private enum operaciones {AGREGAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <DetalleCompra> listaDetalleCompras;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Compras> listaCompras;
    @FXML
    private Button btnAgregar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private TableView tblDetalleCompra;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colCostoU;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNoD;
    @FXML
    private TextField txtCostoU;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtCodigo;
    @FXML
    private ComboBox cmbNoD;
    @FXML
    private ComboBox cmbCodigoP;
    
    @FXML private MenuItem btnRegresar;
    @FXML private MenuItem btnMenuCompras;
    private Main escenarioPrincipal;   
      
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        cargarDatos();
//        cmbCodigoP.setItems(getProductos());
//        cmbNoD.setItems(getCompras());
//    }  
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
