package shopping.product.controller;


import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.product.dto.AddProductRequestDto;
import shopping.product.dto.ProductResponseDto;
import shopping.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@Valid @RequestBody AddProductRequestDto request) {
        ProductResponseDto response = productService.addProduct(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id())).build();
    }
}
