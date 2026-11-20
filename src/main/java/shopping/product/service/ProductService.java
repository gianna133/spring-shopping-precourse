package shopping.product.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.product.dto.AddProductRequestDto;
import shopping.product.dto.ProductListResponseDto;
import shopping.product.dto.ProductResponseDto;
import shopping.product.dto.UpdateProductRequestDto;
import shopping.product.entity.Product;
import shopping.product.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다. id=" + id));
        return ProductResponseDto.from(product);
    }

    public ProductListResponseDto getProductList() {
        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(product -> ProductResponseDto.from(product))
                .toList();
        return new ProductListResponseDto(products);
    }

    @Transactional
    public ProductResponseDto addProduct(AddProductRequestDto request) {
        Product product = new Product(request.name(), request.price(), request.count(), request.url());
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.from(savedProduct);
    }

    @Transactional
    public void updateProduct(Long id, UpdateProductRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다. id=" + id));
        String name = request.name();
        Integer price = request.price();
        Integer count = request.count();
        String url = request.url();
        product.update(name, price, count, url);

        productRepository.save(product);
    }
}
