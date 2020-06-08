package main.control.menu;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Controller;
import main.control.menu.examples.ExamplesController;
import main.control.menu.properties.PropertiesController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController implements Initializable {

    public MenuBar mainMenuBar;
    @FXML private Controller controller;

    @FXML
    MenuItem menuOptFileSaveAs, menuOptFileSave;
    File saveToFile = null;

    // File
    @FXML MenuItem menuOptFileNew;

    @FXML MenuItem menuOptFileProperties;
    @FXML MenuItem menuOptFileClose;

    @FXML MenuItem menuOptToolsExample;

    @FXML MenuItem menuOptEditClear;



    public void injectController(Controller controller) {
        this.controller = controller;
    }

    void saveCanvas(File chosenFile)
    {
        try {
            WritableImage writableImage = new WritableImage( (int) controller.canvas.getWidth(), (int) controller.canvas.getHeight());
            controller.canvas.snapshot(null,writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage,null);
            ImageIO.write(renderedImage, "png", chosenFile);

            saveToFile = chosenFile;

        } catch(IOException eio){
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE,null,eio);
        }

    };


    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuOptToolsExample.setOnAction(actionEvent -> {
            try{
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(MainMenuController.class.getResource("examples/ExamplesView.fxml"));
                Parent root= (Parent) loader.load();
                ExamplesController examplesController=loader.getController();
                examplesController.injectController(controller);
                Stage stage=new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Examples");
                stage.setScene(new Scene(root));
                stage.show();

            }catch(Exception e){
                System.out.println("Unknown error");
                e.printStackTrace();
            }

        });

        menuOptFileSaveAs.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)","*.png");
            fileChooser.getExtensionFilters().add(extensionFilter);

            File chosenFile = fileChooser.showSaveDialog(controller.primaryStage);

            if(chosenFile != null) saveCanvas(chosenFile);

        });
        menuOptFileSave.setOnAction(actionEvent -> {
            if(saveToFile==null) menuOptFileSaveAs.getOnAction();
            else    saveCanvas(saveToFile);
        });


        menuOptFileClose.setOnAction(actionEvent -> Platform.exit());



        menuOptEditClear.setOnAction(actionEvent -> controller.graphicsContext.clearRect(0,0,controller.canvas.getWidth(),controller.canvas.getHeight()));





        menuOptFileProperties.setOnAction(actionEvent -> {
            try{
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(MainMenuController.class.getResource("properties/PropertiesView.fxml"));
                Parent root= (Parent) loader.load();
                PropertiesController propertiesController =loader.getController();
                propertiesController.injectController(controller);
                Stage stage=new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Properties");
                stage.setScene(new Scene(root));
                stage.show();

            }catch(Exception e){
                System.out.println("Unknown error");
                e.printStackTrace();
            }

        });



    }

}
