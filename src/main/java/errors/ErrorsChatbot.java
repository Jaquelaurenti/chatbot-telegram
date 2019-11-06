package errors;

@SuppressWarnings("serial")
public class ErrorsChatbot extends Exception {
    public ErrorsChatbot(String message) {
        super(message);
    }
}
