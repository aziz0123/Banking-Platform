package tn.esprit.spring.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.spring.Entity.Role;

import java.time.LocalDate;


@ToString
@Getter
@Setter
public class RegisterDto {
    private String email;
    private String nom;
    private String prenom;
    private String cin;
    private String password;
    private String telephone;
    private LocalDate dateN ;
    private Role role;
}

