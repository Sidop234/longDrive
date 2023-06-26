package Server.entity;

import java.io.Serializable;
import java.sql.Blob;

public class Files implements Serializable {
    private String filename;
    private String filetype;
    private byte[] fileArray;
    public Files(String filename, String filetype, byte[] fileArray){
        this.filename= filename;
        this.filetype = filetype;
        this.fileArray = fileArray;
    }

    public String getFilename() {
        return filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public byte[] getFileArray() {
        return fileArray;
    }
}
