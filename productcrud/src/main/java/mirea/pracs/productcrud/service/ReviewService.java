package mirea.pracs.productcrud.service;

import mirea.pracs.productcrud.converter.ReviewConverter;
import mirea.pracs.productcrud.converter.UserConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.review.ReviewPostDto;
import mirea.pracs.productcrud.dto.review.ReviewWrapperDto;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.Review;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.ReviewRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewConverter reviewConverter;
  private final UserConverter userConverter;

  public ReviewService(ReviewRepository reviewRepository, ReviewConverter reviewConverter, UserConverter userConverter) {
    this.reviewRepository = reviewRepository;
    this.reviewConverter = reviewConverter;
    this.userConverter = userConverter;
  }

  @Transactional
  public PostResponse createReview(
      ReviewPostDto reviewPostDto,
      Product product,
      User user
  ) {
    var review = reviewConverter.convertToReview(reviewPostDto, product, user);
    reviewRepository.save(review);
    return new PostResponse(review.getReviewId());
  }

  public ReviewWrapperDto readReviews(Product product) {
    var reviewAuthorPairs = reviewRepository.findAllByProduct(product)
        .stream()
        .map(review -> new ImmutablePair<>(
            review,
            userConverter.convertToUserGetDto(review.getAuthor())
        ))
        .toList();
    return reviewConverter.convertToReviewWrapperDto(reviewAuthorPairs);
  }

  public Review readReviewEntity(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Review with id %d not found", reviewId)
        ));
  }

  @Transactional
  public void deleteReview(Long reviewId) {
    reviewRepository.deleteById(reviewId);
  }

}
