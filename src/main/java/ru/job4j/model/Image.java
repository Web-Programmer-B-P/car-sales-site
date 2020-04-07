package ru.job4j.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "advertisement_id")
    private int advertisementId;

    public Image() {
    }

    public Image(int id) {
        this.id = id;
    }

    public Image(String name, byte[] image, int advertisementId) {
        this.name = name;
        this.image = image;
        this.advertisementId = advertisementId;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return id == image.id && Objects.equals(name, image.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Image.class.getSimpleName() + "{", "}")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("image=" + Arrays.toString(image))
                .add("advertisementId=" + advertisementId)
                .toString();
    }
}
