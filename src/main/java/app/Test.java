package app;

import app.domain.Bike;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
     Bike bike = new Bike(1,"Cube Aim","Mountain Bikes" , 45, true );
        System.out.println(bike);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File("database/bike.txt");

        List<Bike> bikes = List.of(
                new Bike(1,"Cannondale Trail SE","Mountain Bikes" , 45, true ),
                new Bike(2,"Buls Copperhead","Mountain Bikes" , 55, true ),
                new Bike(3,"Cube Nuroad One","Gravel" , 35, true ));

        try {
            mapper.writeValue(file, bikes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
