package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.HotarareDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
/** Clasa pentru gestionarea hotararilor asociate unui proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */


public class HotarareController {

    private static Integer procesId;

    public static void setProcesId(Integer id) {
        procesId = id;
    }

    @FXML
    private TableView<HotarareDTO> tableHotarari;

    @FXML
    private TableColumn<HotarareDTO, String> colTip;

    @FXML
    private TableColumn<HotarareDTO, String> colData;

    private final ApiClient api = new ApiClient();

    @FXML
    public void initialize() {

        colTip.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTipHotarare())
        );

        colData.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDataPronuntare().replace("T", " ")
                )
        );

        loadHotarari();
    }

    private void loadHotarari() {
        if (procesId == null) {
            tableHotarari.getItems().clear();
            return;
        }

        tableHotarari.setItems(
                FXCollections.observableArrayList(
                        api.getHotarariByProces(procesId)
                )
        );
    }


    // ================= ADD =================

    @FXML
    private void handleAdd() {

        if (!tableHotarari.getItems().isEmpty()) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Acest proces are deja o hotărâre finală."
            ).show();
            return;
        }

        Dialog<HotarareDTO> dialog = new Dialog<>();
        dialog.setTitle("Adaugă hotărâre");

        ButtonType saveBtn =
                new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes()
                .addAll(saveBtn, ButtonType.CANCEL);

        ComboBox<String> cbTip = new ComboBox<>();
        cbTip.setItems(FXCollections.observableArrayList(
                "ADMIS",
                "RESPINS",
                "ANULAT",
                "ÎNCHIS"
        ));

        DatePicker dpData = new DatePicker();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Tip hotărâre:"), cbTip);
        grid.addRow(1, new Label("Data pronunțării:"), dpData);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {

                if (cbTip.getValue() == null || dpData.getValue() == null) {
                    return null;
                }

                HotarareDTO h = new HotarareDTO();
                h.setIdProces(procesId);
                h.setTipHotarare(cbTip.getValue());

                // LocalDate → LocalDateTime
                h.setDataPronuntare(
                        dpData.getValue().atStartOfDay().toString()
                );

                return h;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(h -> {
            api.addHotarare(h);
            loadHotarari();
        });
    }

    // ================= DELETE =================

    @FXML
    private void handleDelete() {

        HotarareDTO selected =
                tableHotarari.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Selectează o hotărâre!")
                    .show();
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Sigur vrei să ștergi hotărârea?",
                ButtonType.YES, ButtonType.NO
        );

        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                api.deleteHotarare(selected.getIdHotarare());
                loadHotarari();
            }
        });
    }

}
