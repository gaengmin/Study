package com.example.shopping.service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.Product;
import com.example.shopping.input.ProductMaintenanceInput;
import com.example.shopping.repository.ProductRepository;

@Service
@Transactional
public class ProductMaintenanceServiceImpl implements ProductMaintenanceService {
    private final ProductRepository productRepository;

    public ProductMaintenanceServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.selectAll();
    }

    @Override
    public Product findById(String id) {
        return productRepository.selectById(id);
    }

    @Override
    public void update(ProductMaintenanceInput productMaintenanceInput) {
        Product product = new Product();
        product.setId(productMaintenanceInput.getId());
        product.setName(productMaintenanceInput.getName());
        product.setPrice(productMaintenanceInput.getPrice());
        product.setStock(productMaintenanceInput.getStock());
        boolean result = productRepository.update(product);
        if (!result) {
            throw new OptimisticLockingFailureException("이미 삭제됐을 가능성이 있습니다. id=" + product.getId());
        }
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product register(ProductMaintenanceInput productMaintenanceInput) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(productMaintenanceInput.getName());
        product.setPrice(productMaintenanceInput.getPrice());
        product.setStock(productMaintenanceInput.getStock());
        productRepository.insert(product);
        return product;
    }
}
