package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final ConcurrentHashMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public List<Meal> getAll() {
        return storage.values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public void save(Meal meal) {
        storage.put(counter.incrementAndGet(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        return storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }
}
