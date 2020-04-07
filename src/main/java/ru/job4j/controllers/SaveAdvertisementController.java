package ru.job4j.controllers;

import ru.job4j.model.*;
import ru.job4j.services.AdvertisementService;
import ru.job4j.services.ImageService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(maxFileSize = 6000000)
@WebServlet("/save-advertisement")
public class SaveAdvertisementController extends HttpServlet {
    private static final String FILE_KEY_IN_FORM_DATA = "file";
    private static final String CAR_KEY = "car";
    private static final String ENGINE_KEY = "engine";
    private static final String ADVERTISEMENT_KEY = "advert";
    private static final String REDIRECT_URI_AFTER_SAVE = "/all-advertisements";
    private final AdvertisementService advertisementService = AdvertisementService.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> message = new HashMap<>();
        User user = (User) req.getSession().getAttribute("user");
        saveAdvertisement(req, user.getId());
        message.put("url", REDIRECT_URI_AFTER_SAVE);
        UtilBuilder.sendResponseWithMessage(resp, message);
    }

    private void saveAdvertisement(HttpServletRequest req, int userId) throws IOException, ServletException {
        Car car = UtilBuilder.entityBuilder(req.getParameter(CAR_KEY), Car.class);
        Engine engine = UtilBuilder.entityBuilder(req.getParameter(ENGINE_KEY), Engine.class);
        car.setEngine(engine);
        Advertisement advertisement = UtilBuilder.entityBuilder(req.getParameter(ADVERTISEMENT_KEY), Advertisement.class);
        int advertisementId = advertisementService.add(UtilBuilder.buildAdvertisement(advertisement, car, userId));
        Image image = UtilBuilder.imageUpload(req.getPart(FILE_KEY_IN_FORM_DATA), advertisementId);
        imageService.add(image);
    }
}
