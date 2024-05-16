package mirea.pracs.productcrud.dto.review;

import lombok.Data;

@Data
public class ReviewPostDto {

  private String header;
  private String description;
  private Double rating;
  private Long productId;

}
