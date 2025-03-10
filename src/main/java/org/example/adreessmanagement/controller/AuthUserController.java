package org.example.adreessmanagement.controller;

import org.example.adreessmanagement.dto.AuthUserDTO;
import org.example.adreessmanagement.dto.LoginDTO;
import org.example.adreessmanagement.dto.MailDTO;
import org.example.adreessmanagement.dto.PassDTO;
import org.example.adreessmanagement.interfac.AuthUserInterface;
import org.example.adreessmanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authuser")

public class AuthUserController {
    @Autowired
    public AuthUserInterface authUserInterface;

    @Autowired
    EmailService emailService;



    @PostMapping("/register")
    public String register(@RequestBody AuthUserDTO user) {
        return authUserInterface.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO user) {
        return authUserInterface.login(user);

    }

    //UC11 --> For sending mail to another person
    @PostMapping(path = "/sendMail")
    public String sendMail(@RequestBody MailDTO message) {
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
        return "Mail sent";


    }
    //UC12 --> Added Swagger Config to use Swagger at url(/swagger)
    //UC12 --> Added Swagger Config to use Swagger at url(/swagger)

    //UC13 --> Added forgot password functionality
    @PutMapping("/forgotPassword/{email}")
    public AuthUserDTO forgotPassword(@RequestBody PassDTO pass, @PathVariable String email){
        return authUserInterface.forgotPassword(pass,email);
    }
    //UC14 --> Added reset password functionality
    @PutMapping("/resetPassword/{email}")
    public String resetPassword(@PathVariable String email ,@RequestParam String currentPass, @RequestParam String newPass){
        return authUserInterface.resetPassword(email, currentPass, newPass);
    }



}
