# Parkbnb: A Smart Parking Reservation and Management Solution

Parkbnb is a comprehensive parking management system designed to simplify parking reservations, improve efficiency for rentees and renters, and provide a seamless user experience. It addresses challenges in urban and high-traffic areas, offering features like parking spot search, geolocation services, secure payments, and user reviews.

# Video Link

https://northeastern-my.sharepoint.com/:v:/r/personal/patel_vanshi_northeastern_edu/Documents/OOD_FINAL_PROJECT_GROUP2.MOV?csf=1&web=1&e=durQvo&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D

## Contributors 👩🏻‍💻
- Keya Goswami    `Project Manager`
- Shreya Wanisha  `Tech Lead`
- Dharana Kashyap `Developer`
- Vanshi Patel    `Developer`
- Shriya Pratapwar `Designer`

## Tech Stack
***Front End***
- **React.js (Material UI):** Dynamic user interface.
- **Google Maps API:** Map integration for parking spot navigation.
- **Opencage Geocoding API:** Address-to-coordinates conversion.
- **Stripe Payment Integration:** Secure payment processing.
- **Bcrypt.js:** Password hashing for user security.

***Back End***
- **Java (Spring Boot):** Monolith architecture adhering to SOLID principles.
- **Spring Data JPA:** Database operations.
- **PostgreSQL:** Relational database for entity management
- **Security:** JWT for authentication and authorization.

## Key Functionalities
- **Parking Spot Management:** Add, edit, delete, and search for parking spots.
- **Geolocation Services:** Nearby parking spot detection using Google Maps
- **User Reviews and Ratings:** Feedback for parking locations.
- **Reservation Management:** Booking reservations.
- **Payment Processing:** Stripe integration for secure transactions.
- **Role-Based Dashboards:** Renter and rentee-specific features.

## Backend Architecture
***Services implemented include:***
- **BankDetailService:** Manages renters’ bank details.
- **CardService:** Handles rentees’ card details.
- **GeocodingService:** Converts addresses to latitude/longitude and vice versa.
- **ParkingLocationService:** Manages parking locations.
- **ParkingSpotService:** Operates individual parking spots.
- **PaymentService:** Facilitates payments during reservations.
- **ReservationService:** Central reservation management.
- **ReviewService:** Manages reviews and ratings.

## Database Schema
***Entities include:***
- Parking Locations and Spots
- Users (Renters and Rentees)
- Reservations
- Payments
- Reviews and Ratings

## Object - Oriented Principles
- Loose Coupling
- SOLID Principles
- Abstraction
- Inheritance
- Polymorphism
- Encapsulation

## Project Contributions
***Frontend***
- Dharana Kashyap: Parking location pages, Google Maps integration, reviews, and dynamic forms for managing spots.
- Vanshi Patel: Profile sections, landing page, and reusable components.
- Shriya Pratapwar: Sign-in/sign-up, session handling, and password reset.
- Shreya Wanisha: Payment interface, card management, and receipts.
- Keya Goswami: Geolocation-based search and renter dashboard.

***Backend***
- Dharana Kashyap: Database setup, renter/rentee APIs, and image upload functionality.
- Vanshi Patel: Bank details and refund management.
- Shriya Pratapwar: Login, session handling, and reservations.
- Shreya Wanisha: Stripe integration for payments.
- Keya Goswami: Geolocation APIs and parking spot management.

## Future Scope
- Real-time notifications and alerts.
- Advanced analytics for renters.
- Enhanced traffic prediction for parking demand.
- Refunds


# How to run Parkbnb

***Prerequisites***

**Before running the project, ensure you have the following installed:**
- Java 23
- Maven
- Node.js (for the frontend)
- PostgreSQL
- Git

`1. Clone the Repository`
- git clone <repository-url>

`2. Run the Backend`

***Configure Database***
- Open application.properties or application.yml in the src/main/resources directory.
  - **Update the database credentials:** 
    - spring.application.name=parking-application
    - spring.datasource.url=jdbc:postgresql://localhost:5432/parking_db
    - spring.datasource.username=postgres
    - spring.datasource.password=postgres
  
- **Create a PostgreSQL database named parking_db:**
  - CREATE DATABASE parking_db;

***Install Dependencies***
- mvn clean install
- mvn spring-boot:run
    
`3. Run the Frontend`

***Install Dependencies***
**Run the following npm command to install frontend dependencies:**
- npm install

`4. Start the Application`
- npm start

`Stripe Payment Integration`
- Configuration - Configured Stripe in Test Mode to enable secure payment handling for the app.
- Backend Services -  Created backend services to handle payment intents and integrated them with the frontend.
- Error Handling - Implemented error handling to display relevant messages for failed payments (e.g., insufficient funds, card declined).
- Receipt Generation - Generated receipts for successful payments and displayed detailed error messages for failures.
- Test Card Support : Successful Examples

  - Visa: 4242 4242 4242 4242
  - Mastercard: 5555 5555 5555 4444 
  - American Express: 3782 822463 10005
  - Generic Decline: 4000 0000 0000 0002 
  - Insufficient Funds: 4000 0000 0000 9995

**For any queries you can connect any of us 📧**


[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/6xRviSdT)
# Welcome to the course CSYE6200- Concepts of Object Oriented Design
> Northeastern University, College of Engineering


## Professor: Daniel Peters

### Requirements
1. Eclipse or VS Code or IntelliJ.

Note: If you are using Eclipse, please have git CLI installed on your system or GitHub Desktop to commit the code in this repository

### SetUp Instructions
1. Please clone the repository on your local system
2. For Eclipse Import the project as Existing Maven Project, For IntelliJ you can directlty open it using 'Get from VCS'.
4. All code should be pushed to the main branch
3. Ensure the GitHub actions are successful post push

Submissions will have deadlines, failed GitHub Actions would result in point deductions.

### References
1. Cloning a Repository: <https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository>
2. Any GitHub Setup: Please refer to the Git & GitHub Fundamentals Repository shared to you by your respective TA and refer the README.md section

Please reach out to your respective TA if you need any help in regards with submission/ GitHub

Author:
- Rohan Vasudev Ginde (ginde.r@northeastern.edu)
- Yesha Joshi (joshi.ye@northeastern.edu)

