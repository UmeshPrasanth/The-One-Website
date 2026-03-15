package com.example.contact.repository;

import com.example.contact.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCommentContainingIgnoreCase(String name, String email, String comment);
}
