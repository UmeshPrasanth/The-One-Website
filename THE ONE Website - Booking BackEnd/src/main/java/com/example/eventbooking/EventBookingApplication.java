package com.example.eventbooking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.repository.EventRepository;

@SpringBootApplication
public class EventBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBookingApplication.class, args);
    }

    @Bean
CommandLineRunner loadSampleEvents(EventRepository eventRepository) {
    return args -> {
        if (eventRepository.count() == 0) {
            eventRepository.save(new Event(null, "WIENER STATHALE", "Harris Jayaraj musical extravaganza", 100, 499.0));
            eventRepository.save(new Event(null, "NEW YORK EVENT", "Yuvan Shankar Raja's Magic", 150, 799.0));
            eventRepository.save(new Event(null, "LANXESS ARENA", "Anirudh night show", 200, 599.0));
            System.out.println("✅ Sample events inserted successfully!");
        } else {
            System.out.println("ℹ️ Events already exist in the database.");
        }
    };
}
}
