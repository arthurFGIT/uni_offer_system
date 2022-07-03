package de.hsrm.mi.web.projekt.projektuser;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Entity
public class ProjektUser {

    @Id
    @NotBlank @NotNull
    @Size(min=3) 
    private String username;

    @NotBlank @NotNull
    @Size(min=3) 
    private String password;

    private String role = "";

    @OneToOne
    private BenutzerProfil benutzerProfil;


    public ProjektUser(@NotBlank @NotNull @Size(min = 3) String username,
            @NotBlank @NotNull @Size(min = 3) String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public ProjektUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public BenutzerProfil getBenutzerProfil() {
        return benutzerProfil;
    }

    public void setBenutzerProfil(BenutzerProfil benutzerProfil) {
        this.benutzerProfil = benutzerProfil;
    } 



}
