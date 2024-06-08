package cz.krystofcejchan.food_and_order_middleware.support_classes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    INIT(true),
    SENT(true),
    BEING_PREPARED(true),
    BEING_DELIVERED(true),
    FINISHED(false),
    CANCELED(false);

    final boolean updatable;

    public Optional<OrderStatus> update() {
        return Optional.ofNullable(!this.isUpdatable() ? null : values()[this.ordinal() + 1]);
    }
}
