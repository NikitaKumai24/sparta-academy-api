# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Workspace Overview

This is a Java development workspace (`Tech216-8/`) containing multiple Spring Boot and core Java projects for Sparta Global Academy training. The primary project is **sparta-academy-api**.

## Main Project: Sparta Academy API

**Location:** `Tech216-8/sparta-academy-api/SpartaAPI/`

A Spring Boot 3.2.5 application (Java 21) managing Trainers, Trainees, and Courses with both a REST API and a Thymeleaf web layer.

### Build & Run Commands

```bash
# From Tech216-8/sparta-academy-api/SpartaAPI/

# Build the project
./mvnw clean package

# Run the application (starts on port 8091)
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=CourseServiceTests

# Run a single test method
./mvnw test -Dtest=CourseServiceTests#testMethodName
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

### Architecture

Layered Spring Boot architecture with parallel REST API and MVC web controllers:

```
controllers/         → REST API endpoints (@RestController, /api/*)
webcontrollers/      → MVC controllers (@Controller) serving Thymeleaf views
services/            → Business logic layer
repositories/        → Spring Data JPA interfaces
entities/            → JPA entity classes (Course, Trainer, Trainee, User)
dtos/                → Data Transfer Objects and MapStruct mappers
security/            → Spring Security config (WebSecurityConfig)
config/              → Application configuration (AppConfig)
resources/templates/ → Thymeleaf HTML templates (organized by entity)
```

**Key relationships:** Trainer → (1:many) → Course → (1:many) → Trainee

### Dependencies & Configuration

- **Database:** H2 in-memory (`jdbc:h2:mem:sparta-db`), `create-drop` DDL strategy — data resets on restart
- **API docs:** SpringDoc OpenAPI at `/swagger-ui.html`
- **Security:** Spring Security with form login; seeded users: admin/trainer/trainee (all `password123`)
- **Mapping:** MapStruct 1.5.5 for entity↔DTO conversion
- **Testing:** JUnit 5 + Mockito + AssertJ; tests use `@ExtendWith(MockitoExtension.class)` with `@Mock`/`@InjectMocks`

### Test Conventions

- Service tests mock repositories and verify business logic
- Web controller tests verify MVC behavior
- BDD-style test naming with `@DisplayName` annotations

## Other Projects in Tech216-8

| Project | Type |
|---|---|
| `JavaBasics`, `ControlFlow_Lab_Starter`, `Operators_Lab_Starter` | Core Java exercises with JUnit 5 |
| `TestFirstDevelopmentLab` | TDD practice |
| `NorthwindAPI`, `North v3`, `Northwind v2` | Northwind database API projects |
| `Tech516518/SpartaTodo` | Spring Boot todo app |
| `my-profile` | HTML/CSS portfolio site |
| `SpartaJavaCore`, `lesson-code`, `starter-code` | Course materials |

All Maven projects use the Maven Wrapper (`mvnw`/`mvnw.cmd`) and follow the same `./mvnw test` pattern.
