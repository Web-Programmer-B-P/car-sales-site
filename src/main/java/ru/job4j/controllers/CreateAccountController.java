package ru.job4j.controllers;

import ru.job4j.model.User;
import ru.job4j.services.UserService;
import ru.job4j.utils.UtilBuilder;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(maxFileSize = 6000000)
@WebServlet("/save-user")
public class CreateAccountController extends HttpServlet {
    private static final String URI_REDIRECT_IF_USER_UNIQUE = "/sing-in";
    private static final String ERROR_MESSAGE = "Пользователь с таким почтовым адресом и паролем уже существует!";
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> response = new HashMap<>();
        User user = UtilBuilder.entityBuilder(req.getParameter("user"), User.class);
        if (!userService.isUserExist(user)) {
            userService.add(user);
            response.put("url", URI_REDIRECT_IF_USER_UNIQUE);
        } else {
            response.put("error", ERROR_MESSAGE);
        }
        UtilBuilder.sendResponseWithMessage(resp, response);
    }

}
