package cz.krystofcejchan.food_status_middleware.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class RestaurantLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "rest_id")
    private Long id;
    @Column(nullable = false)
    private String location;

    public RestaurantLocation(Long id, String location) {
        this.id = id;
        this.location = location;
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
}
