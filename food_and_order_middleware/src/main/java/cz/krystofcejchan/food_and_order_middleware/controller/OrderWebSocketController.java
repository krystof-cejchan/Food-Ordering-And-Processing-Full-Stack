package cz.krystofcejchan.food_and_order_middleware.controller;

import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.services.OrderService;
import cz.krystofcejchan.food_and_order_middleware.support_classes.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderWebSocketController {

    @Autowired
    private OrderService orderService;

    @SendTo("/topic/orders/new-frontend/{restId}")
    public Order sendNewOrderToFrontEnd(@DestinationVariable String restId, Order order) {
        return order;
    }

    @SendTo("/topic/orders/status-update/{orderId}")
    public OrderStatus sendOrderProgress(@DestinationVariable String orderId, OrderStatus orderStatus) {
        return orderStatus;
    }
}