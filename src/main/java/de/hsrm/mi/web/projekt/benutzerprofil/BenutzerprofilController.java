package de.hsrm.mi.web.projekt.benutzerprofil;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/benutzerprofil")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {
    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);
    
    @ModelAttribute("profil")
    public void initProfil(Model m){
        m.addAttribute("profil", new BenutzerProfil());
    }

    @GetMapping
    public String profilansicht_get(Model m){
        return "/benutzerprofil/profilansicht";
    }

    @GetMapping("/bearbeiten")
    public String profileditor_get(Model m){        
        return "/benutzerprofil/profileditor";
    }

    @PostMapping("/bearbeiten")
    public String profileditor_post(@Valid @ModelAttribute("profil") BenutzerProfil profil, BindingResult result) {
        if(result.hasErrors()){
            return "benutzerprofil/profileditor";
        }
        return "redirect:/benutzerprofil";
    }
}
