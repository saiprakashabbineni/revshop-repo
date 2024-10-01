package com.project.revshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.revshop.entity.Product;
import com.project.revshop.enums.Gender;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    List<Product> findByGenderInAndPriceBetween(List<Gender> genders, double minPrice, double maxPrice);
    
    List <Product> findByPriceBetween(double minPrice, double maxPrice);


	
}
