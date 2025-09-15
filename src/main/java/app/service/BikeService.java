package app.service;

import app.domain.Bike;
import app.exeptions.BikeNotFoundException;
import app.exeptions.BikeSaveException;
import app.exeptions.BikeUpdateException;
import app.repository.BikeRepository;

import java.io.IOException;
import java.util.List;

public class BikeService {
    private final BikeRepository repository;

    public BikeService() throws IOException, IOException {
        repository = new BikeRepository();
    }

    //   Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
    public Bike save(Bike bike) throws BikeSaveException, IOException {
        if (bike == null) {
            throw new BikeSaveException("Байк не может быть null");
        }

        String title = bike.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new BikeSaveException("Наименование байка не может быть пустым");
        }

        if (bike.getPrice() <= 0) {
            throw new BikeSaveException("Цена байка не должна быть отрицательной");
        }

        bike.setActive(true);
        return repository.save(bike);
    }

    //   Вернуть все продукты из базы данных (активные).
    public List<Bike> getAllActiveBikes() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Bike::isActive)
//                .filter(x -> x.isActive())
                .toList();
    }

    //   Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Bike getActiveBikeById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike == null || !bike.isActive()) {
            throw new BikeNotFoundException(id);
        }
        return bike;
    }

    //   Изменить один продукт в базе данных по его идентификатору.
    public void update(Bike bike) throws BikeUpdateException, IOException {
        if (bike == null) {
            throw new BikeUpdateException("Байк не может быть null");
        }

        if (bike.getPrice() <= 0) {
            throw new BikeUpdateException("Цена байк должна быть положительной");
        }

        repository.update(bike);
    }

    //   Удалить продукт из базы данных по его идентификатору.
//   По требованиям должно происходить soft удаление - изменение статуса активности продукта
    public void deleteById(int id) throws IOException, BikeNotFoundException {
        getActiveBikeById(id).setActive(false);
    }

    //   Удалить продукт из базы данных по его наименованию.
    public void deleteByTitle(String title) throws IOException {
        getAllActiveBikes()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));
    }

    //   Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike != null) {
            bike.setActive(true);
        } else {
            throw new BikeNotFoundException(id);
        }
    }

    //   Вернуть общее количество продуктов в базе данных (активных).
    public int getActiveBikesCount() throws IOException {
        return getAllActiveBikes().size();
    }

    //   Вернуть суммарную стоимость всех продуктов в базе данных (активных).
    public double getActiveBikesTotalCost() throws IOException {
        return getAllActiveBikes()
                .stream()
                .mapToDouble(Bike::getPrice)
                .sum();
    }

    //   Вернуть среднюю стоимость продукта в базе данных (из активных)
    public double getActiveBikesAveragePrice() throws IOException {
        int bikeCount = getActiveBikesCount();

        if (bikeCount == 0) {
            return 0.0;
        }

        return getActiveBikesTotalCost()/bikeCount;
    }
}
