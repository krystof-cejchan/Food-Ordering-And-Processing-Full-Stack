package cz.krystofcejchan.food_and_order_middleware.services;

import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record FoodService(FoodRepository foodRepository) {
    @Contract(pure = true)
    @Autowired
    public FoodService {
    }

    public @NotNull List<Food> getAllFood() {
        return foodRepository.findAll();
    }

}
