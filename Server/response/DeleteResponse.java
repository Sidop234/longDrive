package Server.response;

import java.io.Serializable;

public class DeleteResponse extends Response implements Serializable{
    private String response;
    public DeleteResponse (String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
