package ru.job4j.controllers;

import ru.job4j.model.User;
import ru.job4j.services.UserService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/authorization")
public class AuthorizationController extends HttpServlet {
    private static final String ERROR_MESSAGE = "Неправильный логин или пароль!";
    private static final String URL_REDIRECT_IF_USER_EXIST = "/create-advertisement";
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> response = new HashMap<>();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        validateUserAndSetToSession(req, response, email, password);
        UtilBuilder.sendResponseWithMessage(resp, response);
    }

    private void validateUserAndSetToSession(HttpServletRequest req, Map<String, String> response, String email, String password) {
        if (Objects.nonNull(email) && Objects.nonNull(password)) {
            User userFromStore = userService.findByEmailAndPassword(email, password);
            if (Objects.nonNull(userFromStore)) {
                req.getSession().setAttribute("user", userFromStore);
                response.put("url", URL_REDIRECT_IF_USER_EXIST);
            } else {
                response.put("error", ERROR_MESSAGE);
            }
        }
    }
}
