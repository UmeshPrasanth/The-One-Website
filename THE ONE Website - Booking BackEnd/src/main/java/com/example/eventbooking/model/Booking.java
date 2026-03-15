package com.example.eventbooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    private String name;
    private String email;
    private String phone;
    private int numTickets;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
