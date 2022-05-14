package de.hsrm.mi.web.projekt.benutzerprofil;
import java.util.Optional;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/benutzerprofil")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {


    private BenutzerprofilServiceImpl bService;
    @Autowired
    public BenutzerprofilController(BenutzerprofilServiceImpl bService) {
        this.bService = bService;
    }

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

    @GetMapping("/liste")
    public String profilliste_get(Model m, @RequestParam(required = false) Optional<String> op, @RequestParam(required = false) Long id){   
        
        if(op.isPresent()){
            String opLink = op.get();

            if(opLink.equals("loeschen")){
                bService.loescheBenutzerProfilMitId(id);
                logger.warn("User deleted.");
                return "redirect:/benutzerprofil/liste";
            }
            if(opLink.equals("bearbeiten")){
                logger.info("Got user");
                m.addAttribute("profil", bService.holeBenutzerProfilMitId(id).get());     
                return "redirect:/benutzerprofil/bearbeiten";
            }
        }        
        
        m.addAttribute("profilliste", bService.alleBenutzerProfile());     
        return "benutzerprofil/profilliste";
    }

    @GetMapping("/clearsession")
    public String logout(SessionStatus sessionstatus) {
        sessionstatus.setComplete();
        return "redirect:/benutzerprofil/bearbeiten";
    }

    @PostMapping("/bearbeiten")
    public String profileditor_post(@Valid @ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m) {
        if(result.hasErrors()){
            return "benutzerprofil/profileditor";
        }
        m.addAttribute("profil", bService.speichereBenutzerProfil(profil));
        return "redirect:/benutzerprofil";
    }
}
