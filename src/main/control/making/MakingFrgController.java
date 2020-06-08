package main.control.making;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;
import main.control.menu.examples.ExamplesController;
import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;
import main.model.frcfrg.PackFRCFRG;
import main.model.frcfrg.UnpackedFRCFRG;
import main.model.making.FragmentsOnPane;
import main.model.making.SnapToGrid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MakingFrgController implements Initializable {



    double originX, originY;

    ExamplesController examplesController;

    boolean unsaved = false;
    File workingOn;

    @FXML Text fragmentName;
    @FXML ColorPicker colorSelection;
    @FXML Button makingClose;
    @FXML Button makingSave;
    @FXML CheckBox checkBoxGrid;
    @FXML CheckBox checkBoxLabels;

    // design Space
    @FXML Pane designPane;
    Line indicatorLine;

    UnpackedFRCFRG unpacked;
    FragmentsOnPane fop;

    public void injectController(ExamplesController controller) {
        this.examplesController = controller;

    }
    public void loadFragments(File w) {
        workingOn = w;
        fragmentName.setText(workingOn.getName().substring(0, workingOn.getName().length() - 7));
        unpacked = new UnpackedFRCFRG();
        try {
            FragmentType me = unpacked.getByName(fragmentName.getText());
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
            made.info.name = fragmentName.getText();

            PackFRCFRG into = new PackFRCFRG(workingOn);
            try {
                into.addTraitsFrom(made);
            } catch (IOException ex) { ex.printStackTrace(); }

            unsaved = false;
            updateTitle();
        });


        designPane.setOnMousePressed(e->{
            unsaved = true;
            updateTitle();
            switch(e.getButton())
            {
                case PRIMARY:
                    Pair<Double,Double> p = checkBoxGrid.isSelected() ? SnapToGrid.snapToNearest(e.getX(),e.getY()) : new Pair<>(e.getX(), e.getY());
                    originX = p.getKey(); originY = p.getValue();
                break;
                case SECONDARY:
                    fop.deleteNearest(e.getX(),e.getY());
                break;
            }
        });

       designPane.setOnMouseDragged(e->{


           switch(e.getButton())
           {

               case PRIMARY:
                   Pair<Double,Double> p = checkBoxGrid.isSelected() ? SnapToGrid.snapToNearest(e.getX(),e.getY()) : new Pair<>(e.getX(), e.getY());

                   if(indicatorLine==null) {

                       indicatorLine = new Line(originX, originY, p.getKey(), p.getValue());
                       indicatorLine.setStrokeDashOffset(2);
                       try {
                           FragmentType f = unpacked.getByName(examplesController.getSelectedFrg().getText());
                           indicatorLine.setStroke(f.color);
                       } catch (FileNotFoundException ex) { ex.printStackTrace(); }
                       designPane.getChildren().add(indicatorLine);
                   } else {
                       indicatorLine.setEndY(p.getValue()); indicatorLine.setEndX(p.getKey());
                   }
               break;
           }
       });

       designPane.setOnMouseReleased(e->{
           switch(e.getButton()) {
               case PRIMARY:
                  // Pair<Double,Double> p = checkBoxGrid.isSelected() ? SnapToGrid.snapToNearest(e.getX(),e.getY()) : new Pair<>(e.getX(), e.getY());

                   try {
                       fop.add(new Fragment(
                               indicatorLine.getStartX(), indicatorLine.getStartY(), indicatorLine.getEndX(), indicatorLine.getEndY(),
                               unpacked.getByName(examplesController.getSelectedFrg().getText())
                       ));
                   } catch (FileNotFoundException ex) {
                       ex.printStackTrace();
                   }
                   designPane.getChildren().remove(indicatorLine);
                   indicatorLine=null;
                break;
           }

       });


        checkBoxLabels.setOnAction(e->fop.toggleLabels(checkBoxLabels.isSelected()));
        checkBoxGrid.setOnAction(e->fop.toggleGrid(checkBoxGrid.isSelected()));
    }

    void updateTitle()
    {
        if(unsaved) fragmentName.setText(workingOn.getName().substring(0, workingOn.getName().length() - 7) + " (unsaved)");
        else        fragmentName.setText(workingOn.getName().substring(0, workingOn.getName().length() - 7));

    };

}
