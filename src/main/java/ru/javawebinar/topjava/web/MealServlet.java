package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private static final MealRepository repository = new InMemoryMealRepository();

    @Override
    public void init() {
        MEALS.forEach(repository::save);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = DateUtil.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(id, dateTime, description, calories);

        if (id == 0) {
            repository.save(meal);
        } else {
            repository.update(meal);
        }

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("forward to meals");
            request.setAttribute("meals", filteredByStreams(repository.getAll()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        String stringId = request.getParameter("id");
        int id = stringId != null ? Integer.parseInt(stringId) : 0;

        Meal meal = null;

        switch (action) {
            case "delete":
                repository.delete(id);
                response.sendRedirect("meals");
                return;
            case "edit":
                meal = repository.get(id);
                break;
            case "add":
                meal = new Meal();
                break;
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }
}
