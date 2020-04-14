package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import main.control.menu.MainMenuController;
import main.model.fractal.FractalSetting;
import main.model.test.exampleFractal;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    // - Includes

    @FXML private MainMenuController mainMenuController;


    // Canvas and drawing area
    @FXML public Canvas canvas;
    @FXML public ColorPicker colorPicker;

    GraphicsContext graphicsContext;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(mainMenuController);
        mainMenuController.injectController(this);

        graphicsContext= canvas.getGraphicsContext2D();

        //graphicsContext.strokeLine(0,0,100,200);
        FractalSetting fractalEnv = new FractalSetting(graphicsContext);
        fractalEnv.drawFractal(exampleFractal.KochSnowflakeFractal());

        canvas.setOnMouseDragged(e->{
           double x=e.getX();
           double y=e.getY();
           graphicsContext.setFill(colorPicker.getValue());
           graphicsContext.fillOval(x-5.0,y-5.0,5.0,5.0);

        });
    }



}
