package noob.restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BookDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String title;

    @NotBlank
    @NotNull
    private String isbn;
    private String author;
    private BigDecimal price;
    private Integer quantityOnHand;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;

}
