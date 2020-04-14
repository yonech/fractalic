package main.control.menu;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import main.Controller;

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


    public void injectController(Controller controller) {
        this.controller = controller;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(controller);
        menuOptFileSaveAs.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)","*.png");
            fileChooser.getExtensionFilters().add(extensionFilter);

            File chosenFile = fileChooser.showSaveDialog(controller.primaryStage);

            if(chosenFile != null)
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

        });


    }

}
