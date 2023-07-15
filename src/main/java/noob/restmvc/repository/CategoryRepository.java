package noob.restmvc.repository;

import noob.restmvc.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
}
