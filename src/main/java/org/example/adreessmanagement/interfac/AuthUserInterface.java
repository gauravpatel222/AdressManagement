package org.example.adreessmanagement.interfac;

import org.example.adreessmanagement.dto.AuthUserDTO;
import org.example.adreessmanagement.dto.LoginDTO;
import org.example.adreessmanagement.dto.PassDTO;

public interface AuthUserInterface {


    public String register(AuthUserDTO user);


    public String login(LoginDTO user);

    public AuthUserDTO forgotPassword(PassDTO pass, String email);

    public String resetPassword(String email, String currentPass, String newPass);



}
