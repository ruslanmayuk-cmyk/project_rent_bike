package app;

import app.domain.Bike;
import app.domain.BikeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
     Bike bike = new Bike(1,"Cube Aim",BikeType.MOUNTAIN , 45, true );
        System.out.println(bike);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File("database/bike.txt");

        List<Bike> bikes = List.of(
                new Bike(1,"Cannondale Trail SE", BikeType.MOUNTAIN , 45, true ),
                new Bike(2,"Buls Copperhead",BikeType.GRAVEL , 55, true ),
                new Bike(3,"Cube Nuroad One",BikeType.ROAD , 35, true ));

        try {
            mapper.writeValue(file, bikes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
