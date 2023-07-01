package User.controller;

import Server.entity.FileWrapper;
import Server.entity.Files;
import Server.response.*;
import User.entity.Main;
import User.request.*;
import User.util.ExtensionUtil;
import User.util.GuiUtil;
import User.util.HashUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileScreenController implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    Label introLabel;
    @FXML
    Button refresh;
    @FXML
    Button delete;
    @FXML
    Button upload;
    @FXML
    Button download;
    @FXML
    FlowPane txtFlowPane;
    @FXML
    FlowPane imgFlowPane;
    @FXML
    FlowPane pdfFlowPane;
    @FXML
    Label filename;
    @FXML
    Button logout;

    FileWrapper fileWrapper;
    public void logOutButtonResponse() {
        LogOutRequest logOutRequest = new LogOutRequest();
        Main.sendRequest(logOutRequest);
        LogOutResponse logOutResponse = (LogOutResponse)Main.getResponse();
        assert logOutResponse != null;
        System.out.println("Response received ");
        if(logOutResponse.getResponse().equals("Successful")) {
            System.out.println("Logout "+logOutResponse.getResponse());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
            Stage stage=(Stage)logout.getScene().getWindow();
            try {
                Scene scene=new Scene(loader.load(), logout.getScene().getWidth(), logout.getScene().getHeight());
                Main.profileScreenController = null;
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Login");
        }
        else {
            JOptionPane.showMessageDialog(null,"LogOut failed. Please try again!");
        }
    }
    String name;
    public void first(String name){
        Main.profileScreenController = this;
        this.name = name;
        introLabel.setText("longdrive for "+name);
        System.out.println("Inside the first method.");
        refreshButtonResponse();
    }
    public void refreshButtonResponse(){
        getFile();
    }
    public void getFile() {
        txtFlowPane.getChildren().clear();
        imgFlowPane.getChildren().clear();
        pdfFlowPane.getChildren().clear();
        Main.sendRequest(new FileRequest());
        System.out.println("Requesting files!");
        FileResponse fileResponse = (FileResponse) Main.getResponse();
        System.out.println("Files responded!");
        assert fileResponse != null;
        System.out.println("Response is " + fileResponse);
        ArrayList<FileWrapper> filesList = fileResponse.getFileWrapperList();
        if (filesList.isEmpty()) System.out.println("Response is empty!");
        else {
            for (FileWrapper fileWrapper : filesList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/imageCard.fxml"));
                try {
                    Node node = fxmlLoader.load();
                    CardController controller = fxmlLoader.getController();
                    controller.profileScreenController = this;
                    controller.fileWrapper = fileWrapper;
                    if(fileWrapper.getFiletype().equals("txt")){
                        txtFlowPane.getChildren().add(node);
                    }else if(fileWrapper.getFiletype().equals("pdf")){
                        pdfFlowPane.getChildren().add(node);
                    }else{
                        imgFlowPane.getChildren().add(node);
                    }
                    controller.first();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void uploadFile() throws IOException {
        File selectedFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.png","*.jpg","*.jpeg","*.txt","*.pdf"));
        selectedFile = fileChooser.showOpenDialog(null);
        assert selectedFile != null;
        FileInputStream fis = new FileInputStream(selectedFile);
        byte[] fileArray  = new byte[(int) selectedFile.length()];
        fis.read(fileArray);
        fis.close();
        String extension = ExtensionUtil.getExtension(selectedFile);
        String filename = selectedFile.getName();
        String filetype;
        if(extension.equals("txt")){
            filetype="txt";
        }else if(extension.equals("pdf")){
            filetype = "pdf";
        }else{
            filetype = "image";
        }
        UploadRequest changeProfilePicRequest = new UploadRequest(new Files(filename, filetype, fileArray));
        Main.sendRequest(changeProfilePicRequest);
        System.out.println("Upload request sent!");
        UploadResponse uploadResponse = (UploadResponse) Main.getResponse();
        System.out.println("Upload response "+ uploadResponse);
        assert uploadResponse != null;
        if(uploadResponse.getResponse().equals("Successful")) {
            JOptionPane.showMessageDialog(null,filename +" uploaded successfully!");
        }
        else {
            JOptionPane.showMessageDialog(null,"Couldn't upload!");
        }
        getFile();
    }
    public void downloadFile() throws IOException {
        if(filename.getText().isEmpty()){
            GuiUtil.alert(Alert.AlertType.ERROR, "No selected file!");
        }
        else{
            byte[] fileArray;
            DownloadRequest downloadRequest = new DownloadRequest(filename.getText());
            Main.sendRequest(downloadRequest);
            System.out.println("Download request sent!");
            DownloadResponse downloadResponse = (DownloadResponse) Main.getResponse();
            System.out.println("Download response "+downloadResponse);
            assert downloadResponse != null;
            fileArray = downloadResponse.getFiles().getFileArray();
            FileChooser fileChooser = new FileChooser();
            if(downloadResponse.getFiles().getFiletype()=="txt"){
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text files", "*.txt"));
            }else if(downloadResponse.getFiles().getFiletype()=="pdf"){
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf files", "*.pdf"));
            }else{
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
            }
            File newFile = fileChooser.showSaveDialog(null);
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(fileArray);
            JOptionPane.showMessageDialog(null,newFile.getName()+" downloaded successfully!");
            fos.close();
        }
    }
    public void deleteFile(){
        if(filename.getText().isEmpty()){
            GuiUtil.alert(Alert.AlertType.ERROR, "No selected file!");
        }
        else {
            DeleteRequest deleteRequest = new DeleteRequest(filename.getText());
            Main.sendRequest(deleteRequest);
            System.out.println("Delete request sent!");
            DeleteResponse deleteResponse = (DeleteResponse)Main.getResponse();
            System.out.println("Delete response "+deleteResponse);
            assert deleteResponse != null;
            if(deleteResponse.getResponse().equals("Successful")) {
                JOptionPane.showMessageDialog(null,filename.getText()+" deleted successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null,"Couldn't delete!");
            }
        }
        filename.setText("");
        getFile();
    }
    public void viewFile() {
        if(filename.getText().isEmpty()){
            GuiUtil.alert(Alert.AlertType.ERROR, "No selected file!");
        }
        else{
            byte[] fileArray;
            DownloadRequest downloadRequest = new DownloadRequest(filename.getText());
            Main.sendRequest(downloadRequest);
            System.out.println("View request sent!");
            DownloadResponse downloadResponse = (DownloadResponse) Main.getResponse();
            System.out.println("View response "+downloadResponse);
            assert downloadResponse != null;
            fileArray = downloadResponse.getFiles().getFileArray();
            if(downloadResponse.getFiles().getFiletype().equals("txt")){
                File outputFile1 = new File("Server/filepath/file.txt");
                try (FileOutputStream fos1 = new FileOutputStream(outputFile1)) {
                    fos1.write(fileArray);
                    System.out.println("Bytecode successfully written to  file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Desktop.getDesktop().open(outputFile1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(downloadResponse.getFiles().getFiletype().equals("pdf")){
                File outputFile2 = new File("Server/filepath/file.pdf");
                try (FileOutputStream fos2 = new FileOutputStream(outputFile2)) {
                    fos2.write(fileArray);
                    System.out.println("Bytecode successfully written to file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Desktop.getDesktop().open(outputFile2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                File outputFile3 = new File("Server/filepath/file.png");
                try (FileOutputStream fos3 = new FileOutputStream(outputFile3)) {
                    fos3.write(fileArray);
                    System.out.println("Bytecode successfully written to file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Desktop.getDesktop().open(outputFile3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    @FXML
    private PasswordField oldPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField confirmNewPasswordTextField;

    public void changePasswordButtonResponse() {
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmedNewPassword = confirmNewPasswordTextField.getText();

        if(newPassword.equals(confirmedNewPassword)) {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(HashUtil.getMd5(oldPassword),HashUtil.getMd5(newPassword));
            System.out.println("change password request sent");
            Main.sendRequest((Request) changePasswordRequest);
            ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) Main.getResponse();
            assert changePasswordResponse != null;
            if(changePasswordResponse.getResponse().equals("Successful")) {
                JOptionPane.showMessageDialog(null,"Password changed successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null,"Some error occurred.");
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"New password fields don't match.");
        }
    }

}
