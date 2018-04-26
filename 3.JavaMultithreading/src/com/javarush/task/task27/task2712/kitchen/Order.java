package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;



public class Order {
    protected List<Dish> dishes;
    private final Tablet tablet;

    public Order (Tablet tablet) throws IOException {
        this.tablet = tablet;
        dishes = ConsoleHelper.getAllDishesForOrder();
    }
    @Override
    public String toString() {
        if (dishes.isEmpty()) return "";
        else
            return String.format("Your order: %s of %s", dishes, tablet);
    }
    public int getTotalCookingTime() //  время приготовения всего заказа
    {
        int  count = 0;
        for (Dish d : dishes)
        {
            count = count + d.getDuration();
        }
        return  count;
    }
    public boolean isEmpty()
    {
        return dishes.isEmpty();
    }
}
