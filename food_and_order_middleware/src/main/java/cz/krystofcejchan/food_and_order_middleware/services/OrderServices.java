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
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServices {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final StaffRepository staffRepository;

    private final TableRepository tableRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Contract(pure = true)
    @Autowired
    public OrderServices(OrderRepository orderRepository, FoodRepository foodRepository, StaffRepository staffRepository, TableRepository tableRepository, SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.staffRepository = staffRepository;
        this.tableRepository = tableRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Order addOrder(@NotNull Order order) {
        order.setOrderStatus(OrderStatus.SENT);
        order.setOrderCreated(LocalDateTime.now(Clock.systemUTC()));
        order.setTotal(order.getItems()
                .stream()
                .map(food -> foodRepository.getReferenceById(food.getId()))
                .mapToDouble(Food::getPrice)
                .sum());
        final var savedOrder = orderRepository.save(order);
        final var restaurantFromSavedOrder = tableRepository.findById(savedOrder.getTable().getId())
                .orElseThrow()
                .getRestaurantLocation().getId();
        sendOrderUpdate(restaurantFromSavedOrder, savedOrder);
        sendOrderStatus(savedOrder.getOrder_id(), savedOrder.getOrderStatus());
        return savedOrder;
    }

    /**
     * sends order notification to the frontend with the new order
     *
     * @param restId {@link cz.krystofcejchan.food_and_order_middleware.entities.RestaurantLocation} id
     * @param order  newly created order
     */
    private void sendOrderUpdate(@NotNull Long restId, Order order) {
        messagingTemplate.convertAndSend("/topic/orders/new-frontend/%s".formatted(restId), order);
    }

    private void sendOrderStatus(@NotNull String orderId, OrderStatus orderStatus) {
        messagingTemplate.convertAndSend("/topic/orders/status-update/%s".formatted(orderId), orderStatus);
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFound(this));
    }

    public List<Order> getActiveOrders(Long restaurantLocation) {
        return orderRepository.findAllByTable_RestaurantLocation_IdAndOrderStatusIn(restaurantLocation, OrderStatus.SENT, OrderStatus.BEING_PREPARED);
    }

    public List<Object> findFoodByOrderId(String id) {
        return orderRepository.findAllByOrderId(id);
    }

    public final @NotNull Order updateOrderStatus(String orderId, final OrderStatus orderStatus, long staffId, @Nullable Order foundOrder) {
        foundOrder = foundOrder == null ? orderRepository.findById(orderId).orElseThrow(EntityNotFound::new) : foundOrder;
        final var assignedStaff = staffRepository.findById(staffId).orElseThrow(EntityNotFound::new);
        foundOrder.setOrderStatus(orderStatus);
        foundOrder.setAssignedStaff(assignedStaff);
        sendOrderStatus(orderId, orderStatus);
        return orderRepository.save(foundOrder);
    }

    public Order updateOrderStatusByOne(String orderId, long staffId) {
        final var foundOrder = orderRepository.findById(orderId).orElseThrow(EntityNotFound::new);
        return this.updateOrderStatus(orderId, foundOrder.getOrderStatus().update().orElseThrow(), staffId, foundOrder);
    }
}
