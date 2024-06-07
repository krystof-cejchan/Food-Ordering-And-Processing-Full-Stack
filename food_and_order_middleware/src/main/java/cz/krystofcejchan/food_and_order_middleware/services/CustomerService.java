package cz.krystofcejchan.food_and_order_middleware.services;

import cz.krystofcejchan.food_and_order_middleware.entities.Customer;
import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.entities.OrderFood;
import cz.krystofcejchan.food_and_order_middleware.repositories.CustomerRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.OrderFoodRepository;
import cz.krystofcejchan.food_and_order_middleware.repositories.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderFoodRepository orderFoodRepository;

    @Transactional
    public void addOrderToCustomer(Long customerId, @NotNull Order order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

            Food food = foodRepository.findById(order.)
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            OrderFood orderFood = new OrderFood();
            orderFood.setOrder(order);
            orderFood.setFood(food);
            orderFood.setQuantity(1);

            orderFoodRepository.save(orderFood);

    }

    public List<Order> getOrdersForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getOrders();
    }
}

