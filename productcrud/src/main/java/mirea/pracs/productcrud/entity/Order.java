package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import mirea.pracs.productcrud.entity.enums.OrderStatus;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "shop_order")
@Data
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  @Column
  private String address;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
  @ManyToMany
  @JoinTable(
      name = "order_product",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> products;

}
