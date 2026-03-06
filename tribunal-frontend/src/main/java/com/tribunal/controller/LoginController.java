package com.tribunal.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tribunal.api.ApiClient;
import com.tribunal.session.Session;
import com.tribunal.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/** Clasa pentru gestionarea autentificării în interfață.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class LoginController {

    @FXML private TextField txtUser;
    @FXML private PasswordField txtPassHidden;
    @FXML private TextField txtPassVisible;


    private final ApiClient api = new ApiClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    private void handleLogin() {
        try {
            String parola = txtPassHidden.isVisible()
                    ? txtPassHidden.getText()
                    : txtPassVisible.getText();

            String json = api.login(
                    txtUser.getText().trim(),
                    parola.trim()
            );


            JsonNode root = mapper.readTree(json);

            JsonNode successNode = root.get("success");
            if (successNode == null || !successNode.asBoolean()) {
                new Alert(Alert.AlertType.ERROR, "Autentificare eșuată").show();
                return;
            }

            Session.setRol(root.get("rol").asText());

            Stage stage = (Stage) txtUser.getScene().getWindow();
            SceneLoader.loadRoot("menu.fxml", stage);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void togglePassword() {

        if (txtPassVisible.isVisible()) {
            // ascunde parola
            txtPassHidden.setText(txtPassVisible.getText());
            txtPassVisible.setVisible(false);
            txtPassVisible.setManaged(false);
            txtPassHidden.setVisible(true);
            txtPassHidden.setManaged(true);
        } else {
            // arată parola
            txtPassVisible.setText(txtPassHidden.getText());
            txtPassHidden.setVisible(false);
            txtPassHidden.setManaged(false);
            txtPassVisible.setVisible(true);
            txtPassVisible.setManaged(true);
        }
    }

}
