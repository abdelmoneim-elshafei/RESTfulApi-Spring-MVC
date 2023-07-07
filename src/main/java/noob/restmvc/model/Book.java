package noob.restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Book {

    private UUID id;
    private Integer version;
    private String title;
    private String isbn;
    private String author;
    private BigDecimal price;
    private Integer quantityOnHand;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;

}
