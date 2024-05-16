package mirea.pracs.productcrud.repository;

import mirea.pracs.productcrud.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
