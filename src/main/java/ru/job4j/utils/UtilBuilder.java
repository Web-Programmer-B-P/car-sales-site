package ru.job4j.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Advertisement;
import ru.job4j.model.Car;
import ru.job4j.model.Image;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final public class UtilBuilder {
    private static final Logger LOG = LogManager.getLogger(UtilBuilder.class.getName());
    private static final String INVALID_READ_STREAM = "Смотри в создание нового изображения";
    private static final String ENTITY_BUILD_ERROR = "Смотри в преобразование сущности из json строки";
    private static final String RESPONSE_CONTENT_TYPE = "text/html";

    private UtilBuilder() {

    }

    public static Image imageUpload(Part image, int advertisementId) {
        Image readyImage = null;
        if (Objects.nonNull(image)) {
            readyImage = new Image();
            try (BufferedInputStream inputStream = new BufferedInputStream(image.getInputStream())) {
                readyImage.setImage(inputStream.readAllBytes());
                readyImage.setName(image.getSubmittedFileName());
                readyImage.setAdvertisementId(advertisementId);
            } catch (IOException io) {
                LOG.error(INVALID_READ_STREAM, io);
            }
        }
        return readyImage;
    }

    public static Advertisement buildAdvertisement(Advertisement advertisement, Car car, int userId) {
        advertisement.setCar(car);
        advertisement.setCreateDate(new Timestamp(System.currentTimeMillis()));
        advertisement.setUserId(userId);
        return advertisement;
    }

    public static <T> T entityBuilder(String json, Class<T> entity) {
        ObjectMapper mapper = new ObjectMapper();
        T readyObject = null;
        try {
            readyObject = mapper.readValue(json, entity);
        } catch (JsonProcessingException jse) {
            LOG.error(ENTITY_BUILD_ERROR, jse);
        }
        return readyObject;
    }

    public static <T> void sendResponse(HttpServletResponse response, List<T> dataList) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType(RESPONSE_CONTENT_TYPE);
            out.write(new ObjectMapper().writeValueAsString(dataList));
            out.flush();
        }
    }

    public static <T> void sendResponse(HttpServletResponse response, T data) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType(RESPONSE_CONTENT_TYPE);
            out.write(new ObjectMapper().writeValueAsString(data));
            out.flush();
        }
    }

    public static <T> void sendResponseWithMessage(HttpServletResponse resp, Map<String, T> response) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            ObjectMapper mapper = new ObjectMapper();
            String row = mapper.writeValueAsString(response);
            writer.write(row);
            writer.flush();
        }
    }

    public static String parsePhoneNumber(String originalPhone) {
        StringBuilder phoneWithOutBrackets = new StringBuilder();
        String[] res = originalPhone.split("\\(|\\)");
        phoneWithOutBrackets.append(res[0]).append(" ").append(res[1]).append(" ").append(res[2]);
        return phoneWithOutBrackets.toString();
    }
}
