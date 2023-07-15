package noob.restmvc.repository;

import noob.restmvc.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookOrderRepository extends JpaRepository<BookOrder, UUID> {
}
