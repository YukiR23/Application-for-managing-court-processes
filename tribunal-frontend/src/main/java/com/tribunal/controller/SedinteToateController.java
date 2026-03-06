package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.SedintaToateDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;
/** Clasa pentru afișarea și filtrarea tuturor ședințelor.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class SedinteToateController {

    @FXML private TableView<SedintaToateDTO> table;

    @FXML private TableColumn<SedintaToateDTO, String> colData;
    @FXML private TableColumn<SedintaToateDTO, String> colOra;
    @FXML private TableColumn<SedintaToateDTO, String> colSala;
    @FXML private TableColumn<SedintaToateDTO, String> colDosar;
    @FXML private TableColumn<SedintaToateDTO, String> colGrefier;
    @FXML private TableColumn<SedintaToateDTO, String> colRezultat;

    @FXML private DatePicker dpData;
    @FXML private TextField txtDosar;
    @FXML private CheckBox chkFaraRezultat;
    @FXML private Label lblStatistica;

    @FXML private Spinner<Integer> spMinSedinte;
    @FXML private Label lblProceseMinSedinte;

    private final ApiClient api = new ApiClient();
    private Map<String, Integer> nrSedinteMap;


    @FXML
    public void initialize() {

        spMinSedinte.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 2)
        );
        colData.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDataTermen())
        );
        colOra.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getOra())
        );
        colSala.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getSala())
        );
        colDosar.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNrDosar())
        );
        colGrefier.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGrefier())
        );
        colRezultat.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getRezultat())
        );
        nrSedinteMap = api.getNrSedintePerProces();
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, sel) -> {

                    if (sel == null) return;

                    Integer nr = nrSedinteMap.get(sel.getNrDosar());

                    if (nr != null) {
                        lblStatistica.setText(
                                "Procesul " + sel.getNrDosar() +
                                        " are " + nr + " ședințe în total."
                        );
                    }
                });



        loadToate();
    }

    @FXML
    private void handleFiltreaza() {

        String data = dpData.getValue() != null
                ? dpData.getValue().toString()
                : null;

        String dosar = txtDosar.getText().isBlank()
                ? null
                : txtDosar.getText();

        boolean faraRezultat = chkFaraRezultat.isSelected();

        table.setItems(
                FXCollections.observableArrayList(
                        api.getSedinteToate(data, dosar, faraRezultat)
                )
        );
    }

    @FXML
    private void handleAfiseazaProceseCuMinSedinte() {

        int min = spMinSedinte.getValue();

        List<Map<String, Object>> procese =
                api.getProceseCuMinSedinte(min);

        if (procese.isEmpty()) {
            lblStatistica.setText(
                    "Nu există procese cu minim " + min + " ședințe."
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Procese cu minim ")
                .append(min)
                .append(" ședințe:\n");

        for (Map<String, Object> p : procese) {
            sb.append("• ")
                    .append(p.get("nrDosar"))
                    .append("\n");
        }

        lblStatistica.setText(sb.toString());
    }

    @FXML
    private void handleSedinteImportante() {

        if (dpData.getValue() == null) {
            lblStatistica.setText("Selectează o dată!");
            return;
        }

        String data = dpData.getValue().toString();

        table.setItems(
                FXCollections.observableArrayList(
                        api.getSedinteImportante(data)
                )
        );

        lblStatistica.setText(
                "Ședințe importante din data " + data
        );
    }


    @FXML
    private void handleRefresh() {
        dpData.setValue(null);
        txtDosar.clear();
        chkFaraRezultat.setSelected(false);
        loadToate();
    }

    private void loadToate() {
        table.setItems(
                FXCollections.observableArrayList(
                        api.getSedinteToate(null, null, false)
                )
        );
    }


}
