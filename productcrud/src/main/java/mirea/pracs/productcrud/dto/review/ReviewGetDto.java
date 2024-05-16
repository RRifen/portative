package mirea.pracs.productcrud.dto.review;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import mirea.pracs.productcrud.dto.user.UserGetDto;

@Data
@Builder
public class ReviewGetDto {

  private Long reviewId;
  private Double rating;
  private String description;
  private String header;
  private LocalDateTime updatedTimestamp;
  private UserGetDto userGetDto;

}
