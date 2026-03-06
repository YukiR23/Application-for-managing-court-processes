package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.GrefierDTO;
import com.tribunal.dto.SedintaDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
/** Clasa pentru gestionarea ședințelor asociate unui proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class SedintaController {

    @FXML
    private TableView<SedintaDTO> tableSedinte;

    @FXML
    private TableColumn<SedintaDTO, String> colData;

    @FXML
    private TableColumn<SedintaDTO, String> colOra;

    @FXML
    private TableColumn<SedintaDTO, String> colSala;

    @FXML
    private TableColumn<SedintaDTO, String> colRezultat;

    @FXML
    private TableColumn<SedintaDTO, String> colGrefier;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    private final ApiClient api = new ApiClient();

    private static Integer idProces;

    public static void setProcesId(Integer id) {
        idProces = id;
    }

    @FXML
    public void initialize() {

        colData.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDataTermen())
        );
        colOra.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getOra())
        );
        colSala.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getSala())
        );
        colRezultat.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getRezultat())
        );
        colGrefier.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNumeGrefier() + " " +
                                c.getValue().getPrenumeGrefier()
                )
        );


        loadSedinte();
    }

    private void loadSedinte() {
        if (idProces == null) return;

        List<SedintaDTO> sedinte = api.getSedinteByProces(idProces);
        tableSedinte.setItems(FXCollections.observableArrayList(sedinte));
    }

    private SedintaDTO showSedintaDialog(SedintaDTO sedinta) {

        Dialog<SedintaDTO> dialog = new Dialog<>();
        dialog.setTitle(sedinta == null ? "Adaugă ședință" : "Editează ședință");

        ButtonType saveBtn = new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        DatePicker dpData = new DatePicker();
        TextField txtOra = new TextField();

        TextField txtSala = new TextField();
        TextField txtRezultat = new TextField();

        ComboBox<GrefierDTO> cbGrefier = new ComboBox<>();
        cbGrefier.setItems(
                FXCollections.observableArrayList(api.getGrefieri())
        );

        if (sedinta != null && sedinta.getIdGrefier() != null) {
            cbGrefier.getSelectionModel().select(
                    cbGrefier.getItems().stream()
                            .filter(g -> g.getIdGrefier().equals(sedinta.getIdGrefier()))
                            .findFirst()
                            .orElse(null)
            );
        }

        cbGrefier.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(GrefierDTO g, boolean empty) {
                super.updateItem(g, empty);
                setText(empty || g == null ? null : g.getNumeGrefier() + " " + g.getPrenumeGrefier());
            }
        });
        cbGrefier.setButtonCell(cbGrefier.getCellFactory().call(null));

        if (sedinta != null) {
            dpData.setValue(LocalDate.parse(sedinta.getDataTermen()));
            txtOra.setText(sedinta.getOra());
            txtSala.setText(sedinta.getSala());
            txtRezultat.setText(sedinta.getRezultat());

            cbGrefier.getItems().stream()
                    .filter(g -> g.getIdGrefier().equals(sedinta.getIdGrefier()))
                    .findFirst().ifPresent(cbGrefier::setValue);
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("DataTermen:"), dpData);
        grid.addRow(1, new Label("Ora:"), txtOra);
        grid.addRow(2, new Label("Sala:"), txtSala);
        grid.addRow(3, new Label("Rezultat:"), txtRezultat);
        grid.addRow(4, new Label("Grefier:"), cbGrefier);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {

                if (dpData.getValue() == null || txtOra.getText().isBlank()) {
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Completează data și ora!"
                    ).show();
                    return null;
                }

                if (cbGrefier.getValue() == null) {
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Selectează un grefier!"
                    ).show();
                    return null;
                }

                SedintaDTO s = sedinta == null ? new SedintaDTO() : sedinta;

                s.setDataTermen(dpData.getValue().toString());
                s.setOra(txtOra.getText());
                s.setSala(txtSala.getText());
                s.setRezultat(txtRezultat.getText());
                s.setIdGrefier(cbGrefier.getValue().getIdGrefier());

                GrefierDTO grefier = cbGrefier.getSelectionModel().getSelectedItem();
                if (grefier != null) {
                    s.setIdGrefier(grefier.getIdGrefier());
                }


                return s;
            }
            return null;
        });


        return dialog.showAndWait().orElse(null);
    }

    @FXML
    private void handleAdd() {
        SedintaDTO s = showSedintaDialog(null);

        if (s != null) {
            s.setIdProces(idProces);
            api.addSedinta(s);
            loadSedinte();
        }
    }

    @FXML
    private void handleEdit() {
        SedintaDTO selected = tableSedinte.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Selectează o ședință!");
            return;
        }

        SedintaDTO edited = showSedintaDialog(selected);

        if (edited != null) {
            api.updateSedinta(selected.getIdSedinta(), edited);
            loadSedinte();
        }
    }

    @FXML
    private void handleDelete() {
        SedintaDTO selected = tableSedinte.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Selectează o ședință!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Sigur vrei să ștergi ședința?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                api.deleteSedinta(selected.getIdSedinta());
                loadSedinte();
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
