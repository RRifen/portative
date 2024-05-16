package mirea.pracs.productcrud.converter;

import java.util.List;
import mirea.pracs.productcrud.dto.review.ReviewGetDto;
import mirea.pracs.productcrud.dto.review.ReviewPostDto;
import mirea.pracs.productcrud.dto.review.ReviewWrapperDto;
import mirea.pracs.productcrud.dto.user.UserGetDto;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.Review;
import mirea.pracs.productcrud.entity.User;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

  public ReviewGetDto convertToReviewGetDto(Review review, UserGetDto userGetDto) {
    return ReviewGetDto.builder()
        .reviewId(review.getReviewId())
        .header(review.getHeader())
        .description(review.getDescription())
        .rating(review.getRating())
        .updatedTimestamp(review.getUpdatedTimestamp())
        .userGetDto(userGetDto)
        .build();
  }

  public Review convertToReview(ReviewPostDto reviewPostDto, Product product, User author) {
    return new Review()
        .setHeader(reviewPostDto.getHeader())
        .setDescription(reviewPostDto.getDescription())
        .setRating(reviewPostDto.getRating())
        .setProduct(product)
        .setAuthor(author);
  }

  public ReviewWrapperDto convertToReviewWrapperDto(List<ImmutablePair<Review, UserGetDto>> reviewAuthorPairs) {
      var reviewsGetDto = reviewAuthorPairs.stream()
          .map(pair -> convertToReviewGetDto(pair.getLeft(), pair.getRight()))
          .toList();
      return new ReviewWrapperDto(reviewsGetDto);
  }

}
