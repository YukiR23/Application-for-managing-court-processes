package com.tribunal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/** Clasa utilitară pentru încărcarea scenelor JavaFX.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class SceneLoader {

    public static void loadRoot(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneLoader.class.getResource("/" + fxml)
            );
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setUserData(loader.getController());

            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadInto(String fxml, StackPane container) {
        try {
            Parent page = FXMLLoader.load(
                    SceneLoader.class.getResource(fxml)
            );
            container.getChildren().setAll(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
