package Server.requestHandler;

import Server.entity.Files;
import Server.response.DownloadResponse;
import Server.table.FileTable;
import User.request.DownloadRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;

public class DownloadRequestHandler extends RequestHandler{
    private ObjectOutputStream oos;
    private Connection connection;
    private DownloadRequest downloadRequest;
    public DownloadRequestHandler(Connection connection, ObjectOutputStream oos, DownloadRequest downloadRequest){
        this.connection = connection;
        this.oos = oos;
        this.downloadRequest = downloadRequest;
    }
    @Override
    public void sendResponse(String userID) {
        try{
            Files files = null;
            PreparedStatement preparedStatement = connection.prepareStatement(FileTable.QUERY_DOWNLOAD_FILE);
            preparedStatement.setInt(1,Integer.parseInt(userID));
            preparedStatement.setString(2,downloadRequest.getFilename());
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Blob blob =  resultSet.getBlob(FileTable.COLUMN_FILEBLOB);
                int blobLength = (int) blob.length();
                byte[] fileArray = blob.getBytes(1, blobLength);
                blob.free();
                files = new Files(resultSet.getString(FileTable.COLUMN_FILENAME),resultSet.getString(FileTable.COLUMN_FILETYPE),fileArray);
            }
            oos.writeObject(new DownloadResponse(files));
            oos.flush();
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
