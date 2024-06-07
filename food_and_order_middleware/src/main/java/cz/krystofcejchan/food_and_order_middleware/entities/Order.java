package cz.krystofcejchan.food_and_order_middleware.entities;

import cz.krystofcejchan.food_and_order_middleware.support_classes.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String order_id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<OrderFood> items;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Table table;
    @Column(nullable = false)
    private Double total;
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private LocalDateTime orderCreated;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id")
    private Customer customer;


    public Order(List<OrderFood> items, Table table, Double total, OrderStatus orderStatus, Customer customer) {
        this.items = items;
        this.table = table;
        this.total = total;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.orderCreated = LocalDateTime.now(Clock.systemUTC());
    }

    public Order(List<OrderFood> items, Table table, Customer customer) {
        this(items, table, items.stream().map(OrderFood::getFood).mapToDouble(Food::getPrice).sum(),
                OrderStatus.INIT, customer);
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", items=" + items.size() +
                ", table=" + table +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                ", orderCreated=" + orderCreated +
                ", customer=" + customer +
                '}';
    }
}
