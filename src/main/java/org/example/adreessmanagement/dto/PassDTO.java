package org.example.adreessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PassDTO {

    String password;

    PassDTO(String password){
        this.password = password;
    }

}
