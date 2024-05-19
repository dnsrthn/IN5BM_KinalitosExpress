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
import javax.swing.JOptionPane;
import org.ethanjuarez.bean.CargoEmpleado;
import org.ethanjuarez.db.Conexion;
import org.ethanjuarez.system.Principal;

public class CargoEmpleadoViewController implements Initializable {

    private ObservableList<CargoEmpleado> listaCargoEmpleado;
    private Principal escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView<CargoEmpleado> tblCargoEmpleado;
    @FXML
    private TableColumn colCodigoCarg;
    @FXML
    private TableColumn colNombreCargo;
    @FXML
    private TableColumn colDescCarg;
    @FXML
    private TextField txtCodigoCarg;
    @FXML
    private TextField txtNombreCargo;
    @FXML
    private TextField txtDescCarg;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregarCE;
    @FXML
    private Button btnEditarCE;
    @FXML
    private Button btnEliminarCE;
    @FXML
    private Button btnReportesCE;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosCE();
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleado()}");
            ResultSet result = procedure.executeQuery();
            {
                while (result.next()) {
                    lista.add(new CargoEmpleado(
                            result.getInt("codigoCargoEmpleado"),
                            result.getString("nombreCargo"),
                            result.getString("descripcionCargo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargoEmpleado = FXCollections.observableList(lista);
    }

    public void cargarDatosCE() {
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCodigoCarg.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescCarg.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));

    }

    public void seleccionarElemento() {

        txtCodigoCarg.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCargo.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo()));
        txtDescCarg.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }

  
    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCE.setText("Guardar");
                btnEliminarCE.setText("Cancelar");
                btnEditarCE.setDisable(true);
                btnReportesCE.setDisable(true);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                tipoDeOperaciones = CargoEmpleadoViewController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCE.setText("Agregar");
                btnEliminarCE.setText("Eliminar");
                btnEditarCE.setDisable(false);
                btnReportesCE.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/ethanjuarez/images/waste.png"));
                tipoDeOperaciones = CargoEmpleadoViewController.operaciones.NINGUNO;
        }
    }
  public void guardar() {
        CargoEmpleado register = new CargoEmpleado();
        register.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCarg.getText()));
        register.setNombreCargo(txtNombreCargo.getText());
        register.setDescripcionCargo(txtDescCarg.getText());

        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedure.setInt(1, register.getCodigoCargoEmpleado());
            procedure.setString(2, register.getNombreCargo());
            procedure.setString(3, register.getDescripcionCargo());

            procedure.execute();
            listaCargoEmpleado.add(register);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCE.setText("Actualizar");
                    btnReportesCE.setText("Cancelar");
                    btnAgregarCE.setDisable(true);
                    btnEliminarCE.setDisable(true);
                    imgEditar.setImage(new Image("/org/ethanjuarez/images/reload.png"));
                    imgBuscar.setImage(new Image("/org/ethanjuarez/images/cancelar.png"));
                    activarControles();
                    txtCodigoCarg.setEditable(false);
                    tipoDeOperaciones = CargoEmpleadoViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCE.setText("Editar");
                btnReportesCE.setText("Reportes");
                btnAgregarCE.setDisable(false);
                btnEliminarCE.setDisable(false);
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                imgBuscar.setImage(new Image("/org/ethanjuarez/images/buscar.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = CargoEmpleadoViewController.operaciones.NINGUNO;
                cargarDatosCE();

                break;
        }
    }
    public void actualizar() {
        // Verificar si se ha seleccionado un cliente
        try {
            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargoEmpleado(?,?)}");
            CargoEmpleado register = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();

            register.setNombreCargo(txtNombreCargo.getText());
            register.setDescripcionCargo(txtDescCarg.getText());
            procedure.setInt(1, register.getCodigoCargoEmpleado());
            procedure.setString(2, register.getNombreCargo());
            procedure.setString(3, register.getDescripcionCargo());
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
                btnAgregarCE.setText("Agregar");
                btnEditarCE.setText("Editar");
                btnEliminarCE.setDisable(false);
                btnReportesCE.setDisable(false);
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                imgEditar.setImage(new Image("/org/ethanjuarez/images/editar.png"));
                tipoDeOperaciones = CargoEmpleadoViewController.operaciones.NINGUNO;
                cancelar();
                break;
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este Crago de Empleado?", "Eliminar Cargo de Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedure = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedure.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedure.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cargo de empleado para eliminar");
                }
                break;

        }
    }

    public void cancelar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                btnReportesCE.setDisable(false);
                btnAgregarCE.setDisable(false);
                btnEditarCE.setDisable(false);
                btnEliminarCE.setDisable(false);
                btnAgregarCE.setText("Agregar");
                imgAgregar.setImage(new Image("/org/ethanjuarez/images/agregar.png"));
                break;
        }
    }
    public void desactivarControles() {
        txtCodigoCarg.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescCarg.setEditable(false);

    }

    public void activarControles() {
        txtCodigoCarg.setEditable(true);
        txtNombreCargo.setEditable(true);
        txtDescCarg.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoCarg.clear();
        txtNombreCargo.clear();
        txtDescCarg.clear();

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
