package app.repository;
// CRUD - Create(создать), Read(прочитать), Update(обновить), Delete(удалить)

import app.domain.Bike;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BikeRepository {

    private final File database;    // Файл, который является базой данных
    private final ObjectMapper mapper;  // Маппер для чтения и записи объектов в файл
    private int maxId;         // Поле, которое хранит максимальный идентификатор, сохраннёный в БД

    // Конструктор
    // В этом конструкторе мы инициализируем все поля репозитория
    public BikeRepository() throws IOException {
        database = new File("database/bike.txt");
        mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Выясняем, какой идетификатор в БД на данный момент максимальный
        List<Bike> bikes = findAll(); // получаем спискок всех байков, к-рые сейчас хранятся в БД
        if (!bikes.isEmpty()) {         // если спискок не пустой
            Bike lastBike = bikes.get(bikes.size() - 1); // записываем в переменную последний байк
            maxId = lastBike.getId();     // находим id  через геттер
        }
    }

    // Сохранение нового байка в БД
    public Bike save(Bike bike) throws IOException {
        bike.setId(++maxId);           // идентификатор нового байка
        List<Bike> bikes = findAll();  // получаем спискок всех байков, к-рые сейчас хранятся в БД
        bikes.add(bike);               // и добавлем в список новый байк
        mapper.writeValue(database, bikes);  // перезаписываем БД с новым байком
        return bike;
    }

    // Чтение всех байков из БД
    public List<Bike> findAll() throws IOException { // метод возвращает список List<Bike> из БД
        try {
            // Получаем продукты и формируем список
            Bike[] bikes = mapper.readValue(database, Bike[].class); //Записываем в массив из БД с помощью mapper
            return new ArrayList<>(Arrays.asList(bikes)); // возвращаем список
            // Обращаемся к классу Arrays, метод asList, что бы передать в список все значения из массива
        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }

    // Чтение одного байка по id
    public Bike findById(int id) throws IOException { // на вход принимаем id
        return findAll()   // получаем спискок всех байков и сразу результат возвращаем
                .stream()  // запускаем стрим
                .filter(x -> x.getId() == id) // фильтруем все байки, к-рые есть, по id = спискок из одного байка
                .findFirst()  // этот метод возвращает из списка один элемент(первый)
                .orElse(null); // иначе, если такого id нет в списке - вернется null
    }

    // Обновление существующего в БД байка
    // этот метод метод будет менять только цену байка
    public void update(Bike bike) throws IOException {
        int id = bike.getId(); // сохраняем в переменную id обьекта
        double newPrice = bike.getPrice(); // сохраняем новую цену,
                                          // эта цена передается в метод update вместе с обьектом.
        boolean active = bike.isActive();
        List<Bike> bikes = findAll(); // получаем спискок всех байков, к-рые сейчас хранятся в БД
        bikes
                .stream()  // запускаем стрим
                .filter(x -> x.getId() == id) // фильтруем все байки, к-рые есть, по id = спискок из нашего байка
                .forEach(x -> {
                                    x.setPrice(newPrice);
                                    x.setActive(active);
                                   }
                         );
           // с помощью forEach меняем у обьекта, к-рый остался в стриме цену. Все остальные продукты не
          // задействуюся, т.к. после filter в стриме остался только один продукт

        mapper.writeValue(database, bikes); // перезаписываем БД

    }

        // Удаление байка
        public void deleteById(int id) throws IOException { // что бы знать, что удалять - нужен id
            List<Bike> bikes = findAll(); // получаем спискок всех байков, к-рые сейчас хранятся в БД
            bikes.removeIf(x -> x.getId() == id); // метод removeIf удалит,если выполниться условие(поиск по id)
            mapper.writeValue(database,  bikes); // перезаписываем БД
        }

}
