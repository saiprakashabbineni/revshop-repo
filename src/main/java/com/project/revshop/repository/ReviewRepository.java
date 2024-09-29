package com.project.revshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.revshop.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
}
