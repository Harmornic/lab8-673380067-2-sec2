package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public void save(Product product) {
        // ── สำคัญ: ตั้งค่า product กลับไปให้ทุก review ก่อน save ──
        // เพราะ Review เป็นฝั่งเจ้าของ FK (product_id)
        // ถ้าไม่ set ตรงนี้ product_id ใน DB จะเป็น NULL
        List<Review> reviews = product.getReviews();
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            if (review.getReviewer() != null && !review.getReviewer().isEmpty()) {
                review.setProduct(product);
            }
        }
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}