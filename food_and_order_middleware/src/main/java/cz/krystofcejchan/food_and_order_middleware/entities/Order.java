package cz.krystofcejchan.food_and_order_middleware;

import cz.krystofcejchan.food_and_order_middleware.enums.OrderStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long order_id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Food> items;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "tableId")
    private Table table;
    @Column(nullable = false)
    private Integer total;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Long order_id, Set<Food> items, Table table, Integer total, OrderStatus orderStatus) {
        this.order_id = order_id;
        this.items = items;
        this.table = table;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<Food> getItems() {
        return items;
    }

    public void setItems(Set<Food> items) {
        this.items = items;
    }


}
