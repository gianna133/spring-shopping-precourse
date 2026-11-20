package shopping.product.controller;


import org.springframework.web.bind.annotation.*;
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
}
