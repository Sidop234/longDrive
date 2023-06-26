package User.controller;

import Server.entity.FileWrapper;
import Server.entity.Files;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    AnchorPane anchor;
    @FXML
    ImageView icon;
    @FXML
    Label filename;

    ProfileScreenController profileScreenController;
    FileWrapper fileWrapper;
    public void first(){
        if(fileWrapper !=null) {
            this.filename.setText(fileWrapper.getFilename());
            if(fileWrapper.getFiletype()=="txt"){
                this.icon.setImage(new Image("User/resource/txt.png"));
            }else if(fileWrapper.getFiletype()=="pdf"){
                this.icon.setImage(new Image("User/resource/pdf.jpg"));
            }else{
                this.icon.setImage(new Image("User/resource/img.jpg"));
            }
        }
        else
            filename.setText("junk");
    }

    public void onClick(){
        if(fileWrapper !=null) {
            profileScreenController.filename.setText(fileWrapper.getFilename());
            profileScreenController.fileWrapper = this.fileWrapper;
            System.out.println("Files object set in profileScreenController!");
        }
        else
            profileScreenController.filename.setText("junk");
    }
}
