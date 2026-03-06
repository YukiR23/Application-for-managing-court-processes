package com.tribunal.controller;

import com.tribunal.dto.ProcesDTO;
import com.tribunal.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
/** Clasa pentru gestionarea meniului principal al aplicației.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class MenuController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        openProcese();
    }

    @FXML
    private void openProcese() {
        SceneLoader.loadInto("/procese.fxml", contentArea);
    }

    public void openDetaliiProces(ProcesDTO p) {
        ProcesDetaliiController.setProces(p);
        SceneLoader.loadInto("/proces_detalii.fxml", contentArea);
    }
    @FXML
    private void openSedinteToate() {
        SceneLoader.loadInto("/sedinte_toate.fxml", contentArea);
    }

    @FXML
    private void handleLogout() {
        SceneLoader.loadRoot("login.fxml",
                (javafx.stage.Stage) contentArea.getScene().getWindow());
    }
}
