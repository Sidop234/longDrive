package Server.entity;

import java.io.Serializable;
import java.sql.Blob;

public class FileWrapper implements Serializable {
    private String filename;
    private String filetype;
    public FileWrapper(String filename, String filetype){
        this.filename= filename;
        this.filetype = filetype;
    }

    public String getFilename() {
        return filename;
    }

    public String getFiletype() {
        return filetype;
    }
}
