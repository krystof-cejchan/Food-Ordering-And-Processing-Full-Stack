package cz.krystofcejchan.food_and_order_middleware.services;

import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.OrderRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.StaffRepository;
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
    private final StaffRepository staffRepository;
    private final TableRepository tableRepository;


    @Contract(pure = true)
    @Autowired
    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository, StaffRepository staffRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.staffRepository = staffRepository;
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

    public List<Order> getActiveOrders(Long restaurantLocation) {
        return orderRepository.findAllByTableIdAndOrderStatusIn(restaurantLocation, OrderStatus.SENT, OrderStatus.BEING_PREPARED);
    }

    public List<Object> findFoodByOrderId(String id) {
        return orderRepository.findAllByOrderId(id);
    }

    public Order updateOrderStatus(String orderId, OrderStatus orderStatus, long staffId) {
        final var foundOrder = orderRepository.findById(orderId).orElseThrow(EntityNotFound::new);
        final var assignedStaff = staffRepository.findById(staffId).orElseThrow(EntityNotFound::new);
        foundOrder.setOrderStatus(orderStatus);
        foundOrder.setAssignedStaff(assignedStaff);
        return orderRepository.save(foundOrder);
    }

    public Order updateOrderStatusByOne(String orderId) {
        final var foundOrder = orderRepository.findById(orderId).orElseThrow(EntityNotFound::new);
        foundOrder.setOrderStatus(foundOrder.getOrderStatus().update().orElseThrow());
        return orderRepository.save(foundOrder);
    }
}
