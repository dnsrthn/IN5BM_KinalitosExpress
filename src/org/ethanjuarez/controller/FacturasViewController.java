/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.ethanjuarez.bean.Cliente;
import org.ethanjuarez.bean.Empleados;
import org.ethanjuarez.bean.Factura;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.reports.GenerarReportes;
import org.ethanjuarez.system.Main;

/**
 *
 * @author gaber
 */
public class FacturasViewController implements Initializable {
    private enum operaciones {AGREGAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Factura> listaFacturas;
    private ObservableList <Cliente> listaClientes;
    private ObservableList <Empleados> listaEmpleados;
    @FXML private MenuItem btnRegresar;
    @FXML private MenuItem btnDetalleFactura;
    private Main escenarioPrincipal;
    @FXML
    private TableView tblFacturas;
    @FXML
    private TableColumn colNumero;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colCliente;
    @FXML
    private TableColumn colEmpleado;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtEstado;
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
    private TextField txtFecha;
    @FXML
    private ComboBox cmbClientes;
    @FXML
    private ComboBox cmbEmpleados;
    
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbClientes.setItems(getClientes());
        cmbEmpleados.setItems(getEmpleados());
    } 
    
    public void seleccionarElemento(){
        txtNumero.setText(String.valueOf(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstado.setText(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getEstado());
        txtFecha.setText(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getFechaFactura());
        cmbClientes.getSelectionModel().select(buscarCliente(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbEmpleados.getSelectionModel().select(buscarEmpleado(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
    }
    
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarClientes(?);");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("codigoCliente"),
                                             registro.getString("nombresCliente"),
                                             registro.getString("apellidosCliente"),
                                             registro.getString("nitClientes"),
                                             registro.getString("telefonoCliente"),
                                             registro.getString("direccionCliente"),
                                             registro.getString("correoCliente")
            
            );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public Empleados buscarEmpleado(int codigoEmpleado){
        Empleados resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarEmpleados(?);");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                             registro.getString("nombresEmpleado"),
                                             registro.getString("apellidosEmpleado"),
                                             registro.getDouble("sueldo"),
                                             registro.getString("direccion"),
                                             registro.getString("turno"),
                                             registro.getInt("codigoCargoEmpleado")
            
            );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public void cargarDatos(){
        tblFacturas.setItems(getFacturas());
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
    }
    
    public ObservableList<Factura> getFacturas(){
        ArrayList<Factura> lista = new ArrayList<Factura>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarFacturas();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFacturas = FXCollections.observableList(lista);    
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
    
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmpleados();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEmpleados = FXCollections.observableList(lista);    
    }
    
    public void agregar() {
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
                limpiarControles();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEditar.setText("Editar");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnReportes.setDisable(false);
                btnEditar.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                cargarDatos();
                break;
        }
    }
    
    public void guardar() {
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumero.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setFechaFactura(txtFecha.getText());
        registro.setCodigoCliente(((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados)cmbEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarFactura(?,?,?,?,?);");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();
            listaFacturas.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblFacturas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControles();
                    txtNumero.setEditable(false);
                    tipoDeOperaciones = FacturasViewController.operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una factura para editar");
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
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarFacturas(?,?,?,?,?);");
            Factura registro = (Factura)tblFacturas.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstado.getText());
            registro.setFechaFactura(txtFecha.getText());
            registro.setCodigoCliente(((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Empleados)cmbEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar() {

        switch(tipoDeOperaciones){
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
                if(tblFacturas.getSelectionModel().getSelectedItem()  != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar la factura?", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta  == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarFacturas(?);");
                            procedimiento.setInt(1, ((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaFacturas.remove(tblFacturas.getSelectionModel().getSelectedItem());
                            limpiarControles(); 
                        }catch(Exception e){
                            e.printStackTrace();
                        } 
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una factura para eliminar");
                }
                break;
                
        }
    }
    
    public void reportes() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporteFacturas();
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
    
    public void imprimirReporteFacturas(){
        Map parametros = new HashMap();
        int facId = Integer.valueOf(((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());
        parametros.put(facId, facId);
        GenerarReportes.mostrarReportes("reportFactura1.jasper", "Factura", parametros);
        
    }
    

    public void activarControles(){
        txtNumero.setEditable(true);
        txtEstado.setEditable(true);
        txtFecha.setEditable(true);
        cmbClientes.setDisable(false);
        cmbEmpleados.setDisable(false);
    }
    
    public void desactivarControles(){
        txtNumero.setEditable(false);
        txtEstado.setEditable(false);
        txtFecha.setEditable(false);
        cmbClientes.setDisable(true);
        cmbEmpleados.setDisable(true);
    }
    
    public void limpiarControles(){
        txtNumero.clear();
        txtEstado.clear();
        txtFecha.clear();
        cmbClientes.getSelectionModel().clearSelection();
        cmbEmpleados.getSelectionModel().clearSelection();
    }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
           
    
}
