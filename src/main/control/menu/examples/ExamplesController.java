package main.control.menu.examples;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamplesController implements Initializable {

    @FXML
    VBox examplesList;
    @FXML
    Button examplesSelect;

    ArrayList<RadioButton> buttonList;
    ToggleGroup group;
    File exampleFolder;
    Controller controller=null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonList = new ArrayList<>();
        boolean selection=true;
        group=new ToggleGroup();

        exampleFolder= new File("src/resources/fragments");
        if(!exampleFolder.exists()) System.out.println("File not open.");
        FilenameFilter filter= (file, s) -> s.endsWith(".frcfrg");
        String[] files=exampleFolder.list(filter);

        assert files != null;
        for(String filename : files){
            RadioButton button=new RadioButton(filename.substring(0,filename.length()-7));
            button.setToggleGroup(group);
            button.setSelected(selection);
            if(selection) selection=false;
            buttonList.add(button);
            examplesList.getChildren().add(button);
        }
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
    }
    public void injectController(Controller controller) {
        this.controller = controller;
    }
}
