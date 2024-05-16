package mirea.pracs.productcrud.service;

import java.util.Objects;
import mirea.pracs.productcrud.entity.Review;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

  public void requireUserIsAuthor(User user, Review review) {
    Long authorId = review.getAuthor().getUserId();
    Long userId = user.getUserId();
    if (!Objects.equals(authorId, userId)) {
      throw new ForbiddenException("User is not an author");
    }
  }

}
