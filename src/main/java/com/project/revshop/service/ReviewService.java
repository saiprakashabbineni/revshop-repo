package com.project.revshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.revshop.model.Review;
import com.project.revshop.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;

	public void addReview(Review review) {
		reviewRepo.save(review);
	} 
}
