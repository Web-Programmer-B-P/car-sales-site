package ru.job4j.controllers;

import ru.job4j.model.User;
import ru.job4j.services.AdvertisementService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/all-advertisements")
public class ListAdvertisementController extends HttpServlet {
    private static final String ADVERTISEMENTS_HTML = "index.html";
    private static final String DEFAULT_SESSION_USER_ID = "-1";
    private final AdvertisementService advertisementService = AdvertisementService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(String.format("%s%s", req.getContextPath(), ADVERTISEMENTS_HTML)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if (Objects.nonNull(sessionUser)) {
            UtilBuilder.sendResponseWithMessage(resp, Map.of("statusId", sessionUser.getId(), "advertisements", advertisementService.findAll()));
        } else {
            UtilBuilder.sendResponseWithMessage(resp, Map.of("statusId", DEFAULT_SESSION_USER_ID, "advertisements", advertisementService.findAll()));
        }
    }
}
