package cz.krystofcejchan.food_and_order_middleware.controller;

import cz.krystofcejchan.food_and_order_middleware.entities.Order;
import cz.krystofcejchan.food_and_order_middleware.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RecordWebSocketController {

    @Autowired
    private OrderService orderService;

    @MessageMapping("/new-records")
    @SendTo("/topic/records/1")
    public List<Order> sendNewRecords(/*@PathVariable(name = "restId") String restId*/) {
        System.out.println("ahojdaaaaaaaaaaaaaaaaaaaaaa");
        return orderService.getActiveOrders(Long.parseLong("1"));
    }
}
