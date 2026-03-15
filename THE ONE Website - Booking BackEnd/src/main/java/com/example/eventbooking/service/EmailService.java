// // package com.example.eventbooking.service;

// // import com.example.eventbooking.model.Booking;
// // import com.example.eventbooking.model.Event;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.mail.SimpleMailMessage;
// // import org.springframework.mail.javamail.JavaMailSender;
// // import org.springframework.stereotype.Service;

// // @Service
// // public class EmailService {

// //     @Autowired
// //     private JavaMailSender mailSender;

// //     // Send simple mail
// //     public void sendSimpleMessage(String to, String subject, String text) {
// //         SimpleMailMessage message = new SimpleMailMessage();
// //         message.setTo(to);
// //         message.setSubject(subject);
// //         message.setText(text);
// //         mailSender.send(message);
// //     }

// //     // Send booking confirmation
// //     public void sendBookingConfirmation(Booking booking) {
// //         Event event = booking.getEvent();

// //         String to = booking.getEmail();
// //         String subject = "Booking Confirmation - " + event.getName();
// //         String text = "Hi " + booking.getName() + ",\n\n"
// //                 + "Your booking has been confirmed!\n\n"
// //                 + "Event Details:\n"
// //                 + "Title: " + event.getName() + "\n"
// //                 + "Description: " + event.getDescription() + "\n"
// //                 + "Tickets Booked: " + booking.getNumTickets() + "\n\n"
// //                 + "Thank you for booking with THE ONE Event System!\n\n"
// //                 + "- Team THE ONE";

// //         sendSimpleMessage(to, subject, text);
// //     }
// // }


// package com.example.eventbooking.service;

// import com.example.eventbooking.model.Booking;
// import com.example.eventbooking.model.Event;
// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.stereotype.Service;

// @Service
// public class EmailService {

//     @Autowired
//     private JavaMailSender mailSender;

//     // ✅ Inject your Gmail and custom sender name
//     @Value("${spring.mail.username}")
//     private String fromEmail;

//     @Value("${app.mail.from-name:The One Booking System}")
//     private String fromName;

//     public void sendBookingConfirmation(Booking booking) {
//         Event event = booking.getEvent();

//         String to = booking.getEmail();
//         String subject = "🎟️ Booking Confirmation - " + event.getName();


//         String htmlContent = """
//         <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; padding: 20px;">
//           <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 25px;">
//             <h2 style="color: #0d6efd; text-align: center;">🎉 Booking Confirmed!</h2>
//             <p style="font-size: 16px; color: #333;">Hi <b>%s</b>,</p>
//             <p style="font-size: 15px; color: #333;">
//               Thank you for booking with <b>THE ONE Event System</b>! Your booking has been successfully confirmed.
//             </p>
//             <hr style="border: none; border-top: 2px solid #0d6efd; margin: 15px 0;">
//             <h4 style="color: #0d6efd;">Event Details:</h4>
//             <table style="width: 100%%; border-collapse: collapse; font-size: 15px;">
//               <tr><td><b>🎤 Event:</b></td><td>%s</td></tr>
//               <tr><td><b>📝 Description:</b></td><td>%s</td></tr>
//               <tr><td><b>🎟️ Tickets Booked:</b></td><td>%d</td></tr>
//             </table>
//             <hr style="border: none; border-top: 2px solid #eee; margin: 15px 0;">
//             <p style="font-size: 14px; color: #666;">If you have any questions, feel free to reply to this email.</p>
//             <p style="font-size: 14px; color: #666;">We look forward to seeing you at the event!</p>
//             <div style="text-align: center; margin-top: 25px;">
//               <a href="#" style="background: #0d6efd; color: white; padding: 10px 20px; text-decoration: none; border-radius: 6px; font-size: 14px;">
//                 View Your Booking
//               </a>
//             </div>
//             <p style="text-align: center; margin-top: 30px; font-size: 13px; color: #aaa;">
//               © 2025 THE ONE Booking System | All rights reserved.
//             </p>
//           </div>
//         </div>
//         """.formatted(
//                 booking.getName(),
//                 event.getName(),
//                 event.getDescription(),
//                 booking.getNumTickets()
//         );

//         try {
//             MimeMessage message = mailSender.createMimeMessage();
//             MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

//             helper.setTo(to);
//             helper.setSubject(subject);
//             helper.setFrom(String.format("%s <%s>", fromName, fromEmail)); // ✅ custom sender name
//             helper.setText(htmlContent, true); // ✅ enable HTML

//             mailSender.send(message);
//             System.out.println("✅ Booking confirmation email sent to: " + to);
//         } catch (MessagingException e) {
//             e.printStackTrace();
//             System.out.println("❌ Failed to send email to: " + to);
//         }
//     }
// }


package com.example.eventbooking.service;

import com.example.eventbooking.model.Booking;
import com.example.eventbooking.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.mail.from-name:THE ONE Booking System}")
    private String fromName;

    // ✅ Generic email sender (this is the one called from the controller)
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.format("%s <%s>", fromName, fromEmail)); // custom sender name
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // ✅ Booking confirmation email (called after booking)
    public void sendBookingConfirmation(Booking booking) {
        Event event = booking.getEvent();
        if (event == null) return;

        String to = booking.getEmail();
        String subject = "🎟️ Booking Confirmation - " + event.getName();
        String text = String.format(
                "Hi %s,\n\n" +
                "We’re thrilled to confirm your booking with THE ONE Booking System!\n\n" +
                "📅 Event: %s\n" +
                "📝 Description: %s\n" +
                "🎫 Tickets Booked: %d\n\n" +
                "Please arrive 30 minutes before the event.\n" +
                "Thank you for choosing us!\n\n" +
                "Warm regards,\n%s\n%s",
                booking.getName(),
                event.getName(),
                event.getDescription(),
                booking.getNumTickets(),
                fromName,
                fromEmail
        );
        sendSimpleMessage(to, subject, text);
    }
}
