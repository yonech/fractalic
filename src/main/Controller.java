package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


import javafx.stage.Stage;
import main.control.menu.MainMenuController;
import main.model.fractal.FractalSetting;
import main.model.fractal.Fragment;
import main.model.frcfrg.UnpackedFRCFRG;


import java.io.FileNotFoundException;
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

    public GraphicsContext graphicsContext;

    double originX=0, originY=0;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainMenuController.injectController(this);

        graphicsContext= canvas.getGraphicsContext2D();
        //graphicsContext.strokeLine(0,0,100,200);
        FractalSetting fractalEnv = new FractalSetting(graphicsContext);
        UnpackedFRCFRG unpackedFRCFRG = new UnpackedFRCFRG();

        canvas.setOnMousePressed( e-> {originX=e.getX(); originY=e.getY(); });
        //canvas.setOnMouseReleased( e-> fractalEnv.drawFractal(exampleFractal.KochSnowflakeFractal(originX,originY,e.getX(),e.getY())) );
        canvas.setOnMouseReleased( e-> {
            try {
                fractalEnv.drawFractal(new Fragment(originX,originY,e.getX(),e.getY(),unpackedFRCFRG.getByName("expcurve")));
            } catch (FileNotFoundException ignored){};
        });

        /*

        canvas.setOnMouseDragged(e->{
           double x=e.getX();
           double y=e.getY();
           graphicsContext.setFill(colorPicker.getValue());
           graphicsContext.fillOval(x-5.0,y-5.0,5.0,5.0);

        });

         */
    }



}
