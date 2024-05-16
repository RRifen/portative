package mirea.pracs.productcrud.dto.review;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewWrapperDto {

  List<ReviewGetDto> data;

}
