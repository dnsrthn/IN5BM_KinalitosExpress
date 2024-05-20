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
import java.util.Date;
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
import javax.swing.JOptionPane;
import org.ethanjuarez.bean.Compras;
import org.ethanjuarez.bean.TipoProducto;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Principal;

/**
 * FXML Controller class
 *
 * @author Catherine
 */
public class ComprasViewController implements Initializable {

    private ObservableList<Compras> listaCompras;
    private Principal escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView<Compras> tblCompras;

    @FXML
    private TableColumn colNoDoc;
    @FXML
    private TableColumn colDateCompra;
    @FXML
    private TableColumn colDescCompra;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TextField txtNoDoc;
    @FXML
    private TextField txtDateCompra;
    @FXML
    private TextField txtDescCompra;
    @FXML
    private TextField txtTotal;
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

    public void menuPrincipalView() {
        escenarioPrincipal.menuPrincipalView();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
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
        cargarDatosComp();
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new Compras(
                            result.getInt("numeroDocumento"),
                            result.getString("fechaDocumento"),
                            result.getString("descripcion"),
                            result.getDouble("TotalDocumento")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
    }

    public void cargarDatosComp() {
        tblCompras.setItems(getCompras());
        colNoDoc.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colDateCompra.setCellValueFactory(new PropertyValueFactory<Compras, Date>("fechaDocumento"));
        colDescCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));
    }

    public void seleccionarElemento() {

        txtNoDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtDateCompra.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescCompra.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotal.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }

    public void AgregarComp() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControlesComp();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                tipoDeOperaciones = ComprasViewController.operaciones.ACTUALIZAR;
                cancelarComp();
                break;
            case ACTUALIZAR:
                guardarComp();
                desactivarControlesComp();
                limpiarControlesComp();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = ComprasViewController.operaciones.NINGUNO;
        }
    }

     public void guardarComp() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNoDoc.getText()));
//        registro.setFechaDocumento(txtDateCompra.getText());
        registro.setDescripcion(txtDescCompra.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotal.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarCompras(?,?,?,?);");
            procedimiento.setInt(1, registro.getNumeroDocumento());
//            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void editarComp() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControlesComp();
                    txtNoDoc.setEditable(false);
                    tipoDeOperaciones = ComprasViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
               actualizarComp();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgBuscar.setImage(new Image("/org/ethanjuarez/images/buscar.png"));
                desactivarControlesComp();
                limpiarControlesComp();
                tipoDeOperaciones = ComprasViewController.operaciones.NINGUNO;
                cargarDatosComp();

                break;
        }
    }

    public void actualizarComp() {
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompras(?,?,?)}");
//            Compras register = (Compras) tblCompras.getSelectionModel().getSelectedItem();
//
//            register.setFechaDocumento(txtDateCompra.getText());
//            register.setDescripcion(txtDescCompra.getText());
//            register.setTotalDocumento(txtTotal.getText());
//            procedure.setString(1, register.getFechaDocumento());
//            procedure.setString(2, register.getDescripcion());
////            procedure.getDouble(3, register.getTotalDocumento());
            procedure.execute();
        } catch (SQLException e) {
            e.printStackTrace();
  }

    }

    public void eliminarComp() {

        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControlesComp();
                limpiarControlesComp();
                btnAgregar.setText("Agregar");
                btnEditar.setText("Editar");
                btnEliminar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                tipoDeOperaciones = ComprasViewController.operaciones.NINGUNO;
                cancelarComp();
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta Compra?", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedure.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedure.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControlesComp();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una Compra para eliminar");
                }
                break;

        }
    }

    public void cancelarComp() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                btnReportes.setDisable(false);
                btnAgregar.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAgregar.setText("Agregar");
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                break;
        }
    }

    public void desactivarControlesComp() {
        txtNoDoc.setEditable(false);
        txtDateCompra.setEditable(false);
        txtDescCompra.setEditable(false);;
        txtTotal.setEditable(false);
    }

    public void activarControlesComp() {
        txtNoDoc.setEditable(true);
        txtDateCompra.setEditable(true);
        txtDescCompra.setEditable(true);;
        txtTotal.setEditable(true);
    }

    public void limpiarControlesComp() {
        txtNoDoc.clear();
        txtDateCompra.clear();
        txtDescCompra.clear();
        txtTotal.clear();

    }

}
