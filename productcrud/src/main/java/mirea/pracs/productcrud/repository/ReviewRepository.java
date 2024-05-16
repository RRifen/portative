package mirea.pracs.productcrud.repository;

import java.util.List;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findAllByProduct(Product product);

}
