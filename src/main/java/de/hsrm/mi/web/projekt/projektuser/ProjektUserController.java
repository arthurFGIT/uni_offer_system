package de.hsrm.mi.web.projekt.projektuser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registrieren")
public class ProjektUserController {

    @Autowired ProjektUserServiceImpl pServiceImpl;

    @GetMapping()
    public String register_get(ProjektUser pu){        
        return "/projektuser/registrieren";
    }

    @PostMapping()
    public String register_post(@Valid @ModelAttribute("projektuser") ProjektUser pu, BindingResult result, Model m){        
        if(result.hasErrors()){
            return "projektuser/registrieren";
        }
        try {
            pServiceImpl.neuenBenutzerAnlegen(pu.getUsername(), pu.getPassword(), pu.getRole());  
            return "redirect:/benutzerprofil";           
        } catch (ProjektUserServiceException e) {
            return "projektuser/registrieren";
        }
             
    }

    @ModelAttribute("projektuser")
    public void initProfil(Model m){
        m.addAttribute("projektuser", new ProjektUser());
    }
    
}
