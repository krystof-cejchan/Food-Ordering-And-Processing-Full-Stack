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
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "FOOD_ORDER_MAPPING", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> items;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "id")
    private Table table;
    @Column(nullable = false)
    private Double total;
    @Column(columnDefinition = "integer default 0")
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private LocalDateTime orderCreated;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = true, updatable = true, name = "staff_id")
    private Staff assignedStaff;


    public Order(List<Food> items, Table table, Double total, OrderStatus orderStatus, Customer customer, Staff assignedStaff) {
        this.items = items;
        this.table = table;
        this.total = total;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.assignedStaff=assignedStaff;
        this.orderCreated = LocalDateTime.now(Clock.systemUTC());
    }

    public Order(List<Food> items, Table table, Customer customer, Staff staff) {
        this(items, table, items.stream().mapToDouble(Food::getPrice).sum(),
                OrderStatus.INIT, customer, staff);
    }
}
