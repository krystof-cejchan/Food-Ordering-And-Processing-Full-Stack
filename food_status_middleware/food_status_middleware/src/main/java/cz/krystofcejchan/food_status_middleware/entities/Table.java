package cz.krystofcejchan.food_status_middleware.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "table_at_restaurant")
public class Table implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private Character row;
    @Column(nullable = false)
    private Byte column;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "rest_id")
    private RestaurantLocation restaurantLocation;

    public Table(Long id, Character row, Byte column, RestaurantLocation restaurantLocation) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.restaurantLocation = restaurantLocation;
    }

    public Table() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getRow() {
        return row;
    }

    public void setRow(Character row) {
        this.row = row;
    }

    public Byte getColumn() {
        return column;
    }

    public void setColumn(Byte column) {
        this.column = column;
    }

    public RestaurantLocation getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(RestaurantLocation restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }
}
