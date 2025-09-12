package app.domain;

import java.util.Objects;

public class Bike {
    private  int id;
    private String title;
    private String type;
    private double price;
    private boolean active;

    public Bike() {
    }

    public Bike(String title, String type, double price) {
        this.title = title;
        this.type = type;
        this.price = price;
    }

    public Bike(int id, String title, String type, double price, boolean active) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.active = active;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return id == bike.id && Double.compare(price, bike.price) == 0 && active == bike.active && Objects.equals(title, bike.title) && Objects.equals(type, bike.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, price, active);
    }

    @Override
    public String toString() {
        return String.format("Велосипед: id - %d, наименование - %s, тип - %s, цена - %.2f, активен - %b.",
                id, title, type, price, active);
    }
}
