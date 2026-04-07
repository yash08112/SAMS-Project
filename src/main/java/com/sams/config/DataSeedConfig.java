package com.sams.config;

import com.sams.service.AttendanceService;
import com.sams.service.StudentService;
import com.sams.service.SubjectService;
import com.sams.util.DataSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeedConfig {

    @Bean
    CommandLineRunner seedData(StudentService studentService, SubjectService subjectService, AttendanceService attendanceService) {
        return args -> DataSeeder.seed(studentService, subjectService, attendanceService);
    }
}

