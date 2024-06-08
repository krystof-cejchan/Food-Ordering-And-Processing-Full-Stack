package cz.krystofcejchan.food_and_order_middleware.repositories;

import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.entities.Table;
import cz.krystofcejchan.food_and_order_middleware.support_classes.enums.OrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {
    @Query(value = """
    SELECT f.id, f.title, f.price
    FROM food f
    INNER JOIN food_order_mapping o
    ON o.food_id = f.id
    WHERE o.order_id = ?1""", nativeQuery = true)
    List<Object> findAllByOrderId(String orderId);

    List<Order> findAllByTableIdAndOrderStatusIn(Long table, OrderStatus... orderStatuses);
}
