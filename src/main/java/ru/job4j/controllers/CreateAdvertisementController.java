package ru.job4j.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create-advertisement")
public class CreateAdvertisementController extends HttpServlet {
    private static final String CREATE_ADVERTISEMENT_VIEW = "create.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(String.format("%s%s", req.getContextPath(), CREATE_ADVERTISEMENT_VIEW)).forward(req, resp);
    }
}
