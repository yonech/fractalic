package main.control.menu.properties;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import main.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {

    public static final int MAXSIZE = 4096;

    Controller controller;

    @FXML
    public Spinner<Integer> propertiesWidth, propertiesHeight;

    @FXML public Button propertiesOK, propertiesCancel;

    public void injectController(Controller controller) {
        this.controller = controller;
        propertiesWidth.getValueFactory().setValue((int) controller.canvas.getWidth());
        propertiesHeight.getValueFactory().setValue((int) controller.canvas.getHeight());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinner(propertiesWidth);
        initSpinner(propertiesHeight);




        propertiesCancel.setOnAction(e-> ((Stage)propertiesCancel.getScene().getWindow()).close() );
        propertiesOK.setOnAction(e->{
            controller.resizeCanvas(propertiesWidth.getValue(), propertiesHeight.getValue());
            ((Stage)propertiesCancel.getScene().getWindow()).close();
        });

    }

    void initSpinner(Spinner<Integer> x) {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAXSIZE);

        x.setValueFactory(valueFactory);
        x.setEditable(true);

        x.valueProperty().addListener(((observableValue, oldVal, newVal) -> {try { if (newVal == null) x.getValueFactory().setValue(oldVal); } catch (Exception ignored) { }}));
    }




}