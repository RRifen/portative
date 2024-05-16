package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table
@Data
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;
  @Column
  private String header;
  @Column
  private String description;
  @Column
  private Double rating;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

}
