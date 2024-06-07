package cz.krystofcejchan.food_and_order_middleware.support_classes.exceptions;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class EntityNotFound extends RuntimeException {
    public EntityNotFound(@NotNull Object o) {
        super(o.getClass().toGenericString());
    }
}
