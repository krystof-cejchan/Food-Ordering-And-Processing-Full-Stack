package cz.krystofcejchan.food_and_order_middleware.repositories;

import cz.krystofcejchan.food_and_order_middleware.entities.RestaurantLocation;
import cz.krystofcejchan.food_and_order_middleware.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    Optional<Table> findOneByRestaurantLocationAndRowAndColumn(RestaurantLocation restaurantLocation, String row, int column);
}
