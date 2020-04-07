package ru.job4j.controllers;

import ru.job4j.services.UserService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/get-phone")
public class PhoneController extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        UtilBuilder.sendResponse(resp, UtilBuilder.parsePhoneNumber(userService.findPhone(Integer.parseInt(userId))));
    }
}
