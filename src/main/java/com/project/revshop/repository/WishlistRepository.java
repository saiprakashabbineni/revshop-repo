//package com.project.revshop.repository;
//
//
//import com.project.revshop.entity.Wishlist;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Set;
//
//@Repository
//public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
//    Set<Wishlist> findByUserUserId(Integer userId);
//    Wishlist findByUserUserIdAndProductProductId(Integer userId, Integer productId);
//	void addToWishlist(Long userId, Long productId);
//	void removeFromWishlist(Long userId, Long productId);
//}
