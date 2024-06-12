package org.ethanjuarez.controller;

import org.ethanjuarez.bean.Cliente;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Main;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.ethanjuarez.reports.GenerarReportes;

/**
 *
 * @author
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Cliente> listaClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtNitC;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnRegresar;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombresCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidosCliente"));
        colNitC.setCellValueFactory(new PropertyValueFactory<>("nitClientes"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));

    }

    public void seleccionarElemento() {

        txtCodigoC.setText(String.valueOf(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombreC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidoC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtNitC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getNitClientes());
        txtDireccionC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
    }

    public ObservableList<Cliente> getClientes() {

        ArrayList<Cliente> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarClientes();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("nitClientes"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                btnReportes.setDisable(true);
                btnEditar.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/eliminar.png"));
                btnReportes.setDisable(false);
                btnEditar.setDisable(false);

                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Cliente register = new Cliente();
        register.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        register.setNombresCliente(txtNombreC.getText());
        register.setApellidosCliente(txtApellidoC.getText());
        register.setNitClientes(txtNitC.getText());
        register.setTelefonoCliente(txtTelefonoC.getText());
        register.setDireccionCliente(txtDireccionC.getText());
        register.setCorreoCliente(txtCorreoC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarClientes(?,?,?,?,?,?,?);");
            procedimiento.setInt(1, register.getCodigoCliente());
            procedimiento.setString(2, register.getNombresCliente());
            procedimiento.setString(3, register.getApellidosCliente());
            procedimiento.setString(4, register.getNitClientes());
            procedimiento.setString(5, register.getDireccionCliente());
            procedimiento.setString(6, register.getTelefonoCliente());
            procedimiento.setString(7, register.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(register);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
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
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarClientes(?,?,?,?,?,?,?)");
            Cliente registro = (Cliente) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNitClientes(txtNitC.getText());
            registro.setNombresCliente(txtNombreC.getText());
            registro.setApellidosCliente(txtApellidoC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNitClientes());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {

        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar al cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarClientes(?)");
                            procedimiento.setInt(1, ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
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

    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporteClientes();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgBuscar.setImage(new Image("/org/ethanjuarez/images/buscar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

        }
    }

    public void imprimirReporteClientes() {
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("ReporteClientes.jasper", "Reporte Clientes", parametros);

    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtNitC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtNitC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtNitC.clear();
        txtTelefonoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
