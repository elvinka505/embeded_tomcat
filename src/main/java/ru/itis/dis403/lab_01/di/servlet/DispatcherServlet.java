package ru.itis.dis403.lab_01.di.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab_01.di.config.Context;
import ru.itis.dis403.lab_01.di.config.MethodHandler;

import java.io.IOException;
import java.lang.reflect.Method;

public class DispatcherServlet extends HttpServlet {

    private Context context;

    public DispatcherServlet(Context context) {
        this.context = context;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String path = (pathInfo != null) ? pathInfo : "/home";

        MethodHandler handler = context.getHandler(path);
        if (handler == null) {
            resp.setStatus(404);
            resp.getWriter().println("<h1>404 - Страница не найдена: '" + path + "'</h1>");
            resp.getWriter().println("<p>Доступные: /home, /index, /stores</p>");
            return;
        }

        try {
            Method method = handler.getMethod();
            method.setAccessible(true);
            method.invoke(handler.getController(), req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().println("<h1>500 - Ошибка: " + e.getMessage() + "</h1>");
        }
    }
}

