package ru.job4j.filtres;

import ru.job4j.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter("*")
public class AccessCheckFilter implements Filter {
    private final static String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding(ENCODING);
        resp.setCharacterEncoding(ENCODING);
        String currentRequestUri = req.getRequestURI();
        User sessionUser = (User) req.getSession().getAttribute("user");
        if ((currentRequestUri.contains("/create-advertisement") || currentRequestUri.contains("/js/create_advertisement.js")
                || currentRequestUri.contains("/save-advertisement")) && Objects.isNull(sessionUser)) {
            resp.sendRedirect("/sing-in");
            return;
        }
        chain.doFilter(req, resp);
    }
}
