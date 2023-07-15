package noob.restmvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
    //make hibernate save uuid as char no as byte
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @Version
    private Integer version;

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String title;

    @NotBlank
    @NotNull
    private String isbn;
    private String author;
    private BigDecimal price;
    private Integer quantityOnHand;

    @CreationTimestamp
    private LocalDateTime createdData;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
        category.getBooks().add(this);
    }
    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBooks().remove(this);
    }




}
