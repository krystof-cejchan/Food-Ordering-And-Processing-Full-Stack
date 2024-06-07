package cz.krystofcejchan.food_and_order_middleware.entities;

import cz.krystofcejchan.food_and_order_middleware.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

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
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Food> items;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "id")
    private Table table;
    @Column(nullable = false)
    private Integer total;
    private OrderStatus orderStatus;
}
