package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.review.ReviewPostDto;
import mirea.pracs.productcrud.dto.review.ReviewWrapperDto;
import mirea.pracs.productcrud.service.AccessControlService;
import mirea.pracs.productcrud.service.ProductService;
import mirea.pracs.productcrud.service.ReviewService;
import mirea.pracs.productcrud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final UserService userService;
  private final ProductService productService;
  private final ReviewService reviewService;
  private final AccessControlService accessControlService;

  public ReviewController(
      UserService userService,
      ProductService productService,
      ReviewService reviewService,
      AccessControlService accessControlService
  ) {
    this.userService = userService;
    this.productService = productService;
    this.reviewService = reviewService;
    this.accessControlService = accessControlService;
  }

  @GetMapping
  public ResponseEntity<ReviewWrapperDto> getReviews(
      @RequestParam("productId") Long productId
  ) {
    var product = productService.getProductEntity(productId);
    var reviewWrapperDto = reviewService.readReviews(product);
    return ResponseEntity
        .ok(reviewWrapperDto);
  }

  @PostMapping
  public ResponseEntity<PostResponse> createReview(
      @RequestBody ReviewPostDto reviewPostDto,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var product = productService.getProductEntity(reviewPostDto.getProductId());
    var postResponse = reviewService.createReview(reviewPostDto, product, user);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<ReviewWrapperDto> deleteReview(
      @PathVariable("reviewId") Long reviewId,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var review = reviewService.readReviewEntity(reviewId);
    accessControlService.requireUserIsAuthor(user, review);
    reviewService.deleteReview(reviewId);
    return ResponseEntity
        .ok()
        .build();
  }

}
