package de.hsrm.mi.web.projekt.api.gebot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.gebot.GebotService;

@RestController
@RequestMapping("/api/gebot")
public class GebotRestController {

    @Autowired private GebotService gService;

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public List<GetGebotResponseDTO> getGebote_DTOS(){   
        List<Gebot> gebote = gService.findeAlleGebote();
        List<GetGebotResponseDTO> gebotDTO = new ArrayList<>();
        for(Gebot gebot : gebote){
            gebotDTO.add(GetGebotResponseDTO.from(gebot));
        }        
        return gebotDTO;
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public long postGebote_DTOS(@RequestBody AddGebotRequestDTO gebot){   
        gService.bieteFuerAngebot(gebot.benutzerprofilid(), gebot.angebotid(), gebot.betrag());
        return gebot.benutzerprofilid();
    }

    @DeleteMapping(value="/{id}")
    public void deleteGebot(@PathVariable long id){
        gService.loescheGebot(id);
    }    
}
