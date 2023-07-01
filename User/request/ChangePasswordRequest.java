package User.request;


import java.io.Serializable;

public class ChangePasswordRequest extends Request implements Serializable {
    private String oldPassword,newPassword;

    /**
     * When a student wants to change her password, this request is sent
     * @param oldPassword to verify the credentials
     * @param newPassword to be set
     */
    public ChangePasswordRequest(String oldPassword, String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getter and Setter methods:

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}