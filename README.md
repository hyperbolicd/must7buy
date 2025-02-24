# must7buy
React & Spring Boot Full-Stack Application

## Overview
* This project is a full-stack web application built to practice advanced React and Spring Boot development.  
* The backend is secured using Spring Security with JWT authentication and integrates a third-party payment.   
* The frontend implements private routing and state management using Context API.

## Key Features
### Frontend (React)
* Private Routing: Restricts access to authenticated users.
* Context API: Manages user and cart efficiently.
* Component-Based Design: Implements modular and reusable components, like Button, Table, ...
* FetchData Handling: Centralized API request management.
* Debounce Implementation: Optimized input handling to reduce API calls. 

### Backend (Spring Boot)
* Spring Security with JWT: Provides authentication and authorization.
* Third-Party Payment Integration: Connects with an external payment gateway.
* Global Exception Handling: Ensures consistent error handling.
* Unit Testing: Implements test cases to validate functionality.

### Tech Stack
Frontend: React, React Router, Context API, Fetch, debounce

Backend: Spring Boot, Spring Security, JWT, JUnit

Database: PostgreSQL/MySQL (based on configuration)