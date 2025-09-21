package client;

import app.controller.BikeController;
import app.controller.CustomerController;
import app.domain.Bike;
import app.domain.BikeType;

import java.util.Scanner;

public class Client {
    private static BikeController bikeController;
    private static CustomerController customerController;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Создаём объекты контроллеров для взаимодействия с приложением
        try {
            bikeController = new BikeController(); // вызываем конструктор
            customerController = new CustomerController();
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Логика общения для пользователя:
        while (true) {
            System.out.println("Выберите желаемую операцию");
            System.out.println("1 - операции с байками");
            System.out.println("2 - операции с покупателями");
            System.out.println("0 - выход");

            String input = scanner.nextLine(); // принимаем выбор пользователя

            switch (input) { // варианты для действия, которое выберет пользователь
                case "1":
                    bikeOperations();
                    break;
                case "2":
                    customerOperations();
                    break;
                case "0":
                    return; // return из главного метода = завершение программы
                default:
                    System.out.println("Некорректный ввод!");
                    break;
            }
        }

    }

    public static void bikeOperations() { // следующий уровень меню
        while (true) {
            try {
                System.out.println("Выберите желаемую операцию с байками:");
                System.out.println("1 - сохранить байк");
                System.out.println("2 - получить все байки");
                System.out.println("3 - получить байк по идентификатору");
                System.out.println("4 - изменить байк");
                System.out.println("5 - удалить байк по идентификатору");
                System.out.println("6 - удалить байк по названию");
                System.out.println("7 - восстановить байк по идентификатору");
                System.out.println("8 - получить количество байков");
                System.out.println("9 - получить суммарную стоимость всех байков");
                System.out.println("10 - получить среднюю стоимость байка");
                System.out.println("0 - выход");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Введите название байка");
                        String title = scanner.nextLine();
                        System.out.println("Введите цену цену байка");
                        double price = Double.parseDouble(scanner.nextLine());

                        System.out.println("Выберите тип байка:"); // выводим список всех типов байков
                        // BikeType.values() возвращает массив всех элементов enum BikeType.
                        for (BikeType type : BikeType.values()) {
                            System.out.println((type.ordinal() + 1) + " - " + type.getRussianName());
                            // type.ordinal() —  порядковый номер элемента в enum
                        }
                        // считываем выбор пользователя
                        int typeChoice = Integer.parseInt(scanner.nextLine());
                        // получаем соответствующий элемент enum
                        BikeType bikeType = BikeType.values()[typeChoice - 1];
                        // массив начинается с индекса 0, а пользователь вводил с 1
                        Bike bike = new Bike(title, bikeType, price);
                        System.out.println(bikeController.save(bike));
                        break;
                    case "2":
                        bikeController.getAllActiveBikes().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.println("Введите идентификатор байка");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println(bikeController.getActiveBikeById(id));
                        break;
                    case "4":
                        System.out.println("Введите идентификатор байка");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите новую цену байка");
                        price = Double.parseDouble(scanner.nextLine());
                        bikeController.update(id, price);
                        break;
                    case "5":
                        System.out.println("Введите идентификатор байка");
                        id = Integer.parseInt(scanner.nextLine());
                        bikeController.deleteById(id);
                        break;
                    case "6":
                        System.out.println("Введите название байка");
                        title = scanner.nextLine();
                        bikeController.deleteByTitle(title);
                        break;
                    case "7":
                        System.out.println("Введите идентификатор байка");
                        id = Integer.parseInt(scanner.nextLine());
                        bikeController.restoreById(id);
                        break;
                    case "8":
                        System.out.println("Количество байков: " + bikeController.getActiveBikesCount());
                        break;
                    case "9":
                        System.out.println("Суммарная стоимость байков: " +
                                bikeController.getActiveBikesTotalCost());
                        break;
                    case "10":
                        System.out.println("Средняя стоимость байков: " +
                                bikeController.getActiveBikesAveragePrice());
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неккоректный ввод!");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void customerOperations() {
        while (true) {
            try {
                System.out.println("Выберите желаемую операцию с покупателями:");
                System.out.println("1 - сохранить покупателя");
                System.out.println("2 - получить всех покупателей");
                System.out.println("3 - получить покупателя по идентификатору");
                System.out.println("4 - изменить покупателя");
                System.out.println("5 - удалить покупателя по идентификатору");
                System.out.println("6 - удалить покупателя по имени");
                System.out.println("7 - восстановить покупателя по идентификатору");
                System.out.println("8 - получить количество покупателей");
                System.out.println("9 - получить стоимость корзины покупателя");
                System.out.println("10 - получить среднюю стоимость байка в корзине покупателя");
                System.out.println("11 - добавить байк в корзину покупателя");
                System.out.println("12 - удалить байк из корзины покупателя");
                System.out.println("13 - очистить корзину покупателя");
                System.out.println("14 - изменить количество дней аренды байка в корзине покупателя");
                System.out.println("0 - выход");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Введите имя покупателя");
                        String name = scanner.nextLine();
                        System.out.println(customerController.save(name));
                        break;
                    case "2":
                        customerController.getAllActiveCustomers().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.println("Введите идентификатор");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println(customerController.getActiveCustomerById(id));
                        break;
                    case "4":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите новое имя покупателя");
                        name = scanner.nextLine();
                        customerController.update(id, name);
                        break;
                    case "5":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        customerController.deleteById(id);
                        break;
                    case "6":
                        System.out.println("Введите  имя покупателя");
                        name = scanner.nextLine();
                        customerController.deleteByName(name);
                        break;
                    case "7":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        customerController.restoreById(id);
                        break;
                    case "8":
                        System.out.println("Колличество покупателей: " +
                                customerController.getActiveCustomerCount());
                        break;
                    case "9":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Стоимость корзины покупателя: " +
                                customerController.getCustomerCartTotalPrice(id));
                        break;
                    case "10":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Средняя цена корзины покупателя: " +
                                customerController.getCustomerCartAveragePrice(id));
                        break;
                    case "11":
                        System.out.println("Введите идентификатор покупателя");
                        int customerId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите идентификатор байка");
                        int bikeId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите количество дней аренды");
                        int rentDays = Integer.parseInt(scanner.nextLine());
                        customerController.addProductToCustomerCart(customerId, bikeId, rentDays);
                        break;
                    case "12":
                        System.out.println("Введите идентификатор покупателя");
                        customerId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите идентификатор байка");
                        bikeId = Integer.parseInt(scanner.nextLine());
                        customerController.removeProductFromCustomerCart(customerId, bikeId);
                        break;
                    case "13":
                        System.out.println("Введите идентификатор");
                        id = Integer.parseInt(scanner.nextLine());
                        customerController.clearCustomerCart(id);
                        break;
                    case "14":
                        System.out.println("Введите идентификатор покупателя");
                        customerId = Integer.parseInt(scanner.nextLine());

                        System.out.println("Введите идентификатор байка");
                        bikeId = Integer.parseInt(scanner.nextLine());

                        System.out.println("Введите новое количество дней аренды");
                        rentDays = Integer.parseInt(scanner.nextLine());

                        customerController.updateBikeRentDaysInCustomerCart(customerId, bikeId, rentDays);
                        System.out.println("Количество дней аренды обновлено!");
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неккоректный ввод!");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
