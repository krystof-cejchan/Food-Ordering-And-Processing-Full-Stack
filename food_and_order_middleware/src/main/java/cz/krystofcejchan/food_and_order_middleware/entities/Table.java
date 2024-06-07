package cz.krystofcejchan.food_and_order_middleware;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "rest_table")
public class Table implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long tableId;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "rest_id")
    private RestaurantLocation restaurantLocation;
    @Column(length = 2)
    private String row;
    private Integer column;

    public Table(Long tableId, RestaurantLocation restaurantLocation, String row, Integer column) {
        this.tableId = tableId;
        this.restaurantLocation = restaurantLocation;
        this.row = row;
        this.column = column;
    }

    public Table() {
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public RestaurantLocation getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(RestaurantLocation restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
