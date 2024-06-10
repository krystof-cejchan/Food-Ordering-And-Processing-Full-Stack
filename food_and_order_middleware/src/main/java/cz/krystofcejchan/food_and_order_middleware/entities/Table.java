package cz.krystofcejchan.food_and_order_middleware.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "tables")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Table implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 2, nullable = false, updatable = true)
    private String row;
    @Column(nullable = false, updatable = true)
    private Integer column;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "rest_id", nullable = false)
    private RestaurantLocation restaurantLocation;
}
