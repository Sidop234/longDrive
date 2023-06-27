package Server.requestHandler;

import Server.response.DeleteResponse;
import Server.table.FileTable;
import User.request.DeleteRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRequestHandler extends  RequestHandler{
    private ObjectOutputStream oos;
    private Connection connection;
    private DeleteRequest deleteRequest;
    public DeleteRequestHandler(Connection connection, ObjectOutputStream oos, DeleteRequest deleteRequest){
        this.connection = connection;
        this.oos = oos;
        this.deleteRequest = deleteRequest;
    }
    @Override
    public void sendResponse(String userID) {
        int result = 0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(FileTable.QUERY_DELETE_FILE);
            preparedStatement.setInt(1,Integer.parseInt(userID));
            preparedStatement.setString(2,deleteRequest.getFilename());
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(result==0)oos.writeObject(new DeleteResponse("Failure"));
            else oos.writeObject(new DeleteResponse("Successful"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
