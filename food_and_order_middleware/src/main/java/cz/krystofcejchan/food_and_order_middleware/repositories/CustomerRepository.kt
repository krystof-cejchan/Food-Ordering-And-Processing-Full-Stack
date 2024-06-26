package cz.krystofcejchan.food_and_order_middleware.repositories

import cz.krystofcejchan.food_and_order_middleware.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long>
