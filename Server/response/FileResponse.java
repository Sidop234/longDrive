package Server.response;

import Server.entity.FileWrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class FileResponse extends Response implements Serializable {
    private ArrayList<FileWrapper> fileWrapperList;

    public FileResponse(ArrayList<FileWrapper> fileWrapperList) {
        this.fileWrapperList = fileWrapperList;
    }

    public ArrayList<FileWrapper> getFileWrapperList() {
        return fileWrapperList;
    }
}
