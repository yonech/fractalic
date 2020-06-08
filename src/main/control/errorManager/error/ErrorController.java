package main.control.errorManager.error;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ErrorController implements Initializable {

    @FXML Button okButton;

    @FXML public Text errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okButton.setOnAction(actionEvent -> ( (Stage) okButton.getScene().getWindow()).close());
    }
}
