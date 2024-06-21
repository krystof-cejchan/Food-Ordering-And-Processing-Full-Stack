package cz.krystofcejchan.food_and_order_middleware.resources;

import cz.krystofcejchan.food_and_order_middleware.entities.Food;
import cz.krystofcejchan.food_and_order_middleware.services.FoodService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "*")
public record FoodResource(FoodService orderService) {
    @Contract(" -> new")
    @GetMapping("/all")
    public @NotNull ResponseEntity<List<Food>> getAllFood(){
        return new ResponseEntity<>(orderService.getAllFood(), HttpStatus.OK);
    }
}
