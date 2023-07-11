package noob.restmvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book{

    @Id
    //@GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    //@GenericGenerator(name = "UUID" , type = org.hibernate.id.UUIDGenerator.class)
    @UuidGenerator
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "varchar")
    private UUID id;

    @Version
    private Integer version;

    private String title;
    private String isbn;
    private String author;
    private BigDecimal price;
    private Integer quantityOnHand;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;

}
