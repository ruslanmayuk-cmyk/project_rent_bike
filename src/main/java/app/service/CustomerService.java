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
        getAllActiveCustomers() // получаем всех активных пользователей
                .stream()
                .filter(x -> x.getName().equals(name)) // находим пользователя, к-рый соответствует имени
                .forEach(x -> x.setActive(false)); // forEach переназначит статус пользователю,
                // к-рый остался в стриме на false
    }

    //    Восстановить удалённого покупателя в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, CustomerNotFoundException {
        // типизируем переменную customer, в которую будем сохранть покупателя
        // обращаемся к репозиторию, вызываем метод findById и передаем в него id
        Customer customer = repository.findById(id);


        if (customer != null) {
            customer.setActive(true);
        } else {  //  может быть, что покупатель не найден  - тогда обрабатываем ошибку
            throw new CustomerNotFoundException(id);
        }
    }

    //    Вернуть общее количество покупателей в базе данных (активных).
    public int getActiveCustomerCount() throws IOException {
        //  возвращаем сразу результат метода getAllActiveCustomers + size()
        return getAllActiveCustomers().size();
    }

    //    Вернуть стоимость корзины покупателя по его идентификатору (если он активен).
    public double getCustomerCartTotalPrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)// получаем покупателя
                .getBikes() // получаем его велики
                .stream()
                .filter(Bike::isActive) // нужны только активные
                .mapToDouble(Bike::getPrice) // получаем их цену
                .sum(); // суммируем
    }

    //    Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    public double getCustomerCartAveragePrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)// получаем пользователя
                .getBikes() // получаем байки
                .stream()
                .filter(Bike::isActive) // фильтруем, что бы были только активные
                .mapToDouble(Bike::getPrice) // получаем цены
                .average()
                .orElse(0.0); // если великов не было
    }

    //    Добавить байк в корзину покупателя по их идентификаторам (если оба активны)
    public void addBikeToCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        Customer customer = getActiveCustomerById(customerId); // забираем покупателя в переменную
        Bike bike = bikeService.getActiveBikeById(bikeId); // получаем в переменную байк
        customer.getBikes().add(bike); // получаем от пользователя список байков и делаем в него add
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
