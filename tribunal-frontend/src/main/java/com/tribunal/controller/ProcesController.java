package com.tribunal.controller;

import com.tribunal.api.ApiClient;
import com.tribunal.dto.JudecatorDTO;
import com.tribunal.dto.ProcesDTO;
import com.tribunal.dto.ProcurorDTO;
import com.tribunal.session.Session;
import com.tribunal.util.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
import java.util.List;
/** Clasa pentru gestionarea proceselor în interfața grafică.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ProcesController {

    @FXML
    private StackPane contentArea;


    @FXML private TextField txtSearch;

    @FXML private TableView<ProcesDTO> tableProcese;
    @FXML private TableColumn<ProcesDTO, String> colNrDosar;
    @FXML private TableColumn<ProcesDTO, String> colMaterie;
    @FXML private TableColumn<ProcesDTO, String> colStadiu;
    @FXML private TableColumn<ProcesDTO, String> colData;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private HBox undoBar;
    @FXML private Label lblUndo;


    private ProcesDTO procesPendingDelete;
    private javafx.animation.PauseTransition undoTimer;

    private final ApiClient api = new ApiClient();

    @FXML
    public void initialize() {

        colNrDosar.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getNrDosar())
        );
        colMaterie.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getMaterieJuridica())
        );
        colStadiu.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getStadiuProces())
        );
        colData.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDataInregistrare())
        );

        if (Session.isReadOnly()) {
            btnAdd.setDisable(true);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
        }

        loadProcese();
    }

    private void loadProcese() {
        tableProcese.setItems(
                FXCollections.observableArrayList(api.getProcese())
        );
    }

    @FXML
    private void handleSearch() {
        String q = txtSearch.getText();

        if (q == null || q.isBlank()) {
            loadProcese();
            return;
        }

        List<ProcesDTO> filtrate = api.getProcese()
                .stream()
                .filter(p ->
                        p.getNrDosar().toLowerCase().contains(q.toLowerCase()) ||
                                p.getMaterieJuridica().toLowerCase().contains(q.toLowerCase()) ||
                                p.getStadiuProces().toLowerCase().contains(q.toLowerCase())
                )
                .toList();

        tableProcese.setItems(FXCollections.observableArrayList(filtrate));
    }

    @FXML
    private void handleRefresh()
    {
        txtSearch.clear();
        loadProcese();
    }

    @FXML
    private void handleDetaliiProces() {
        ProcesDTO selected = tableProcese.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Selectează un proces!");
            return;
        }

        Scene scene = tableProcese.getScene();
        MenuController menu =
                (MenuController) scene.getUserData();

        menu.openDetaliiProces(selected);
    }

    private ProcesDTO showProcesDialog(ProcesDTO proces) {

        Dialog<ProcesDTO> dialog = new Dialog<>();
        dialog.setTitle(proces == null ? "Adaugă proces" : "Editează proces");

        ButtonType saveBtn = new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        TextField txtNr = new TextField();
        TextField txtMaterie = new TextField();
        TextField txtStadiu = new TextField();
        DatePicker dpData = new DatePicker();

        ComboBox<JudecatorDTO> cbJudecator = new ComboBox<>();
        ComboBox<ProcurorDTO> cbProcuror = new ComboBox<>();

        cbJudecator.setItems(FXCollections.observableArrayList(api.getJudecatori()));
        cbProcuror.setItems(FXCollections.observableArrayList(api.getProcurori()));

        cbJudecator.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(JudecatorDTO j, boolean empty) {
                super.updateItem(j, empty);
                setText(empty || j == null ? null : j.getNume() + " " + j.getPrenume());
            }
        });
        cbJudecator.setButtonCell(cbJudecator.getCellFactory().call(null));

        cbProcuror.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ProcurorDTO p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getNume() + " " + p.getPrenume());
            }
        });
        cbProcuror.setButtonCell(cbProcuror.getCellFactory().call(null));

        if (proces != null) {
            txtNr.setText(proces.getNrDosar());
            txtMaterie.setText(proces.getMaterieJuridica());
            txtStadiu.setText(proces.getStadiuProces());
            dpData.setValue(LocalDate.parse(proces.getDataInregistrare()));

            cbJudecator.getItems().stream()
                    .filter(j -> j.getIdJudecator().equals(proces.getIdJudecator()))
                    .findFirst()
                    .ifPresent(cbJudecator::setValue);

            if (proces.getIdProcuror() != null) {
                cbProcuror.getItems().stream()
                        .filter(p -> p.getIdProcuror().equals(proces.getIdProcuror()))
                        .findFirst()
                        .ifPresent(cbProcuror::setValue);
            }
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Nr dosar:"), txtNr);
        grid.addRow(1, new Label("Materie:"), txtMaterie);
        grid.addRow(2, new Label("Stadiu:"), txtStadiu);
        grid.addRow(3, new Label("Data:"), dpData);
        grid.addRow(4, new Label("Judecător:"), cbJudecator);
        grid.addRow(5, new Label("Procuror:"), cbProcuror);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {
                ProcesDTO p = proces != null ? proces : new ProcesDTO();
                p.setNrDosar(txtNr.getText());
                p.setMaterieJuridica(txtMaterie.getText());
                p.setStadiuProces(txtStadiu.getText());
                p.setDataInregistrare(dpData.getValue().toString());
                if (cbJudecator.getValue() == null) {
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Selectează un judecător!"
                    ).show();
                    return null;
                }

                p.setIdJudecator(cbJudecator.getValue().getIdJudecator());
                p.setIdProcuror(
                        cbProcuror.getValue() != null
                                ? cbProcuror.getValue().getIdProcuror()
                                : null
                );
                return p;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    @FXML
    private void handleAdd() {
        ProcesDTO p = showProcesDialog(null);
        if (p != null) {
            api.addProces(p);
            loadProcese();
        }
    }

    @FXML
    private void handleEdit() {
        ProcesDTO selected = tableProcese.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează un proces!");
            return;
        }

        ProcesDTO edited = showProcesDialog(selected);
        if (edited != null) {
            edited.setIdProces(selected.getIdProces());
            api.updateProces(edited);
            loadProcese();
        }
    }

    @FXML
    private void handleDelete() {

        ProcesDTO selected =
                tableProcese.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Selectează un proces!");
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Sigur vrei să ștergi procesul " + selected.getNrDosar() + " ?",
                ButtonType.OK,
                ButtonType.CANCEL
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        procesPendingDelete = selected;
        tableProcese.getItems().remove(selected);

        showUndoBar(
                "Procesul " + selected.getNrDosar() + " a fost șters"
        );

        undoTimer = new javafx.animation.PauseTransition(
                javafx.util.Duration.seconds(5)
        );
        undoTimer.setOnFinished(e -> deleteFinal());
        undoTimer.play();
    }

    private void deleteFinal() {

        hideUndoBar();

        if (procesPendingDelete != null) {
            api.deleteProces(procesPendingDelete.getIdProces());
            procesPendingDelete = null;
        }
    }

    @FXML
    private void undoDelete() {

        if (undoTimer != null) {
            undoTimer.stop();
        }

        hideUndoBar();

        if (procesPendingDelete != null) {
            tableProcese.getItems().add(procesPendingDelete);
            procesPendingDelete = null;
        }
    }

    private void showUndoBar(String text) {
        lblUndo.setText(text);
        undoBar.setVisible(true);
        undoBar.setManaged(true);
    }

    private void hideUndoBar() {
        undoBar.setVisible(false);
        undoBar.setManaged(false);
    }


    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
