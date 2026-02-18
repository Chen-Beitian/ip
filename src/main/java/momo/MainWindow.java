package momo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Momo momo;

    private final Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image momoImage =
            new Image(this.getClass().getResourceAsStream("/images/Momo.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Momo instance */
    public void setMomo(Momo momo) {
        this.momo = momo;
        dialogContainer.getChildren().add(
                DialogBox.getMomoDialog(momo.getWelcomeMessage(), momoImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Momo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = momo.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMomoDialog(response, momoImage)
        );
        userInput.clear();

        if (momo.isExit()) {
            javafx.animation.PauseTransition delay =
                    new javafx.animation.PauseTransition(javafx.util.Duration.seconds(0.8));
            delay.setOnFinished(event -> javafx.application.Platform.exit());
            delay.play();
        }
    }
}
