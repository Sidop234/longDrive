package Server.main;

import Server.response.Response;
import Server.util.RandomString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private static Connection connection;
    private static RandomString randomString;


    public static void main(String[] args) {
        //Declaring socket
        ServerSocket serverSocket= null;
        Socket socket;
        try {
            // Creating Server Sockets, one for client requests and Chat.
            serverSocket=new ServerSocket(6970);
            System.out.println("Server Socket Created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                assert serverSocket != null;

                //ServerSocket accepts the incoming socket from the client.
                socket=serverSocket.accept();
                System.out.println("Server Socket Connected!");
                //Starting a thread that listens for client requests and creates a chat socket connection.
                Thread thread=new Thread(new RequestIdentifier(socket
//                        , chatServerSocket
                ));
                thread.start();

                //Starting a thread that sends notifications to client dynamically

                /***Notification feature***/
//                Thread notification=new Thread(new SendNotification());
//                notification.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Static method to create a connection with database using JDBC.
     *  Need to add the my 'mysql-connector-java-8.0.30' Jar in the library.
     *   Import the DriverManager class from the java.sql package
     * @return SQL Connection
     */
    public static Connection getConnection() {
        System.out.println("Connecting to database....");
        if(connection!=null)return connection;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


            String url="jdbc:mysql://localhost:3306/ld";

            connection= DriverManager.getConnection(url,"root","123");
            System.out.println("Database connected!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Static function to send the response object to the client.
     * @param outputStream we use the write method of this objectOutputStream instance to send the response.
     * @param response Object to be sent
     */
    public static void sendResponse(ObjectOutputStream outputStream, Response response){
        try {
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to receive requests from the client
     * @param inputStream used to read the incoming object
     * @return returns an Object
     */
    public static Object receiveRequest(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Request Received!");
        return inputStream.readObject();
    }

    /**
     * Used to generate random strings
     * @return Returns a String object
     */
    public static String getRandomString() {
        if(randomString == null) {
            randomString = new RandomString(8);
        }
        return randomString.nextString();
    }

}
