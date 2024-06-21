package cz.krystofcejchan.food_and_order_middleware.support_classes.enums

import java.util.Optional

enum class OrderStatus(private val updatable: Boolean) {
    INIT(true), SENT(true), BEING_PREPARED(true),
    BEING_DELIVERED(true), FINISHED(true), CANCELED(true);

    fun update(): Optional<OrderStatus> =
        Optional.ofNullable(if (!this.updatable) null else entries[this.ordinal]);
}