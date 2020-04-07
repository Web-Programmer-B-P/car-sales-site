package ru.job4j.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "sale_status")
    private boolean saleStatus;

    @Column(name = "price")
    private String price;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "fk_car"))
    private Car car;

    public Advertisement() {
    }

    public Advertisement(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(boolean saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advertisement that = (Advertisement) o;
        return id == that.id && saleStatus == that.saleStatus && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Advertisement.class.getSimpleName() + "{", "}")
                .add("id=" + id)
                .add("description='" + description + "'")
                .add("saleStatus=" + saleStatus)
                .add("price='" + price + "'")
                .add("createDate=" + createDate)
                .add("userId=" + userId)
                .add("car=" + car)
                .toString();
    }
}
