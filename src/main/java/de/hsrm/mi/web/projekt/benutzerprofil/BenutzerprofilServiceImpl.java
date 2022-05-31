package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.angebot.AngebotRepository;
import de.hsrm.mi.web.projekt.geo.AdressInfo;
import de.hsrm.mi.web.projekt.geo.GeoServiceImpl;

@Service
public class BenutzerprofilServiceImpl implements BenutzerprofilService {
        
    @Autowired private BenutzerprofilRepository bRepository;
    @Autowired private GeoServiceImpl gServiceImpl;
    @Autowired private AngebotRepository aRepository;

    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);

    @Transactional
    @Override
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        logger.info("User will be saved.");
        List<AdressInfo> adressListe = gServiceImpl.findeAdressInfo(bp.getAdresse());
        if(!adressListe.isEmpty()){
            bp.setLat(adressListe.get(0).lat());
            bp.setLon(adressListe.get(0).lon());
        }
        else{
            bp.setLat(0);
            bp.setLon(0);
        }
        return bRepository.save(bp);
    }

    @Override
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        logger.info("Get user with id.");
        return bRepository.findById(id);
    }

    @Override
    public List<BenutzerProfil> alleBenutzerProfile() {
        logger.info("User will be deleted.");
        return bRepository.findAll(Sort.by("name"));
    }

    @Override
    public void loescheBenutzerProfilMitId(Long loesch) {
        logger.warn("User will be deleted.");
        bRepository.deleteById(loesch);   
    }

    @Override
    public void fuegeAngebotHinzu(long id, Angebot angebot) {
        if(angebot.getAbholort() == null || angebot.getAbholort() == ""){
            angebot.setLat(0);
            angebot.setLon(0);
        }
        List<AdressInfo> adressListe = gServiceImpl.findeAdressInfo(angebot.getAbholort());
        angebot.setLat(adressListe.get(0).lat());
        angebot.setLon(adressListe.get(0).lon());

        Optional<BenutzerProfil> benutzer = holeBenutzerProfilMitId(id);        
        angebot.setAnbieter(benutzer.get());
        benutzer.get().getAngebote().add(angebot);
    }

    @Transactional
    @Override
    public void loescheAngebot(long id) {

        Optional<Angebot> optAngebot = aRepository.findById(id);       
        if(optAngebot.isPresent()){
            Angebot angebot = optAngebot.get();
            Optional<BenutzerProfil> optBenutzer = holeBenutzerProfilMitId(angebot.getAnbieter().getId());
            
            if(optBenutzer.isPresent()){
                BenutzerProfil benutzer = optBenutzer.get();
                benutzer.getAngebote().remove(angebot);
            }
            else{
                logger.warn("There is no Offer to delete"); 
            }
            aRepository.deleteById(id);
        }        
    }

    @Override
    public List<Angebot> alleAngebote() {
        return aRepository.findAll();
    }
    @Override
    public Optional<Angebot> findeAngebotMitId(long angebotid) {
        return aRepository.findById(angebotid);
    }    
}
