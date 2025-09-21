package app.domain;

import java.util.Objects;

public class Bike {
    private  int id;
    private String title;
    private BikeType type;
    private double price; // цена за день
    private int rentDays;  // количество дней аренды
    private boolean active;

    public Bike() {
    }
    public Bike(String title, double price) {
        this.title = title;
        this.price = price;
        this.active = true; // по умолчанию активен
        this.rentDays = 1; // по умолчанию арендуем на 1 день
    }
    // Конструктор для сохранения нового байка
    public Bike(String title, BikeType type, double price) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.active = true;
        this.rentDays = 1; // по умолчанию арендуем на 1 день
    }

    // Конструктор для обновления по id + цена
    public Bike(int id, double price) {
        this.id = id;
        this.price = price;
        this.active = true;
        this.rentDays = 1;
    }

    // Конструктор для обновления по id + имя
    public Bike(int id, String title) {
        this.id = id;
        this.title = title;
        this.active = true;
        this.rentDays = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bike)) return false;
        Bike bike = (Bike) o;
        return id == bike.id &&
                Double.compare(bike.price, price) == 0 &&
                active == bike.active &&
                rentDays == bike.rentDays &&
                Objects.equals(title, bike.title) &&
                type == bike.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, price, active, rentDays);
    }

    @Override
    public String toString() {
        return String.format("Байк: id=%d, название=%s, тип=%s, цена=%.2f, активен=%b, дней аренды=%d",
                id, title, type, price, active, rentDays);
    }
}
