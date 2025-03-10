package org.example.adreessmanagement.service;

import org.example.adreessmanagement.dto.AuthUserDTO;
import org.example.adreessmanagement.dto.LoginDTO;
import org.example.adreessmanagement.dto.PassDTO;
import org.example.adreessmanagement.interfac.AuthUserInterface;
import org.example.adreessmanagement.model.AuthUser;
import org.example.adreessmanagement.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthUserService implements AuthUserInterface {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Register User
    public String register(AuthUserDTO user) {
        AtomicInteger c = new AtomicInteger();
        authUserRepository.findAll().forEach(authUser -> {
            if (authUser.getEmail().equals(user.getEmail())) {
                System.out.println("Email already exists: " + authUser.getEmail());
                c.set(1);
            }
        });
        if (c.get() == 1) {
            return "User already exists";
        }

        String hashedPassword = encoder.encode(user.getPassword());
        authUserRepository.save(new AuthUser(null, user.getName(), hashedPassword, user.getEmail()));
        return "User registered";
    }

    // Login User
    @Override
    public String login(LoginDTO user) {
        AuthUser authUser = authUserRepository.findByEmail(user.getEmail());

        if (authUser == null) {
            return "User not found!";
        }

        if (!encoder.matches(user.getPassword(), authUser.getPassword())) {
            return "Invalid password!";
        }

        String subject = "Login Successful!";
        String body = "Hello " + authUser.getName() + ",\n\nYou have successfully logged in.\n\nBest Regards,\nYour Team";
        emailService.sendEmail(authUser.getEmail(), subject, body);

        return "Login successful! Email sent to " + authUser.getEmail();
    }

    // Forgot Password
    public AuthUserDTO forgotPassword(PassDTO pass, String email) {
        AuthUser foundUser = authUserRepository.findByEmail(email);

        if (foundUser == null) {
            throw new RuntimeException("User not registered!");
        }

        String hashedPassword = encoder.encode(pass.getPassword());
        foundUser.setPassword(hashedPassword);

        authUserRepository.save(foundUser);

        emailService.sendEmail(email, "Password Reset Status", "Your password has been changed!");

        return new AuthUserDTO(foundUser.getName(), foundUser.getPassword(), foundUser.getEmail());
    }

    // Reset Password
    public String resetPassword(String email, String currentPass, String newPass) {
        AuthUser foundUser = authUserRepository.findByEmail(email);

        if (foundUser == null) {
            return "User not registered!";
        }

        if (!encoder.matches(currentPass, foundUser.getPassword())) {
            return "Incorrect current password!";
        }

        foundUser.setPassword(encoder.encode(newPass));
        authUserRepository.save(foundUser);

        emailService.sendEmail(email, "Password Reset Status", "Your password has been reset successfully!");

        return "Password reset successful!";
    }
}
