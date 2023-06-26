package Server.main;

import Server.requestHandler.*;
import User.request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestIdentifier implements Runnable{
    Socket socket;
    ObjectOutputStream oos=null;
    ObjectInputStream ois=null;
    public String userID;

    /**
     * Constructor that takes multiple socket parameters and initialises the I/O streams accordingly
     * @param socket is used to create ObjectOutput/Input streams through which communication takes place
     *
//     * @param chatServerSocket used to create a chat connection

     */
    public RequestIdentifier(Socket socket){
        this.socket=socket;
        try {
            System.out.println("io streams instantiated in request identifier!");
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Abstract method of the Runnable interface.
     * This method is called when the thread starts.
     * Listens for requests from the client, uses if else to categorise the request
     * Creates the respective requestHandler instance and calls the sendResponse method.
     * sendResponse method processes the request and sends the appropriate response back to the client
     */

    @Override
    public void run() {
        System.out.println("New identifier thread started!");
        while(socket.isConnected()) {
            Object request;
            try {
                System.out.println("Receiving request...");
                request = Server.receiveRequest(ois);
                System.out.println("Finding instance of request!");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

            if (request instanceof LoginRequest) {
                userID = ((LoginRequest) request).getUsername();
                LoginRequestHandler loginRequestHandler = new LoginRequestHandler(oos, (LoginRequest) request, Server.getConnection());
                loginRequestHandler.sendResponse(userID);

            } else if (request instanceof RegisterRequest) {
                System.out.println("Registration request being handled!");
                RegisterRequestHandler registerRequestHandler = new RegisterRequestHandler((RegisterRequest) request, oos, Server.getConnection());
                registerRequestHandler.sendResponse(userID);
            }
//            else if(request instanceof ChangePasswordRequest){
//                ChangePasswordRequestHandler changePasswordRequestHandler=new ChangePasswordRequestHandler(Server.getConnection(),oos,(ChangePasswordRequest)request);
//                changePasswordRequestHandler.sendResponse(userID);
//            }
            else if(request instanceof LogOutRequest){
                LogOutRequestHandler logOutRequestHandler=new LogOutRequestHandler(Server.getConnection(),oos);
                logOutRequestHandler.sendResponse(userID);
            }
            else if(request instanceof FileRequest){
                FileRequestHandler fileRequestHandler = new FileRequestHandler(Server.getConnection(), oos);
                fileRequestHandler.sendResponse(userID);
            }
            else if(request instanceof UploadRequest){
                UploadRequestHandler uploadRequestHandler = new UploadRequestHandler(Server.getConnection(), oos ,(UploadRequest)request);
                uploadRequestHandler.sendResponse(userID);
            }
            else if(request instanceof DownloadRequest){
                DownloadRequestHandler downloadRequestHandler = new DownloadRequestHandler(Server.getConnection(), oos, (DownloadRequest)request);
                downloadRequestHandler.sendResponse(userID);
            }
            else if(request instanceof DeleteRequest){
                DeleteRequestHandler deleteRequestHandler = new DeleteRequestHandler(Server.getConnection(), oos, (DeleteRequest)request);
                deleteRequestHandler.sendResponse(userID);
            }
            else{
                System.out.println("No instance found");
                try {
                    oos.writeObject(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        System.out.println("Should have broken");
    }
}
