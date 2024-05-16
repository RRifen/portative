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
@Table(name = "support_request")
@Data
public class SupportRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long supportRequestId;
  @Column
  private String theme;
  @Column
  private String description;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
