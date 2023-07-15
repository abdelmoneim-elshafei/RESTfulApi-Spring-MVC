package noob.restmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class BookOrder {
    public BookOrder(UUID id, Long version, Timestamp createdDate,
                     Timestamp updateDate, String customerRef, Customer customer,
                     BookOrderShipment bookOrderShipment) {

        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.customerRef = customerRef;
        this.setCustomer(customer);
        this.setBookOrderShipment(bookOrderShipment);
    }

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    boolean isNew(){
        return this.id == null;
    }
    private String customerRef;

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = CascadeType.PERSIST)
    BookOrderShipment bookOrderShipment;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBookOrders().add(this);
    }

    public void setBookOrderShipment(BookOrderShipment bookOrderShipment) {
        this.bookOrderShipment = bookOrderShipment;
        bookOrderShipment.setBookOrder(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BookOrder bookOrder = (BookOrder) o;
        return getId() != null && Objects.equals(getId(), bookOrder.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
