package User.request;

import Server.entity.Files;

import java.io.Serializable;

public class UploadRequest extends Request implements Serializable {
    Files files;
    public  UploadRequest(Files files){
        this.files = files;
    }

    public Files getFile() {
        return files;
    }
}
