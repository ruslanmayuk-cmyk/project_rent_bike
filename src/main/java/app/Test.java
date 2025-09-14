package app;

import app.domain.Bike;
import app.domain.BikeType;
import app.domain.Customer;
import app.repository.BikeRepositoty;
import app.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
//    public static void main(String[] args) throws IOException {
//     Bike bike = new Bike(1,"Cube Aim",BikeType.MOUNTAIN , 45, true );
//        System.out.println(bike);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        File file = new File("database/bike.txt");
//
//        List<Bike> bikes = List.of(
//                new Bike(1,"Cannondale Trail SE", BikeType.MOUNTAIN , 45, true ),
//                new Bike(2,"Buls Copperhead",BikeType.GRAVEL , 55, true ),
//                new Bike(3,"Cube Nuroad One",BikeType.ROAD , 35, true ));
//
//        try {
//            mapper.writeValue(file, bikes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//public static void main(String[] args) throws IOException {
//    // Ручная проверка репозитория.
//    // Создаём экземпляр класса BikeRepositoty , что бы протестировать его методы
//
//    BikeRepositoty  repository = new BikeRepositoty();
//    Bike bike = new Bike(4,"Orbea Onna",BikeType.MOUNTAIN,30,true);
//    repository.save(bike);
//
//    repository.findAll().forEach(System.out::println);
//
//    System.out.println();
//    Bike bikeById = repository.findById(4);
//        System.out.println("Найденный продукт");
//        System.out.println(bikeById);
//
//
//    Bike newBike = new Bike(4,"Orbea Onna",BikeType.MOUNTAIN,130,true);
//    repository.update(newBike);
//
//    repository.deleteById(1);
//
//
//}
public static void main(String[] args) throws IOException {
    CustomerRepository repository = new CustomerRepository();

    Customer customer1 = new Customer();
    customer1.setName("Николай");
    customer1.setActive(true);

    Customer customer2 = new Customer();
    customer2.setName("Андрей");
    customer2.setActive(true);

    Customer customer3 = new Customer();
    customer3.setName("Сергей");
    customer3.setActive(true);

    repository.save(customer1);
    repository.save(customer2);
    repository.save(customer3);
}
}
