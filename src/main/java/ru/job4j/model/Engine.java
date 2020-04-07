package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "horse_power")
    private int horsePower;

    public Engine() {
    }

    public Engine(int id) {
        this.id = id;
    }

    public Engine(String fuelType, int horsePower) {
        this.fuelType = fuelType;
        this.horsePower = horsePower;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id && horsePower == engine.horsePower && Objects.equals(fuelType, engine.fuelType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Engine.class.getSimpleName() + "{", "}")
                .add("id=" + id)
                .add("fuelType='" + fuelType + "'")
                .add("horsePower=" + horsePower)
                .toString();
    }
}
