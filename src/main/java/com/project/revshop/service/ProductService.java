package com.project.revshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.revshop.controller.ViewProductsController;
import com.project.revshop.entity.Category;
import com.project.revshop.entity.Product;
import com.project.revshop.entity.Size;
import com.project.revshop.enums.Gender;
import com.project.revshop.repository.CategoryRepository;
import com.project.revshop.repository.ProductRepository;
import com.project.revshop.repository.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private CategoryRepository categoryRepository;
	
    @Autowired
	private ProductRepository productRepository;
    
    @Autowired
    private SizeRepository sizeRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
//    public List<Size> getSizesByCategory(Optional<Category> category) {
//        return sizeRepository.findByCategory(category);
//    }
    
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

	public Optional<Category> getCategoryById(int categoryId) {
	        return categoryRepository.findById(categoryId);
	    }

    public List<Size> getAllSizes(int categoryId) {
	    	Optional<Category> category = categoryRepository.findById(categoryId);
	        return sizeRepository.findByCategory(category);
	    }

	public Size getSizeById(int sizeId) {
			// TODO Auto-generated method stub
			return sizeRepository.getReferenceById(sizeId);
			
		}

	public Product getProductDetails(int productId) {
			// TODO Auto-generated method stub
			return productRepository.getReferenceById(productId);
		}

	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		productRepository.deleteById(productId);
	}
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productRepository.findById(productId).get();	
	}
	

	    public List<Product> getProductsByGenderInAndPriceBetween(List<Gender> genders, double minPrice, double maxPrice) {
	        return productRepository.findByGenderInAndPriceBetween(genders, minPrice, maxPrice);
	    }
	    
	    public List<Product> getProductsByPriceBetween(double minPrice, double maxPrice){
	    	
	    	return productRepository.findByPriceBetween(minPrice, maxPrice);
	    }
	    
	 
	    public List<Product> getProductsByCategoryId(List<Integer> categoryId){
	    	
	    	return productRepository.findAllByCategory_CategoryIdIn(categoryId);
	    }
	    
	    public List<Product> getProductsByGenderInAndCategoryIn(List<Gender> genders,List<Integer> categoryId){
	    	
	    	return productRepository.findByGenderInAndCategory_CategoryIdIn(genders, categoryId);
	    }

		public List<Product> getProductsByCategoryIdSizeIdAndPriceBetween(List<Integer> categoryId,List <Integer> sizeId , double minPrice,
				double maxPrice) {
			// TODO Auto-generated method stub
	        return productRepository.findByCategory_CategoryIdInAndSize_SizeIdInAndPriceBetween(categoryId,sizeId, minPrice, maxPrice);
		}
		
	
	    public List<Size> getSizesByCategory(List<Integer> categoryIds) {
	        // Call the repository to fetch sizes based on the categories
	        return sizeRepository.findByCategory_CategoryIdIn(categoryIds);
	    }
	    
	 
	public List<Product>  getProductsByGenderPriceCategoryIdSizeId(List<Gender> genders,List<Integer> categoryId,List <Integer> sizeId , double minPrice,
				double maxPrice){
		return productRepository.findByGenderInAndCategory_CategoryIdInAndSize_SizeIdInAndPriceBetween(genders, categoryId, sizeId, minPrice, maxPrice);
	}

	public List<Product> getProductsByGenderCategoryIdSizeId(List<Gender> selectedGenders, List<Integer> categoryId,
			List<Integer> sizeId) {
		// TODO Auto-generated method stub
		return productRepository.findByGenderInAndCategory_CategoryIdInAndSize_SizeIdIn(selectedGenders, categoryId, sizeId);
	}
} 

