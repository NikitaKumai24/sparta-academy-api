package com.sparta.spartaapi.config;

import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.entities.User;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TraineeRepository;
import com.sparta.spartaapi.repositories.TrainerRepository;
import com.sparta.spartaapi.repositories.UserRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI spartaAcademyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sparta Academy API")
                        .description("REST API for managing trainers, trainees, and courses")
                        .version("1.0"));
    }

    @Bean
    @Transactional
    public CommandLineRunner loadData(TrainerRepository trainerRepository,
                                      CourseRepository courseRepository,
                                      TraineeRepository traineeRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {

        return args -> {
            System.out.println("DataLoader running...");

            if (trainerRepository.count() == 0) {

                // Trainers
                var trainer1 = new Trainer(null, "Nish", "Mandal", "nish.mandal@sparta.com", "07000000001", "Java", "Tiger");
                var trainer2 = new Trainer(null, "Cathy", "French", "cathy.french@sparta.com", "07000000002", "C#", "Panda");

                trainerRepository.save(trainer1);
                trainerRepository.save(trainer2);

                // Courses
                var course1 = new Course("Java Bootcamp", "Spring Boot + APIs", LocalDate.now(), LocalDate.now().plusWeeks(8), trainer1);
                var course2 = new Course("C# Bootcamp", ".NET + APIs", LocalDate.now(), LocalDate.now().plusWeeks(8), trainer2);

                courseRepository.save(course1);
                courseRepository.save(course2);

                // Trainees
                var trainee1 = new Trainee(null, "Nikki", "Kumai", "nikki@sparta.com", "07000000011", "Java");
                trainee1.setCourse(course1);

                var trainee2 = new Trainee(null, "Adil", "Hussain", "adil@sparta.com", "07000000012", "C#");
                trainee2.setCourse(course2);

                traineeRepository.save(trainee1);
                traineeRepository.save(trainee2);

                // Add test users
                var user1 = new User("admin", passwordEncoder.encode("password123"), "ROLE_ADMIN");
                var user2 = new User("trainer", passwordEncoder.encode("password123"), "ROLE_TRAINER");
                var user3 = new User("trainee", passwordEncoder.encode("password123"), "ROLE_TRAINEE");

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                System.out.println("Seed data added (including users)");
            } else {
                System.out.println("Seed skipped");
            }
        };
    }
}
