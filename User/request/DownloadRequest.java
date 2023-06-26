package User.request;

import java.io.Serializable;

public class DownloadRequest extends Request implements Serializable {
    String filename;
    public  DownloadRequest(String filename){
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
