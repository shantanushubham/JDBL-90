package org.geeksforgeeks.food_ordering_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.MenuItem;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MostOrderedItemResponse {

    private UUID menuItemId;
    private String name;
    private Double price;
    private String description;
    private long quantityOrdered;

    public static MostOrderedItemResponse from(MenuItem menuItem, long quantityOrdered) {
        return MostOrderedItemResponse.builder()
                .menuItemId(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .quantityOrdered(quantityOrdered)
                .build();
    }

    /** Build from native query row: [id, name, price, description, total_quantity]. */
    public static MostOrderedItemResponse fromNativeRow(Object[] row) {
        if (row == null || row.length < 5) return null;
        UUID id = row[0] instanceof UUID ? (UUID) row[0] : UUID.fromString(row[0].toString());
        String name = row[1] != null ? row[1].toString() : null;
        Double price = row[2] != null ? ((Number) row[2]).doubleValue() : null;
        String description = row[3] != null ? row[3].toString() : null;
        long quantityOrdered = row[4] instanceof Number ? ((Number) row[4]).longValue() : 0L;
        return MostOrderedItemResponse.builder()
                .menuItemId(id)
                .name(name)
                .price(price)
                .description(description)
                .quantityOrdered(quantityOrdered)
                .build();
    }
}
