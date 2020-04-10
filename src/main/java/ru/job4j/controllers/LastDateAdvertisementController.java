package ru.job4j.controllers;

import ru.job4j.model.User;
import ru.job4j.services.AdvertisementService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/get-last")
public class LastDateAdvertisementController extends HttpServlet {
    private static final String DEFAULT_SESSION_USER_ID = "-1";
    private final AdvertisementService advertisementService = AdvertisementService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if (Objects.nonNull(sessionUser)) {
            UtilBuilder.sendResponseWithMessage(resp, Map.of("statusId", sessionUser.getId(), "advertisements", advertisementService.findAllWithLastDate()));
        } else {
            UtilBuilder.sendResponseWithMessage(resp, Map.of("statusId", DEFAULT_SESSION_USER_ID, "advertisements", advertisementService.findAllWithLastDate()));
        }
    }
}
