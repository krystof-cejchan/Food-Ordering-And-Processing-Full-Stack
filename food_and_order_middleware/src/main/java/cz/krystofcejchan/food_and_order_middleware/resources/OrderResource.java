package cz.krystofcejchan.food_and_order_middleware.resources;

import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.entities.OrderFood;
import cz.krystofcejchan.food_and_order_middleware.services.OrderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public record OrderResource(OrderService orderService) {
    @PostMapping("/add")
    public @NotNull ResponseEntity<Order> addNewOrder(@RequestBody() Order order) {
        return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/getFood/{order-id}")
    public @NotNull ResponseEntity<List<OrderFood>> getFoodFromOrder(@PathVariable("order-id") String orderId) {
        return new ResponseEntity<>(orderService.findFoodByOrderId(orderId), HttpStatus.OK);
    }
}
