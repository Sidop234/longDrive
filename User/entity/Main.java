package User.entity;
import User.controller.LoginController;
import User.controller.ProfileScreenController;
import User.request.Request;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {
    public static Socket socket=null;
    public static ObjectInputStream ois=null;
    public static ObjectOutputStream oos=null;
    public static String userRegistrationNumber;
    public static String userFullName = "CAFE BABE";
    public static ProfileScreenController profileScreenController = null;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        System.out.println("Application invoked!");
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
        System.out.println("Login FXML Loaded!");
        try {
            System.out.println("Creating a new connection");
            socket=new Socket("localhost",6970);
            System.out.println(socket);
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection established and io streams created");

            System.out.println(Thread.currentThread());
        } catch (IOException e) {
            System.out.println("socket connect negative!");
            e.printStackTrace();
        }

        primaryStage.setTitle("Sign In");
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            LoginController login=fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();

    }
    public static void sendRequest(Request request){
        try {
            oos.writeObject(request);
            oos.flush();
            System.out.println("Request sent to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object getResponse(){
        try {
            System.out.println("response is sent on Main method");
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
