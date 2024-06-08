package cz.krystofcejchan.food_and_order_middleware.resources;

import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.services.OrderService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public record OrderResource(OrderService orderService) {
    @PostMapping("/add")
    public @NotNull ResponseEntity<Order> addNewOrder(@RequestBody() Order order) {
        final Order saved= orderService.addOrder(order);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/getFood/{order-id}")
    public @NotNull ResponseEntity<List<Object>> getFoodFromOrder(@PathVariable("order-id") String orderId) {
        final List<Object> found = orderService.findFoodByOrderId(orderId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @Contract("_ -> new")
    @GetMapping("/getActive/{restaurant-id}")
    public @NotNull ResponseEntity<List<Order>> getAllActiveOrders(@PathVariable("restaurant-id") String tableId) {
        final var found = orderService.getActiveOrders(Long.parseLong(tableId));
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
