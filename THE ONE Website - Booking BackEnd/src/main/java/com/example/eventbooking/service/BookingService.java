// // package com.example.eventbooking.service;

// // import com.example.eventbooking.model.Booking;
// // import com.example.eventbooking.model.Event;
// // import com.example.eventbooking.repository.BookingRepository;
// // import com.example.eventbooking.repository.EventRepository;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import java.util.List;
// // import java.util.Optional;

// // @Service
// // public class BookingService {

// //     @Autowired
// //     private BookingRepository bookingRepository;

// //     @Autowired
// //     private EventRepository eventRepository;
    
// //     @Autowired
// //     private EmailService emailService;

// //     public Booking bookEvent(String name, String email, String phone, Long eventId, int numTickets) {
// //         Optional<Event> eventOpt = eventRepository.findById(eventId);
// //         if (eventOpt.isEmpty()) {
// //             throw new RuntimeException("Event not found");
// //         }

// //         Event event = eventOpt.get();

// //         if (numTickets > event.getAvailableTickets()) {
// //             throw new RuntimeException("Not enough tickets available");
// //         }

// //         event.setAvailableTickets(event.getAvailableTickets() - numTickets);
// //         eventRepository.save(event);

// //         Booking booking = new Booking();
// //         booking.setName(name);
// //         booking.setEmail(email);
// //         booking.setPhone(phone);
// //         booking.setNumTickets(numTickets);
// //         booking.setEvent(event);

// //         return bookingRepository.save(booking);
// //     }

// //     public List<Booking> getAllBookings() {
// //         return bookingRepository.findAll();
// //     }

// //     public void cancelBooking(Long bookingId) {
// //         Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
// //         if (bookingOpt.isEmpty()) {
// //             throw new RuntimeException("Booking not found");
// //         }

// //         Booking booking = bookingOpt.get();
// //         Event event = booking.getEvent();

// //         if (event != null) {
// //             event.setAvailableTickets(event.getAvailableTickets() + booking.getNumTickets());
// //             eventRepository.save(event);
// //         }

// //         bookingRepository.deleteById(bookingId);

// //     }
// // }


// package com.example.eventbooking.service;

// import com.example.eventbooking.model.Booking;
// import com.example.eventbooking.model.Event;
// import com.example.eventbooking.repository.BookingRepository;
// import com.example.eventbooking.repository.EventRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class BookingService {

//     @Autowired
//     private BookingRepository bookingRepository;

//     @Autowired
//     private EventRepository eventRepository;

//     @Autowired
//     private EmailService emailService;

//     // ✅ Book an Event
//     public Booking bookEvent(String name, String email, String phone, Long eventId, int numTickets) {
//         Optional<Event> eventOpt = eventRepository.findById(eventId);
//         if (eventOpt.isEmpty()) {
//             throw new RuntimeException("Event not found");
//         }

//         Event event = eventOpt.get();

//         // ✅ Check ticket availability
//         if (numTickets > event.getAvailableTickets()) {
//             throw new RuntimeException("Not enough tickets available");
//         }

//         // ✅ Reduce available tickets
//         event.setAvailableTickets(event.getAvailableTickets() - numTickets);
//         eventRepository.save(event);

//         // ✅ Save booking
//         Booking booking = new Booking();
//         booking.setName(name);
//         booking.setEmail(email);
//         booking.setPhone(phone);
//         booking.setNumTickets(numTickets);
//         booking.setEvent(event);

//         Booking savedBooking = bookingRepository.save(booking);

//         // ✅ Send booking confirmation email
//         try {
//             emailService.sendBookingConfirmation(savedBooking);
//         } catch (Exception e) {
//             System.err.println("⚠️ Failed to send booking confirmation email: " + e.getMessage());
//         }

//         return savedBooking;
//     }

//     // ✅ Fetch all bookings (for admin)
//     public List<Booking> getAllBookings() {
//         return bookingRepository.findAll();
//     }

//     // ✅ Cancel booking and send cancellation email
//     public void cancelBooking(Long bookingId) {
//         Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
//         if (bookingOpt.isEmpty()) {
//             throw new RuntimeException("Booking not found");
//         }

//         Booking booking = bookingOpt.get();
//         Event event = booking.getEvent();

//         // ✅ Restore event ticket availability
//         if (event != null) {
//             event.setAvailableTickets(event.getAvailableTickets() + booking.getNumTickets());
//             eventRepository.save(event);
//         }

//         // ✅ Delete the booking
//         bookingRepository.deleteById(bookingId);

//         // ✅ Send cancellation email
//         try {
//             String to = booking.getEmail();
//             String subject = "Booking Cancelled - " + (event != null ? event.getName() : "Event");
//             String text = "Hi " + booking.getName() + ",\n\n"
//                     + "Your booking for \"" + (event != null ? event.getName() : "the event") + "\" "
//                     + "has been successfully cancelled.\n\n"
//                     + "We’ve restored your ticket availability in our system.\n\n"
//                     + "Best regards,\n"
//                     + "THE ONE Event Team";

//             emailService.sendSimpleMessage(to, subject, text);
//         } catch (Exception e) {
//             System.err.println("⚠️ Failed to send cancellation email: " + e.getMessage());
//         }
//     }
// }


package com.example.eventbooking.service;

import com.example.eventbooking.model.Booking;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailService;

    // ✅ Book an event and send confirmation email
    public Booking bookEvent(String name, String email, String phone, Long eventId, int numTickets) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new RuntimeException("Event not found");
        }

        Event event = eventOpt.get();

        if (numTickets > event.getAvailableTickets()) {
            throw new RuntimeException("Not enough tickets available");
        }

        // Reduce ticket count
        event.setAvailableTickets(event.getAvailableTickets() - numTickets);
        eventRepository.save(event);

        // Create and save booking
        Booking booking = new Booking();
        booking.setName(name);
        booking.setEmail(email);
        booking.setPhone(phone);
        booking.setNumTickets(numTickets);
        booking.setEvent(event);

        Booking savedBooking = bookingRepository.save(booking);

        // ✅ Send confirmation email
        try {
            emailService.sendBookingConfirmation(savedBooking);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to send confirmation email: " + e.getMessage());
        }

        return savedBooking;
    }

    // ✅ Fetch all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ✅ Cancel a booking and restore tickets (optional feature)
    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }

        Booking booking = bookingOpt.get();
        Event event = booking.getEvent();

        if (event != null) {
            event.setAvailableTickets(event.getAvailableTickets() + booking.getNumTickets());
            eventRepository.save(event);
        }

        bookingRepository.deleteById(bookingId);
        System.out.println("✅ Booking cancelled successfully for ID: " + bookingId);
    }
}
