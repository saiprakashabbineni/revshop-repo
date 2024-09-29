package com.project.revshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.revshop.model.Category;
import com.project.revshop.model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
	List<Size> findByCategory(Optional<Category> category);
}

