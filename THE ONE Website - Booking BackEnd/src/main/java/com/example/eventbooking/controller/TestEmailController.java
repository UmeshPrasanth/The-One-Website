// package com.example.eventbooking.controller;

// import com.example.eventbooking.service.EmailService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/test-email")
// public class TestEmailController {

//     @Autowired
//     private EmailService emailService;

//     @GetMapping
//     public String sendTestEmail() {
//         try {
//             emailService.sendSimpleMessage(
//                     "youremail@gmail.com",              // Replace with your Gmail (or any test recipient)
//                     "Test Email from Spring Boot",
//                     "✅ This is a test email sent using Gmail SMTP & Spring Boot!"
//             );
//             return "Email sent successfully!";
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "Failed to send email: " + e.getMessage();
//         }
//     }
// }


package com.example.eventbooking.controller;

import com.example.eventbooking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class TestEmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String testEmail(@RequestParam String to) {
        try {
            emailService.sendSimpleMessage(to, 
                    "Test Email from THE ONE Booking System", 
                    "✅ This is a test email confirming that your email configuration is working fine.\n\n— Team THE ONE");
            return "✅ Test email sent successfully to: " + to;
        } catch (Exception e) {
            return "❌ Failed to send email: " + e.getMessage();
        }
    }
}

