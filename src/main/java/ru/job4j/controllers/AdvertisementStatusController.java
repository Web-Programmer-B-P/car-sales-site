package ru.job4j.controllers;

import ru.job4j.model.User;
import ru.job4j.services.AdvertisementService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@WebServlet("/status")
public class AdvertisementStatusController extends HttpServlet {
    private final AdvertisementService advertisementService = AdvertisementService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        int advertisementId = Integer.parseInt(req.getParameter("id"));
        int statusId = Integer.parseInt(req.getParameter("statusId"));
        if (Objects.nonNull(user) && user.getId() == statusId) {
            advertisementService.changeStatusById(advertisementId, status);
        }
    }
}
