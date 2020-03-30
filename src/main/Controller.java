package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Canvas canvas;
    @FXML
    public ColorPicker colorPicker;
    GraphicsContext graphicsContext;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext= canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e->{
           double x=e.getX();
           double y=e.getY();
           graphicsContext.setFill(colorPicker.getValue());
           graphicsContext.fillOval(x-5.0,y-5.0,5.0,5.0);
        });
    }
}
