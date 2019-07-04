package tran.example.ppmtool.payload;

public class AccountActivatedResponse {
    private String message;

    public AccountActivatedResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AccountActivatedResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
