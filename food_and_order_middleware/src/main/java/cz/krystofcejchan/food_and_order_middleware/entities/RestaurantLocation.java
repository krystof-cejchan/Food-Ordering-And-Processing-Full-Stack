package cz.krystofcejchan.food_and_order_middleware;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
public class RestaurantLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "rest_id")
    private Long id;
    @Column(nullable = false)
    private String location;
    @OneToMany(mappedBy = "restaurantLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Table> tableSet;

    public RestaurantLocation(Long id, String location, Set<Table> tableSet) {
        this.id = id;
        this.location = location;
        this.tableSet = tableSet;
    }

    public RestaurantLocation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Table> getTableSet() {
        return tableSet;
    }

    public void setTableSet(Set<Table> tableSet) {
        this.tableSet = tableSet;
    }
}
