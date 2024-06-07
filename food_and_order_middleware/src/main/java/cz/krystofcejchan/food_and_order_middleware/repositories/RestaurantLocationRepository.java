package cz.krystofcejchan.food_and_order_middleware.repositories;

import cz.krystofcejchan.food_and_order_middleware.entities.RestaurantLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
}
