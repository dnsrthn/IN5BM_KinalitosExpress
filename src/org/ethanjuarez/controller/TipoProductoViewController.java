    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.controller;

import org.ethanjuarez.bean.TipoProducto;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Principal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
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
import javax.swing.JOptionPane;
import org.ethanjuarez.bean.Cliente;

/**
 * FXML Controller class
 *
 * @author Catherine
 */
public class TipoProductoViewController implements Initializable {

    private ObservableList<TipoProducto> listaTipoProductos;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;

    @FXML
    private TableView<TipoProducto> tblTtipoProductos;

    @FXML
    private TableColumn colCodTP;
    @FXML
    private TableColumn colDescripcionTP;
    @FXML
    private TextField txtcodTP;
    @FXML
    private TextField txtDescTP;
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
        cargarDatos();
    }

    public ObservableList<TipoProducto> getTipoProductos() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new TipoProducto(
                            result.getInt("codigoTipoProducto"),
                            result.getString("descripcion")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProductos = FXCollections.observableList(lista);
    }

    public void cargarDatos() {
        tblTtipoProductos.setItems(getTipoProductos());
        colCodTP.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcionTP.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public void seleccionarElemento() {

        txtcodTP.setText(String.valueOf(((TipoProducto) tblTtipoProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescTP.setText(((TipoProducto) tblTtipoProductos.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public void AgregarTP() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControlesTP();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                cancelarTP();
                break;
            case ACTUALIZAR:
                guardarTP();
                desactivarControlesTP();
                limpiarControlesTP();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    public void guardarTP() {
        TipoProducto register = new TipoProducto();
        register.setCodigoTipoProducto(Integer.parseInt(txtcodTP.getText()));
        register.setDescripcion(txtDescTP.getText());

        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?,?)}");
            procedure.setInt(1, register.getCodigoTipoProducto());
            procedure.setString(2, register.getDescripcion());
            procedure.execute();
            listaTipoProductos.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editaTP() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblTtipoProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControlesTP();
                    txtcodTP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizarTP();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgBuscar.setImage(new Image("/org/ethanjuarez/images/buscar.png"));
                desactivarControlesTP();
                limpiarControlesTP();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();

                break;
        }
     }
    public void actualizarTP() {
        // Verificar si se ha seleccionado un cliente
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?,?)}");
            TipoProducto register = (TipoProducto) tblTtipoProductos.getSelectionModel().getSelectedItem();

            register.setDescripcion(txtDescTP.getText());
            procedure.setInt(1, register.getCodigoTipoProducto());
            procedure.setString(2, register.getDescripcion());
            procedure.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void eliminarTP() {

        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControlesTP();
                limpiarControlesTP();
                btnAgregar.setText("Agregar");
                btnEditar.setText("Editar");
                btnEliminar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cancelarTP();
                break;
            default:
                if (tblTtipoProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este Tipo de Producto?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedure.setInt(1, ((TipoProducto) tblTtipoProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedure.execute();
                            listaTipoProductos.remove(tblTtipoProductos.getSelectionModel().getSelectedItem());
                            limpiarControlesTP();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Tipo de Producto para eliminar");
                }
                break;

        }
    }

    public void cancelarTP() {
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

     public void desactivarControlesTP() {
        txtcodTP.setEditable(false);
        txtDescTP.setEditable(false);
   
    }

    public void activarControlesTP() {
        txtcodTP.setEditable(true);
        txtDescTP.setEditable(true);

    }

    public void limpiarControlesTP() {
        txtcodTP.clear();
        txtDescTP.clear();

    }

}
