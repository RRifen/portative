package mirea.pracs.productcrud.repository;

import mirea.pracs.productcrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
