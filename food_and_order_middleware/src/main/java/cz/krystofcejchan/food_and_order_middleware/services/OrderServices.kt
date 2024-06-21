package cz.krystofcejchan.food_and_order_middleware.services

import cz.krystofcejchan.food_and_order_middleware.entities.Order
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository
import cz.krystofcejchan.food_and_order_middleware.repositories.OrderRepository
import cz.krystofcejchan.food_and_order_middleware.repositories.StaffRepository
import cz.krystofcejchan.food_and_order_middleware.repositories.TableRepository
import cz.krystofcejchan.food_and_order_middleware.support_classes.enums.OrderStatus
import cz.krystofcejchan.food_and_order_middleware.support_classes.exceptions.EntityNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime

@Service
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val foodRepository: FoodRepository,
    private val staffRepository: StaffRepository,
    private val tableRepository: TableRepository,
    private val messagingTemplate: SimpMessagingTemplate
) {
    fun addOrder(order: Order): Order {
        order.orderStatus = OrderStatus.SENT
        order.orderCreated = LocalDateTime.now(Clock.systemUTC())
        order.total = order.items.stream().map { food -> foodRepository.getReferenceById(food.id) }
            .mapToDouble { f -> f.price }.sum();
        val savedOrder = orderRepository.save(order)
        val restaurantFromSavedOrder =
            tableRepository.findById(savedOrder.table.id).orElseThrow { EntityNotFound() }.restaurantLocation.id;
        sendOrderUpdate(restaurantFromSavedOrder, savedOrder)
        sendOrderStatus(savedOrder.order_id, savedOrder.orderStatus)
        return savedOrder;
    }

    private fun sendOrderUpdate(restId: Long, order: Order) {
        messagingTemplate.convertAndSend("/topic/orders/new-frontend/$restId", order)
    }

    private fun sendOrderStatus(orderId: String, orderStatus: OrderStatus) {
        messagingTemplate.convertAndSend("/topic/orders/status-update/$orderId", orderStatus)
    }

    fun updateOrderStatus(orderId: String, orderStatus: OrderStatus?, staffId: Long, foundOrder: Order?): Order {
        foundOrder ?: orderRepository.findById(orderId).orElseThrow { EntityNotFound() }
        val assignedStaff = staffRepository.findById(staffId).orElseThrow { EntityNotFound() }
        foundOrder!!.orderStatus = orderStatus
        foundOrder.assignedStaff = assignedStaff
        sendOrderStatus(orderId, orderStatus!!)
        return orderRepository.save(foundOrder)
    }

    fun updateOrderStatusByOne(orderId: String, staffId: Long): Order {
        val foundOrder = orderRepository.findById(orderId).orElseThrow { EntityNotFound() }
        return this.updateOrderStatus(orderId, foundOrder.orderStatus.update().orElseThrow(), staffId, foundOrder)
    }

    fun findById(id: String): Order {
        return orderRepository.findById(id).orElseThrow {
            EntityNotFound(this)
        }
    }

    fun getActiveOrders(restaurantLocation: Long): List<Order> {
        return orderRepository.findAllByTable_RestaurantLocation_IdAndOrderStatusIn(
            restaurantLocation,
            OrderStatus.SENT,
            OrderStatus.BEING_PREPARED
        )
    }

    fun findFoodByOrderId(id: String): List<Any> {
        return orderRepository.findAllByOrderId(id)
    }


}