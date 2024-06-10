package cz.krystofcejchan.food_and_order_middleware.resources;

import cz.krystofcejchan.food_and_order_middleware.entities.Table;
import cz.krystofcejchan.food_and_order_middleware.services.TableService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/table")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
public record TableResource(TableService tableService) {
    @GetMapping("/getTableId")
    public @NotNull ResponseEntity<Long> getFoodFromOrder(@RequestBody Table table) {
        final Long found = tableService.getTableId(table.getRestaurantLocation(), table.getRow(), table.getColumn());
        return new ResponseEntity<>(found, HttpStatus.FOUND);
    }

}
