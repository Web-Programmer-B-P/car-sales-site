package ru.job4j.services;

import ru.job4j.model.Advertisement;
import ru.job4j.persistence.AdvertisementDao;
import java.util.List;

public class AdvertisementService {
    private final AdvertisementDao advertisementDao = AdvertisementDao.getInstance();
    private static final AdvertisementService INSTANCE = new AdvertisementService();

    private AdvertisementService() {

    }

    public static AdvertisementService getInstance() {
        return INSTANCE;
    }

    public int add(Advertisement advertisement) {
        return advertisementDao.add(advertisement);
    }

    public List<Advertisement> findAll() {
        return advertisementDao.findAll();
    }

    public void changeStatusById(int id, boolean status) {
        Advertisement advertisement = advertisementDao.find(new Advertisement(id));
        advertisement.setSaleStatus(status);
        advertisementDao.update(advertisement);
    }

    public List<Advertisement> findAllWithLastDate() {
        return advertisementDao.findAllByLastDate();
    }

    public List<Advertisement> findAllByCriterion(String criterion) {
        return advertisementDao.findAllByCriterion(criterion);
    }
}
