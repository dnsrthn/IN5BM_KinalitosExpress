/*
* Nombre completo:Ethan Jared Alberto Juarez Pinto
* Fecha de creacion: 05/04/2024
* Fecha de Modificacion: 8/04/2024 10/04/2024 11/04
 */
package org.ethanjuarez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.ethanjuarez.controller.CargoEmpleadoViewController;
import org.ethanjuarez.controller.ComprasViewController;
import org.ethanjuarez.controller.EmpleadosViewController;
import org.ethanjuarez.controller.FacturasViewController;
import org.ethanjuarez.controller.MenuClientesController;
import org.ethanjuarez.controller.MenuPrincipalController;
import org.ethanjuarez.controller.ProductosViewController;
import org.ethanjuarez.controller.ProgramadorViewController;
import org.ethanjuarez.controller.ProveedoresViewController;
import org.ethanjuarez.controller.TelefonoProveedoresViewController;
import org.ethanjuarez.controller.TipoProductoViewController;

/**
 *
 * @author informatica
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/ethanjuarez/views/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();
        /* menuClientesView();
        programadorScreen();*/

        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Principal.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 823, 464);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            // System.out.println(e.getMessage()); 
            e.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 1139, 630);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            //System.out.println(e.getMessage()); 
            e.printStackTrace();
        }

    }

    public void programadorView() {
        try {
            ProgramadorViewController programadorView = (ProgramadorViewController) cambiarEscena("ProgramadorView.fxml", 823, 464);
            programadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void tipoProductosView() {
        try {
            TipoProductoViewController tipoProductoView = (TipoProductoViewController) cambiarEscena("TipoProductoView.fxml", 927, 515);
            tipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void comprasView() {
        try {
            ComprasViewController comprasView = (ComprasViewController) cambiarEscena("ComprasView.fxml", 986, 555);
            comprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void productosView() {
        try {
            ProductosViewController productosView = (ProductosViewController) cambiarEscena("ProductosView.fxml", 1173, 660);
            productosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void proveedoresView() {
        try {
            ProveedoresViewController proveedoresView = (ProveedoresViewController) cambiarEscena("ProveedoresView.fxml", 1139, 630);
            proveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void empleadosView() {
        try {
            EmpleadosViewController empleadosView = (EmpleadosViewController) cambiarEscena("EmpleadosView.fxml", 1139, 660);
            empleadosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void facturasView() {
        try {
            FacturasViewController facturasView = (FacturasViewController) cambiarEscena("FacturasView.fxml", 854, 483);
            facturasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void telefonoProveedoresView() {
        try {
            TelefonoProveedoresViewController telefonoProveedoresView = (TelefonoProveedoresViewController) cambiarEscena("telefonoProveedoresView.fxml", 647, 375);
            telefonoProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void cargoEmpleadoView() {
        try {
            CargoEmpleadoViewController cargoEmpleadoView = (CargoEmpleadoViewController) cambiarEscena("CargoEmpleadoView.fxml", 986, 555);
            cargoEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
