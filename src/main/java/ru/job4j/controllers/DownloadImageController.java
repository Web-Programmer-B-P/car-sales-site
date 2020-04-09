package ru.job4j.controllers;

import ru.job4j.model.Image;
import ru.job4j.services.ImageService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebServlet("/download")
public class DownloadImageController extends HttpServlet {
    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (Objects.nonNull(id)) {
            Image responseImage = imageService.findById(Integer.parseInt(id));
            if (Objects.nonNull(responseImage)) {
                String name = responseImage.getName();
                String contentType = this.getServletContext().getMimeType(responseImage.getName());
                resp.setHeader("Content-Type", contentType);
                resp.setHeader("Content-Length", String.valueOf(responseImage.getImage().length));
                resp.setHeader("Content-Disposition", "attachment; filename*=utf-8"
                        + java.net.URLEncoder.encode(name, StandardCharsets.UTF_8) + ";");
                resp.getOutputStream().write(responseImage.getImage());
            }
        }
    }
}
