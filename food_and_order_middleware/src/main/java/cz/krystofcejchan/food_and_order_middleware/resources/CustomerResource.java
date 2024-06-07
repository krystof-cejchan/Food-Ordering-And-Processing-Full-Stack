package cz.krystofcejchan.food_and_order_middleware.resources;

import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerResource {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/{customerId}/orders")
    public void addOrderToCustomer(@PathVariable Long customerId, @RequestBody List<Order> foodIds) {
        customerService.addOrderToCustomer(customerId, foodIds);
    }

    @GetMapping("/{customerId}/orders")
    public List<Order> getOrdersForCustomer(@PathVariable Long customerId) {
        return customerService.getOrdersForCustomer(customerId);
    }
}
