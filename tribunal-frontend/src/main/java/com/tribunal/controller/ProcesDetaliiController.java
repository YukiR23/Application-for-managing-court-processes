package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.JudecatorDTO;
import com.tribunal.dto.ProcurorDTO;
import com.tribunal.dto.ProcesDTO;
import com.tribunal.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.Map;
import java.util.Objects;
/** Clasa pentru afișarea detaliilor unui proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ProcesDetaliiController {

    private static ProcesDTO proces;

    public static void setProces(ProcesDTO p) {
        proces = p;
    }

    @FXML private Label lblNrDosar;
    @FXML private Label lblJudecator;
    @FXML private Label lblProcuror;

    @FXML private StackPane tabContent;

    private final ApiClient api = new ApiClient();

    @FXML
    public void initialize() {
        if (proces == null) return;

        lblNrDosar.setText("Dosar: " + proces.getNrDosar());

        loadJudecator();
        loadProcuror();

        openSedinte();
    }

    private void loadJudecator() {
        Map<String, Object> j =
                api.getJudecatorProces(proces.getIdProces());

        if (j == null || j.isEmpty()) {
            lblJudecator.setText("Judecător:");
            return;
        }

        lblJudecator.setText(
                "Judecător: " +
                        j.get("nume") + " " +
                        j.get("prenume") +
                        " (" + j.get("sectie") + ")"
        );
    }

    private void loadProcuror() {
        Map<String, Object> p =
                api.getProcurorProces(proces.getIdProces());

        if (p == null || p.isEmpty()) {
            lblProcuror.setText("Procuror:");
            return;
        }

        lblProcuror.setText(
                "Procuror: " +
                        p.get("nume") + " " +
                        p.get("prenume") +
                        " (" + p.get("parchet") + ")"
        );
    }

    @FXML
    public void openSedinte() {
        if (proces == null || proces.getIdProces() == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Selectează un proces valid!")
                    .show();
            return;
        }

        SedintaController.setProcesId(proces.getIdProces());
        SceneLoader.loadInto("/sedinte.fxml", tabContent);
    }


    @FXML
    private void openParti() {
        ParteController.setProcesId(proces.getIdProces());
        SceneLoader.loadInto("/parti.fxml", tabContent);
    }

    @FXML
    public void openHotarari() {
        if (proces == null || proces.getIdProces() == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Selectează un proces valid!")
                    .show();
            return;
        }

        HotarareController.setProcesId(proces.getIdProces());
        SceneLoader.loadInto("/hotarari.fxml", tabContent);
    }


}
