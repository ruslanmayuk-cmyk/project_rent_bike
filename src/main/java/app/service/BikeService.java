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

    public BikeService() throws IOException {
        repository = new BikeRepository();
    }

    //   Сохранить байк в базе данных
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

    //   Получить все активные байки из базы данных
    public List<Bike> getAllActiveBikes() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Bike::isActive)
                .toList();
    }

    //   Получить один активный байк по id из базы данных
    public Bike getActiveBikeById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike == null || !bike.isActive()) {
            throw new BikeNotFoundException(id);
        }
        return bike;
    }

    //   Обновить байк
    public void update(Bike bike) throws BikeUpdateException, IOException {
        if (bike == null) {
            throw new BikeUpdateException("Байк не может быть null");
        }

        if (bike.getPrice() <= 0) {
            throw new BikeUpdateException("Цена байк должна быть положительной");
        }
        bike.setActive(true);
        repository.update(bike);
    }

    //   Удаление по id (soft delete)- изменение статуса активности
      public void deleteById(int id) throws IOException, BikeNotFoundException {
        Bike bike = getActiveBikeById(id);
        bike.setActive(false);
        repository.update(bike); // сохраняем изменения
    }

    // Удаление по названию (soft delete)
    public void deleteByTitle(String title) throws IOException, BikeNotFoundException {
       Bike bike = getAllActiveBikes()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .peek(x -> x.setActive(false))
                .findFirst()
                .orElseThrow(
            ()-> new BikeNotFoundException(title)
                );
        repository.update(bike); // сохраняем изменения

    }

    //   Восстановление байка по id
    public void restoreById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike != null) {
            bike.setActive(true);
            repository.update(bike); // сохраняем изменения
        } else {
            throw new BikeNotFoundException(id);
        }
    }

    //   Статистика = вернуть общее количество байков в базе данных (активных).
    public int getActiveBikesCount() throws IOException {
        return getAllActiveBikes().size();
    }

    //  Статистика = вернуть суммарную стоимость всех байков в базе данных (активных).
    public double getActiveBikesTotalCost() throws IOException {
        return getAllActiveBikes()
                .stream()
                .mapToDouble(Bike::getPrice)
                .sum();
    }

    //   Статистика = вернуть среднюю стоимость аренды байка в базе данных (из активных)
    public double getActiveBikesAveragePrice() throws IOException {
        int сount = getActiveBikesCount();

        if (сount == 0) {
            return 0.0;
        }

        return getActiveBikesTotalCost()/сount;
    }

    // Возвращает стоимость аренды для байка с учётом rentDays, если days = 0, берём текущее rentDays
    public double calculateRentCost(int id, int days) throws IOException, BikeNotFoundException {
        Bike bike = getActiveBikeById(id);

        if (days > 0) {
            bike.setRentDays(days);
            repository.update(bike); // сохраняем изменения
        } else if (bike.getRentDays() <= 0) {
            bike.setRentDays(1); // дефолт 1 день, если не установлен
        }

        return bike.getPrice() * bike.getRentDays();
    }


}
