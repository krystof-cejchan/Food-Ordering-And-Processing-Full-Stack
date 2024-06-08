package cz.krystofcejchan.food_and_order_middleware.services;

import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.OrderRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.TableRepository;
import cz.krystofcejchan.food_and_order_middleware.support_classes.enums.OrderStatus;
import cz.krystofcejchan.food_and_order_middleware.support_classes.exceptions.EntityNotFound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final TableRepository tableRepository;


    @Contract(pure = true)
    @Autowired
    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.tableRepository = tableRepository;
    }

    public Order addOrder(@NotNull Order order) {
        order.setOrderStatus(OrderStatus.SENT);
        order.setOrderCreated(LocalDateTime.now(Clock.systemUTC()));
        order.setTotal(order.getItems()
                .stream()
                .map(food -> foodRepository.getReferenceById(food.getId()))
                .mapToDouble(Food::getPrice)
                .sum());
        return orderRepository.save(order);
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFound(this));
    }

    public List<Order> getActiveOrders(Long tableId) {
        return orderRepository.findAllByTableIdAndOrderStatusIn(tableId, OrderStatus.SENT, OrderStatus.BEING_PREPARED);
    }

    public List<Object> findFoodByOrderId(String id) {
        return orderRepository.findAllByOrderId(id);
    }
}
