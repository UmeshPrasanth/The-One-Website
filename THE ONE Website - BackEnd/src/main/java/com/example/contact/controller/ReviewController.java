package com.example.contact.controller;

import com.example.contact.model.Review;
import com.example.contact.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Save new review
    // @PostMapping
    // public Review addReview(@RequestBody Review review) {
    //     return reviewService.saveReview(review);
    // }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        System.out.println("📩 Received review ->");
        System.out.println("Name: " + review.getName());
        System.out.println("Email: " + review.getEmail());
        System.out.println("Comment: " + review.getComment());
        return reviewService.saveReview(review);
    }


    // Get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Delete review by ID
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully!";
    }

    // Mark as read/unread
    @PutMapping("/{id}/toggle-read")
    public Review toggleReadStatus(@PathVariable Long id) {
        return reviewService.toggleRead(id);
    }

    // Search (by name, email, or comment)
    @GetMapping("/search")
    public List<Review> searchReviews(@RequestParam String query) {
        return reviewService.searchReviews(query);
    }
}
