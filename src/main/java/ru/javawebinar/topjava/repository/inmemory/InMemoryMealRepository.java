package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(authUserId(), new ConcurrentHashMap<>());
        MealsUtil.MEALS.forEach(userId -> save(authUserId(), userId));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info(meal.isNew() ? "save {} for user {}" : "update {} for user {}", meal, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);

        if (mealMap == null) return null;

        if (meal.isNew()) {
            meal.setId(counter.decrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete {} for user {}", mealId, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.containsKey(mealId);
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get {} for user {}", mealId, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.get(mealId) : null;
    }

    @Override
    public List<Meal> filterByDate(int userId, Predicate<Meal> predicate) {
        log.info("getAll for user {}", userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? Collections.emptyList() :
                mealMap.values().stream()
                        .filter(predicate)
                        .sorted(Comparator.comparing(Meal::getDate).reversed())
                        .collect(Collectors.toList());
    }
}

