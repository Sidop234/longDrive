package User.controller;

import Server.response.LoginResponse;
import User.entity.Main;
import User.request.LoginRequest;
import User.util.GuiUtil;
import User.util.HashUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    public AnchorPane loginPane;
    @FXML
    public Button loginButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;
    @FXML
    public Label signinLabel;
    @FXML
    public Hyperlink signupLink;

    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
    public void login(ActionEvent actionEvent){
        LoginRequest request = new LoginRequest(usernameField.getText(), HashUtil.getMd5(passwordField.getText()));
        Main.sendRequest(request);
        LoginResponse response = (LoginResponse) Main.getResponse();
        if (response ==null)
            GuiUtil.alert(Alert.AlertType.ERROR,"Incorrect Information.Please try again.");
        else{
            Main.userRegistrationNumber = String.valueOf(response.getRegistrationNo());

            FXMLLoader homepageLoader= new FXMLLoader(getClass().getResource("../fxml/ProfileScreen.fxml"));
            Stage currentStage=(Stage)loginButton.getScene().getWindow();
            Scene scene=null;
            try {
                scene=new Scene(homepageLoader.load(), 682, 580.6667);
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentStage.setScene(scene);
            currentStage.setResizable(false);
            currentStage.setTitle("Welcome");
            currentStage.setOnCloseRequest(event -> {
                try {
                    Main.oos.close();
                    Main.ois.close();
                    Main.oos = null;
                    Main.ois = null;
                } catch (IOException e) {
                    Main.oos = null;
                    Main.ois = null;
                    e.printStackTrace();
                }
            });
            Main.userFullName = response.getFirstName() + " " + response.getLastName();
            ProfileScreenController profileScreenController=homepageLoader.getController();
            profileScreenController.first(response.getFirstName()+" "+response.getLastName());
        }

    }



    public void switchToSignup(ActionEvent actionEvent) {
        FXMLLoader registerLoader=new FXMLLoader(getClass().getResource("../fxml/Register.fxml"));
        Scene scene=null;
        Stage stage=(Stage)signupLink.getScene().getWindow();
        try {
            scene=new Scene(registerLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        RegisterController registerController=registerLoader.getController();
        registerController.first();
        stage.setTitle("Sign Up");
    }

}