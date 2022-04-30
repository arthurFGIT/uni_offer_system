package de.hsrm.mi.web.projekt.benutzerprofil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/benutzerprofil")
public class BenutzerProfileController {
    Logger logger = LoggerFactory.getLogger(BenutzerProfileController.class);
    
    @GetMapping
    public String profilansicht_get(Model m){

        BenutzerProfil profil = new BenutzerProfil();

        profil.setName("Arthur");
        profil.setGeburtsdatum(LocalDate.of(2000,12,20));
        profil.setAdresse("Nußbaumweg 8, 65597 Hünfelden");
        profil.setEmail("arthur.fie00@gmail.com");
        profil.setLieblingsfarbe("#000000");
        profil.setInteressen("pc, sport, fahrrad");
        
        m.addAttribute("name", "Arthur")
        .addAttribute("gebdatum", profil.getGeburtsdatum())
        .addAttribute("adresse", profil.getAdresse())
        .addAttribute("email", profil.getEmail())
        .addAttribute("lieblingsfarbe", profil.getLieblingsfarbe());

        return "benutzerprofil/profilansicht";
    }

    // @PostMapping()
    // public String 



    // @GetMapping
    // public String profileditor_get(Model m){
        
    //     return "benutzerprofil/profilansicht";
    // }
    
}
