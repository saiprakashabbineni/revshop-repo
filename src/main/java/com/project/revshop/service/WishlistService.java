package com.project.revshop.service;

import com.project.revshop.entity.Product;
import com.project.revshop.entity.Wishlist;
import com.project.revshop.repository.ProductRepository;
import com.project.revshop.repository.UserRepository;
import com.project.revshop.repository.WishlistRepository;
import jakarta.transaction.Transactional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToWishlist(Integer userId, Integer productId) {
        if (wishlistRepository.findByUserUserIdAndProductProductId(userId, productId) != null) {
            throw new IllegalArgumentException("Product already in wishlist");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser((User) userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
        wishlist.setProduct(productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found")));

        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(Integer userId, Integer productId) {
        Wishlist wishlist = wishlistRepository.findByUserUserIdAndProductProductId(userId, productId);
        if (wishlist == null) {
            throw new IllegalArgumentException("Product not in wishlist");
        }
        wishlistRepository.delete(wishlist);
    }

    public Set<Product> getWishlist(Integer userId) {
        Set<Wishlist> wishlistItems = wishlistRepository.findByUserUserId(userId);
        return wishlistItems.stream()
                .map(Wishlist::getProduct)
                .collect(Collectors.toSet());
    }
}
