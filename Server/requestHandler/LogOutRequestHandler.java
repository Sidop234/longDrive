package Server.requestHandler;

import Server.response.LogOutResponse;
import Server.table.UserTable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogOutRequestHandler extends RequestHandler  {
    private Connection connection;
    private ObjectOutputStream oos;

    public LogOutRequestHandler(Connection connection, ObjectOutputStream oos) {
        this.connection = connection;
        this.oos = oos;
    }

    @Override
    public void sendResponse(String userID) {
        int result=0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(UserTable.QUERY_UPDATE_LAST_ACTIVE);
            preparedStatement.setInt(1,Integer.parseInt(userID));
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(result==0)oos.writeObject(new LogOutResponse(""));
            else oos.writeObject(new LogOutResponse("Successful"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
