package User.request;

import java.io.Serializable;

public class DeleteRequest extends Request implements Serializable {
    String filename;
    public  DeleteRequest(String filename){
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
