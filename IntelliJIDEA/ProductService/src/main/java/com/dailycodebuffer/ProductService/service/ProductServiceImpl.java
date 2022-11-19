package com.dailycodebuffer.ProductService.service;

import com.dailycodebuffer.ProductService.entity.Product;
import com.dailycodebuffer.ProductService.exception.ProductServiceCustomException;
import com.dailycodebuffer.ProductService.model.ProductRequest;
import com.dailycodebuffer.ProductService.model.ProductResponse;
import com.dailycodebuffer.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductResponse productResponse;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product..");
        Product product= Product.builder().productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity()).build();
        productRepository.save(product);

        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}", productId);

        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));
        System.out.println("print product--"+product.getProductName());
        //ProductResponse productResponse
          //      = new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        System.out.println("print in product--"+productResponse.getProductName());
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productid, long quantity) {
        log.info("Reduce Quantity {} for Id: {}", quantity,productid);
        Product product = productRepository.findById(productid)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity) {
            throw new ProductServiceCustomException(
                    "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }
}
