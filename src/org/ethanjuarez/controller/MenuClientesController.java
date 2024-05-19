package org.ethanjuarez.controller;

import org.ethanjuarez.bean.Cliente;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Principal;
import java.net.URL;
import java.sql.Connection;
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
import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class MenuClientesController implements Initializable {

    private ObservableList<Cliente> listaClientes;
    private Principal escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView<Cliente> tblCliente;

    @FXML
    private TableColumn colClienteId;
    @FXML
    private TableColumn colNombresCliente;
    @FXML
    private TableColumn colApellidoCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colNitCliente;
    @FXML
    private TableColumn colTelefonoCliente;
    @FXML
    private TableColumn colCorreoCliente;
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
    private TextField txtIdC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtNitC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtCorreoC;
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

    public ObservableList<Cliente> getCliente() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new Cliente(
                            result.getInt("clienteID"),
                            result.getString("nombreClientes"),
                            result.getString("apellidoClientes"),
                            result.getString("nitClientes"),
                            result.getString("telefonoClientes"),
                            result.getString("direccionClientes"),
                            result.getString("correoClientes")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);
    }

    public void cargarDatos() {
        tblCliente.setItems(getCliente());
        colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteID"));
        colNombresCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreClientes"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidoClientes"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nitClientes"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefonoClientes"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccionClientes"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correoClientes"));
    }

    public void seleccionarElemento() {

        txtIdC.setText(String.valueOf(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getClienteID()));
        txtNombreC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getNombreClientes());
        txtApellidoC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getApellidoClientes());
        txtNitC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getNitClientes());
        txtDireccionC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getDireccionClientes());
        txtTelefonoC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getTelefonoClientes());
        txtCorreoC.setText(((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getCorreoClientes());
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void guardar() {
        Cliente register = new Cliente();
        register.setClienteID(Integer.parseInt(txtIdC.getText()));
        register.setNombreClientes(txtNombreC.getText());
        register.setApellidoClientes(txtApellidoC.getText());
        register.setDireccionClientes(txtDireccionC.getText());
        register.setNitClientes(txtNitC.getText());
        register.setTelefonoClientes(txtTelefonoC.getText());
        register.setCorreoClientes(txtCorreoC.getText());
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            procedure.setInt(1, register.getClienteID());
            procedure.setString(2, register.getNombreClientes());
            procedure.setString(3, register.getApellidoClientes());
            procedure.setString(4, register.getDireccionClientes());
            procedure.setString(5, register.getNitClientes());
            procedure.setString(6, register.getTelefonoClientes());
            procedure.setString(7, register.getCorreoClientes());
            procedure.execute();
            listaClientes.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControles();
                    txtIdC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgBuscar.setImage(new Image("/org/ethanjuarez/images/buscar.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();

                break;
        }
    }

    public void actualizar() {
        // Verificar si se ha seleccionado un cliente
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarClientes(?,?,?,?,?,?,?)}");
            Cliente register = (Cliente) tblCliente.getSelectionModel().getSelectedItem();

            register.setNombreClientes(txtNombreC.getText());
            register.setNitClientes(txtNitC.getText());
            register.setApellidoClientes(txtApellidoC.getText());
            register.setTelefonoClientes(txtTelefonoC.getText());
            register.setDireccionClientes(txtDireccionC.getText());
            register.setCorreoClientes(txtCorreoC.getText());
            procedure.setInt(1, register.getClienteID());
            procedure.setString(2, register.getNitClientes());
            procedure.setString(3, register.getNombreClientes());
            procedure.setString(4, register.getApellidoClientes());
            procedure.setString(5, register.getDireccionClientes());
            procedure.setString(6, register.getTelefonoClientes());
            procedure.setString(7, register.getCorreoClientes());
            procedure.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {

        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEditar.setText("Editar");
                btnEliminar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cancelar();
                break;
            default:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar al cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedure.setInt(1, ((Cliente) tblCliente.getSelectionModel().getSelectedItem()).getClienteID());
                            procedure.execute();
                            listaClientes.remove(tblCliente.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para eliminar");
                }
                break;

        }
    }

    public void cancelar() {
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

    public void desactivarControles() {
        txtIdC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtNitC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtCorreoC.setEditable(false);
    }

    public void activarControles() {
        txtIdC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtNitC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtCorreoC.setEditable(true);
    }

    public void limpiarControles() {
        txtIdC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtNitC.clear();
        txtTelefonoC.clear();
        txtCorreoC.clear();
    }

}
