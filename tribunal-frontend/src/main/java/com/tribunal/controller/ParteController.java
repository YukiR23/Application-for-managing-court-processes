package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.AvocatDTO;
import com.tribunal.dto.ParteDTO;
import com.tribunal.dto.PersoanaDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
/** Clasa pentru gestionarea părților unui proces în UI.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ParteController {
    @FXML
    private TableView<ParteDTO> tableParti;

    @FXML
    private TableColumn<ParteDTO, String> colPersoana;

    @FXML
    private TableColumn<ParteDTO, String> colTip;

    @FXML
    private TableColumn<ParteDTO, String> colAvocat;


    private final ApiClient api = new ApiClient();

    private static Integer idProces;

    public static void setProcesId(Integer id) {
        idProces = id;
    }

    @FXML
    public void initialize() {

        colPersoana.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNumePersoana() + " " +
                                c.getValue().getPrenumePersoana()
                )
        );

        colTip.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTipParte())
        );

        colAvocat.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNumeAvocat() == null
                                ? "-"
                                : c.getValue().getNumeAvocat() + " " +
                                c.getValue().getPrenumeAvocat()
                )
        );

        loadParti();
    }

    private void loadParti() {
        if (idProces == null) return;

        List<ParteDTO> list = api.getParti(idProces);
        tableParti.setItems(FXCollections.observableArrayList(list));
    }

    private ParteDTO showParteDialog(ParteDTO parte) {

        Dialog<ParteDTO> dialog = new Dialog<>();
        dialog.setTitle("Adaugă parte");

        ButtonType saveBtn =
                new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes()
                .addAll(saveBtn, ButtonType.CANCEL);

        ComboBox<PersoanaDTO> cbPersoana = new ComboBox<>();
        ComboBox<AvocatDTO> cbAvocat = new ComboBox<>();
        TextField txtTip = new TextField();

        cbPersoana.setItems(
                FXCollections.observableArrayList(api.getPersoane())
        );
        cbAvocat.setItems(
                FXCollections.observableArrayList(api.getAvocati())
        );

        cbPersoana.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PersoanaDTO p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null
                        ? null
                        : p.getNume() + " " + p.getPrenume());
            }
        });
        cbPersoana.setButtonCell(
                cbPersoana.getCellFactory().call(null)
        );

        cbAvocat.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(AvocatDTO a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null
                        ? null
                        : a.getNume() + " " + a.getPrenume());
            }
        });
        cbAvocat.setButtonCell(
                cbAvocat.getCellFactory().call(null)
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Persoană:"), cbPersoana);
        grid.addRow(1, new Label("Tip parte:"), txtTip);
        grid.addRow(2, new Label("Avocat:"), cbAvocat);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {

                if (cbPersoana.getValue() == null ||
                        txtTip.getText().isBlank()) {

                    new Alert(
                            Alert.AlertType.WARNING,
                            "Completează persoana și tipul părții!"
                    ).show();
                    return null;
                }

                ParteDTO p = new ParteDTO();

                p.setIdProces(idProces);
                p.setIdPersoana(cbPersoana.getValue().getIdPersoana());
                p.setTipParte(txtTip.getText());

                if (cbAvocat.getValue() != null) {
                    p.setIdAvocat(cbAvocat.getValue().getIdAvocat());
                }

                return p;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    @FXML
    private void handleAdd() {
        ParteDTO p = showParteDialog(null);

        if (p != null) {
            api.addParte(p);
            loadParti();
        }
    }

    @FXML
    private void handleDelete() {
        ParteDTO selected =
                tableParti.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Selectează o parte!");
            return;
        }

        api.deleteParte(selected.getIdParte());
        loadParti();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
