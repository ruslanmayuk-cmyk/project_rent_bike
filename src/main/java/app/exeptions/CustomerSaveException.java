package app.exeptions;

public class CustomerSaveException extends Exception{

    public CustomerSaveException(String message) {
        super(message);
    }
}