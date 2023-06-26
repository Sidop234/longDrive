package Server.requestHandler;

import Server.response.UploadResponse;
import Server.table.FileTable;
import User.request.UploadRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadRequestHandler extends RequestHandler{
    private ObjectOutputStream oos;
    private Connection connection;
    private UploadRequest uploadRequest;
    public UploadRequestHandler(Connection connection, ObjectOutputStream oos, UploadRequest uploadRequest){
        this.connection = connection;
        this.oos = oos;
        this.uploadRequest = uploadRequest;
    }
    @Override
    public void sendResponse(String userID) {
        int result = 0;
        try {
            InputStream fis= new ByteArrayInputStream(uploadRequest.getFile().getFileArray());
            PreparedStatement preparedStatement=connection.prepareStatement(FileTable.QUERY_INSERT_FILE);
            preparedStatement.setInt(1,Integer.parseInt(userID));
            preparedStatement.setString(2,uploadRequest.getFile().getFilename());
            preparedStatement.setString(3, uploadRequest.getFile().getFiletype());
            preparedStatement.setBlob(4,fis);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(result==0)oos.writeObject(new UploadResponse("Failure"));
            else oos.writeObject(new UploadResponse("Successful"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
