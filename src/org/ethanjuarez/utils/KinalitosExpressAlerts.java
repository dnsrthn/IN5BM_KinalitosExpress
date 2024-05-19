/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethanjuarez.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Catherine
 */
public class KinalitosExpressAlerts {

    private static KinalitosExpressAlerts instance;

    private KinalitosExpressAlerts() {
    }

    public static KinalitosExpressAlerts getInstance() {
        if (instance == null) {
            instance = new KinalitosExpressAlerts();
        }
        return instance;
    }

    public void mostrarAlerta(int messageCode) {
        if (messageCode == 400) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Se regitro el cliente correctamente");
            alert.showAndWait();
        }
    }
}
