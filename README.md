THE ONE – Band Music and Ticketing Platform:

THE ONE is a modern web platform designed for music lovers to explore bands, discover live music events, and book tickets seamlessly. The platform connects bands and audiences by providing an easy way to manage concerts, sell tickets, and engage with fans.

Features:

Event Discovery: Users can explore upcoming concerts and live music events.
Band Profiles: Dedicated pages for bands showcasing their music, members, and upcoming performances.
Online Ticket Booking: Secure ticket booking system for concerts and music shows.
Admin Dashboard: Admin can manage events, bookings, and band details.
User Authentication: Secure login and registration for users.
Booking Management: Users can view and manage their booked tickets.
Responsive Design: Optimized for mobile, tablet, and desktop devices.
Event Notifications: Users get updates about upcoming shows and bookings.


Screenshots

Home Page
<img width="1901" height="966" alt="image" src="https://github.com/user-attachments/assets/4230048a-c310-470a-b2e4-5a3e29e719fe" />

Event Listing
<img width="1918" height="1014" alt="image" src="https://github.com/user-attachments/assets/ec9cfda8-e417-413c-90b2-a41e82962f3e" />

<img width="1905" height="1012" alt="image" src="https://github.com/user-attachments/assets/48e3c2c8-9e5f-468e-95fe-1059c94f7041" />

Ticket Booking Page
<img width="1919" height="1020" alt="image" src="https://github.com/user-attachments/assets/23abcdf2-36d2-4185-8e22-b67730a4303e" />

Admin Dashboard
<img width="1919" height="1013" alt="image" src="https://github.com/user-attachments/assets/d086ad9f-7fc7-4f56-8e14-1ca2ea31d970" />


Tech Stack

Java Spring Boot – Backend development

HTML5 – Page structure

CSS3 – Styling and layout

JavaScript – Frontend functionality

MySQL – Database management

REST API – Communication between frontend and backend

Git & GitHub – Version control

Self-Hosting Guide

Deploy with Vercel / Cloud Platforms

You can deploy the frontend using Vercel, Netlify, or any cloud hosting platform and run the backend using Spring Boot server or cloud services like Render / Railway / AWS.


Alternatively:

1. Clone the repository
   
git clone https://github.com/UmeshPrasanth/The-One-Website.git

3. Navigate to project folder
   
cd THE-ONE-Band-Music-Ticketing-Platform

5. Configure database

Create a MySQL database and update the application.properties file.

spring.datasource.url=jdbc:mysql://localhost:3306/theone_db
spring.datasource.username=your_username
spring.datasource.password=your_password

4. Run the Spring Boot backend
   
mvn spring-boot:run

or run the main application file in your IDE.

5. Open the frontend
   
Open the frontend files in your browser or run using Live Server.

Future Improvements

Online payment gateway integration (Razorpay / Stripe)
Live music streaming
Artist merchandise store
Real-time seat selection for events
Mobile application version

Author

UmeshPrasanth S

B.E Computer Science and Engineering

Sathyabama Institute of Science and Technology
