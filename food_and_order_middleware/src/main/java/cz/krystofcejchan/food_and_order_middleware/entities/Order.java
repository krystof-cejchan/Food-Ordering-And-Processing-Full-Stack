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
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String order_id;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "FOOD_ORDER_MAPPING", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> items;
    @ManyToOne(cascade = CascadeType.DETACH)
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
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = true, updatable = true, name = "staff_id")
    private Staff assignedStaff;


    public Order(List<Food> items, Table table, Double total, OrderStatus orderStatus, Customer customer, Staff assignedStaff) {
        this.items = items;
        this.table = table;
        this.total = total;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.assignedStaff = assignedStaff;
        this.orderCreated = LocalDateTime.now(Clock.systemUTC());
    }

    public Order(List<Food> items, Table table, Customer customer, Staff staff) {
        this(items, table, items.stream().mapToDouble(Food::getPrice).sum(),
                OrderStatus.INIT, customer, staff);
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<Food> getItems() {
        return items;
    }

    public void setItems(List<Food> items) {
        this.items = items;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderCreated() {
        return orderCreated;
    }

    public void setOrderCreated(LocalDateTime orderCreated) {
        this.orderCreated = orderCreated;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getAssignedStaff() {
        return assignedStaff;
    }

    public void setAssignedStaff(Staff assignedStaff) {
        this.assignedStaff = assignedStaff;
    }
}
