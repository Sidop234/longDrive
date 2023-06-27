package Server.requestHandler;

import Server.entity.FileWrapper;
import Server.response.FileResponse;
import Server.table.FileTable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileRequestHandler extends RequestHandler{
    Connection connection;
    ObjectOutputStream oos;
    public FileRequestHandler(Connection connection, ObjectOutputStream oos){
        this.connection = connection;
        this.oos = oos;
    }
    @Override
    public void sendResponse(String userID) {
        ArrayList<FileWrapper> fileWrapperList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(FileTable.QUERY_FETCH_FILES);
            preparedStatement.setInt(1,Integer.parseInt(userID));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("Non-empty result set!");
                    fileWrapperList.add( new FileWrapper(
                            resultSet.getString(FileTable.COLUMN_FILENAME),
                            resultSet.getString(FileTable.COLUMN_FILETYPE)
                    ));
            }
            oos.writeObject(new FileResponse(fileWrapperList));
            oos.flush();
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
