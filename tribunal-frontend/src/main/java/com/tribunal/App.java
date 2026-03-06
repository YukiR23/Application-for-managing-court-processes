package com.tribunal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** Clasa principală pentru pornirea aplicației JavaFX.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        loader.setCharset(java.nio.charset.StandardCharsets.UTF_8);
        Scene scene = new Scene(loader.load());
        stage.setTitle("Tribunal - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
