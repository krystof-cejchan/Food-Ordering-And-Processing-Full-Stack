package cz.krystofcejchan.food_and_order_middleware.services

import cz.krystofcejchan.food_and_order_middleware.entities.RestaurantLocation
import cz.krystofcejchan.food_and_order_middleware.repositories.TableRepository
import org.jetbrains.annotations.Contract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableService @Autowired constructor(private val tableRepository: TableRepository) {
    fun getTableId(rl: RestaurantLocation, row: String, column: Int): Long {
        return tableRepository.findOneByRestaurantLocationAndRowAndColumn(rl, row, column).orElseThrow().id
    }
}