package cz.krystofcejchan.food_and_order_middleware.repositories;

import cz.krystofcejchan.food_and_order_middleware.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}
