package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllByDate(String startDate, String startTime,
                                     String endDate, String endTime) {
        log.info("getAll by date {} {} {} {}", startDate, startTime, endDate, endTime);

        LocalDate fromDate = startDate != null ? LocalDate.parse(startDate) : LocalDate.MIN;
        LocalTime fromTime = startTime != null ? LocalTime.parse(startTime) : LocalTime.MIN;
        LocalDate toDate = endDate != null ? LocalDate.parse(endDate) : LocalDate.MAX;
        LocalTime toTime = endTime != null ? LocalTime.parse(endTime) : LocalTime.MAX;

        List<Meal> meals = service.getAllByDate(authUserId(), fromDate, toDate);
        return getFilteredTos(meals, authUserCaloriesPerDay(), fromTime, toTime);
    }

    public Meal get(int id) {
        log.info("get meal with id {}", id);
        return service.get(authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete meal with id {}", id);
        service.delete(authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id {}", meal, id);
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }
}