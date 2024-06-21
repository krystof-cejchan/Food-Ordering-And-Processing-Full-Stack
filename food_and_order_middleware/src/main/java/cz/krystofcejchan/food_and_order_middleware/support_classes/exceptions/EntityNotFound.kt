package cz.krystofcejchan.food_and_order_middleware.support_classes.exceptions

class EntityNotFound : RuntimeException {
    constructor(o: Any) : super(o.javaClass.toGenericString())
    constructor() : super()
}
