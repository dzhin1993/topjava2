package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenInclusive;

public interface MealRepository {
    // null if not found, when updated
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int mealId);

    // null if not found
    Meal get(int userId, int mealId);

    default List<Meal> getAll(int userId) {
        return filterByDate(userId, meal -> true);
    }

    default List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return filterByDate(userId, meal -> isBetweenInclusive(meal.getDate(), startDate, endDate));
    }

    List<Meal> filterByDate(int userId, Predicate<Meal> predicate);
}
