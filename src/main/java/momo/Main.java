package momo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Momo using FXML.
 */
public class Main extends Application {
    private static final String DATA_FILE = "data/momo.txt";

    private final Momo momo = new Momo(DATA_FILE);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            fxmlLoader.<MainWindow>getController().setMomo(momo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
