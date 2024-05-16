package mirea.pracs.productcrud.service;

import mirea.pracs.productcrud.converter.OrderConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.order.OrderPostDto;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import mirea.pracs.productcrud.repository.OrderRepository;
import mirea.pracs.productcrud.repository.ProductRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderConverter orderConverter;

  public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderConverter orderConverter) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.orderConverter = orderConverter;
  }

  @Transactional
  public PostResponse createOrder(OrderPostDto orderPostDto, User user) {
    var pairs = orderPostDto.getOrderRecords()
        .stream()
        .map(recordPostDto -> {
          var product = productRepository.findById(recordPostDto.getProductId()).orElseThrow(() -> new ForbiddenException("sdf"));
          return new ImmutablePair<>(recordPostDto, product);
        })
        .toList();
    var order = orderConverter.convertToOrder(orderPostDto, pairs, user);
    orderRepository.save(order);
    return new PostResponse(order.getOrderId());
  }

}
