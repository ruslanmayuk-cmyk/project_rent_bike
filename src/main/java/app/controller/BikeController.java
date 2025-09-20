package app.controller;

import app.domain.Bike;
import app.exeptions.BikeNotFoundException;
import app.exeptions.BikeSaveException;
import app.exeptions.BikeUpdateException;
import app.service.BikeService;

import java.io.IOException;
import java.util.List;

public class BikeController {
    private final BikeService service; // поле для связи с сервисом

    public BikeController() throws IOException {
        service = new BikeService();
    }

    // копируем методы из  сервиса байков и очищаем тела методов, оставляем только названия

    // Для сохранения байка от клиента требуется только название, тип и цена
    public Bike save(String title, double price) throws BikeSaveException, IOException {
        Bike bike = new Bike (title, price);
        return service.save(bike);
    }

    public List<Bike> getAllActiveProducts() throws IOException {
        return service.getAllActiveBikes(); // вызываем метод из сервиса
    }

    public Bike getActiveProductById(int id) throws IOException, BikeNotFoundException {
        return service.getActiveBikeById(id);
    }

    public void update(int id, double price) throws BikeUpdateException, IOException {
        Bike bike = new Bike (id, price);
        service.update(bike);
    }

    public void deleteById(int id) throws IOException, BikeNotFoundException {
        service.deleteById(id);
    }

    public void deleteByTitle(String title) throws IOException, BikeNotFoundException {
        service.deleteByTitle(title);
    }

    public void restoreById(int id) throws IOException, BikeNotFoundException {
        service.restoreById(id);
    }

    public int getActiveBikesCount() throws IOException {
        return service.getActiveBikesCount();
    }

    public double getActiveBikesTotalCost() throws IOException {
        return service.getActiveBikesTotalCost();
    }

    public double getActiveBikesAveragePrice() throws IOException {
        return service.getActiveBikesAveragePrice();
    }
}
