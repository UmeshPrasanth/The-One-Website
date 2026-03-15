package com.example.eventbooking.controller;

import com.example.eventbooking.dto.InitiateBookingRequest;
import com.example.eventbooking.model.Booking;
import com.example.eventbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired 
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> bookEvent(@RequestBody InitiateBookingRequest req) {
        try {
            Booking booking = bookingService.bookEvent(
                    req.getName(),
                    req.getEmail(),
                    req.getPhone(),
                    req.getEventId(),
                    req.getNumTickets()
            );
            return ResponseEntity.ok(Map.of(
                    "message", "Booking confirmed!",
                    "bookingId", booking.getBookingId(),
                    "event", booking.getEvent().getName(),
                    "numTickets", booking.getNumTickets()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok(Map.of("message", "Booking cancelled successfully!"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("✅ Booking API is working!");
    }
}
