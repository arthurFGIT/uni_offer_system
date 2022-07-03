package de.hsrm.mi.web.projekt.benutzerprofil;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.messaging.BackendInfoService;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;
import de.hsrm.mi.web.projekt.projektuser.ProjektUser;
import de.hsrm.mi.web.projekt.projektuser.ProjektUserServiceImpl;

@Controller
@RequestMapping("/benutzerprofil")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {

    @Autowired private BenutzerprofilServiceImpl bService;

    @Autowired ProjektUserServiceImpl pServiceImpl;

    @Autowired private BackendInfoService bInfoService;


    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);
    
    @ModelAttribute("profil")
    public void initProfil(Model m, Principal p){

        if(p != null){
            ProjektUser pu = pServiceImpl.findeBenutzer(p.getName());
            if(pu != null){
                m.addAttribute("profil", pu.getBenutzerProfil());
            }
            else{
                m.addAttribute("profil", new BenutzerProfil());
            }
        }
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

    @PostMapping("/angebot")
    public String angebot_post(Model m, @ModelAttribute("profil") BenutzerProfil profil, @ModelAttribute("angebot") Angebot angebot) {        
        
        bService.fuegeAngebotHinzu(profil.getId(), angebot);
        profil = bService.holeBenutzerProfilMitId(profil.getId()).get();
        m.addAttribute("profil", profil);        
        bInfoService.sendInfo("angebot", BackendOperation.CREATE, angebot.getId());
        return "redirect:/benutzerprofil";
    }

    @GetMapping("/angebot")
    public String angebot_get(Model m){   
        m.addAttribute("angebot", new Angebot());     
        return "/benutzerprofil/angebotsformular";
    }

    @GetMapping("/angebot/{id}/del")
    public String angebotLoeschen(Model m, @ModelAttribute("profil") BenutzerProfil profil, @PathVariable("id") long id) {
        
        long userId = profil.getId();
        bService.loescheAngebot(id);
        m.addAttribute("profil", bService.holeBenutzerProfilMitId(userId).get());
        bInfoService.sendInfo("angebot", BackendOperation.DELETE, id);

        return "redirect:/benutzerprofil";
    }

}
