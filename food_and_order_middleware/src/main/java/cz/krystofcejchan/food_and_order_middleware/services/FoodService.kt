package cz.krystofcejchan.food_and_order_middleware.services

import cz.krystofcejchan.food_and_order_middleware.entities.Food
import cz.krystofcejchan.food_and_order_middleware.repositories.FoodRepository
import org.jetbrains.annotations.Contract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@JvmRecord
data class FoodService @Contract(pure = true) @Autowired constructor(val foodRepository: FoodRepository) {
    val allFood: List<Food>
        get() = foodRepository.findAll()
}
