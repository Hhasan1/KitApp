package com.kitapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kitapp.entity.User;
import com.kitapp.repository.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Eğer admin kullanıcı yoksa ekle
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@kitapp.com");
            admin.setPhone("05555555555");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRole(User.Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Default admin user created.");
        }
    }
}