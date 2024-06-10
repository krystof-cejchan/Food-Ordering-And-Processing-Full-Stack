package cz.krystofcejchan.food_and_order_middleware.services;

import cz.krystofcejchan.food_and_order_middleware.entities.RestaurantLocation;
import cz.krystofcejchan.food_and_order_middleware.repositories.TableRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public record TableService(TableRepository tableRepository) {
    @Contract(pure = true)
    @Autowired
    public TableService {
    }

    public @NotNull Long getTableId(RestaurantLocation rl, String row, int column) {
        return tableRepository.findOneByRestaurantLocationAndRowAndColumn(rl, row, column).orElseThrow().getId();
    }

}