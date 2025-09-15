package app.exeptions;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(int id) {
        super(String.format("Покупатель с идетификатором %d не найден", id));
    }

    public CustomerNotFoundException(String name) {
        super(String.format("Customer with name %s not found", name));
    }
}