package Server.response;

import java.io.Serializable;

public class UploadResponse extends Response implements Serializable{
    private String response;
    public UploadResponse (String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
