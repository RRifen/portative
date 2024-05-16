package mirea.pracs.productcrud.service;

import mirea.pracs.productcrud.converter.ProductConverter;
import mirea.pracs.productcrud.converter.ProductTypeConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.product.ProductGetDto;
import mirea.pracs.productcrud.dto.product.ProductPatchDto;
import mirea.pracs.productcrud.dto.product.ProductPostDto;
import mirea.pracs.productcrud.dto.product.ProductWrapperDto;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.ProductType;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.ProductRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductConverter productConverter;
  private final ProductTypeConverter productTypeConverter;

  public ProductService(ProductRepository productRepository, ProductConverter productConverter, ProductTypeConverter productTypeConverter) {
    this.productRepository = productRepository;
    this.productConverter = productConverter;
    this.productTypeConverter = productTypeConverter;
  }

  public ProductWrapperDto getProducts() {
    var productPairs = productRepository.findAll()
        .stream()
        .map(product -> new ImmutablePair<>(
            product,
            productTypeConverter.convertToProductTypeGetDto(product.getProductType())
        ))
        .toList();
    return productConverter.convertToProductWrapperDto(productPairs);
  }

  public ProductGetDto getProduct(Long productId) {
    var product = productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product with id %d not foung", productId)
        ));
    return productConverter.convertToProductGetDto(
        product,
        productTypeConverter.convertToProductTypeGetDto(product.getProductType())
    );
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public PostResponse createProduct(ProductPostDto productPostDto, ProductType productType, String imageSrc) {
    Product product = productConverter.convertToProduct(productPostDto, productType, imageSrc);
    product.setImageSrc(imageSrc);
    productRepository.save(product);
    return new PostResponse(product.getProductId());
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public void updateProduct(Long productId, ProductPatchDto productPatchDto) {
    Product oldProduct = productRepository
        .findById(productId)
        .orElseThrow(RuntimeException::new);
    productConverter.merge(oldProduct, productPatchDto, null);
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public void deleteProduct(Long productId) {
    productRepository.deleteById(productId);
  }

}
