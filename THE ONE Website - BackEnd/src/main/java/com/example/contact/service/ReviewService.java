package com.example.contact.service;

import com.example.contact.model.Review;
import com.example.contact.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review toggleRead(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setReadFlag(!review.isReadFlag());
        return reviewRepository.save(review);
    }

    public List<Review> searchReviews(String query) {
        return reviewRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCommentContainingIgnoreCase(query, query, query);
    }
}
