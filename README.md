# Sparta Academy API

This project is a Spring Boot REST API built for the Sparta Global Academy.
It manages Trainers, Trainees, and Courses and has been extended with a web layer
using Spring MVC and Thymeleaf.

---

## Requirements

- Java 21
- Maven
- Git
- An IDE (IntelliJ IDEA recommended)

---

## Setup and Run Instructions

### 1. Clone the repository

```bash
git clone https://github.com/NikitaKumai24/sparta-academy-api.git
```

### 2. Open the project

- Open the cloned project folder in your IDE.


### 3. Run the application

- Run the main class: SpartaApiApplication

- The application will start on:

```bash
 http://localhost:8091
```



## API Documentation (Swagger)

- Swagger UI is available at:

```bash
  http://localhost:8091/swagger-ui.html
 ```

- Use Swagger to view and test all REST API endpoints.



## Web Layer (Thymeleaf)

- This application includes a web layer built using Spring MVC and Thymeleaf

- Once the application is running, you can access the following pages:

   - Courses: http://localhost:8091/courses
   - Trainers: http://localhost:8091/trainers
   - Trainees: http://localhost:8091/trainees

These pages display data rendered using Thymeleaf templates.


## Authentication (Spring Security)

Most web pages require login. If you are not authenticated, you will be redirected to the default Spring Security login page:

http://localhost:8091/login

Seeded demo credentials:
- admin / password123
- trainer / password123
- trainee / password123

