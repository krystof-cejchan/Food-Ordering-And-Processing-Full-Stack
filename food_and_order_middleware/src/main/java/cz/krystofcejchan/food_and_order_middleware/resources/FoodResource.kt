package cz.krystofcejchan.food_and_order_middleware.resources

import cz.krystofcejchan.food_and_order_middleware.entities.Food
import cz.krystofcejchan.food_and_order_middleware.services.FoodService
import org.jetbrains.annotations.Contract
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = ["*"])
data class FoodResource(val orderService: FoodService) {
    @get:GetMapping("/all")
    @get:Contract(" -> new")
    val allFood: ResponseEntity<List<Food>>
        get() = ResponseEntity(orderService.allFood, HttpStatus.OK)
}
