package cz.krystofcejchan.food_and_order_middleware.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "rest_id")
    private Long id;
    @Column(nullable = false)
    private String location;
    @OneToMany(mappedBy = "restaurantLocation", orphanRemoval = true)
    private Set<Table> tableSet;

}
