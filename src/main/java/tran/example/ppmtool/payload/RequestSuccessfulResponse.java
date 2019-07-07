package tran.example.ppmtool.payload;

public class RequestSuccessfulResponse {
    private String message;

    public RequestSuccessfulResponse(String message) {
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
        return "RequestSuccessfulResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
