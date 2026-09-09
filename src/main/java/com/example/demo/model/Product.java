package com.example.demo.model;

import com.example.demo.strategy.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String brand;
    private Integer stock;
    private Double price;
    private String discountType;

    // ── 1:1 กับ ProductDetail — Product เป็นเจ้าของ FK (detail_id) ──
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;

    // ── 1:N กับ Review — Product เป็นฝั่ง inverse (ไม่เก็บ FK) ──
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // ── Getters / Setters ──
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public ProductDetail getDetail() { return detail; }
    public void setDetail(ProductDetail detail) { this.detail = detail; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    // ── คำนวณราคาหลังหักส่วนลด ด้วย Strategy Pattern (จาก Lab 7) ──
    public Double getDiscountedPrice() {
        DiscountStrategy strategy;

        if (discountType == null) {
            strategy = new NoDiscountStrategy();
        } else if (discountType.equals("MEMBER")) {
            strategy = new StudentDiscountStrategy();
        } else if (discountType.equals("SEASONAL")) {
            strategy = new SeasonalSaleStrategy();
        } else {
            strategy = new NoDiscountStrategy();
        }

        DiscountContext context = new DiscountContext();
        context.setStrategy(strategy);
        return context.executeStrategy(price);
    }
}