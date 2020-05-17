package main.control.menu.examples;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Controller;
import main.control.making.MakingFrgController;
import main.model.fractal.FractalSetting;
import main.model.fractal.Fragment;
import main.model.frcfrg.UnpackedFRCFRG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamplesController implements Initializable {

    @FXML
    VBox examplesList;
    @FXML
    Button examplesSelect;
    @FXML
    Canvas canvas;
    GraphicsContext graphicsContext;
    FractalSetting fractalEnv;

    ArrayList<RadioButton> buttonList;
    ToggleGroup group;
    File exampleFolder;
    Controller controller=null;

    @FXML TextField newFractals;
    @FXML Button examplesEdit;
    @FXML Button examplesRemove;

    private MakingFrgController makingFrgController;

    @FXML public Pane display;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        group=new ToggleGroup();
        graphicsContext=canvas.getGraphicsContext2D();
        fractalEnv=new FractalSetting(graphicsContext);

        makeButtonList();

        examplesSelect.setOnAction(actionEvent -> {
            if(controller==null) System.out.println("Null controller");
            else{
                int i;
                String fractal=new String("");
                for(i=0;i<buttonList.size();i++){
                    if(buttonList.get(i).isSelected()) {
                        fractal=buttonList.get(i).getText();
                        break;
                    }
                }
                if(fractal.equals("")) System.out.println("Fractal not chosen");
                else{
                    controller.setChosenFractal(fractal);
                }

            }
            Stage stage= (Stage) examplesSelect.getScene().getWindow();
            stage.close();
        });
        if(buttonList.size()>0) previewFractal(buttonList.get(0).getText());


        // Inserting new fragments

       newFractals.setOnKeyPressed(e->{
           if(!e.getCode().equals(KeyCode.ENTER)) return;
            String name = newFractals.getText();
            newFractals.setText("");
            if(name.equals("")) return;
            for(RadioButton r : buttonList) if(r.getText().equals(name))  return;
           try {
               File file = new File("src/resources/fragments/"+name+".frcfrg");
               file.createNewFile();
           } catch (IOException ex) { ex.printStackTrace(); }

           makeButtonList();
       });

       // Removing existing fragments

        examplesRemove.setOnAction(e->{
            for (RadioButton r: buttonList) if(r.isSelected()) {
                File file = new File("src/resources/fragments/" + r.getText() + ".frcfrg");
                if(file.exists())  file.delete();
            }
            makeButtonList();
        });

        // Edit pattern of fragment
        examplesEdit.setOnAction(e->{
            File loc = null;
            for (RadioButton r: buttonList) if(r.isSelected()) loc = new File("src/resources/fragments/" + r.getText() + ".frcfrg");
            BorderPane newPane = null;
            if(loc!=null)
                try {
                    FXMLLoader loader= new FXMLLoader();

                    loader.setLocation(MakingFrgController.class.getResource("MakingFrgView.fxml"));
                    newPane = loader.load();
                    System.out.println(newPane);

                    makingFrgController=loader.getController();
                    //System.out.println((String) loader.getController());
                    makingFrgController.injectController(this);
                    makingFrgController.setName(loc.getName());
                    makingFrgController.loadFragments(loc);

                    display.getChildren().clear();
                    display.getChildren().add(newPane);



                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        });
    }


    public void injectController(Controller controller) {
        this.controller = controller;
    }

    public void returnToCanvas() {
        display.getChildren().clear();
        display.getChildren().add(canvas);
    }

    public void previewFractal(String fractal){
        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        UnpackedFRCFRG unpackedFRCFRG=new UnpackedFRCFRG();
        try{
            fractalEnv.setDepth(4);
            fractalEnv.drawFractal(new Fragment(150,200,350 , 200,unpackedFRCFRG.getByName(fractal)));
        }catch(FileNotFoundException  ignored){};
    }


    void makeButtonList()
    {
        examplesList.getChildren().clear();
        buttonList = new ArrayList<>();
        boolean selection=true;


        exampleFolder= new File("src/resources/fragments");
        if(!exampleFolder.exists()) System.out.println("File not open.");
        FilenameFilter filter= (file, s) -> s.endsWith(".frcfrg");
        String[] files=exampleFolder.list(filter);

        assert files != null;
        for(String filename : files){
            RadioButton button=new RadioButton(filename.substring(0,filename.length()-7));
            button.setToggleGroup(group);
            button.setOnAction(actionEvent -> {
                /*
                int i;
                System.out.println(((RadioButton)group.getSelectedToggle()).getText());
                for(i=0;i<buttonList.size();i++){
                    if(buttonList.get(i).isSelected()){
                        System.out.println(buttonList.get(i).getText());
                    }
                }

                 */
                previewFractal(((RadioButton)group.getSelectedToggle()).getText());
            });
            button.setSelected(selection);
            if(selection) selection=false;
            buttonList.add(button);
            examplesList.getChildren().add(button);

        }

    };





}
