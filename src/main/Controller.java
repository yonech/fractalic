package main;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.control.menu.MainMenuController;
import main.model.fractal.FractalSetting;
import main.model.fractal.Fragment;
import main.model.frcfrg.UnpackedFRCFRG;
import main.model.shapes.Arrow;


import java.io.File;
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
    @FXML public Pane canvasPane;
    @FXML public Canvas canvas;
    @FXML public ColorPicker colorPicker;

    @FXML public Pane indicatorPane;


    public GraphicsContext graphicsContext;
    public String chosenFractal;
    double originX=0, originY=0;

    @FXML public Spinner<Integer> depthSpinner;

    FractalSetting fractalEnv;

    double valCanvasWid, valCanvasHig;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        valCanvasWid = canvas.getWidth();
        valCanvasHig = canvas.getHeight();

        mainMenuController.injectController(this);
        indicatorPane.setViewOrder(-50);
        //dragLine.setOnMouseDragged(e->{dragLine.se});
        //depthSpinner.getValueFactory().setValue(5);

        graphicsContext= canvas.getGraphicsContext2D();
        fractalEnv = new FractalSetting(graphicsContext);

        //canvasPane.getChildren().add(new Arrow(0,0,100,200));
        //graphicsContext.strokeLine(0,0,100,200);
        //FractalSetting fractalEnv = new FractalSetting(graphicsContext);
        //UnpackedFRCFRG unpackedFRCFRG = new UnpackedFRCFRG();

        canvasPane.setOnMousePressed( e-> {
            originX=e.getX(); originY=e.getY();

        });


        canvasPane.setOnMouseDragged(e->{
            indicatorPane.getChildren().clear();

            indicatorPane.getChildren().add(new Line(originX,originY,e.getX(),e.getY()));

        });

        //canvas.setOnMouseReleased( e-> fractalEnv.drawFractal(exampleFractal.KochSnowflakeFractal(originX,originY,e.getX(),e.getY())) );
        setChosenFractal("kochSnowflakeEdge");
        /*canvas.setOnMouseReleased( e-> {
            try {
                fractalEnv.drawFractal(new Fragment(originX,originY,e.getX(),e.getY(),unpackedFRCFRG.getByName("expcurve")));
            } catch (FileNotFoundException ignored){};
        });*/

        /*

        canvas.setOnMouseDragged(e->{
           double x=e.getX();
           double y=e.getY();
           graphicsContext.setFill(colorPicker.getValue());
           graphicsContext.fillOval(x-5.0,y-5.0,5.0,5.0);

        });

         */
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        depthSpinner.setValueFactory(valueFactory);
        depthSpinner.setEditable(true);
        depthSpinner.valueProperty().addListener(this::handleSpin);




    }


    private void handleSpin(ObservableValue<?> observableValue, Integer oldValue, Integer newValue) {
        try {
            if (newValue == null)
                depthSpinner.getValueFactory().setValue(oldValue);

        } catch (Exception ignored) { }
    }



    public void setChosenFractal(String fractal){
        chosenFractal=fractal;

        UnpackedFRCFRG unpackedFRCFRG = new UnpackedFRCFRG();

        canvasPane.setOnMouseReleased( e-> {
            indicatorPane.getChildren().clear();
            try {
                fractalEnv.setDepth(depthSpinner.getValueFactory().getValue());
                fractalEnv.drawFractal(new Fragment(originX,originY,e.getX(),e.getY(),unpackedFRCFRG.getByName(chosenFractal)));
            } catch (FileNotFoundException ignored){};
        });
    }


    public void resizeCanvas(double w, double h)
    {
        canvas.setWidth(w);
        canvas.setHeight(h);
        canvasPane.setMinWidth(w+3);
        canvasPane.setMinHeight(h+3);
        canvasPane.setMaxWidth(w+3);
        canvasPane.setMinHeight(h+3);
        indicatorPane.setMinHeight(h+3);
        indicatorPane.setMinWidth(w+3);
        indicatorPane.setMaxHeight(h+3);
        indicatorPane.setMaxWidth(w+3);
    };

}
