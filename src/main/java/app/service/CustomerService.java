package app.service;

import app.domain.Bike;
import app.domain.Customer;
import app.exeptions.BikeNotFoundException;
import app.exeptions.CustomerNotFoundException;
import app.exeptions.CustomerSaveException;
import app.exeptions.CustomerUpdateException;
import app.repository.CustomerRepository;

import java.io.IOException;
import java.util.List;

public class CustomerService {
    private final CustomerRepository repository;
    private final BikeService bikeService;

    // Конструктор
    public CustomerService() throws IOException {
        repository = new CustomerRepository();
        bikeService = new BikeService();
    }

    //   Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается активным).
    public Customer save(Customer customer) throws IOException, CustomerSaveException {
        if (customer == null) {
            throw new CustomerSaveException("Покупатель не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Имя покупателя не может быть пустым");
        }

        customer.setActive(true);
        return repository.save(customer);
    }

    //   Вернуть всех покупателей из базы данных (активных).
    public List<Customer> getAllActiveCustomers() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
    }

    //    Вернуть одного покупателя из базы данных по его идентификатору (если он активен).
    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);

        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

    //    Изменить одного покупателя в базе данных по его идентификатору.
    public void update(Customer customer) throws CustomerUpdateException, IOException {
        if (customer == null) {
            throw new CustomerUpdateException("Покупатель не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerUpdateException("Имя покупателя не может быть пустым");
        }

        repository.update(customer);
    }

    //    Удалить покупателя из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, CustomerNotFoundException {
        getActiveCustomerById(id).setActive(false);
    }

    //    Удалить покупателя из базы данных по его имени.
    public void deleteByName(String name) throws IOException {
        getAllActiveCustomers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }

    //    Восстановить удалённого покупателя в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);

        if (customer != null) {
            customer.setActive(true);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    //    Вернуть общее количество покупателей в базе данных (активных).
    public int getActiveCustomerCount() throws IOException {
        return getAllActiveCustomers().size();
    }

    //    Вернуть стоимость корзины покупателя по его идентификатору (если он активен).
    public double getCustomerCartTotalPrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getBikes()
                .stream()
                .filter(Bike::isActive)
                .mapToDouble(Bike::getPrice)
                .sum();
    }

    //    Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    public double getCustomerCartAveragePrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getBikes()
                .stream()
                .filter(Bike::isActive)
                .mapToDouble(Bike::getPrice)
                .average()
                .orElse(0.0);
    }

    //    Добавить байк в корзину покупателя по их идентификаторам (если оба активны)
    public void addBikeToCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        Customer customer = getActiveCustomerById(customerId);
        Bike bike = bikeService.getActiveBikeById(bikeId);
        customer.getBikes().add(bike);
    }

    //    Удалить байк из корзины покупателя по идентификатору
    public void removeBikeFromCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        Customer customer = getActiveCustomerById(customerId);
        Bike bike = bikeService.getActiveBikeById(bikeId);
        customer.getBikes().remove(bike);
    }

    //    Полностью очистить корзину покупателя по его идентификатору (если он активен
    public void clearCustomerCart(int id) throws IOException, CustomerNotFoundException {
        getActiveCustomerById(id).getBikes().clear();
    }
}
