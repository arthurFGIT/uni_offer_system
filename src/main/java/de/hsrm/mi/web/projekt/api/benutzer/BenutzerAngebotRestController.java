package de.hsrm.mi.web.projekt.api.benutzer;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilService;
import de.hsrm.mi.web.projekt.gebot.Gebot;

@RestController
@RequestMapping
public class BenutzerAngebotRestController {

    @Autowired private BenutzerprofilService bService;


    @GetMapping("/api/angebot")
    public List<GetAngebotResponseDTO> getAngebote_DTOS(){   
        List<Angebot> angebote = bService.alleAngebote();
        List<GetAngebotResponseDTO> angebotDTO = new ArrayList<>();
        for(Angebot gebot : angebote){
            angebotDTO.add(GetAngebotResponseDTO.from(gebot));
        }        
        return angebotDTO;
    }

    @GetMapping("/api/angebot/{id}")
    public GetAngebotResponseDTO getAngebot_DTO(@PathVariable long id){   
        return GetAngebotResponseDTO.from(bService.findeAngebotMitId(id).get());
    }

    @GetMapping("/api/angebot/{id}/gebot")
    public List<GetGebotResponseDTO> getGebote_DTOS(@PathVariable("id") long id){   
        Angebot angebot = bService.findeAngebotMitId(id).get();
        List<GetGebotResponseDTO> geboteDTO = new ArrayList<>();
        for(Gebot gebot : angebot.getGebote()){
            geboteDTO.add(GetGebotResponseDTO.from(gebot));
        }      
        return geboteDTO;
    }
    
    @GetMapping("/api/benutzer/{id}/angebot")
    public List<GetAngebotResponseDTO> getAngeboteNutzerID_DTOS(@PathVariable("id") long id){
        BenutzerProfil profil = bService.holeBenutzerProfilMitId(id).get();
        List<GetAngebotResponseDTO> angeboteDTO = new ArrayList<>();
        for(Angebot angebot : profil.getAngebote()){
            angeboteDTO.add(GetAngebotResponseDTO.from(angebot));
        }
        return angeboteDTO;
    }    
}
