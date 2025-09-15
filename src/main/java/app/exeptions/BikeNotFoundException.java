package app.exeptions;

public class BikeNotFoundException extends Exception{
    public BikeNotFoundException(int id) {
        super(String.format("Байк с идетификатором %d не найден", id));
    }
}
