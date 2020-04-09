package ru.job4j.services;

import ru.job4j.model.Image;
import ru.job4j.persistence.ImageDao;

import java.util.List;

public class ImageService {
    private final ImageDao imageDao = ImageDao.getInstance();
    private static final ImageService INSTANCE = new ImageService();

    private ImageService() {

    }

    public static ImageService getInstance() {
        return INSTANCE;
    }

    public void add(Image image) {
        imageDao.add(image);
    }

    public Image findById(int id) {
        Image result = null;
        if (imageDao.findImageByAdvertisementId(id).size() > 0) {
            result = imageDao.findImageByAdvertisementId(id).get(0);
        }
        return result;
    }
}
