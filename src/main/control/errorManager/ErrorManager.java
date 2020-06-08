package main.control.errorManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.control.errorManager.error.ErrorController;
import main.control.menu.MainMenuController;

public class ErrorManager {
    public static void errorPopup(String message){
        try{
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(ErrorManager.class.getResource("error/ErrorView.fxml"));
            //System.out.println(loader.getLocation());
            //System.out.println("fetched");
            Parent root= (Parent) loader.load();
            ErrorController errorController =loader.getController();
            errorController.errorMessage.setText(message);
            Stage stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error");
            stage.setScene(new Scene(root));
            stage.show();

        }catch(Exception e){
            System.out.println("Unknown error");
            e.printStackTrace();
        }
    }
}
