package main.control.making;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import main.control.menu.examples.ExamplesController;
import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;
import main.model.frcfrg.PackFRCFRG;
import main.model.frcfrg.UnpackedFRCFRG;
import main.model.making.FragmentsOnPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MakingFrgController implements Initializable {

    final static double deleteRange = 10;

    double originX, originY;

    ExamplesController examplesController;


    File workingOn;

    @FXML Text fragmentName;
    @FXML ColorPicker colorSelection;
    @FXML Button makingClose;
    @FXML Button makingSave;
    @FXML CheckBox checkBoxGrid;
    @FXML CheckBox checkBoxLabels;

    // design Space
    @FXML Pane designPane;

    UnpackedFRCFRG unpacked;
    FragmentsOnPane fop;

    public void injectController(ExamplesController controller) {
        this.examplesController = controller;

    }
    public void setName(String name){ fragmentName.setText(name); }
    public void loadFragments(File w) {
        workingOn = w;
        unpacked = new UnpackedFRCFRG();
        try {
            FragmentType me = unpacked.getByName(workingOn.getName().substring(0, workingOn.getName().length() - 7));
            if(me!=null)    colorSelection.setValue(me.color);
            fop = new FragmentsOnPane(designPane);
            fop.addStartAndEndCircles();
            if(me!=null) fop.layFragments(me);

        } catch (Exception e){};
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makingClose.setOnAction(e->{
            examplesController.returnToCanvas();
        });

        makingSave.setOnAction(e->{
            Fragment made = fop.toFragment();
            made.info.color = colorSelection.getValue();
            made.info.name = workingOn.getName().substring(0, workingOn.getName().length() - 7);
            System.out.println("HELLO!!");
            PackFRCFRG into = new PackFRCFRG(workingOn);
            try {
                into.addTraitsFrom(made);
            } catch (IOException ex) { ex.printStackTrace(); }
        });

        designPane.setOnMouseClicked(e ->{
            if(e.getButton()== MouseButton.SECONDARY)
            {
                //TODO delete closest in range
            };
        });
      //  designPane.setOnMousePressed();

       // designPane.setOnMouseReleased();

       // designPane.setOnMouseDragged();


    }

}
