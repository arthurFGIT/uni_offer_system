package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilService;
import de.hsrm.mi.web.projekt.messaging.BackendInfoMessage;
import de.hsrm.mi.web.projekt.messaging.BackendInfoService;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;

@Service
public class GebotServiceImpl implements GebotService {

    @Autowired private GebotRepository gRepository;
    @Autowired private BenutzerprofilService bService;
    @Autowired private BackendInfoService bInfoService;

    @Autowired SimpMessagingTemplate message;

    @Override
    public List<Gebot> findeAlleGebote() {
        return gRepository.findAll(Sort.by("betrag"));
    }

    @Override
    public List<Gebot> findeAlleGeboteFuerAngebot(long angebotid) {
        Optional<Angebot> angebot = bService.findeAngebotMitId(angebotid);
        if(angebot.isPresent()){
            return angebot.get().getGebote();
        }
        return null;
    }

    @Override
    public Gebot bieteFuerAngebot(long benutzerprofilid, long angebotid, long betrag) {
        Optional<Gebot> optGebot = gRepository.findByAngebotIdAndBieterId(angebotid, benutzerprofilid);
        Gebot gebot;
        if(optGebot.isPresent()){
            gebot = optGebot.get();
            gebot.setBetrag(betrag);
            gebot.setGebotzeitpunkt(LocalDateTime.now());
        }
        else{
            gebot = new Gebot();
            gebot.setBetrag(betrag);
            gebot.setGebotzeitpunkt(LocalDateTime.now());
            gebot.setAngebot(bService.findeAngebotMitId(angebotid).get());
            gebot.setGebieter(bService.holeBenutzerProfilMitId(benutzerprofilid).get());
        }
        gebot = gRepository.save(gebot);
        bService.holeBenutzerProfilMitId(benutzerprofilid).get().getGebote().add(gebot);
        bService.findeAngebotMitId(angebotid).get().getGebote().add(gebot);
        bService.speichereBenutzerProfil(bService.holeBenutzerProfilMitId(benutzerprofilid).get());
        
        GetGebotResponseDTO gebotDTO = GetGebotResponseDTO.from(gebot);
        message.convertAndSend("/topic/gebot" + gebotDTO.angebotid(), gebotDTO);
        
        return gebot;
    }

    @Override
    public void loescheGebot(long gebotid) {
        Gebot gebot = gRepository.getById(gebotid);

        BenutzerProfil profil = gebot.getGebieter();
        profil.getGebote().remove(gebot);
        
        Angebot angebot = gebot.getAngebot();
        angebot.getGebote().remove(gebot);

        gRepository.delete(gebot);
                
    }
    
}
