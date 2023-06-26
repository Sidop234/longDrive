package Server.response;

import java.io.Serializable;

public class LogOutResponse extends Response implements Serializable {
    private String response;
    public LogOutResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}