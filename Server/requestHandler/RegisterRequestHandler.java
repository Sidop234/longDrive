package Server.requestHandler;

import Server.response.RegisterResponse;
import Server.table.UserTable;
import User.request.RegisterRequest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterRequestHandler extends RequestHandler{
    private RegisterRequest registerRequest;
    private ObjectOutputStream oos;
    private Connection connection;

    public RegisterRequestHandler(RegisterRequest registerRequest, ObjectOutputStream oos, Connection connection) {
        this.registerRequest = registerRequest;
        this.oos = oos;
        this.connection = connection;
    }
    @Override
    public void sendResponse(String userID) {
        PreparedStatement preparedStatement;
        System.out.println("Registration process invoked !");
        int result = 0;
        try {
            preparedStatement=connection.prepareStatement(UserTable.QUERY_REGISTER);
            preparedStatement.setString(1,registerRequest.getUsername());
            preparedStatement.setString(2,registerRequest.getFirstName());
            preparedStatement.setString(3,registerRequest.getLastName());
            preparedStatement.setString(4,registerRequest.getEmailID());
            preparedStatement.setString(5,registerRequest.getPassword());
            System.out.println(preparedStatement);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result==0) {
            try {
                oos.writeObject(new RegisterResponse(""));
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                oos.writeObject(new RegisterResponse("Registered successfully"));
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    }


