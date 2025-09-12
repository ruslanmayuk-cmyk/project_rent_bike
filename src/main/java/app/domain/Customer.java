package app.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private int id;
    private String name;
    private  boolean active;
    private List<Bike> bikes = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && active == customer.active && Objects.equals(name, customer.name) && Objects.equals(bikes, customer.bikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, bikes);
    }

    @Override
    public String toString() {
        return String.format("Покупатель: id - %d, имя - %s, активен - %b, лист покупок - %s.",
                id, name, active, bikes);
    }
}
