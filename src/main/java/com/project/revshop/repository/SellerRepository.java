package com.project.revshop.repository;

import com.project.revshop.entity.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerModel, Integer> {
    // Custom query methods can be added here if needed
}
