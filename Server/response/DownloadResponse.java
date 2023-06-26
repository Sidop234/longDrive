package Server.response;

import Server.entity.Files;

import java.io.Serializable;

public class DownloadResponse extends Response implements Serializable {
    Files files;
    public DownloadResponse(Files files){
        this.files = files;
    }

    public Files getFiles() {
        return files;
    }
}
